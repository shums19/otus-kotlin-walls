# API

## Сущности

1. RealEstateAd - объявление с информацией о жилой недвижимости

## Описание сущности Ad

1. Title (6-128 символов) - заголовок объявления
2. Description (100-10000 символов) - описание недвижимости
3. Owner (UserId) - владелец объявления
4. IsActive (Boolean) - опубликовано; снято с публикации
2. Type (Apartment, House, Townhouse, Dacha, Other) - тип недвижимости
3. Status (Construction, New, Resale) - новостройка/вторичка
4. Area (Number) - площадь
5. Price (Number) - стоимость
6. BuildingType (Brick, Panel, Мonolithic, Block, Wood, Other) - строительный тип дома: кирпичный, панельный и т.д.
7. HasLift (Boolean) - присутствует ли лифт
8. RoomsNumber (Int > 0) - количество комнат
9. Floor (Int > 0) - номер этажа в случае квартиры; количество этажей в случае дома, таунхауса, дачи

## Функции (эндпоинты)

1. Ad CRUDS
   1. create
   2. read
   3. update
   4. delete
   5. search - поиск по фильтрам
