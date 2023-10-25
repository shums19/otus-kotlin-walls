package ru.otus.kotlin.walls.biz.permissions

import ru.otus.kotlin.walls.auth.checkPermitted
import ru.otus.kotlin.walls.auth.resolveRelationsTo
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.fail
import ru.otus.kotlin.walls.common.models.AdError
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.chain
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == State.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        adRepoRead.principalRelations = adRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к объявлению") {
        permitted = checkPermitted(command, adRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(AdError(message = "User is not allowed to perform this operation"))
        }
    }
}

