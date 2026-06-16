package com.example.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.SmartStoreApplication
import com.example.data.AccountRole
import com.example.data.LocalAccount
import com.example.data.Phone
import com.example.data.PhoneDataProvider
import com.example.data.SparePartsDataProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    object Catalog : Screen()
    object Cart : Screen()
    object Checkout : Screen()
    object Profile : Screen()
}

data class SavedCard(
    val number: String,
    val holder: String,
    val expiry: String,
    val cvv: String
)

data class PaymentSuccessDetails(
    val amount: Int,
    val cardLast4: String,
    val orderId: String
)

class ShopViewModel : ViewModel() {

    // Текущий вошедший аккаунт
    private val _currentAccount = MutableStateFlow<LocalAccount?>(null)
    val currentAccount: StateFlow<LocalAccount?> = _currentAccount.asStateFlow()

    // Зарегистрированные локальные аккаунты
    private val _registeredAccounts = MutableStateFlow<List<LocalAccount>>(emptyList())
    val registeredAccountsList: StateFlow<List<LocalAccount>> = _registeredAccounts.asStateFlow()

    // Кастомные телефоны, созданные разработчиками
    private val _customPhones = MutableStateFlow<List<Phone>>(emptyList())
    val customPhones: StateFlow<List<Phone>> = _customPhones.asStateFlow()

    // Динамический список марок (фирм) с учетом всех добавленных и встроенных брендов
    val brandFilterList: StateFlow<List<String>> = combine(
        _customPhones,
        _currentAccount
    ) { customList, account ->
        val baseList = PhoneDataProvider.phonesList + customList
        val existingBrands = baseList.filter { !it.isSparePart }.map { it.brand.uppercase().trim() }.distinct().sorted()
        val list = mutableListOf<String>()
        list.add("Все")
        list.addAll(existingBrands)
        if (account?.role == AccountRole.BUSINESS) {
            list.add("Запчасти")
        }
        list
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf("Все", "SAMSUNG", "APPLE", "XIAOMI", "GOOGLE", "POCO", "REALME", "OPPO", "VIVO", "MOTOROLA", "TECNO", "INFINIX")
    )

    // Фильтрация и поиск
    private val _selectedBrand = MutableStateFlow("Все")
    val selectedBrand: StateFlow<String> = _selectedBrand.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Навигация
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Catalog)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Детализация выбранного телефона (Bottom Sheet / Detail Dialog)
    private val _selectedPhone = MutableStateFlow<Phone?>(null)
    val selectedPhone: StateFlow<Phone?> = _selectedPhone.asStateFlow()

    // Корзина в памяти приложения: Phone -> Количество
    private val _cartItems = MutableStateFlow<Map<Phone, Int>>(emptyMap())
    val cartItems: StateFlow<Map<Phone, Int>> = _cartItems.asStateFlow()

    // Общая стоимость корзины
    private val _cartTotal = MutableStateFlow(0)
    val cartTotal: StateFlow<Int> = _cartTotal.asStateFlow()

    // Вводы для страницы оформления / оплаты
    private val _cardNumber = MutableStateFlow("")
    val cardNumber: StateFlow<String> = _cardNumber.asStateFlow()

    private val _cardHolder = MutableStateFlow("")
    val cardHolder: StateFlow<String> = _cardHolder.asStateFlow()

    private val _cardExpiry = MutableStateFlow("")
    val cardExpiry: StateFlow<String> = _cardExpiry.asStateFlow()

    private val _cardCvv = MutableStateFlow("")
    val cardCvv: StateFlow<String> = _cardCvv.asStateFlow()

    // Опция сохранения способа оплаты (чекбокс)
    private val _saveCardChecked = MutableStateFlow(true)
    val saveCardChecked: StateFlow<Boolean> = _saveCardChecked.asStateFlow()

    // Список сохраненных способов оплаты
    private val _savedCards = MutableStateFlow<List<SavedCard>>(emptyList())
    val savedCards: StateFlow<List<SavedCard>> = _savedCards.asStateFlow()

    // Информация об успешной оплате для выпадающей плашки (Material You)
    private val _paymentSuccess = MutableStateFlow<PaymentSuccessDetails?>(null)
    val paymentSuccess: StateFlow<PaymentSuccessDetails?> = _paymentSuccess.asStateFlow()

    // Ивент вызова тостов в MainActivity
    private val _toastFlow = MutableSharedFlow<String>()
    val toastFlow: SharedFlow<String> = _toastFlow.asSharedFlow()

    private val _selectedPartCategory = MutableStateFlow("Все детали")
    val selectedPartCategory: StateFlow<String> = _selectedPartCategory.asStateFlow()

    fun selectPartCategory(category: String) {
        _selectedPartCategory.value = category
    }

    // Список телефонов с учетом фильтров, поиска, добавленных телефонов и запчастей
    val filteredPhonesList: StateFlow<List<Phone>> = combine(
        _selectedBrand,
        _searchQuery,
        _customPhones,
        _currentAccount,
        _selectedPartCategory
    ) { brand, query, customList, account, partCat ->
        val baseList = PhoneDataProvider.phonesList + customList
        val listToFilter = if (brand == "Запчасти" && account?.role == AccountRole.BUSINESS) {
            SparePartsDataProvider.customPhonesList = customList
            val allParts = SparePartsDataProvider.sparePartsList
            if (partCat == "Все детали") {
                allParts
            } else {
                allParts.filter { part ->
                    when (partCat) {
                        "Платы" -> part.name.contains("плата", ignoreCase = true) || part.name.contains("камер", ignoreCase = true)
                        "Экраны" -> part.name.contains("дисплей", ignoreCase = true) || part.name.contains("экран", ignoreCase = true) || part.name.contains("модуль", ignoreCase = true)
                        "Батареи" -> part.name.contains("аккумулятор", ignoreCase = true) || part.name.contains("батарея", ignoreCase = true) || part.name.contains("акб", ignoreCase = true)
                        "Корпуса" -> part.name.contains("корпус", ignoreCase = true) || part.name.contains("рамка", ignoreCase = true) || part.name.contains("крышка", ignoreCase = true)
                        else -> true
                    }
                }
            }
        } else {
            baseList
        }

        listToFilter.filter { phone ->
            val brandMatch = if (brand == "Все") {
                // "Все" показывает только обычные девайсы, не запчасти
                !phone.isSparePart
            } else if (brand == "Запчасти") {
                phone.isSparePart
            } else {
                phone.brand.equals(brand, ignoreCase = true) && !phone.isSparePart
            }

            val queryMatch = if (query.isBlank()) {
                true
            } else {
                phone.name.contains(query, ignoreCase = true) ||
                        phone.description.contains(query, ignoreCase = true) ||
                        phone.brand.contains(query, ignoreCase = true)
            }
            brandMatch && queryMatch
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        // Подсчет суммы при изменениях в корзине
        viewModelScope.launch {
            _cartItems.collect { cart ->
                val total = cart.entries.sumOf { it.key.price * it.value }
                _cartTotal.value = total
            }
        }
        // Загрузка сохраненных карт
        try {
            _savedCards.value = loadSavedCards()
        } catch (_: Exception) {}

        // Загрузка локальных аккаунтов, кастомных телефонов и сессии
        try {
            _registeredAccounts.value = loadAccountsFromPrefs()
            _customPhones.value = loadCustomPhonesFromPrefs()
            SparePartsDataProvider.customPhonesList = _customPhones.value
            val savedSessionEmail = loadSession()
            if (savedSessionEmail != null) {
                val matched = _registeredAccounts.value.find { it.email.equals(savedSessionEmail, ignoreCase = true) }
                if (matched != null) {
                    _currentAccount.value = matched
                }
            }
        } catch (_: Exception) {}
    }

    // Сохранение и получение способов оплаты из SharedPreferences
    private fun loadSavedCards(): List<SavedCard> {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("saved_cards_pref", Context.MODE_PRIVATE)
        val serialized = prefs.getString("cards", "") ?: ""
        if (serialized.isEmpty()) return emptyList()
        return try {
            serialized.split("|||").mapNotNull { cardStr ->
                val parts = cardStr.split(":::")
                if (parts.size >= 4) {
                    SavedCard(parts[0], parts[1], parts[2], parts[3])
                } else null
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveCardsToPrefs(cards: List<SavedCard>) {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("saved_cards_pref", Context.MODE_PRIVATE)
        val serialized = cards.joinToString("|||") { "${it.number}:::${it.holder}:::${it.expiry}:::${it.cvv}" }
        prefs.edit().putString("cards", serialized).apply()
    }

    fun selectSavedCard(card: SavedCard) {
        _cardNumber.value = card.number
        _cardHolder.value = card.holder
        _cardExpiry.value = card.expiry
        _cardCvv.value = card.cvv
        viewModelScope.launch {
            _toastFlow.emit("Способ оплаты •••• ${card.number.takeLast(4)} выбран")
        }
    }

    fun deleteSavedCard(card: SavedCard) {
        val current = _savedCards.value.filter { it.number != card.number }
        _savedCards.value = current
        saveCardsToPrefs(current)
        viewModelScope.launch {
            _toastFlow.emit("Способ оплаты удален")
        }
    }

    fun toggleSaveCard() {
        _saveCardChecked.value = !_saveCardChecked.value
    }

    fun dismissPaymentSuccess() {
        _paymentSuccess.value = null
    }

    // Изменение фильтров
    fun selectBrand(brand: String) {
        _selectedBrand.value = brand
        if (brand != "Запчасти") {
            _selectedPartCategory.value = "Все детали"
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun viewPhoneDetails(phone: Phone?) {
        _selectedPhone.value = phone
    }

    // Функции корзины
    fun addToCart(phone: Phone) {
        // Проверка входа в аккаунт
        if (_currentAccount.value == null) {
            viewModelScope.launch {
                _toastFlow.emit("Для добавления товара войдите или зарегистрируйтесь!")
            }
            _currentScreen.value = Screen.Profile
            return
        }

        val current = _cartItems.value.toMutableMap()
        current[phone] = (current[phone] ?: 0) + 1
        _cartItems.value = current
        viewModelScope.launch {
            _toastFlow.emit("«${phone.name}» добавлен в корзину")
        }
    }

    fun decreaseQuantity(phone: Phone) {
        val current = _cartItems.value.toMutableMap()
        val count = current[phone] ?: return
        if (count <= 1) {
            current.remove(phone)
        } else {
            current[phone] = count - 1
        }
        _cartItems.value = current
    }

    fun removeFromCart(phone: Phone) {
        val current = _cartItems.value.toMutableMap()
        current.remove(phone)
        _cartItems.value = current
    }

    // ЛОКАЛЬНАЯ АВТОРИЗАЦИЯ И РЕГИСТРАЦИЯ
    fun registerNewAccount(email: String, role: AccountRole, passport: String = "", businessDocs: String = ""): Boolean {
        if (email.isBlank() || !email.contains("@")) {
            viewModelScope.launch { _toastFlow.emit("Введите корректную электронную почту") }
            return false
        }
        val isExist = _registeredAccounts.value.any { it.email.equals(email, ignoreCase = true) }
        if (isExist) {
            viewModelScope.launch { _toastFlow.emit("Аккаунт $email уже существует, выполняем вход") }
            switchAccount(email)
            return true
        }

        // Если это девелопер или бизнес, у нас также могут быть фейковые паспортные данные или доки
        val cleanPassport = if (role == AccountRole.DEVELOPER && passport.isBlank()) "Фейк Паспорт 123456" else passport
        val cleanDocs = if (role == AccountRole.BUSINESS && businessDocs.isBlank()) "Фейк Лицензия Бизнеса 789" else businessDocs

        val newAcc = LocalAccount(email, role, cleanPassport, cleanDocs)
        val updated = _registeredAccounts.value + newAcc
        _registeredAccounts.value = updated
        saveAccountsToPrefs(updated)
        
        _currentAccount.value = newAcc
        saveSession(email)

        viewModelScope.launch {
            val roleName = when(role) {
                AccountRole.REGULAR -> "Обычный"
                AccountRole.DEVELOPER -> "Разработчик"
                AccountRole.BUSINESS -> "Бизнес"
            }
            _toastFlow.emit("Аккаунт создан! Добро пожаловать, статус: $roleName")
        }
        _currentScreen.value = Screen.Catalog
        return true
    }

    fun switchAccount(email: String) {
        val matched = _registeredAccounts.value.find { it.email.equals(email, ignoreCase = true) }
        if (matched != null) {
            _currentAccount.value = matched
            saveSession(email)
            _selectedBrand.value = "Все" // сбрасываем фильтр, так как запчасти могут быть недоступны
            viewModelScope.launch {
                _toastFlow.emit("Переключено на аккаунт: ${matched.email}")
            }
            _currentScreen.value = Screen.Catalog
        }
    }

    fun logout() {
        val oldEmail = _currentAccount.value?.email ?: ""
        _currentAccount.value = null
        saveSession(null)
        _selectedBrand.value = "Все"
        _cartItems.value = emptyMap() // очищаем корзину при выходе
        viewModelScope.launch {
            _toastFlow.emit("Вы вышли из аккаунта $oldEmail")
        }
        _currentScreen.value = Screen.Profile
    }

    // ДОБАВЛЕНИЕ НОВОГО СМАРТФОНА РАЗРАБОТЧИКОМ
    fun createAndSaveCustomPhone(
        brand: String, 
        model: String, 
        description: String, 
        specs: List<Pair<String, String>>, 
        price: Int,
        batteryCapacity: Int,
        memory: String,
        bodyMaterial: String
    ) {
        if (brand.isBlank() || model.isBlank() || description.isBlank() || price <= 0) {
            viewModelScope.launch {
                _toastFlow.emit("Заполните корректно все данные телефона")
            }
            return
        }

        val cleanBrand = brand.trim().uppercase()
        val finalSpecs = specs.filter { it.first.isNotBlank() && it.second.isNotBlank() }.toMutableList()
        if (batteryCapacity > 0) {
            finalSpecs.add("Батарея" to "$batteryCapacity мАч")
        }
        if (memory.isNotBlank()) {
            finalSpecs.add("Память" to memory)
        }
        if (bodyMaterial.isNotBlank()) {
            finalSpecs.add("Материал корпуса" to bodyMaterial)
        }

        val customPhone = Phone(
            id = "custom_${cleanBrand.lowercase()}_${System.currentTimeMillis()}",
            name = "$brand $model",
            brand = cleanBrand,
            price = price,
            description = description,
            rating = 5.0,
            reviewsCount = 1,
            specs = finalSpecs,
            highlightColorHex = listOf("#E53935", "#2196F3", "#009688", "#9C27B0", "#FF9800").random(),
            isSparePart = false,
            batteryCapacity = batteryCapacity,
            memory = memory,
            bodyMaterial = bodyMaterial
        )

        val updated = _customPhones.value + customPhone
        _customPhones.value = updated
        SparePartsDataProvider.customPhonesList = updated
        saveCustomPhonesToPrefs(updated)

        viewModelScope.launch {
            _toastFlow.emit("Смартфон ${customPhone.name} успешно опубликован в каталог!")
        }
        _currentScreen.value = Screen.Catalog
    }

    // Хранение аккаунтов в SharedPreferences
    private fun saveAccountsToPrefs(accounts: List<LocalAccount>) {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("saved_accounts_pref", Context.MODE_PRIVATE)
        val serialized = accounts.joinToString("|||") { "${it.email}:::${it.role.name}:::${it.passport}:::${it.businessDocs}" }
        prefs.edit().putString("accounts", serialized).apply()
    }

    private fun loadAccountsFromPrefs(): List<LocalAccount> {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("saved_accounts_pref", Context.MODE_PRIVATE)
        val serialized = prefs.getString("accounts", "") ?: ""
        if (serialized.isEmpty()) return emptyList()
        return try {
            serialized.split("|||").mapNotNull { accStr ->
                val parts = accStr.split(":::")
                if (parts.size >= 2) {
                    val email = parts[0]
                    val role = AccountRole.valueOf(parts[1])
                    val passport = parts.getOrElse(2) { "" }
                    val docs = parts.getOrElse(3) { "" }
                    LocalAccount(email, role, passport, docs)
                } else null
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Хранение кастомных телефонов
    private fun saveCustomPhonesToPrefs(phones: List<Phone>) {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("custom_phones_pref", Context.MODE_PRIVATE)
        val serialized = phones.joinToString("[PHONE_SEP]") { phone ->
            val specsStr = phone.specs.joinToString("[SPEC_SEP]") { "${it.first}[PAIR_SEP]${it.second}" }
            "${phone.id}###${phone.name}###${phone.brand}###${phone.price}###${phone.description}###${phone.rating}###${phone.reviewsCount}###$specsStr###${phone.highlightColorHex}###${phone.isSparePart}###${phone.batteryCapacity}###${phone.memory}###${phone.bodyMaterial}"
        }
        prefs.edit().putString("phones", serialized).apply()
    }

    private fun loadCustomPhonesFromPrefs(): List<Phone> {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("custom_phones_pref", Context.MODE_PRIVATE)
        val serialized = prefs.getString("phones", "") ?: ""
        if (serialized.isEmpty()) return emptyList()
        return try {
            serialized.split("[PHONE_SEP]").mapNotNull { phoneStr ->
                val parts = phoneStr.split("###")
                if (parts.size >= 9) {
                    val id = parts[0]
                    val name = parts[1]
                    val brand = parts[2]
                    val price = parts[3].toIntOrNull() ?: 0
                    val desc = parts[4]
                    val rating = parts[5].toDoubleOrNull() ?: 5.0
                    val reviews = parts[6].toIntOrNull() ?: 1
                    val specsStr = parts[7]
                    val specs = if (specsStr.isEmpty()) emptyList() else {
                        specsStr.split("[SPEC_SEP]").mapNotNull { specPair ->
                            val specParts = specPair.split("[PAIR_SEP]")
                            if (specParts.size == 2) {
                                specParts[0] to specParts[1]
                            } else null
                        }
                    }
                    val color = parts[8]
                    val isSpare = parts.getOrNull(9)?.toBoolean() ?: false
                    val battery = parts.getOrNull(10)?.toIntOrNull() ?: 5000
                    val memory = parts.getOrNull(11) ?: "12/256 ГБ"
                    val material = parts.getOrNull(12) ?: "Авиационный алюминий"
                    Phone(id, name, brand, price, desc, rating, reviews, specs, color, isSpare, battery, memory, material)
                } else null
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Сохранение сессии
    private fun saveSession(email: String?) {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("active_session_pref", Context.MODE_PRIVATE)
        prefs.edit().putString("active_email", email ?: "").apply()
    }

    private fun loadSession(): String? {
        val prefs = SmartStoreApplication.instance.getSharedPreferences("active_session_pref", Context.MODE_PRIVATE)
        val email = prefs.getString("active_email", "") ?: ""
        return if (email.isEmpty()) null else email
    }

    // Функции оплаты
    fun updateCardNumber(number: String) {
        val cleanNumber = number.filter { it.isDigit() }.take(16)
        _cardNumber.value = cleanNumber
    }

    fun updateCardHolder(holder: String) {
        _cardHolder.value = holder.uppercase().filter { it.isLetter() || it == ' ' }
    }

    fun updateCardExpiry(expiry: String) {
        val cleanExpiry = expiry.filter { it.isDigit() }.take(4)
        _cardExpiry.value = cleanExpiry
    }

    fun updateCardCvv(cvv: String) {
        val cleanCvv = cvv.filter { it.isDigit() }.take(3)
        _cardCvv.value = cleanCvv
    }

    fun payOrder() {
        // Блокировка оплаты, если не авторизован
        if (_currentAccount.value == null) {
            viewModelScope.launch {
                _toastFlow.emit("Прежде чем оплачивать, необходимо войти в аккаунт!")
            }
            _currentScreen.value = Screen.Profile
            return
        }

        val num = _cardNumber.value
        val expiry = _cardExpiry.value
        val cvv = _cardCvv.value
        val holder = _cardHolder.value

        if (num.length < 16) {
            viewModelScope.launch {
                _toastFlow.emit("Введите корректный номер карты (16 цифр)")
            }
            return
        }
        if (expiry.length < 4) {
            viewModelScope.launch {
                _toastFlow.emit("Введите срок действия карты (ММ/ГГ)")
            }
            return
        }
        if (cvv.length < 3) {
            viewModelScope.launch {
                _toastFlow.emit("Введите трёхзначный CVV код")
            }
            return
        }
        if (holder.isBlank()) {
            viewModelScope.launch {
                _toastFlow.emit("Введите имя держателя карты")
            }
            return
        }

        if (_saveCardChecked.value) {
            val exists = _savedCards.value.any { it.number == num }
            if (!exists) {
                val updated = _savedCards.value + SavedCard(num, holder, expiry, cvv)
                _savedCards.value = updated
                saveCardsToPrefs(updated)
            }
        }

        val totalAmount = _cartTotal.value
        val last4 = num.takeLast(4)

        viewModelScope.launch {
            _toastFlow.emit("Оплата прошла успешно! Ваш заказ оформлен")
            
            val orderId = "SS-${(100000..999999).random()}"
            _paymentSuccess.value = PaymentSuccessDetails(amount = totalAmount, cardLast4 = last4, orderId = orderId)

            _cartItems.value = emptyMap()
            _cardNumber.value = ""
            _cardExpiry.value = ""
            _cardCvv.value = ""
            _cardHolder.value = ""
        }
    }
}
