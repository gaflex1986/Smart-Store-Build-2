package com.example.data

data class Phone(
    val id: String,
    val name: String,
    val brand: String,
    val price: Int,    // в грн
    val description: String,
    val rating: Double,
    val reviewsCount: Int,
    val specs: List<Pair<String, String>>,
    val highlightColorHex: String,
    val isSparePart: Boolean = false,
    val batteryCapacity: Int = 5000,
    val memory: String = "12/256 ГБ",
    val bodyMaterial: String = "Авиационный алюминий"
)

object PhoneDataProvider {
    val phonesList: List<Phone> by lazy {
        val list = mutableListOf<Phone>()

        // 1. Samsung Galaxy S26 Ultra
        list.add(
            Phone(
                id = "samsung_s26_ultra",
                name = "Samsung Galaxy S26 Ultra",
                brand = "SAMSUNG",
                price = 64999,
                description = "Новейший ультимативный супер-флагман 2026 года в премиальном титановом корпусе. Оснащен высокопроизводительным процессором Snapdragon 8 Elite, передовой системой искусственного интеллекта Galaxy AI следующего поколения и усовершенствованным ярким экраном Dynamic AMOLED 2X с антибликовым напылением Gorilla Armor.",
                rating = 4.9,
                reviewsCount = 142,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8 Elite (3 нм, до 4.32 ГГц)",
                    "Экран" to "6.8\" Dynamic AMOLED 2X, QHD+ (3120x1440), LTPO 1-120 Гц, 3000 нит",
                    "Камеры" to "200 Мп (OIS, f/1.7) + 50 Мп (Перископ, 5x, OIS) + 50 Мп (Широкоугольный, макро) + 10 Мп (Телефото, 3x)",
                    "Батарея" to "5000 мАч, быстрая зарядка Super Fast Charging 2.0 45W, беспроводная 15W Qi2",
                    "Память" to "12 ГБ LPDDR5X RAM / 512 ГБ UFS 4.0 ROM",
                    "Защита и Доп." to "Встроенный S Pen, влагозащита IP68, титановый сплав Grade 5"
                ),
                highlightColorHex = "#1D70B8"
            )
        )

        // 2. Apple iPhone 17 Pro Max
        list.add(
            Phone(
                id = "apple_iphone_17_pro_max",
                name = "Apple iPhone 17 Pro Max",
                brand = "APPLE",
                price = 69999,
                description = "Флагманский смартфон Apple поколения 2026 года. Изготовлен на революционном чипе A19 Pro. Обеспечивает невероятные игровые возможности, расширенные функции Neural Engine для локальной обработки искусственного интеллекта Apple Intelligence и инновационный 48-мегапиксельный сенсор во всех трех основных камерах.",
                rating = 5.0,
                reviewsCount = 98,
                specs = listOf(
                    "Процессор" to "Apple A19 Pro (2 нм, 6 ядер, 16-ядерный Neural Engine)",
                    "Экран" to "6.9\" Super Retina XDR OLED, 2868x1320, LTPO ProMotion 120 Гц, Ceramic Shield v2",
                    "Камеры" to "48 Мп (Основная, OIS сдвигом матрицы) + 48 Мп (Телефото перископ, 5x OIS) + 48 Мп (Сверхширокоугольная)",
                    "Батарея" to "4852 мАч, зарядка PD 3.0 (до 30 Вт), беспроводная магнитная MagSafe 25W",
                    "Память" to "12 ГБ LPDDR5X RAM / 256 ГБ NVMe ROM",
                    "Особенности" to "Рама из титана, Face ID, USB Type-C 3.2 Gen 2 (до 10 Гбит/с), защита IP68"
                ),
                highlightColorHex = "#7F7F7F"
            )
        )

        // 3. Google Pixel 10 Pro XL
        list.add(
            Phone(
                id = "google_pixel_10_pro_xl",
                name = "Google Pixel 10 Pro XL",
                brand = "GOOGLE",
                price = 52999,
                description = "Чистый Android-флагман от Google. Впервые работает под управлением кастомного чипа Tensor G5, произведенного полностью по технологическому процессу TSMC. Предлагает непревзойденный пользовательский интерфейс, передовую обработку HDR-фотографий и глубочайшую интеграцию с ИИ-ассистентом Google Gemini Advanced.",
                rating = 4.8,
                reviewsCount = 64,
                specs = listOf(
                    "Процессор" to "Google Tensor G5 (3 нм, специально оптимизирован под ИИ)",
                    "Экран" to "6.8\" Super Actua LTPO OLED, 2992x1344, 1-120 Гц, 3000 нит, Gorilla Glass Victus 2",
                    "Камеры" to "50 Мп (f/1.68, OIS) + 48 Мп (Телефото, 5x оптический зум, OIS) + 48 Мп (Ультраширокий, макро)",
                    "Батарея" to "5060 мАч, быстрая зарядка 45W, реверсивная беспроводная зарядка",
                    "Память" to "16 ГБ LPDDR5X RAM / 512 ГБ UFS 4.0 ROM",
                    "Обновления" to "Гарантированная поддержка обновлений ОС и безопасности в течение 7 лет"
                ),
                highlightColorHex = "#4285F4"
            )
        )

        // 4. Xiaomi 16 Ultra
        list.add(
            Phone(
                id = "xiaomi_16_ultra",
                name = "Xiaomi 16 Ultra",
                brand = "XIAOMI",
                price = 59999,
                description = "Ультимативный камерофон со стодюймовым дюймовым сенсором и бесступенчатой диафрагмой, разработанный в тесном партнерстве с Leica. Обладает рекордным временем автономной работы благодаря кремний-углеродному аккумулятору и экрану новой генерации.",
                rating = 4.9,
                reviewsCount = 57,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8 Elite (3 нм)",
                    "Экран" to "6.73\" LTPO AMOLED C9, 2K (3200x1440), 120 Гц, Сверхвысокая частота ШИМ (2160 Гц)",
                    "Камеры" to "Leica 50 Мп (1\" LYT-900, OIS, переменная диафрагма f/1.4 - f/4.0) + Три сенсора по 50 Мп (Leica)",
                    "Батарея" to "5500 мАч, проводная зарядка HyperCharge 120W, беспроводная 80W",
                    "Память" to "16 ГБ LPDDR5X RAM / 512 ГБ UFS 4.0 ROM",
                    "Корпус" to "Рама из высокопрочного сплава, задняя крышка из экокожи Leica Nano-tech, IP68"
                ),
                highlightColorHex = "#D32F2F"
            )
        )

        // 5. POCO F8 Pro
        list.add(
            Phone(
                id = "poco_f8_pro",
                name = "POCO F8 Pro",
                brand = "POCO",
                price = 24999,
                description = "Игровой зверь нового сезона от POCO. Предлагает производительность флагманского уровня благодаря мощнейшему субфлагманскому процессору и продвинутой системе жидкостного охлаждения LiquidCool Technology 4.0. Идеальный выбор для киберспорта и ресурсоемких приложений.",
                rating = 4.8,
                reviewsCount = 210,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8 Gen 3 (4 нм, графический процессор Adreno 750)",
                    "Экран" to "6.67\" Flow AMOLED, WQHD+ (3200x1440), 120 Гц, частота дискретизации тача 480 Гц",
                    "Камеры" to "50 Мп (Lytia LYT-600, OIS) + 8 Мп (Ультраширокий) + 2 Мп (Макро)",
                    "Батарея" to "5000 мАч, сверхбыстрая зарядка 120W в комплекте поставки",
                    "Память" to "12 ГБ LPDDR5X RAM / 512 ГБ UFS 4.0 ROM",
                    "Конструкция" to "Стереодинамики с Dolby Atmos, ИК-порт, вибромотор по оси X"
                ),
                highlightColorHex = "#FFB300"
            )
        )

        // 6. Realme GT 6
        list.add(
            Phone(
                id = "realme_gt_6",
                name = "Realme GT 6",
                brand = "REALME",
                price = 21999,
                description = "«Убийца флагманов» с экстремально ярким дисплеем в своем классе (до 6000 нит в пике). Модель объединяет в себе отличный баланс автономности, высокой производительности процессоров нового класса и элегантный зеркальный дизайн корпуса.",
                rating = 4.7,
                reviewsCount = 189,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8s Gen 3 (4 нм)",
                    "Экран" to "6.78\" 8T LTPO OLED, FHD+ (2780x1264), 120 Гц, яркость 6000 нит (пик), Gorilla Glass Victus 2",
                    "Камеры" to "50 Мп (Sony LYT-808, OIS, f/1.69) + 50 Мп (Телефото, 2х, OIS) + 8 Мп (Сверхширокоугольный)",
                    "Батарея" to "5500 мАч, быстрая зарядка SUPERVOOC 120W (50% за 10 минут)",
                    "Память" to "16 ГБ RAM / 512 ГБ UFS 4.0 ROM",
                    "Дополнительно" to "Камера испарения 10014 мм², NFC, стереозвук Hi-Res"
                ),
                highlightColorHex = "#00A86B"
            )
        )

        // 7. Oppo Find X8 Pro
        list.add(
            Phone(
                id = "oppo_find_x8_pro",
                name = "Oppo Find X8 Pro",
                brand = "OPPO",
                price = 49999,
                description = "Флагманский аппарат от Oppo с продвинутым набором камер Hasselblad Portrait. Задняя панель выполнена из прочного матового стекла особой технологии обработки, а процессор Dimensity 9400 гарантирует высочайший запас производительности на годы вперед.",
                rating = 4.9,
                reviewsCount = 34,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 9400 (3 нм, второе поколение All-Big-Core микроархитектуры)",
                    "Экран" to "6.78\" AMOLED, 1.5K (2780x1264), Quad-Curved, LTPS 120 Гц, HDR10+",
                    "Камеры" to "Четыре модуля Hasselblad: 50 Мп (1/1.4\", OIS) + 50 Мп (Ультраширокоугольный) + 50 Мп (Телефото 3х) + 50 Мп (Перископ 6х, OIS)",
                    "Батарея" to "5910 мАч (Кремний-углеродная матрица), зарядка 80W проводная и 50W беспроводная AIRVOOC",
                    "Память" to "16 ГБ LPDDR5X RAM / 512 ГБ UFS 4.0 ROM",
                    "Защита" to "Пыле- и влагозащита IP68 / IP69, ультратонкий оптический сканер отпечатков"
                ),
                highlightColorHex = "#FF5722"
            )
        )

        // 8. Vivo X100 Pro
        list.add(
            Phone(
                id = "vivo_x100_pro",
                name = "Vivo X100 Pro",
                brand = "VIVO",
                price = 47999,
                description = "Камерофон, открывший новую эру мобильной съемки благодаря легендарному объективу Zeiss APO Floating Mutishift. Оснащен отдельным графическим чипом Vivo V3 собственной разработки для кинематографического размытия видео и цветопередачи Zeiss Natural Color.",
                rating = 4.9,
                reviewsCount = 45,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 9300 (4 нм, All big cores)",
                    "Экран" to "6.78\" LTPO AMOLED, FHD+ (2800x1260), 120 Гц, локальное затемнение 2160 Гц PWM",
                    "Камеры" to "50.9 Мп (Sony IMX989, 1-дюймовый сенсор, OIS) + 50 Мп Zeiss APO Телефото (OIS) + 50 Мп",
                    "Батарея" to "5400 мАч, проводная зарядка FlashCharge 100W, беспроводная 50W",
                    "Память" to "16 ГБ RAM / 512 ГБ UFS 4.0 ROM",
                    "Сертификация" to "Линзы с антибликовым покрытием Zeiss T* Coating, защита IP68"
                ),
                highlightColorHex = "#03A9F4"
            )
        )

        // 9. Motorola Edge 50 Ultra
        list.add(
            Phone(
                id = "motorola_edge_50_ultra",
                name = "Motorola Edge 50 Ultra",
                brand = "MOTOROLA",
                price = 39999,
                description = "Изысканный флагман с уникальной текстурой задней спинки под дерево или премиальную экокожу. Тонкий эргономичный корпус, изогнутый 144-герцовый дисплей pOLED и максимально чистая операционная система Hello UX с функциями Moto AI.",
                rating = 4.8,
                reviewsCount = 42,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8s Gen 3 (4 нм)",
                    "Экран" to "6.7\" pOLED Super HD (1220p), частота обновления 144 Гц, HDR10+, 2500 нит",
                    "Камеры" to "50 Мп (OIS, f/1.6) + 64 Мп (Перископ, 3x оптический зум, OIS) + 50 Мп (Ультраширокий, f/2.0)",
                    "Батарея" to "4500 мАч, сверхбыстрая TurboPower 125W, беспроводная 50W",
                    "Память" to "16 ГБ LPDDR5X RAM / 1 ТБ (1000 ГБ) UFS 4.0 ROM",
                    "Материалы" to "Задняя часть - Натуральное дерево нордического леса, рама из авиационного алюминия, влагозащита IP68"
                ),
                highlightColorHex = "#8B5A2B"
            )
        )

        // 10. Tecno Phantom V Fold 2
        list.add(
            Phone(
                id = "tecno_phantom_v_fold_2",
                name = "Tecno Phantom V Fold 2",
                brand = "TECNO",
                price = 45999,
                description = "Складной смартфон нового поколения, предлагающий форм-фактор планшета в сложенном виде при толщине корпуса всего чуть более 10 мм. Улучшенный титановый шарнир обеспечивает практически полное отсутствие складки на упругом гибком экране.",
                rating = 4.7,
                reviewsCount = 28,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 9000+ (4 нм, 8 ядер)",
                    "Экран (внутр)" to "7.85\" Складной LTPO AMOLED 2K, 120 Гц, адаптивный, поддержка стилуса",
                    "Экран (внеш)" to "6.42\" LTPO AMOLED, FHD+, 120 Гц, защитное стекло Gorilla Glass Victus",
                    "Камеры" to "Тройная система: 50 Мп (Основная) + 50 Мп (Телефото, 2х) + 50 Мп (Ультраширокоугольная)",
                    "Батарея" to "5000 мАч, быстрая зарядка 70W проводная, 15W беспроводная",
                    "Память" to "12 ГБ LPDDR5X RAM / 512 ГБ ROM"
                ),
                highlightColorHex = "#4A148C"
            )
        )

        // 11. Infinix GT 20 Pro
        list.add(
            Phone(
                id = "infinix_gt_20_pro",
                name = "Infinix GT 20 Pro",
                brand = "INFINIX",
                price = 13999,
                description = "Яркий геймерский смартфон среднего класса в стиле кибер-механики с настраиваемой светодиодной подсветкой на спинке корпуса. Оборудован выделенным видеочипом Pixelworks X5 Turbo для апскейлинга частоты кадров в мобильных играх до стабильных 120 FPS.",
                rating = 4.8,
                reviewsCount = 114,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 8200 Ultimate (4 нм) + Гейминг-сопроцессор Pixelworks X5",
                    "Экран" to "6.78\" LTPS AMOLED, FHD+, 144 Гц, ШИМ затемнение 2304 Гц",
                    "Камеры" to "108 Мп (Основной сенсор, OIS) + 2 Мп + 2 Мп (макро)",
                    "Батарея" to "5000 мАч, быстрая зарядка 45W, поддержка режима Bypass Charge (питание в обход батареи)",
                    "Память" to "12 ГБ LPDDR5X RAM / 256 ГБ UFS 3.1 ROM",
                    "Охлаждение" to "Испарительная камера увеличенного масштаба с термо-гелем графен-полимер"
                ),
                highlightColorHex = "#76FF03"
            )
        )

        // 12. Apple iPhone 16 Pro
        list.add(
            Phone(
                id = "apple_iphone_16_pro",
                name = "Apple iPhone 16 Pro",
                brand = "APPLE",
                price = 54999,
                description = "Компактный титановый флагман 16-го поколения. Получил новую сенсорную физическую кнопку Camera Control для удобной регулировки параметров съемки, выросший до 6.3 дюймов безрамочный экран и мощь процессора Apple A18 Pro.",
                rating = 4.9,
                reviewsCount = 76,
                specs = listOf(
                    "Процессор" to "Apple A18 Pro (3 нм, 6 ядер CPU, 6 ядер GPU)",
                    "Экран" to "6.3\" Super Retina XDR OLED, 2622x1206, ProMotion 1-120 Гц, пиковая яркость 2000 нит",
                    "Камеры" to "48 Мп (f/1.78, OIS) + 48 Мп (Ультраширокий, макро) + 12 Мп Телефото (5х оптический зум, OIS)",
                    "Батарея" to "3582 мАч, быстрая беспроводная зарядка MagSafe до 25W",
                    "Память" to "8 ГБ LPDDR5X RAM / 128 ГБ NVMe ROM",
                    "Особенности" to "Кнопка Camera Control, Action Button, Face ID, USB Type-C 3.2"
                ),
                highlightColorHex = "#D4AF37"
            )
        )

        // 13. Apple iPhone 16
        list.add(
            Phone(
                id = "apple_iphone_16",
                name = "Apple iPhone 16",
                brand = "APPLE",
                price = 39999,
                description = "Базовая модель с обновленным вертикальным расположением модулей двойной камеры для съемки пространственного видео (Spatial Video). Снабжен продвинутым чипом Apple A18, сенсорной панелью Camera Control и программируемой кнопкой Action Button.",
                rating = 4.8,
                reviewsCount = 135,
                specs = listOf(
                    "Процессор" to "Apple A18 Bionic (3 нм, 5-ядерный графический ускоритель)",
                    "Экран" to "6.1\" Super Retina XDR OLED, 2556x1179, Super Ceramic Shield",
                    "Камеры" to "48 Мп Fusion (f/1.6, OIS, 2x телеобъектив без потерь) + 12 Мп ультраширокий (f/2.2)",
                    "Батарея" to "3561 мАч, работа в режиме видео до 22 часов, USB Type-C",
                    "Память" to "8 ГБ LPDDR5X RAM / 128 ГБ NVMe ROM",
                    "Органы управления" to "Camera Control, Action Button, Face ID, Dynamic Island"
                ),
                highlightColorHex = "#E1BEE7"
            )
        )

        // 14. Apple iPhone SE (2022)
        list.add(
            Phone(
                id = "apple_iphone_se_2022",
                name = "Apple iPhone SE (2022)",
                brand = "APPLE",
                price = 18999,
                description = "Самый доступный и компактный смартфон Apple классического форм-фактора с кнопкой Домой и сканером отпечатков пальцев Touch ID. Внутри установлен производительный флагманский чип A15 Bionic, обеспечивающий молниеносную скорость работы и долгие годы обновлений iOS.",
                rating = 4.6,
                reviewsCount = 422,
                specs = listOf(
                    "Процессор" to "Apple A15 Bionic (5 нм, 6 ядер)",
                    "Экран" to "4.7\" Retina HD IPS, 1334x750, поддержка True Tone и широкого цветового охвата DCI-P3",
                    "Камера" to "12 Мп (Основная с оптической стабилизацией изображения, f/1.8)",
                    "Батарея" to "2018 мАч, быстрая зарядка Qi (до 15 Вт)",
                    "Память" to "4 ГБ RAM / 128 ГБ ROM",
                    "Защита" to "Пыле- и влагозащита IP67, прочный стеклянный корпус, Touch ID"
                ),
                highlightColorHex = "#A6A6A6"
            )
        )

        // 15. Samsung Galaxy Z Fold7
        list.add(
            Phone(
                id = "samsung_galaxy_z_fold7",
                name = "Samsung Galaxy Z Fold7",
                brand = "SAMSUNG",
                price = 79999,
                description = "Премиальное складное устройство, устанавливающее новые стандарты многозадачности в 2026 году. В развернутом виде представляет собой портативный планшет, снабженный специальным защитным ультратонким стеклом UTG повышенной износостойкости и поддержкой пера S Pen Fold Edition.",
                rating = 5.0,
                reviewsCount = 19,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8 Elite (3 нм, до 4.32 ГГц)",
                    "Основной экран" to "7.6\" Dynamic AMOLED 2X, 2K+ (2176x1812), гибкий, LTPO 120 Гц, яркость 2800 нит",
                    "Внешний экран" to "6.3\" Dynamic AMOLED 2X, FHD+, 120 Гц, Gorilla Glass Victus 2",
                    "Камеры" to "50 Мп (OIS) + 10 Мп (3x зум, OIS) + 12 Мп + Скрытая подэкранная камера",
                    "Батарея" to "4400 мАч, безопасная скоростная зарядка 25W, беспроводная 15W",
                    "Память" to "16 ГБ RAM / 512 ГБ UFS 4.0 ROM"
                ),
                highlightColorHex = "#1D70B8"
            )
        )

        // 16. Samsung Galaxy Z Flip7
        list.add(
            Phone(
                id = "samsung_galaxy_z_flip7",
                name = "Samsung Galaxy Z Flip7",
                brand = "SAMSUNG",
                price = 44999,
                description = "Стильная и компактная вертикальная раскладушка в ярких цветовых исполнениях. Огромный интерактивный внешний экран Flex Window позволяет отвечать на сообщения, запускать виджеты и контролировать камеру в закрытом положении смартфона.",
                rating = 4.8,
                reviewsCount = 41,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8 Elite (3 нм, Snapdragon for Galaxy)",
                    "Внутренний экран" to "6.7\" Dynamic AMOLED 2X, FHD+, LTPO 1-120 Гц",
                    "Внешний экран" to "3.4\" Super AMOLED Flex Window, 60 Гц, кастомная карусель виджетов",
                    "Камеры" to "50 Мп (f/1.8, OIS) + 12 Мп сверхширокоугольный",
                    "Батарея" to "4000 мАч, оптимизированная двухмодульная архитектура питания, 25 Вт зарядка",
                    "Память" to "12 ГБ LPDDR5X RAM / 256 ГБ UFS 4.0 ROM"
                ),
                highlightColorHex = "#8E24AA"
            )
        )

        // 17. Samsung Galaxy A56 5G
        list.add(
            Phone(
                id = "samsung_galaxy_a56",
                name = "Samsung Galaxy A56 5G",
                brand = "SAMSUNG",
                price = 18999,
                description = "Лучший среднебюджетный выбор Samsung 2026 года. Свежий 4-нанометровый процессор Exynos 1580 с графическим ядром на базе инновационной архитектуры AMD RDNA, полностью металлические грани и влагозащита IP67 делают этот телефон народным долгожителем.",
                rating = 4.8,
                reviewsCount = 92,
                specs = listOf(
                    "Процессор" to "Samsung Exynos 1580 (4 нм, графика AMD Xclipse 940)",
                    "Экран" to "6.6\" Super AMOLED, FHD+ (2340x1080), 120 Гц, защищен Gorilla Glass Victus+",
                    "Камеры" to "50 Мп (OIS, f/1.8) + 12 Мп (Широкоугольная) + 5 Мп (Макро)",
                    "Батарея" to "5000 мАч, быстрая зарядка 25 Вт",
                    "Память" to "8 ГБ RAM / 256 ГБ ROM, поддержка карт MicroSD до 1 ТБ",
                    "Безопасность" to "Оптический сканер в дисплее, защищенное ядро Samsung Knox Vault"
                ),
                highlightColorHex = "#1D70B8"
            )
        )

        // 18. Samsung Galaxy A36 5G
        list.add(
            Phone(
                id = "samsung_galaxy_a36",
                name = "Samsung Galaxy A36 5G",
                brand = "SAMSUNG",
                price = 14999,
                description = "Оптимальный баланс стоимости и возможностей. Смартфон обеспечивает отличную плавность благодаря экрану Super AMOLED 120 Гц, имеет качественную тройную камеру высокой четкости с оптической стабилизацией и длительный цикл поддержки обновлениями Android.",
                rating = 4.7,
                reviewsCount = 75,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 6 Gen 3 (4 нм)",
                    "Экран" to "6.6\" Super AMOLED, FHD+, 120 Гц, частота мерцания пониженная Eye Comfort",
                    "Камеры" to "50 Мп (Сенсор с OIS) + 8 Мп (Широкоугольный) + 2 Мп",
                    "Батарея" to "5000 мАч, быстрая зарядка Type-C 25 Вт",
                    "Память" to "8 ГБ LPDDR4X RAM / 128 ГБ ROM",
                    "Особенности" to "Защита корпуса от пыли и брызг по стандарту IP67, стереодинамики"
                ),
                highlightColorHex = "#1D70B8"
            )
        )

        // 19. Samsung Galaxy A16 5G
        list.add(
            Phone(
                id = "samsung_galaxy_a16",
                name = "Samsung Galaxy A16 5G",
                brand = "SAMSUNG",
                price = 8499,
                description = "Качественный бюджетный телефон с поддержкой сетей пятого поколения. Оснащен контрастным экраном Super AMOLED сочного спектра цветов, встроенным чипом бесконтактной оплаты NFC и мощной батареей на весь день.",
                rating = 4.6,
                reviewsCount = 59,
                specs = listOf(
                    "Процессор" to "Samsung Exynos 1330 (5 нм, 8 ядер)",
                    "Экран" to "6.7\" Super AMOLED, FHD+ (2400x1080), 90 Гц, яркость 800 нит",
                    "Камеры" to "50 Мп (f/1.8 основная) + 5 Мп (ультраширокоугольная) + 2 Мп (макро)",
                    "Батарея" to "5000 мАч, зарядка мощностью 15W через порт Type-C",
                    "Память" to "6 ГБ RAM / 128 ГБ ROM, гибридный слот расширения SIM/MicroSD",
                    "Корпус" to "Высокопрочный поликарбонат с приятной матовой текстурой, NFC датчик"
                ),
                highlightColorHex = "#1D70B8"
            )
        )

        // 20. Xiaomi 15
        list.add(
            Phone(
                id = "xiaomi_15",
                name = "Xiaomi 15",
                brand = "XIAOMI",
                price = 34999,
                description = "Компактный шедевр от Xiaomi на передовом процессоре Snapdragon 8 Elite. Тонкие симметричные рамки дисплея со всех четырех сторон, продвинутая профессиональная оптика и опция калибровки цветов Leica делают его незаменимым повседневным компаньоном.",
                rating = 4.8,
                reviewsCount = 111,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 8 Elite (3 нм)",
                    "Экран" to "6.36\" LTPO OLED, 1.5K (2670x1200), 1-120 Гц, пиковая яркость 3200 нит, Dolby Vision",
                    "Камеры" to "Leica Summilux: 50 Мп (OIS, f/1.6) + 50 Мп (Телефото 3.2х OIS) + 50 Мп (Широкоугольный)",
                    "Батарея" to "5400 мАч, сверхкомфортная турбо-зарядка 90 Вт проводная, 50 Вт беспроводная",
                    "Память" to "12 ГБ LPDDR5X RAM / 256 ГБ UFS 4.0 ROM",
                    "Защита" to "Полноценная влагозащищенность по стандарту IP68, алюминиевый сплав шасси"
                ),
                highlightColorHex = "#D32F2F"
            )
        )

        // 21. Xiaomi Redmi Note 15 Pro+ 5G
        list.add(
            Phone(
                id = "redmi_note_15_pro_plus",
                name = "Xiaomi Redmi Note 15 Pro+ 5G",
                brand = "XIAOMI",
                price = 19999,
                description = "Супер-хит в линейке Redmi 2026. Обладает великолепным изогнутым экраном-водопадом высокого разрешения и беспрецедентной турбо-зарядочной станцией на 120 Вт, способной реанимировать аккумулятор от 0 до 100% всего за 19 минут.",
                rating = 4.9,
                reviewsCount = 204,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 7400-Ultra (4 нм)",
                    "Экран" to "6.67\" Изогнутый CrystalRes Flow AMOLED, 1.5K, 120 Гц, защитное стекло Gorilla Glass ",
                    "Камеры" to "200 Мп (Samsung HP3 с оптическим стабилизатором OIS) + 8 Мп (широкий) + 2 Мп",
                    "Батарея" to "5000 мАч, скоростная турбо-зарядка HyperCharge 120W в комплекте",
                    "Память" to "12 ГБ LPDDR5 RAM / 512 ГБ UFS 3.1 ROM",
                    "Защита" to "Премиальная герметизация корпуса по жесткому стандарту IP68"
                ),
                highlightColorHex = "#D32F2F"
            )
        )

        // 22. Xiaomi Redmi Note 15 Pro
        list.add(
            Phone(
                id = "redmi_note_15_pro",
                name = "Xiaomi Redmi Note 15 Pro",
                brand = "XIAOMI",
                price = 14999,
                description = "Народная модель с отличной автономностью и камерой высокой четкости. Прекрасное дополнение знаменитой 15-й линейки Redmi, оснащенное премиальным AMOLED дисплеем высокой частоты обновления.",
                rating = 4.7,
                reviewsCount = 145,
                specs = listOf(
                    "Процессор" to "MediaTek Helio G100 (6 нм, оптимизирован под повседневную многозадачность)",
                    "Экран" to "6.67\" AMOLED, FHD+ (2400x1080), 120 Гц, частота дискретизации тача 240 Гц",
                    "Камеры" to "108 Мп (Основной фотомодуль высокого разрешения) + 8 Мп + 2 Мп (макро)",
                    "Батарея" to "5000 мАч, быстрая зарядка мощностью 67 Вт с термодатчиком",
                    "Память" to "8 ГБ LPDDR4X RAM / 256 ГБ ROM, слот карт памяти MicroSD до 1 ТБ",
                    "Звук" to "Громкие стереофонические динамики Dolby Atmos, аудиовыход 3.5 мм"
                ),
                highlightColorHex = "#D32F2F"
            )
        )

        // 23. Xiaomi Redmi 14C
        list.add(
            Phone(
                id = "redmi_14c",
                name = "Xiaomi Redmi 14C",
                brand = "XIAOMI",
                price = 5499,
                description = "Бюджетный лидер продаж с ультрамодным круглым блоком камер на глянцевой спинке. Огромный плавный дисплей отлично подходит для просмотра видео и игр, а ёмкий аккумулятор обеспечивает два дня автономной работы.",
                rating = 4.5,
                reviewsCount = 318,
                specs = listOf(
                    "Процессор" to "MediaTek Helio G81 Ultra (12 нм, 8 ядер)",
                    "Экран" to "6.88\" IPS LCD, HD+ (1640x720), 120 Гц плавный фреймрейт, защита зрения TUV",
                    "Камеры" to "50 Мп (Сенсор с поддержкой ИИ-алгоритмов) + 2 Мп вспомогательный датчик",
                    "Батарея" to "5160 мАч, поддержка быстрой безопасной зарядки 18W Type-C",
                    "Память" to "4 ГБ RAM (виртуальное расширение до 8 ГБ) / 128 ГБ ROM",
                    "Опции" to "Боковой сканер отпечатков пальцев, встроенный чип NFC, разъем 3.5мм"
                ),
                highlightColorHex = "#D32F2F"
            )
        )

        // 24. POCO X8 Pro 5G
        list.add(
            Phone(
                id = "poco_x8_pro",
                name = "POCO X8 Pro 5G",
                brand = "POCO",
                price = 16999,
                description = "Король производительности среднего класса. Флагманская память LPDDR5X и накопитель стандарта UFS 4.0 обеспечивают молниеносный запуск любых приложений, а обновленный дизайн в стиле карбонового волокна подчеркивает игровой характер.",
                rating = 4.8,
                reviewsCount = 176,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 8300-Ultra (4 нм, архитектура ARMv9)",
                    "Экран" to "6.67\" 120Hz CrystalRes Flow AMOLED, 1.5K (2712x1220), 1800 нит, контрастность 5М:1",
                    "Камеры" to "64 Мп (с оптической стабилизацией OIS) + 8 Мп (ультраширокоугольный) + 2 Мп",
                    "Батарея" to "5000 мАч, быстрая турбо-зарядка 67W в комплекте поставки",
                    "Память" to "8 ГБ LPDDR5X RAM / 256 ГБ UFS 4.0 ROM",
                    "Дополнительно" to "Линейный вибромотор по оси X, Bluetooth 5.4, встроенный NFC"
                ),
                highlightColorHex = "#FFB300"
            )
        )

        // 25. Google Pixel 10
        list.add(
            Phone(
                id = "google_pixel_10_base",
                name = "Google Pixel 10",
                brand = "GOOGLE",
                price = 38999,
                description = "Стандартный компактный флагман от Google нового поколения. Фирменное качество съемки Real Tone, чистый интерфейс Material You без лишних надстроек и мощь долгожданного чипа Tensor G5, созданного на современных мощностях TSMC.",
                rating = 4.7,
                reviewsCount = 38,
                specs = listOf(
                    "Процессор" to "Google Tensor G5 (3 нм, TSMC Foundry)",
                    "Экран" to "6.3\" Actua OLED дисплей, 120 Гц, пиковая яркость 2700 нит",
                    "Камеры" to "50 Мп (f/1.68) с сенсором оптической стабилизации + 48 Мп (сверхширокоугольный модуль)",
                    "Батарея" to "4700 мАч, зарядка мощностью 30 Вт, быстрая беспроводная зарядка Pixel Stand",
                    "Память" to "12 ГБ LPDDR5X RAM / 128 ГБ UFS 4.0 ROM",
                    "Поддержка" to "7 лет гарантированных Feature Drops обновлений непосредственно от Google"
                ),
                highlightColorHex = "#4285F4"
            )
        )

        // 26. Google Pixel 9a
        list.add(
            Phone(
                id = "google_pixel_9a",
                name = "Google Pixel 9a",
                brand = "GOOGLE",
                price = 21999,
                description = "Среднебюджетный хит от Google. Смартфон практически не отличается по возможностям фотосъемки от старших братьев, обладая тем же набором ИИ-алгоритмов Magic Eraser, Best Take и Audio Magic Eraser для обработки видео.",
                rating = 4.8,
                reviewsCount = 49,
                specs = listOf(
                    "Процессор" to "Google Tensor G4 (4 нм)",
                    "Экран" to "6.1\" OLED, FHD+ (2400x1080), 120 Гц, частота дискретизации кадра супер-плавная",
                    "Камеры" to "48 Мп (f/1.7 основная камера, Quad Bayer) + 13 Мп (Ультраширокоугольный модуль)",
                    "Батарея" to "4650 мАч, интеллектуальное управление расходом заряда Adaptive Battery",
                    "Память" to "8 ГБ LPDDR5 RAM / 128 ГБ UFS 3.1 ROM",
                    "Защита" to "Сертифицированная влагозащита IP67, матовая рамка из переработанного алюминия"
                ),
                highlightColorHex = "#4285F4"
            )
        )

        // 27. Realme 12 Pro+ 5G
        list.add(
            Phone(
                id = "realme_12_pro_plus",
                name = "Realme 12 Pro+ 5G",
                brand = "REALME",
                price = 17999,
                description = "Выдающийся фоторешение среднего сегмента с настоящим телеобъективом-перископом. Роскошное оформление задней панели от известного часового мастера Оливье Савео, имитирующее премиальный хронограф.",
                rating = 4.8,
                reviewsCount = 89,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 7s Gen 2 (4 нм)",
                    "Экран" to "6.7\" Curved Vision OLED, FHD+, 120 Гц частота обновления, 100% охват DCI-P3",
                    "Камеры" to "64 Мп (Перископический телеобъектив Omnivision, OIS, 3х зум) + 50 Мп (Sony IMX890, OIS) + 8 Мп",
                    "Батарея" to "5000 мАч, безопасная зарядка SUPERVOOC мощностью 67 Вт",
                    "Память" to "12 ГБ LPDDR4X RAM / 512 ГБ UFS 3.1 ROM",
                    "Корпус" to "Высококачественная веганская кожа с золотистой декоративной прострочкой, IP65"
                ),
                highlightColorHex = "#00A86B"
            )
        )

        // 28. Oppo Reno 12 Pro
        list.add(
            Phone(
                id = "oppo_reno_12_pro",
                name = "Oppo Reno 12 Pro",
                brand = "OPPO",
                price = 22999,
                description = "Тонкий портативный гаджет повышенной прочности со специальным титановым бронированием под дисплеем. Оборудован мощным процессором с ИИ-алгоритмами обработки связи, устраняющими обрывы в зонах слабого покрытия.",
                rating = 4.7,
                reviewsCount = 41,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 7300-Energy (4 нм, энергосберегающая специальная версия)",
                    "Экран" to "6.7\" Infinite View AMOLED, Четырехсторонний изгиб, 120 Гц, Gorilla Glass 7i",
                    "Камеры" to "50 Мп (Sony LYT-600, OIS) + 50 Мп (Портретный телеобъектив 2х) + 8 Мп (Сверхширокоугольный)",
                    "Батарея" to "5000 мАч, поддержка быстрой флеш-зарядки SUPERVOOC 80W",
                    "Память" to "12 ГБ LPDDR4X RAM / 512 ГБ ROM",
                    "ИИ-фишки" to "AI Eraser 2.0 (ластик), AI Clear Face (улучшение портретов), AI LinkBoost"
                ),
                highlightColorHex = "#FF5722"
            )
        )

        // 29. Vivo V40 Pro
        list.add(
            Phone(
                id = "vivo_v40_pro",
                name = "Vivo V40 Pro",
                brand = "VIVO",
                price = 23999,
                description = "Великолепный портретник, созданный совместно с Zeiss. Кольцевая мягкая подсветка Aura Light обеспечивает студийное заполнение светом при вечерней и ночной портретной съемке. Тонкий эргономичный изогнутый корпус отлично лежит в ладони.",
                rating = 4.8,
                reviewsCount = 53,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 9200+ (4 нм, флагманский уровень)",
                    "Экран" to "6.78\" Изогнутый 3D AMOLED, 1.5K (2800x1260), 120 Гц, частота пульсации 2160 Гц",
                    "Камеры" to "Тройной портретный тандем от Zeiss: 50 Мп (Sony IMX921, OIS) + 50 Мп (Ширик) + 50 Мп (Zeiss портретник)",
                    "Батарея" to "5500 мАч, кремний-углеродный ультратонкий аккумулятор, 80W проводная зарядка",
                    "Память" to "12 ГБ LPDDR5X RAM / 512 ГБ UFS 3.1 ROM",
                    "Влагозащита" to "Улучшенная защита по стандартам IP68 и IP69 (выдерживает брызги под давлением)"
                ),
                highlightColorHex = "#03A9F4"
            )
        )

        // 30. Motorola Moto G85
        list.add(
            Phone(
                id = "motorola_moto_g85",
                name = "Motorola Moto G85",
                brand = "MOTOROLA",
                price = 9999,
                description = "Доступный, но очень стильный молодежный смартфон с потрясающим 3D Curved pOLED экраном без рамок. Задняя панель выполнена из износостойкой веганской кожи в сочных оттенках палитры Pantone.",
                rating = 4.6,
                reviewsCount = 118,
                specs = listOf(
                    "Процессор" to "Qualcomm Snapdragon 6s Gen 3 (6 нм, 8 ядер)",
                    "Экран" to "6.67\" Изогнутый Endless Edge pOLED, FHD+, 120 Гц, пиковая яркость 1600 нит",
                    "Камеры" to "50 Мп (Sony LYT-600 основной сенсор с OIS) + 8 Мп (Сверхширокоугольный / Макро)",
                    "Батарея" to "5000 мАч, быстрая зарядка TurboPower 30W",
                    "Память" to "8 ГБ LPDDR4X RAM / 256 ГБ UFS 2.2 ROM, поддержка карточек MicroSD",
                    "Материалы" to "Силикон-кожа от Pantone, динамики стерео с сертификатом Dolby Atmos"
                ),
                highlightColorHex = "#8B5A2B"
            )
        )

        // 31. Tecno Camon 30 Premier
        list.add(
            Phone(
                id = "tecno_camon_30_premier",
                name = "Tecno Camon 30 Premier",
                brand = "TECNO",
                price = 18999,
                description = "Камерофон оригинального эко-дизайна, стилизованный под классический винтажный фотоаппарат с поворотным металлическим кольцом. Обладает великолепным LTPO экраном высокого разрешения и четырьмя раздельными фотосенсорами.",
                rating = 4.7,
                reviewsCount = 31,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 8200 Ultimate (4 нм) + Sony Imaging ISP чип",
                    "Экран" to "6.77\" LTPO AMOLED, 1.5K (2700x1264), адаптивная частота 1-120 Гц, 10-бит цвет",
                    "Камеры" to "Три модуля на 50 Мп: 50 Мп (Sony IMX890, OIS) + 50 Мп (Портретный телеобъектив) + 50 Мп",
                    "Батарея" to "5000 мАч, безопасная скоростная зарядка 70W UltraCharge",
                    "Память" to "12 ГБ LPDDR5X RAM / 512 ГБ UFS 3.1 ROM",
                    "Корпус" to "Эко-замша с активной текстурой из биоволокна, рамка из авиационного алюминия"
                ),
                highlightColorHex = "#4A148C"
            )
        )

        // 32. Infinix Zero 40
        list.add(
            Phone(
                id = "infinix_zero_40",
                name = "Infinix Zero 40",
                brand = "INFINIX",
                price = 16999,
                description = "Стильный и сбалансированный флагман от Infinix. Сгибающийся во всех направлениях AMOLED экран 144 Гц дарит невероятное ощущение премиальности, а специальный режим съемки GoPro сопряжен со спортивными экшн-камерами.",
                rating = 4.8,
                reviewsCount = 29,
                specs = listOf(
                    "Процессор" to "MediaTek Dimensity 8200 Ultimate (4 нм)",
                    "Экран" to "6.78\" 3D Curved AMOLED, FHD+, 144 Гц сверхвысокий фреймрейт, пиковая яркость 1300 нит",
                    "Камеры" to "108 Мп (Основной датчик Samsung HM6, OIS) + 50 Мп (Сверхширокоугольный) + 2 Мп",
                    "Батарея" to "5000 мАч, скоростная зарядка мощностью 45W проводная, 20W беспроводная Wireless Pro",
                    "Память" to "12 ГБ LPDDR5 RAM / 512 ГБ UFS 3.1 ROM",
                    "Мультимедиа" to "Два стереодинамика, настроенные совместно с лабораторией JBL, поддержка Wi-Fi 6"
                ),
                highlightColorHex = "#76FF03"
            )
        )

        list
    }
}
