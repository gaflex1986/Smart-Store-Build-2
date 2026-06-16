package com.example.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import com.example.data.Phone
import com.example.data.AccountRole
import com.example.data.LocalAccount
import com.example.data.SparePartsDataProvider
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.composed
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput

// Форматирование цены: 114880 -> "114 880 грн"
fun formatPrice(price: Int): String {
    return "${"%,d".format(price).replace(',', ' ')} грн"
}

// Пружинный тактильный отклик при нажатии на интерактивные элементы в стиле Material 3 Expressive
fun Modifier.bounceClick(): Modifier = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bounceScale"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    try {
                        awaitRelease()
                    } finally {
                        isPressed = false
                    }
                }
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopAppScreen(viewModel: ShopViewModel) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()
    val selectedPhone by viewModel.selectedPhone.collectAsState()
    val paymentSuccess by viewModel.paymentSuccess.collectAsState()

    // Слушатель всплывающих окон (Toast)
    LaunchedEffect(key1 = Unit) {
        viewModel.toastFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isTablet = maxWidth >= 600.dp
        val cartCount = cartItems.values.sum()

        Scaffold(
            bottomBar = {
                if (!isTablet) {
                    ShopBottomNavigationBar(
                        currentScreen = currentScreen,
                        cartCount = cartCount,
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (isTablet) innerPadding else innerPadding)
            ) {
                if (isTablet) {
                    ShopNavigationRail(
                        currentScreen = currentScreen,
                        cartCount = cartCount,
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    // Ограничиваем контент по центру на широких дисплеях для идеального баланса пустоты
                    val contentModifier = if (isTablet) {
                        Modifier
                            .fillMaxSize()
                            .widthIn(max = 1100.dp)
                            .align(Alignment.TopCenter)
                            .padding(24.dp)
                    } else {
                        Modifier.fillMaxSize()
                    }

                    Box(modifier = contentModifier) {
                        when (currentScreen) {
                            is Screen.Catalog -> CatalogTabScreen(viewModel = viewModel)
                            is Screen.Cart -> CartTabScreen(viewModel = viewModel)
                            is Screen.Checkout -> CheckoutTabScreen(viewModel = viewModel)
                            is Screen.Profile -> ProfileTabScreen(viewModel = viewModel)
                        }
                    }

                    // Модальное окно с характеристиками (BottomSheet)
                    if (selectedPhone != null) {
                        PhoneDetailsBottomSheet(
                            phone = selectedPhone!!,
                            onDismiss = { viewModel.viewPhoneDetails(null) },
                            onAddToCart = {
                                viewModel.addToCart(it)
                                viewModel.viewPhoneDetails(null)
                            }
                        )
                    }
                }
            }
        }

        // Выпадающая плашка успешной оплаты в стиле M3 Expressive
        AnimatedVisibility(
            visible = paymentSuccess != null,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
                .zIndex(10f)
        ) {
            paymentSuccess?.let { details ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    border = BorderStroke(3.dp, MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(topStart = 28.dp, bottomEnd = 28.dp, topEnd = 6.dp, bottomStart = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth(if (isTablet) 0.6f else 1f)
                        .clickable { viewModel.dismissPaymentSuccess() }
                        .testTag("payment_success_banner")
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.tertiary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Транзакция Monet Pay одобрена!",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    letterSpacing = (-0.5).sp
                                )
                            )
                            Text(
                                text = "Оплачено: ${formatPrice(details.amount)} • Карта: •••• ${details.cardLast4}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = "Чек: MPT-${details.orderId}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f),
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        IconButton(
                            onClick = { viewModel.dismissPaymentSuccess() },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.1f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Закрыть",
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }

                // Автоматическое скрытие плашки через 6 секунд
                LaunchedEffect(details) {
                    kotlinx.coroutines.delay(6000)
                    viewModel.dismissPaymentSuccess()
                }
            }
        }
    }
}

@Composable
fun ShopNavigationRail(
    currentScreen: Screen,
    cartCount: Int,
    onNavigate: (Screen) -> Unit
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        header = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PhoneAndroid,
                        contentDescription = "SmartStore Logo",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "MONET",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        modifier = Modifier.fillMaxHeight().border(width = (1.5).dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        NavigationRailItem(
            selected = currentScreen is Screen.Catalog,
            onClick = { onNavigate(Screen.Catalog) },
            icon = {
                Icon(
                    imageVector = if (currentScreen is Screen.Catalog) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Каталог"
                )
            },
            label = { Text("Каталог", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_catalog").bounceClick().padding(vertical = 4.dp)
        )
        NavigationRailItem(
            selected = currentScreen is Screen.Cart,
            onClick = { onNavigate(Screen.Cart) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartCount > 0) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error,
                                modifier = Modifier.offset(y = (-4).dp, x = 4.dp)
                            ) {
                                Text(cartCount.toString(), color = MaterialTheme.colorScheme.onError, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (currentScreen is Screen.Cart) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                        contentDescription = "Корзина"
                    )
                }
            },
            label = { Text("Корзина", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_cart").bounceClick().padding(vertical = 4.dp)
        )
        NavigationRailItem(
            selected = currentScreen is Screen.Checkout,
            onClick = { onNavigate(Screen.Checkout) },
            icon = {
                Icon(
                    imageVector = if (currentScreen is Screen.Checkout) Icons.Filled.CreditCard else Icons.Outlined.CreditCard,
                    contentDescription = "Оплата"
                )
            },
            label = { Text("Оплата", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_checkout").bounceClick().padding(vertical = 4.dp)
        )
        NavigationRailItem(
            selected = currentScreen is Screen.Profile,
            onClick = { onNavigate(Screen.Profile) },
            icon = {
                Icon(
                    imageVector = if (currentScreen is Screen.Profile) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Профиль"
                )
            },
            label = { Text("Профиль", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_profile").bounceClick().padding(vertical = 4.dp)
        )
    }
}

@Composable
fun ShopBottomNavigationBar(
    currentScreen: Screen,
    cartCount: Int,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier.border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
    ) {
        NavigationBarItem(
            selected = currentScreen is Screen.Catalog,
            onClick = { onNavigate(Screen.Catalog) },
            icon = {
                Icon(
                    imageVector = if (currentScreen is Screen.Catalog) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Каталог"
                )
            },
            label = { Text("Каталог", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_catalog").bounceClick()
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Cart,
            onClick = { onNavigate(Screen.Cart) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartCount > 0) {
                            Badge(containerColor = MaterialTheme.colorScheme.error) {
                                Text(cartCount.toString(), color = MaterialTheme.colorScheme.onError, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (currentScreen is Screen.Cart) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                        contentDescription = "Корзина"
                    )
                }
            },
            label = { Text("Корзина", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_cart").bounceClick()
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Checkout,
            onClick = { onNavigate(Screen.Checkout) },
            icon = {
                Icon(
                    imageVector = if (currentScreen is Screen.Checkout) Icons.Filled.CreditCard else Icons.Outlined.CreditCard,
                    contentDescription = "Оплата"
                )
            },
            label = { Text("Оплата", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_checkout").bounceClick()
        )
        NavigationBarItem(
            selected = currentScreen is Screen.Profile,
            onClick = { onNavigate(Screen.Profile) },
            icon = {
                Icon(
                    imageVector = if (currentScreen is Screen.Profile) Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Профиль"
                )
            },
            label = { Text("Профиль", fontWeight = FontWeight.Bold) },
            modifier = Modifier.testTag("nav_profile").bounceClick()
        )
    }
}

// ---------------------- 1. Экран КАТАЛОГА ----------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogTabScreen(viewModel: ShopViewModel) {
    val phonesList by viewModel.filteredPhonesList.collectAsState()
    val selectedBrand by viewModel.selectedBrand.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useGrid = maxWidth >= 720.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Заголовок в стиле Material 3 Expressive (Очень жирный, сбитый трендовый шрифт)
            Text(
                text = "SmartStore Expressive",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1.5).sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = "Ваш премиальный гид по умным технологиям",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = (-0.2).sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Поле поиска в превосходном стиле Material 3 Expressive (Капсула, без границ, с приятным тональным выделением)
            TextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text("Поиск лучших моделей...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.updateSearchQuery("")
                            keyboardController?.hide()
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Очистить поиск")
                        }
                    }
                },
                singleLine = true,
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Горизонтальный список фильтров брендов в виде таблеток-капсул M3 Expressive без рамок и обводок
            val brands by viewModel.brandFilterList.collectAsState()
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(brands) { brand ->
                    val isSelected = selectedBrand == brand
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectBrand(brand) },
                        label = { Text(brand, fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold) },
                        leadingIcon = if (isSelected) {
                            { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        border = null,
                        shape = CircleShape,
                        modifier = Modifier.testTag("chip_$brand").bounceClick()
                    )
                }
            }

            if (selectedBrand == "Запчасти") {
                val selectedPartCategory by viewModel.selectedPartCategory.collectAsState()
                val partCategories = listOf("Все детали", "Платы", "Экраны", "Батареи", "Корпуса")
                
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "⚙️ Категории оригинальных узлов:",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                )
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    items(partCategories) { cat ->
                        val isCatSelected = selectedPartCategory == cat
                        FilterChip(
                            selected = isCatSelected,
                            onClick = { viewModel.selectPartCategory(cat) },
                            label = { Text(cat, fontWeight = if (isCatSelected) FontWeight.ExtraBold else FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            border = null,
                            shape = CircleShape,
                            modifier = Modifier.testTag("part_category_$cat").bounceClick()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Результаты каталога
            if (phonesList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = "Товары не найдены",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Попробуйте изменить поисковый запрос или выбрать другой бренд",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp)
                        )
                    }
                }
            } else {
                if (useGrid) {
                    // Используем адаптивное сеточное расположение на широких дисплеях (планшетах/развернутых foldable)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .testTag("phones_list")
                    ) {
                        items(phonesList) { phone ->
                            PhoneCard(
                                phone = phone,
                                onDetailClick = { viewModel.viewPhoneDetails(phone) },
                                onAddToCart = { viewModel.addToCart(phone) }
                            )
                        }
                    }
                } else {
                    // Обычный список на мобильных экранах
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .testTag("phones_list")
                    ) {
                        items(phonesList) { phone ->
                            PhoneCard(
                                phone = phone,
                                onDetailClick = { viewModel.viewPhoneDetails(phone) },
                                onAddToCart = { viewModel.addToCart(phone) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Карточка телефона в каталоге с экспрессивным асимметричным дизайном и Canvas-узором
@Composable
fun PhoneCard(
    phone: Phone,
    onDetailClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val highlightColor = remember(phone.highlightColorHex) {
        try {
            Color(android.graphics.Color.parseColor(phone.highlightColorHex))
        } catch (_: Exception) {
            Color.Gray
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("phone_card_${phone.id}"),
        shape = if (phone.isSparePart) RoundedCornerShape(24.dp) else RoundedCornerShape(28.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.bounceClick().clickable { onDetailClick() }) {
            // Креативный геометрический верхний блок на Canvas (стиль Google Expressive)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .clip(if (phone.isSparePart) RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp) else RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                    .background(highlightColor.copy(alpha = 0.08f))
            ) {
                // Геометрический арт-узор бренда через Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    
                    // Рисуем круги и экспрессивные пересечения
                    drawCircle(
                        color = highlightColor.copy(alpha = 0.12f),
                        radius = h * 0.75f,
                        center = Offset(w * 0.85f, h * 0.2f)
                    )
                    drawCircle(
                        color = highlightColor.copy(alpha = 0.06f),
                        radius = h * 1.2f,
                        center = Offset(w * 0.1f, h * 0.9f)
                    )
                    // Трендовая наклонная линия
                    drawLine(
                        color = highlightColor.copy(alpha = 0.18f),
                        start = Offset(0f, h * 0.5f),
                        end = Offset(w, h * 0.8f),
                        strokeWidth = 3.dp.toPx()
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = highlightColor.copy(alpha = 0.15f),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (phone.isSparePart) Icons.Filled.Build else Icons.Filled.PhoneAndroid,
                                    contentDescription = null,
                                    tint = highlightColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            // Фоновый бейдж бренда
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = highlightColor.copy(alpha = 0.2f),
                                    modifier = Modifier.padding(bottom = 2.dp)
                                ) {
                                    Text(
                                        text = "  ${phone.brand}  ",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Black,
                                            color = highlightColor
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                                if (phone.isSparePart) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    ) {
                                        Text(
                                            text = " Оригинал Monet Safe ",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.onTertiary
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = phone.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    letterSpacing = (-0.3).sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Чип рейтинга в Expressive стиле (капсула, без границ)
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Рейтинг",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = phone.rating.toString(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            // Описание и цена
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = phone.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Стоимость",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text(
                                text = "  ${formatPrice(phone.price)}  ",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    letterSpacing = (-0.5).sp
                                ),
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
                            )
                        }
                    }

                    // Кнопка быстрой покупки в стиле M3 Expressive (высота 56.дп, капсула, без границ)
                    Button(
                        onClick = { onAddToCart() },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .bounceClick()
                            .height(56.dp)
                            .testTag("add_to_cart_btn_${phone.id}")
                    ) {
                        Icon(
                            imageVector = if (phone.isSparePart) Icons.Filled.Build else Icons.Filled.ShoppingCart,
                            contentDescription = "Добавить",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (phone.isSparePart) "Закупка в СЦ" else "Купить",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Black
                            )
                        )
                    }
                }
            }
        }
    }
}

// ---------------------- 2. Экран КОРЗИНЫ ----------------------
@Composable
fun CartTabScreen(viewModel: ShopViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок в стиле Material 3 Expressive
        Text(
            text = "Ваша корзина",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = (-1.5).sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )
        
        Text(
            text = "Проверьте выбранные гаджеты перед оплатой",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(54.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "В корзине пока пусто",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Выберите лучшие устройства и оригинальные комплектующие в SmartStore",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedButton(
                        onClick = { viewModel.navigateTo(Screen.Catalog) },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = CircleShape,
                        modifier = Modifier.height(56.dp).bounceClick().testTag("go_to_catalog")
                    ) {
                        Text("Вернуться в каталог", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .testTag("cart_list")
            ) {
                items(cartItems.keys.toList()) { phone ->
                    val quantity = cartItems[phone] ?: 0
                    CartItemRow(
                        phone = phone,
                        quantity = quantity,
                        onAdd = { viewModel.addToCart(phone) },
                        onDecrease = { viewModel.decreaseQuantity(phone) },
                        onDelete = { viewModel.removeFromCart(phone) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val ticketColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            ) {
                val circleRadius = 6.dp.toPx()
                val spacing = 16.dp.toPx()
                val totalWidth = size.width
                
                // Рисуем перфорацию (шашечку вырезанных отверстий в билете)
                var currentX = 0f
                while (currentX < totalWidth) {
                    drawCircle(
                        color = ticketColor,
                        radius = circleRadius,
                        center = Offset(currentX + circleRadius, 6.dp.toPx())
                    )
                    currentX += circleRadius * 2 + spacing
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Итоговый ценник и кнопка перехода на оплату в стиле M3 Expressive (без границ, тональное возвышение)
            Card(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Итоговая сумма",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Включая спецпредложения",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                        Text(
                            text = formatPrice(cartTotal),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = (-1).sp
                            ),
                            modifier = Modifier.testTag("cart_total_price")
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.navigateTo(Screen.Checkout) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .bounceClick()
                            .testTag("proceed_checkout"),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(Icons.Filled.CreditCard, contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Оформить заказ",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = (-0.2).sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    phone: Phone,
    quantity: Int,
    onAdd: () -> Unit,
    onDecrease: () -> Unit,
    onDelete: () -> Unit
) {
    val highlightColor = remember(phone.highlightColorHex) {
        try {
            Color(android.graphics.Color.parseColor(phone.highlightColorHex))
        } catch (_: Exception) {
            Color.Gray
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cart_item_${phone.id}"),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка гаджета слева c фоновым цветом и круглой Expressive формой без жестких рамок
            Surface(
                shape = CircleShape,
                color = highlightColor.copy(alpha = 0.15f),
                modifier = Modifier.size(54.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.PhoneAndroid,
                        contentDescription = null,
                        tint = highlightColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Основная текстовая информация
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = phone.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, letterSpacing = (-0.3).sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = formatPrice(phone.price),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Управление количеством с точными тач-таргетами в M3 Expressive стиле
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                IconButton(
                    onClick = { onDecrease() },
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .bounceClick()
                        .testTag("dec_qty_${phone.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Уменьшить количество",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(28.dp)
                        .testTag("qty_label_${phone.id}")
                )

                IconButton(
                    onClick = { onAdd() },
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .bounceClick()
                        .testTag("inc_qty_${phone.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Увеличить количество",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Кнопка удаления
            IconButton(
                onClick = { onDelete() },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.25f), CircleShape)
                    .size(36.dp)
                    .bounceClick()
                    .testTag("delete_${phone.id}")
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить из корзины",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// Вспомогательный метод для получения дефолтного цвета билета
@Composable
fun highlightColorFromScheme(colorScheme: Any?): Color {
    return MaterialTheme.colorScheme.primary
}

// ---------------------- 3. Карточные маски и Экран ОПЛАТЫ ----------------------

class CardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) text.text.substring(0, 16) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 4 == 3 && i < 15) out += " "
        }

        val creditCardOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return offset
                val spaces = (offset - 1) / 4
                return (offset + spaces).coerceAtMost(out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 0) return offset
                val spaces = offset / 5
                return (offset - spaces).coerceAtMost(trimmed.length)
            }
        }
        return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
    }
}

class ExpiryDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 4) text.text.substring(0, 4) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1 && trimmed.length > 2) out += "/"
        }

        val expiryOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                return (offset + 1).coerceAtMost(out.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                return (offset - 1).coerceAtMost(trimmed.length)
            }
        }
        return TransformedText(AnnotatedString(out), expiryOffsetTranslator)
    }
}

@Composable
fun CheckoutTabScreen(viewModel: ShopViewModel) {
    val cartTotal by viewModel.cartTotal.collectAsState()
    val cardNumber by viewModel.cardNumber.collectAsState()
    val cardHolder by viewModel.cardHolder.collectAsState()
    val cardExpiry by viewModel.cardExpiry.collectAsState()
    val cardCvv by viewModel.cardCvv.collectAsState()
    val savedCards by viewModel.savedCards.collectAsState()
    val saveCardChecked by viewModel.saveCardChecked.collectAsState()
    val paymentSuccess by viewModel.paymentSuccess.collectAsState()

    var isProcessing by remember { mutableStateOf(false) }
    var processingStep by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    // Вспомогательное форматирование вывода номера на карте (например "4111 2222 3333 4444")
    val displayedCardNumber = remember(cardNumber) {
        if (cardNumber.isEmpty()) {
            "•••• •••• •••• ••••"
        } else {
            val padded = cardNumber.padEnd(16, '•')
            buildString {
                for (i in 0 until 16) {
                    append(padded[i])
                    if ((i + 1) % 4 == 0 && i < 15) {
                        append(' ')
                    }
                }
            }
        }
    }

    // Вспомогательное форматирование вывода срока действия (например "12/28")
    val displayedExpiry = remember(cardExpiry) {
        if (cardExpiry.isEmpty()) {
            "ММ/ГГ"
        } else if (cardExpiry.length <= 2) {
            cardExpiry
        } else {
            "${cardExpiry.substring(0, 2)}/${cardExpiry.substring(2)}"
        }
    }

    // Определение бренда платежной системы на основе номера карты
    val cardBrand = remember(cardNumber) {
        when {
            cardNumber.startsWith("4") -> "VISA Secure"
            cardNumber.startsWith("5") -> "Mastercard"
            cardNumber.startsWith("3") -> "Amex Express"
            else -> "Monet Card"
        }
    }

    // Получаем цвета темы для фоновых кругов на карте
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    if (paymentSuccess != null) {
        val details = paymentSuccess!!
        // ЧЕК/ИНВОЙС: Материал 3 Экспрессивный стиль Перфорированного Тикета
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("successful_payment_receipt")
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Асимметричный крутой бейдж успешной оплаты
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Успешно",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Оплачено через Monet Pay!",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = (-0.5).sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Код транзакции авторизован в системе",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Имитация перфорированной линии
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(24) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.outlineVariant,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    // Детали чека
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Поле "Сумма" в рамке
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(28.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "ИТОГО К ВЫПЛАТЕ",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    formatPrice(details.amount),
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Провайдер:", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Monet Pay • Экспресс", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Способ оплаты:", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Card •••• ${details.cardLast4}", style = MaterialTheme.typography.bodyMedium, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Номер чека:", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("MPT-${details.orderId}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.primary)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Уровень защиты:", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("ISO-8583 (AES-256)", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                        }
                    }

                    // Вторая перфорированная линия
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(24) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.outlineVariant,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    Text(
                        text = "Электронная квитанция сохранена в сессии. Нажмите кнопку ниже для перехода к списку товаров.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Асимметричная кнопка выхода
                    Button(
                        onClick = {
                            viewModel.dismissPaymentSuccess()
                            viewModel.navigateTo(Screen.Catalog)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .testTag("dismiss_receipt_button")
                    ) {
                        Text("Вернуться в каталог товаров", fontWeight = FontWeight.Black, fontSize = 16.sp)
                    }
                }
            }
        }
    } else if (isProcessing) {
        // ХОЛДЕР-СПИННЕР: Безопасный шлюз верификации Monet Pay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Экспрессивный спиннер: Тройной бейдж на фоне
                    Box(
                        modifier = Modifier.size(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 6.dp,
                            modifier = Modifier.fillMaxSize()
                        )
                        Icon(
                            imageVector = Icons.Filled.Build,
                            contentDescription = "Обработка",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Monet Pay Core Gateway",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            letterSpacing = 1.sp
                        )

                        val statusText = when (processingStep) {
                            0 -> "Аудит SSL-шифрования..."
                            1 -> "Обфускация реквизитов (AES-256)..."
                            2 -> "Запрос к Monet Pay Core API..."
                            3 -> "Проверка протокола 3D-Secure 2.0..."
                            else -> "Генерация инвойса SmartStore..."
                        }

                        // Экспрессивный цветной пузырь с меняющимся статусом без обводок
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text(
                                text = statusText,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }

                    Text(
                        text = "Вся зашифрованная сессия проходит исключительно внутри изолированного песочного контура устройства.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🚀 MONET SPEED", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Black)
                        Text("•", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        Text("SANDBOX MODE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Monet Pay",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = (-1).sp
                            )
                        )

                        // Экспрессивный чип Monet без обводок с округлостью 28.dp
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text(
                                text = "MONET TOKENS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                    Text(
                        text = "Безопасное проведение транзакций с мгновенной обработкой через процессинговые динамические токены Monet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }

            // ИНТЕРАКТИВНАЯ КАРТА: Стиль Material 3 Expressive (без градиента, асимметричный, с векторным артом)
            item {
                Card(
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .testTag("visual_card_mockup")
                ) {
                    // В качестве фона рисуем экспрессивную геометрическую абстракцию через Canvas
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            // Левый полукруг (третичный цвет)
                            drawCircle(
                                color = tertiaryColor.copy(alpha = 0.15f),
                                radius = 110.dp.toPx(),
                                center = Offset(x = 0f, y = size.height / 2)
                            )
                            // Правый верхний эллипс (первичный цвет)
                            drawCircle(
                                color = primaryColor.copy(alpha = 0.12f),
                                radius = 140.dp.toPx(),
                                center = Offset(x = size.width, y = 0f)
                            )
                            // Центральный фоновый акцент (вторичный цвет)
                            drawCircle(
                                color = secondaryColor.copy(alpha = 0.1f),
                                radius = 70.dp.toPx(),
                                center = Offset(x = size.width * 0.7f, y = size.height * 0.8f)
                            )
                        }

                        // Сам контент карты поверх геометрического арта
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                                    )
                                    Text(
                                        text = "MONET",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Black,
                                            fontFamily = FontFamily.SansSerif,
                                            letterSpacing = 2.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                }

                                // Название бренда платежной системы
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                                    shape = RoundedCornerShape(28.dp)
                                ) {
                                    Text(
                                        text = cardBrand.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 1.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Чип и логотип
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Стильный чип Monet с тонкими линиями без обводок
                                Box(
                                    modifier = Modifier
                                        .width(42.dp)
                                        .height(30.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Тонкая линия чипа
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.5f)
                                            .height(2.dp)
                                            .background(MaterialTheme.colorScheme.primary)
                                    )
                                }

                                Text(
                                    text = "EXPRESSIVE PAY",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                                )
                            }

                            // Номер карты
                            Text(
                                text = displayedCardNumber,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    letterSpacing = 1.sp
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "ДЕРЖАТЕЛЬ КАРТЫ",
                                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        text = if (cardHolder.isBlank()) "IVAN IVANOV" else cardHolder.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "СРОК",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        text = displayedExpiry,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // СОХРАНЕННЫЕ КАРТЫ: в стиле Expressive - Овальные таблетки (Pill-образные кнопки)
            if (savedCards.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Сохранённые методы (Monet Safe)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(savedCards) { card ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                                    ),
                                    shape = RoundedCornerShape(28.dp),
                                    modifier = Modifier
                                        .width(185.dp)
                                        .clickable { viewModel.selectSavedCard(card) }
                                        .testTag("saved_card_${card.number.takeLast(4)}")
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(horizontal = 14.dp, vertical = 10.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.CreditCard,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp),
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = "•• ${card.number.takeLast(4)}",
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                )
                                            }
                                            Text(
                                                text = card.holder.uppercase(),
                                                style = MaterialTheme.typography.labelSmall,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                                            )
                                        }
                                        IconButton(
                                            onClick = { viewModel.deleteSavedCard(card) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Clear,
                                                contentDescription = "Удалить карту",
                                                modifier = Modifier.size(14.dp),
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ПОЛЯ ВВОДА: Стиль Material 3 Expressive (Экстремальные скругления 28.dp и жирные акценты без обводок)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Номер карты
                    TextField(
                        value = cardNumber,
                        onValueChange = { viewModel.updateCardNumber(it) },
                        label = { Text("Номер банковской карты", fontWeight = FontWeight.Bold) },
                        placeholder = { Text("0000 0000 0000 0000") },
                        leadingIcon = { Icon(Icons.Filled.CreditCard, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = CardNumberVisualTransformation(),
                        shape = RoundedCornerShape(28.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("card_number_input")
                    )

                    // Имя держателя (только буквы)
                    TextField(
                        value = cardHolder,
                        onValueChange = { viewModel.updateCardHolder(it) },
                        label = { Text("Имя владельца на латинице", fontWeight = FontWeight.Bold) },
                        placeholder = { Text("IVAN IVANOV") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Characters
                        ),
                        shape = RoundedCornerShape(28.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("card_holder_input")
                    )

                    // Срок действия и CVV
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TextField(
                            value = cardExpiry,
                            onValueChange = { viewModel.updateCardExpiry(it) },
                            label = { Text("Срок (ММ/ГГ)", fontWeight = FontWeight.Bold) },
                            placeholder = { Text("12/28") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = ExpiryDateVisualTransformation(),
                            shape = RoundedCornerShape(28.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("card_expiry_input")
                        )

                        TextField(
                            value = cardCvv,
                            onValueChange = { viewModel.updateCardCvv(it) },
                            label = { Text("CVV2", fontWeight = FontWeight.Bold) },
                            placeholder = { Text("•••") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(28.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("card_cvv_input")
                        )
                    }

                    // Чекбокс сохранения карты (Материал 3 Овальный без обводок)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.toggleSaveCard() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Checkbox(
                                checked = saveCardChecked,
                                onCheckedChange = { viewModel.toggleSaveCard() },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Запомнить карту в Monet Cryptographic Vault",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Кнопка оплаты и Лицензия безопасности
            item {
                Spacer(modifier = Modifier.height(4.dp))

                // Гарантия безопасности Monet без обводок
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("🛡️", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Безопасное соединение Monet Pay Core",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Black),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Узел авторизации осуществляет обработку сквозного шифрования эквайринга по стандартам PCI-DSS. Покупка защищена сквозным блокчейн-протоколом, сохраняя данные в зашифрованном хранилище.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }

                // КНОПКА ОПЛАТЫ: Массивная капсула в Monet стиле
                Button(
                    onClick = {
                        // Блокировка оплаты, если не авторизован (через ViewModel)
                        if (viewModel.currentAccount.value == null) {
                            viewModel.payOrder()  // ViewModel выдаст тост и перенаправит
                            return@Button
                        }

                        // Локальная валидация на шлюзе перед спиннером
                        if (cardNumber.length < 16) {
                            viewModel.updateCardNumber(cardNumber)
                            viewModel.payOrder()
                            return@Button
                        }
                        if (cardExpiry.length < 4) {
                            viewModel.payOrder()
                            return@Button
                        }
                        if (cardCvv.length < 3) {
                            viewModel.payOrder()
                            return@Button
                        }
                        if (cardHolder.isBlank()) {
                            viewModel.payOrder()
                            return@Button
                        }

                        // Запуск красивого экспрессивного шлюза верификации на 3.8 секунд
                        coroutineScope.launch {
                            isProcessing = true
                            processingStep = 0
                            kotlinx.coroutines.delay(800)
                            processingStep = 1
                            kotlinx.coroutines.delay(800)
                            processingStep = 2
                            kotlinx.coroutines.delay(800)
                            processingStep = 3
                            kotlinx.coroutines.delay(800)
                            processingStep = 4
                            kotlinx.coroutines.delay(600)
                            isProcessing = false
                            viewModel.payOrder() // Подтверждает транзакцию, генерирует чек!
                        }
                    },
                    enabled = cartTotal > 0,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier
                        .bounceClick()
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("pay_button")
                ) {
                    Text(
                        text = if (cartTotal > 0) "Оплатить ${formatPrice(cartTotal)} через Monet Pay" else "Корзина пуста",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-0.3).sp
                        )
                    )
                }

                if (cartTotal == 0) {
                    Text(
                        text = "Добавьте товары в корзину перед оплатой в разделе «Каталог»",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

// ---------------------- Детализация товара (ModalBottomSheet) ----------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneDetailsBottomSheet(
    phone: Phone,
    onDismiss: () -> Unit,
    onAddToCart: (Phone) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val highlightColor = remember(phone.highlightColorHex) {
        try {
            Color(android.graphics.Color.parseColor(phone.highlightColorHex))
        } catch (_: Exception) {
            Color.Gray
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = Modifier.testTag("details_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
        ) {
            // Заголовок и марка
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = phone.brand,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = highlightColor
                        )
                    )
                    Text(
                        text = phone.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

                // Кнопка закрытия
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f), CircleShape)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Закрыть",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Оценка и отзывы
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = phone.rating.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${phone.reviewsCount} отзывов покупателей)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Описание телефона
            Text(
                text = "Описание модели",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = phone.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Технические характеристики телефона
            Text(
                text = "Технические спецификации",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Рендер таблицы технических фишек без обводок
            Card(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    phone.specs.forEachIndexed { index, pair ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = pair.first,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = pair.second,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1.5f),
                                textAlign = TextAlign.End
                            )
                        }
                        if (index < phone.specs.lastIndex) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Итоговая стоимость и кнопка заказа
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Стоимость устройства",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatPrice(phone.price),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Button(
                    onClick = { onAddToCart(phone) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .bounceClick()
                        .height(56.dp)
                        .testTag("specs_add_to_cart")
                ) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "В корзину",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTabScreen(viewModel: ShopViewModel) {
    val currentAccount by viewModel.currentAccount.collectAsState()
    val registeredAccounts by viewModel.registeredAccountsList.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Личный кабинет",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1.5).sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = "Управляйте своим счетом и ролями в Monet Pay Club",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (currentAccount == null) {
            // ЛОГИН / РЕГИСТРАЦИЯ (Стиль M3 Expressive, без обводки, с тональным возвышением)
            item {
                Card(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Вход или Регистрация",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, letterSpacing = (-0.5).sp)
                        )
                        val textAlpha = 0.82f
                        Text(
                            text = "Введите абсолютно любую фейковую почту, выберите категорию и войдите в систему.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = textAlpha)
                        )

                        var email by remember { mutableStateOf("") }
                        var role by remember { mutableStateOf(AccountRole.REGULAR) }
                        var passport by remember { mutableStateOf("") }
                        var businessDocs by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Электронная почта", fontWeight = FontWeight.Bold) },
                            placeholder = { Text("any@email.com") },
                            singleLine = true,
                            shape = RoundedCornerShape(28.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("profile_register_email")
                        )

                        Text(
                            text = "Категория аккаунта в системе:",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                        )

                        // Три карточки для выбора роли
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            // 1. Обычный (безграничный, скругленный)
                            Card(
                                onClick = { role = AccountRole.REGULAR },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (role == AccountRole.REGULAR) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                                    }
                                ),
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.fillMaxWidth().bounceClick()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = null,
                                        tint = if (role == AccountRole.REGULAR) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Обычный аккаунт",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Black)
                                        )
                                        Text(
                                            text = "Просмотр каталога, корзины, покупка смартфонов.",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)
                                        )
                                    }
                                }
                            }

                            // 2. Разработчик (безграничный, скругленный)
                            Card(
                                onClick = { role = AccountRole.DEVELOPER },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (role == AccountRole.DEVELOPER) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                                    }
                                ),
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.fillMaxWidth().bounceClick()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Build,
                                        contentDescription = null,
                                        tint = if (role == AccountRole.DEVELOPER) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Аккаунт Разработчика",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Black)
                                        )
                                        Text(
                                            text = "Инструменты публикации личных моделей девайсов в общий каталог.",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)
                                        )
                                    }
                                }
                            }

                            if (role == AccountRole.DEVELOPER) {
                                OutlinedTextField(
                                    value = passport,
                                    onValueChange = { passport = it },
                                    label = { Text("Паспортные данные (произвольно)", fontWeight = FontWeight.Bold) },
                                    placeholder = { Text("Например: ID-918239") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(28.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                        .testTag("profile_register_passport")
                                )
                            }

                            // 3. Бизнес (безграничный, скругленный)
                            Card(
                                onClick = { role = AccountRole.BUSINESS },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (role == AccountRole.BUSINESS) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                                    }
                                ),
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.fillMaxWidth().bounceClick()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Business,
                                        contentDescription = null,
                                        tint = if (role == AccountRole.BUSINESS) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Бизнес аккаунт",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Black)
                                        )
                                        Text(
                                            text = "Заказ запчастей (экраны, GaN зарядки, титаны) и покупка смартфонов.",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)
                                        )
                                    }
                                }
                            }

                            if (role == AccountRole.BUSINESS) {
                                OutlinedTextField(
                                    value = businessDocs,
                                    onValueChange = { businessDocs = it },
                                    label = { Text("Документы бизнеса / Лицензии (произвольно)", fontWeight = FontWeight.Bold) },
                                    placeholder = { Text("Например: ООО ВивоСнаб, Лицензия №441") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(28.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                        .testTag("profile_register_docs")
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Button(
                            onClick = {
                                viewModel.registerNewAccount(
                                    email = email,
                                    role = role,
                                    passport = passport,
                                    businessDocs = businessDocs
                                )
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .bounceClick()
                                .testTag("profile_register_submit")
                        ) {
                            Text("Войти / Зарегистрироваться", fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        } else {
            // ПОЛЬЗОВАТЕЛЬ ВОШЕЛ (Стиль M3 Expressive, без обводки, с тональным возвышением)
            val acc = currentAccount!!
            item {
                Card(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when (acc.role) {
                                            AccountRole.REGULAR -> MaterialTheme.colorScheme.primaryContainer
                                            AccountRole.DEVELOPER -> MaterialTheme.colorScheme.tertiaryContainer
                                            AccountRole.BUSINESS -> MaterialTheme.colorScheme.secondaryContainer
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = acc.email.take(2).uppercase(),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        color = when (acc.role) {
                                            AccountRole.REGULAR -> MaterialTheme.colorScheme.onPrimaryContainer
                                            AccountRole.DEVELOPER -> MaterialTheme.colorScheme.onTertiaryContainer
                                            AccountRole.BUSINESS -> MaterialTheme.colorScheme.onSecondaryContainer
                                        }
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = acc.email,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                                )
                                val roleString = when (acc.role) {
                                    AccountRole.REGULAR -> "Обычный аккаунт"
                                    AccountRole.DEVELOPER -> "Разработчик ПО"
                                    AccountRole.BUSINESS -> "Бизнес партнер"
                                }
                                Text(
                                    text = roleString,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = when (acc.role) {
                                            AccountRole.REGULAR -> MaterialTheme.colorScheme.primary
                                            AccountRole.DEVELOPER -> MaterialTheme.colorScheme.tertiary
                                            AccountRole.BUSINESS -> MaterialTheme.colorScheme.secondary
                                        }
                                    )
                                )
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                        if (acc.role == AccountRole.DEVELOPER) {
                            Text(
                                text = "Паспорт: ${acc.passport}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        } else if (acc.role == AccountRole.BUSINESS) {
                            Text(
                                text = "Лицензии: ${acc.businessDocs}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Text(
                            text = when (acc.role) {
                                AccountRole.REGULAR -> "Вам доступен полный каталог девайсов SmartStore."
                                AccountRole.DEVELOPER -> "Вы можете добавлять собственные марки телефонов в каталог через панель ниже. Любой пользователь сможет их приобрести!"
                                AccountRole.BUSINESS -> "Раздел запчастей разблокирован. Заказывайте оригинальные узлы и запчасти со скидкой!"
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )

                        if (acc.role == AccountRole.BUSINESS) {
                            Button(
                                onClick = {
                                    viewModel.selectBrand("Запчасти")
                                    viewModel.navigateTo(Screen.Catalog)
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .bounceClick()
                                    .testTag("business_to_parts_button")
                            ) {
                                Text("Открыть каталог запчастей", fontWeight = FontWeight.Black)
                            }
                        }

                        OutlinedButton(
                            onClick = { viewModel.logout() },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .bounceClick()
                                .testTag("profile_logout_btn")
                        ) {
                            Icon(Icons.Filled.Clear, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Выйти из аккаунта", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // ИНСТРУМЕНТЫ РАЗРАБОТЧИКА (Стиль M3 Expressive, без обводки, с тональным возвышением)
            if (acc.role == AccountRole.DEVELOPER) {
                item {
                    Card(
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "🛠️ Создать модель смартфона",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = MaterialTheme.colorScheme.tertiary
                            )

                            var dBrand by remember { mutableStateOf("") }
                            var dModel by remember { mutableStateOf("") }
                            var dPrice by remember { mutableStateOf("") }
                            var dDesc by remember { mutableStateOf("") }

                            var specCpu by remember { mutableStateOf("") }
                            var specScreen by remember { mutableStateOf("") }
                            var specCamera by remember { mutableStateOf("") }

                            var batteryCapacityText by remember { mutableStateOf("") }
                            var memoryText by remember { mutableStateOf("") }
                            var bodyMaterialText by remember { mutableStateOf("") }

                            val tfColors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )

                            TextField(
                                value = dBrand,
                                onValueChange = { dBrand = it },
                                label = { Text("Бренд (SAMSUNG, APPLE, XIAOMI...)", fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_brand")
                            )

                            TextField(
                                value = dModel,
                                onValueChange = { dModel = it },
                                label = { Text("Название модели (например: S26 Neo)", fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_model")
                            )

                            TextField(
                                value = dPrice,
                                onValueChange = { dPrice = it.filter { c -> c.isDigit() } },
                                label = { Text("Цена устройства (в грн)", fontWeight = FontWeight.Bold) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_price")
                            )

                            TextField(
                                value = dDesc,
                                onValueChange = { dDesc = it },
                                label = { Text("Описание и особенности", fontWeight = FontWeight.Bold) },
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_desc")
                            )

                            Text(
                                text = "Параметры Smart-модели:",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.tertiary)
                            )

                            TextField(
                                value = specCpu,
                                onValueChange = { specCpu = it },
                                label = { Text("Процессор / Чип (например: Snapdragon 8 Elite)", fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_spec_cpu")
                            )

                            TextField(
                                value = specScreen,
                                onValueChange = { specScreen = it },
                                label = { Text("Экран / Матрица (например: 6.8\" Dynamic AMOLED)", fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_spec_screen")
                            )

                            TextField(
                                value = specCamera,
                                onValueChange = { specCamera = it },
                                label = { Text("Камера / Оптика (например: 200 + 50 + 12 Мп)", fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_spec_camera")
                            )

                            Text(
                                text = "Дополнительные обязательные поля:",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                            )

                            TextField(
                                value = batteryCapacityText,
                                onValueChange = { batteryCapacityText = it.filter { c -> c.isDigit() } },
                                label = { Text("Емкость батареи (в мАч, например: 5000)", fontWeight = FontWeight.Bold) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_battery_capacity")
                            )

                            TextField(
                                value = memoryText,
                                onValueChange = { memoryText = it },
                                label = { Text("Объем памяти (например: 12/256 ГБ)", fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_memory")
                            )

                            TextField(
                                value = bodyMaterialText,
                                onValueChange = { bodyMaterialText = it },
                                label = { Text("Материал корпуса (например: Титан Grade 5)", fontWeight = FontWeight.Bold) },
                                singleLine = true,
                                shape = CircleShape,
                                colors = tfColors,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("create_phone_body_material")
                            )

                            Button(
                                onClick = {
                                    val specs = listOf(
                                        "Процессор" to specCpu,
                                        "Экран" to specScreen,
                                        "Камера" to specCamera
                                    )
                                    viewModel.createAndSaveCustomPhone(
                                        brand = dBrand,
                                        model = dModel,
                                        description = dDesc,
                                        specs = specs,
                                        price = dPrice.toIntOrNull() ?: 0,
                                        batteryCapacity = batteryCapacityText.toIntOrNull() ?: 5000,
                                        memory = memoryText.ifBlank { "12/256 ГБ" },
                                        bodyMaterial = bodyMaterialText.ifBlank { "Авиационный алюминий" }
                                    )
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = MaterialTheme.colorScheme.onTertiary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .bounceClick()
                                    .testTag("create_phone_submit")
                            ) {
                                Text("Опубликовать в общий каталог", fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            }
        }

        // РАЗДЕЛ БЫСТРОГО ПЕРЕКЛЮЧЕНИЯ (Стиль M3 Expressive, без обводки, с тональным возвышением)
        val otherAccounts = registeredAccounts.filter { currentAccount?.email != it.email }
        if (otherAccounts.isNotEmpty()) {
            item {
                Card(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Быстрое переключение профилей",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, letterSpacing = (-0.3).sp)
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            otherAccounts.forEach { account ->
                                Card(
                                    onClick = { viewModel.switchAccount(account.email) },
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .bounceClick()
                                        .testTag("switch_account_${account.email}")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(
                                                text = account.email,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                            )
                                            val badgeText = when (account.role) {
                                                AccountRole.REGULAR -> "Обычный"
                                                AccountRole.DEVELOPER -> "Разработчик"
                                                AccountRole.BUSINESS -> "Бизнес"
                                            }
                                            Text(
                                                text = "Роль в системе: $badgeText",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Icon(
                                            imageVector = Icons.Filled.Person,
                                            contentDescription = "Переключиться",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

