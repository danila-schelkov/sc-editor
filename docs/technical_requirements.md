# 1. Цель проекта

Цель проекта — разработать программу для просмотра и редактирования файлов, которые
отвечают за визуализацию объектов в играх компании Supercell.

# 2. Описание программы

Программа состоит из следующих блоков:

1. Импорт файлов Графики
    1. Формат SC
    2. Формат SC2
2. Импорт файлов Текстур
    1. Формат KTX
    2. Формат ZKTX
    3. Формат SCTX
3. Просмотр объектов
4. Редактирование объектов
5. Экспорт изображений, видео
6. Экспорт файлов графики

## 2.1. Импорт файлов Графики

В этот блок входит открытие файла, загрузка внутренних объектов. Объекты,
доступные к просмотру, нужно добавлять в таблицу-список.

### 2.1.1 Формат SC

Формат SC — это проприетарная версия формата SWF от Adobe. Модификация формата позволила
сократить объем используемой памяти.

Для чтения и записи файла используется собственный механизм (де-)сериализации,
основанный на потоковом чтении данных. В игре поддерживается только в сжатом виде (LZMA,
LZHAM, ZSTD).

Формат выведен из использования в пользу новой версии формата, также известной как SC2.
При этом расширение файла не изменилось.

### 2.1.2 Формат SC2

Формат SC2 состоит из тех же по структуре объектов (сущностей), но использует внешнюю
библиотеку для сериализации — [FlatBuffers](https://flatbuffers.dev) от Google.

Библиотека FlatBuffers эффективнее для памяти читает данные (zero-copy
parsing), но теряется преимущество потокового чтения: файл обязан находиться в памяти
устройства в разжатом (uncompressed) виде.

Из-за этого у компании возникали проблемы, при которых если в устройстве отсутствует
необходимое количество памяти, то игра не загрузится.

Преимущества формата не видны в игре из-за наличия обратной совместимости с прошлым
форматом: объекты FlatBuffer пересобираются в объекты формата SC.

## 2.2. Импорт файлов Текстур

Программа должна поддерживать импорт файлов Текстур всех форматов, поддерживаемых
форматом SC. Текстуры должны добавляться в таблицу-список.

### 2.2.1. Формат KTX

Формат разработан The Khronos Group и служит контейнером для текстур. Является
стандартом для мобильных игр, так как поддерживается на большинстве устройств "из
коробки".

Формат имеет потери качества, поэтому не подходит для некоторых целей (например, light
maps).

Раньше использовался для большинства текстур в играх компании.

### 2.2.2 Формат ZKTX

Файл формата KTX, сжатый с помощью Zstandard.

### 2.2.3 Формат SCTX

Формат SCTX является проприетарным контейнер для текстур,
он использует [FlatBuffers](https://flatbuffers.dev) от Google.

Имеет собственные типы текстур, благодаря чему поддерживает текстуры без сжатия.

## 2.3. Просмотр объектов

Программа должна отображать объект и информацию о нём при нажатии на него в таблице
объектов, а также передвигаться по сцене, приближать или отдалять объект на сцене.

В структуре файлов присутствуют следующие объекты:

1. Shape — фигура с текстурой
2. MovieClip — объект анимации, который содержит внутри себя другие объекты
3. TextField — текстовое поле
4. SWFTexture — текстура

### 2.3.1. Просмотр фигуры

Вместе с отображением фигуры необходима возможность указывать сетку, по которой фигура
растягивается, для просмотра "9-slice" фигур, также допускается редактирование этой
сетки путём растягивания объекта по крайним и угловым точкам, но только в режиме
просмотра.

Информация об этом объекте должна содержать описание команд,
по которым отрисовывается фигура.

### 2.3.2. Просмотр анимации

Покадровое отображение анимации с возможностью выбора диапазона кадров по "ключам" -
именам кадров. Разные варианты отображения в зависимости от типа выборки кадров.

Информация об этом объекте должна включать:

1. Таблицу-список детей
2. Таблицу-список кадров
3. Таблицу детей текущего кадра
4. Тип выборки кадров
5. Количества кадров в секунду

### 2.3.3. Просмотр текстового поля

Отображать в качестве текста текстовых полей нужно установленный в настройках
текст для предпросмотра.

Информация об объекте:

1. Шрифт
2. Размер шрифта
3. Исходный текст
4. Поддержка переноса строки
5. Стиль текста: жирный, курсив

### 2.3.4. Просмотр текстур

Информация об объекте содержит только размеры холста и формат пикселей.

Также должна присутствовать возможность поиска Shape по фрагменту текстуры. Это облегчит
поиск нужных объектов.

## 2.4. Редактирование

Редактирование подразумевает создание новых и редактирование существующих объектов.
Функционал данного блока включает в себя:

1. Создание объекта
2. Изменение свойств объекта: настройка шрифт текстового поля, размер текста,
   количество FPS анимации и тому подобные
3. Добавление "детей" объекта созданием нового элемента таблицы "Детей" с помощью кнопки
   или перемещением объекта из общего списка объектов в область таблицы объекта или на
   сцену, удаление существующих
4. Создание новых кадров, удаление существующих
5. Изменение меша по точкам
6. Импорт новых текстур в файл графики
7. Замена существующих текстур
8. Самостоятельный выбор области текстуры, являющейся текстурой фигуры
9. Блокирование элементов для изменения

## 2.5. Экспорт изображений, видео

Программа должна получать изображения из буфера OpenGL и сохранять их в:

1. PNG файл если это экспорт кадра или объекта, который не поддерживает анимацию
2. MP4 или AVI или какой-либо другой видео-формат для объекта, который поддерживает
   анимацию

## 2.6 Экспорт файлов графики.

Экспортироваться должны как текстуры, так и файла информации. Должна быть возможность
выбора свойств файла, отдельно ли экспортируются эти файлы или объединяться в один, есть
ли текстура с высоким расширением и тому подобные свойства. Также выбрать версию
экспортируемого файла для применения нужного вида сжатия.
