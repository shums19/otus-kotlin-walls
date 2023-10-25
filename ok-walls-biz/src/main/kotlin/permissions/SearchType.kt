package ru.otus.kotlin.walls.biz.permissions

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.SearchPermissions
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.permissions.UserPermissions
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.chain
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == State.RUNNING }
    worker("Определение типа поиска") {
        adFilterValidated.searchPermissions = setOfNotNull(
            SearchPermissions.OWN.takeIf { permissionsChain.contains(UserPermissions.SEARCH_OWN) },
            SearchPermissions.PUBLIC.takeIf { permissionsChain.contains(UserPermissions.SEARCH_PUBLIC) },
        ).toMutableSet()
    }
}
