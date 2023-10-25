package ru.otus.kotlin.walls.biz.permissions

import ru.otus.kotlin.walls.auth.resolveChainPermissions
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker


fun ICorChainDsl<AdContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on { state == State.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
    }
}
