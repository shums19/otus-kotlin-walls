package ru.otus.kotlin.walls.biz.permissions

import ru.otus.kotlin.walls.auth.resolveFrontPermissions
import ru.otus.kotlin.walls.auth.resolveRelationsTo
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == State.RUNNING }

    handle {
        adRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                adRepoDone.resolveRelationsTo(principal)
            )
        )

        for (ad in adsRepoDone) {
            ad.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    ad.resolveRelationsTo(principal)
                )
            )
        }
    }
}
