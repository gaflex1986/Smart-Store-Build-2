package com.example.data

object SparePartsDataProvider {
    var customPhonesList: List<Phone> = emptyList()

    val sparePartsList: List<Phone>
        get() {
            val allPhones = PhoneDataProvider.phonesList + customPhonesList
            return allPhones.flatMap { phone ->
                generatePartsForPhone(phone)
            }
        }

    private fun generatePartsForPhone(phone: Phone): List<Phone> {
        val parts = mutableListOf<Phone>()

        // Helper parsers to extract specs precisely
        val cpu = phone.specs.find { it.first.equals("процессор", ignoreCase = true) || it.first.contains("чип", ignoreCase = true) }?.second
            ?: "Высокопроизводительный оригинальный процессор"
            
        val display = phone.specs.find { it.first.equals("экран", ignoreCase = true) || it.first.contains("дисплей", ignoreCase = true) || it.first.contains("матриц", ignoreCase = true) }?.second
            ?: "6.7\" Оригинальный AMOLED / OLED Ретина-класс модуль"
            
        val camera = phone.specs.find { it.first.equals("камеры", ignoreCase = true) || it.first.equals("камера", ignoreCase = true) || it.first.contains("оптик", ignoreCase = true) }?.second
            ?: "Многомодульная оригинальная система камер с автофокусом"

        val batteryCap = if (phone.batteryCapacity > 0) "${phone.batteryCapacity} мАч" else {
            phone.specs.find { it.first.equals("батарея", ignoreCase = true) || it.first.contains("аккум", ignoreCase = true) }?.second?.let {
                it
            } ?: "5000 мАч"
        }

        val material = phone.bodyMaterial.ifBlank {
            phone.specs.find { it.first.contains("корпус", ignoreCase = true) || it.first.contains("особен", ignoreCase = true) || it.first.contains("защит", ignoreCase = true) || it.first.contains("материал", ignoreCase = true) }?.second
                ?: "Авиационный алюминиевый сплав"
        }

        val basePrice = phone.price

        // 1. Motherboard (40% price)
        parts.add(
            Phone(
                id = "part_board_${phone.id}",
                name = "Материнская плата для ${phone.name}",
                brand = phone.brand,
                price = (basePrice * 0.40).toInt().coerceAtLeast(100),
                description = "Оригинальная системная плата высокой надежности для смартфона ${phone.name}. Несет на себе интегрированный чипсет, модем связи и контроллеры питания.",
                rating = phone.rating,
                reviewsCount = (phone.reviewsCount / 3).coerceAtLeast(1),
                specs = listOf(
                    "Тип узла" to "Системная плата",
                    "Процессор" to cpu,
                    "Память" to phone.memory,
                    "Совместимость" to phone.name,
                    "Гарантия" to "12 месяцев",
                    "Стандарт" to "Оригинал Monet Safe"
                ),
                highlightColorHex = phone.highlightColorHex,
                isSparePart = true,
                batteryCapacity = phone.batteryCapacity,
                memory = phone.memory,
                bodyMaterial = phone.bodyMaterial
            )
        )

        // 2. Display module (30% price)
        parts.add(
            Phone(
                id = "part_display_${phone.id}",
                name = "Дисплейный модуль для ${phone.name}",
                brand = phone.brand,
                price = (basePrice * 0.30).toInt().coerceAtLeast(100),
                description = "Оригинальный сенсорный экран в сборе с рамкой и защитным стеклом высокой прочности для ${phone.name}. Полный спектр цветов, поддержка заводской герцовки.",
                rating = phone.rating,
                reviewsCount = (phone.reviewsCount / 4).coerceAtLeast(1),
                specs = listOf(
                    "Тип узла" to "Дисплейный модуль в сборе",
                    "Характеристики экрана" to display,
                    "Совместимость" to phone.name,
                    "Защитное покрытие" to "Олеофобный слой Gorilla Glass",
                    "Стандарт" to "Оригинал Monet Safe"
                ),
                highlightColorHex = phone.highlightColorHex,
                isSparePart = true,
                batteryCapacity = phone.batteryCapacity,
                memory = phone.memory,
                bodyMaterial = phone.bodyMaterial
            )
        )

        // 3. Cameras Block (10% price)
        parts.add(
            Phone(
                id = "part_camera_${phone.id}",
                name = "Оригинальный блок камер для ${phone.name}",
                brand = phone.brand,
                price = (basePrice * 0.10).toInt().coerceAtLeast(100),
                description = "Оригинальный блок камер в сборе с датчиками автофокуса и линзами оптической стабилизации для ${phone.name}. Позволяет полностью вернуть заводское качество съемки.",
                rating = phone.rating,
                reviewsCount = (phone.reviewsCount / 5).coerceAtLeast(1),
                specs = listOf(
                    "Тип узла" to "Блок камер в сборе",
                    "Характеристики сенсоров" to camera,
                    "Совместимость" to phone.name,
                    "Стабилизация" to "Заводская оптическая OIS",
                    "Стандарт" to "Оригинал Monet Safe"
                ),
                highlightColorHex = phone.highlightColorHex,
                isSparePart = true,
                batteryCapacity = phone.batteryCapacity,
                memory = phone.memory,
                bodyMaterial = phone.bodyMaterial
            )
        )

        // 4. Battery (10% price)
        parts.add(
            Phone(
                id = "part_battery_${phone.id}",
                name = "Энергетический аккумулятор для ${phone.name}",
                brand = phone.brand,
                price = (basePrice * 0.10).toInt().coerceAtLeast(100),
                description = "Оригинальный накопительный аккумулятор повышенной плотности емкостью $batteryCap для ${phone.name}. Снабжен чипсетом аварийной защиты от нагрева и перезаряда.",
                rating = phone.rating,
                reviewsCount = (phone.reviewsCount * 0.4).toInt().coerceAtLeast(1),
                specs = listOf(
                    "Тип узла" to "Аккумуляторная батарея (АКБ)",
                    "Номинальная емкость" to batteryCap,
                    "Совместимость" to phone.name,
                    "Технология" to "Кремний-углеродная / Li-Polymer",
                    "Стандарт" to "Оригинал Monet Safe"
                ),
                highlightColorHex = phone.highlightColorHex,
                isSparePart = true,
                batteryCapacity = phone.batteryCapacity,
                memory = phone.memory,
                bodyMaterial = phone.bodyMaterial
            )
        )

        // 5. Casing/Housing (10% price)
        parts.add(
            Phone(
                id = "part_housing_${phone.id}",
                name = "Элементы корпуса для ${phone.name}",
                brand = phone.brand,
                price = (basePrice * 0.10).toInt().coerceAtLeast(100),
                description = "Комплект внешних элементов (заднее стекло / рамка / лотки SIM / металлический обод) для смартфона ${phone.name}. Изготовлен из оригинального материала: $material.",
                rating = phone.rating,
                reviewsCount = (phone.reviewsCount / 6).coerceAtLeast(1),
                specs = listOf(
                    "Тип узла" to "Корпусные детали и рама",
                    "Используемый материал" to material,
                    "Совместимость" to phone.name,
                    "Герметизация" to "Пыле-влагозащитный контур",
                    "Стандарт" to "Оригинал Monet Safe"
                ),
                highlightColorHex = phone.highlightColorHex,
                isSparePart = true,
                batteryCapacity = phone.batteryCapacity,
                memory = phone.memory,
                bodyMaterial = phone.bodyMaterial
            )
        )

        return parts
    }
}
