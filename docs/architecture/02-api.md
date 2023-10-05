# API

## Сущности

1. Ad - объявление с информацией о жилой недвижимости

## Описание сущности Ad

1. Title (6-128 символов) - заголовок объявления
2. Description (100-10000 символов) - описание недвижимости
3. Owner (UserId) - владелец объявления
4. IsActive (Boolean) - опубликовано; снято с публикации
2. RealEstateType (Apartment, House, Townhouse, Dacha, Other) - тип недвижимости
3. Status (New, Resale) - новостройка/вторичка
4. Area (Number) - площадь
5. Price (Number) - стоимость
6. BuildingType (Brick, Panel, Мonolithic, block, Wood, Other) - строительный тип дома: кирпичный, панельный и т.д.
7. ExternalStructure (Balcony, Loggia, Veranda, Other, None) - тип выносного строения, если есть
8. ElevatorType (Passenger, Service, None) - тип лифта
9. RoomsNumber (Int > 0) - количество комнат
10. Floor (Int) - номер этажа в случае квартиры; количество этажей в случае дома, таунхауса, дачи

## Функции (эндпоинты)

1. Ad CRUDS
   1. create
   2. read
   3. update
   4. delete
   5. search - поиск по фильтрам
