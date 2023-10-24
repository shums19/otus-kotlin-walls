package ru.otus.kotlin.walls.biz.general

import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.helpers.errorAdministration
import ru.otus.kotlin.walls.common.helpers.fail
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.repo.IAdRepository
import ru.otus.kotlin.walls.cor.ICorChainDsl
import ru.otus.kotlin.walls.cor.worker

fun ICorChainDsl<AdContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        adRepo = when {
            workMode == WorkMode.TEST -> settings.repoTest
            workMode == WorkMode.STUB -> IAdRepository.NONE
            else -> settings.repoProd
        }
        if (workMode != WorkMode.STUB && adRepo == IAdRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
