package ru.otus.kotlin.walls.biz

import ru.otus.kotlin.walls.biz.general.initStatus
import ru.otus.kotlin.walls.biz.general.operation
import ru.otus.kotlin.walls.biz.stub.stubCreateSuccess
import ru.otus.kotlin.walls.biz.stub.stubDbError
import ru.otus.kotlin.walls.biz.stub.stubDeleteSuccess
import ru.otus.kotlin.walls.biz.stub.stubNoCase
import ru.otus.kotlin.walls.biz.stub.stubNotFoundError
import ru.otus.kotlin.walls.biz.stub.stubReadSuccess
import ru.otus.kotlin.walls.biz.stub.stubSearchSuccess
import ru.otus.kotlin.walls.biz.stub.stubUpdateSuccess
import ru.otus.kotlin.walls.biz.stub.stubValidationBadArea
import ru.otus.kotlin.walls.biz.stub.stubValidationBadBuildingType
import ru.otus.kotlin.walls.biz.stub.stubValidationBadDescription
import ru.otus.kotlin.walls.biz.stub.stubValidationBadFloor
import ru.otus.kotlin.walls.biz.stub.stubValidationBadId
import ru.otus.kotlin.walls.biz.stub.stubValidationBadPrice
import ru.otus.kotlin.walls.biz.stub.stubValidationBadRoomsNumber
import ru.otus.kotlin.walls.biz.stub.stubValidationBadSearchString
import ru.otus.kotlin.walls.biz.stub.stubValidationBadStatus
import ru.otus.kotlin.walls.biz.stub.stubValidationBadTitle
import ru.otus.kotlin.walls.biz.stub.stubValidationBadType
import ru.otus.kotlin.walls.biz.stub.stubs
import ru.otus.kotlin.walls.biz.validation.cleanDescription
import ru.otus.kotlin.walls.biz.validation.cleanTitle
import ru.otus.kotlin.walls.biz.validation.finishAdFilterValidation
import ru.otus.kotlin.walls.biz.validation.finishAdValidation
import ru.otus.kotlin.walls.biz.validation.validateAreaPositive
import ru.otus.kotlin.walls.biz.validation.validateBuildingTypeHasContent
import ru.otus.kotlin.walls.biz.validation.validateDescriptionHasContent
import ru.otus.kotlin.walls.biz.validation.validateDescriptionNotEmpty
import ru.otus.kotlin.walls.biz.validation.validateFloorPositive
import ru.otus.kotlin.walls.biz.validation.validateIdNotEmpty
import ru.otus.kotlin.walls.biz.validation.validateIdProperFormat
import ru.otus.kotlin.walls.biz.validation.validatePricePositive
import ru.otus.kotlin.walls.biz.validation.validateRoomsNumberPositive
import ru.otus.kotlin.walls.biz.validation.validateSearchString
import ru.otus.kotlin.walls.biz.validation.validateStatusHasContent
import ru.otus.kotlin.walls.biz.validation.validateTitleHasContent
import ru.otus.kotlin.walls.biz.validation.validateTitleNotEmpty
import ru.otus.kotlin.walls.biz.validation.validateTypeHasContent
import ru.otus.kotlin.walls.biz.validation.validation
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.cor.rootChain
import ru.otus.kotlin.walls.cor.worker

class AdProcessor {
    suspend fun exec(ctx: AdContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<AdContext> {
            initStatus("Инициализация статуса")

            operation("Создание объявления", AdCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubValidationBadPrice("Имитация ошибки валидации стоимости")
                    stubValidationBadArea("Имитация ошибки валидации площади")
                    stubValidationBadRoomsNumber("Имитация ошибки валидации количества комнат")
                    stubValidationBadFloor("Имитация ошибки валидации этажа")
                    stubValidationBadType("Имитация ошибки валидации типа недвижимости")
                    stubValidationBadStatus("Имитация ошибки валидации статуса строительства недвижимости")
                    stubValidationBadBuildingType("Имитация ошибки валидации строительного типа здания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }

                    worker("Очистка id") { adValidating.id = AdId.NONE }
                    cleanTitle("Очистка заголовка")
                    cleanDescription("Очистка описания")

                    validateTitleNotEmpty("Проверка, что заголовок не пуст")
                    validateTitleHasContent("Проверка, что заголовок содержит символы")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка, что описание содержит символы")
                    validatePricePositive("Проверка, что цена - позитивное число")
                    validateAreaPositive("Проверка, что площадь - позитивное число")
                    validateRoomsNumberPositive("Проверка, что количество комнат - позитивное число")
                    validateFloorPositive("Проверка, что этаж - позитивное число")
                    validateTypeHasContent("Проверка, что указан тип недвижимости")
                    validateStatusHasContent("Проверка, что указан статус строительства недвижимости")
                    validateBuildingTypeHasContent("Проверка, что указан строительный тип здания")

                    finishAdValidation("Завершение проверок")
                }
            }
            operation("Получить объявление", AdCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubNotFoundError("Имитация ошибки при ненайденном объявлении")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }

                    worker("Очистка id") { adValidating.id = AdId(adValidating.id.value.trim()) }

                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменить объявление", AdCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadTitle("Имитация ошибки валидации заголовка")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubValidationBadPrice("Имитация ошибки валидации стоимости")
                    stubValidationBadArea("Имитация ошибки валидации площади")
                    stubValidationBadRoomsNumber("Имитация ошибки валидации количества комнат")
                    stubValidationBadFloor("Имитация ошибки валидации этажа")
                    stubValidationBadType("Имитация ошибки валидации типа недвижимости")
                    stubValidationBadStatus("Имитация ошибки валидации статуса строительства недвижимости")
                    stubValidationBadBuildingType("Имитация ошибки валидации строительного типа здания")
                    stubNotFoundError("Имитация ошибки при ненайденном объявлении")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }

                    worker("Очистка id") { adValidating.id = AdId(adValidating.id.value.trim()) }
                    cleanTitle("Очистка заголовка")
                    cleanDescription("Очистка заголовка")

                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateTitleNotEmpty("Проверка на непустой заголовок")
                    validateTitleHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")
                    validatePricePositive("Проверка, что цена - позитивное число")
                    validateAreaPositive("Проверка, что площадь - позитивное число")
                    validateRoomsNumberPositive("Проверка, что количество комнат - позитивное число")
                    validateFloorPositive("Проверка, что этаж - позитивное число")
                    validateTypeHasContent("Проверка, что указан тип недвижимости")
                    validateStatusHasContent("Проверка, что указан статус строительства недвижимости")
                    validateBuildingTypeHasContent("Проверка, что указан строительный тип здания")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Удалить объявление", AdCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubNotFoundError("Имитация ошибки при ненайденном объявлении")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }

                    worker("Очистка id") { adValidating.id = AdId(adValidating.id.value.trim()) }

                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск объявлений", AdCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadSearchString("Имитация ошибки валидации поисковой строки")
                    stubNotFoundError("Имитация ошибки при ненайденном объявлении")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { adFilterValidating = adFilterRequest.copy() }

                    validateSearchString("Проверка, что поисковая строка содержит только допустимые символы")

                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }

            }
        }.build()
    }
}
