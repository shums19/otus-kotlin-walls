package ru.otus.kotlin.walls.biz.auth

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.CorSettings
import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.models.AdPermissionClient
import ru.otus.kotlin.walls.common.models.State
import ru.otus.kotlin.walls.common.models.UserId
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.common.permissions.PrincipalModel
import ru.otus.kotlin.walls.common.permissions.UserGroups
import ru.otus.kotlin.walls.repo.inmemory.AdRepoInMemory
import ru.otus.kotlin.walls.stubs.AdStub

class BizAuthTest : FreeSpec({
    "create success" {
        val userId = UserId("123")
        val repo = AdRepoInMemory()
        val processor = AdProcessor(
            settings = CorSettings(
                repoTest = repo
            )
        )
        val context = AdContext(
            workMode = WorkMode.TEST,
            adRequest = AdStub.prepareResult {
                permissionsClient.clear()
                id = AdId.NONE
            },
            command = AdCommand.CREATE,
            principal = PrincipalModel(
                id = userId,
                groups = setOf(
                    UserGroups.USER,
                    UserGroups.TEST,
                )
            )
        )

        processor.exec(context)

        context.state shouldBe State.FINISHING
        context.adResponse.id.value.shouldNotBeBlank()
        context.adResponse.permissionsClient.shouldContainAll(
            AdPermissionClient.READ,
            AdPermissionClient.UPDATE,
            AdPermissionClient.DELETE,
        )
    }

    "read success" {
        val adObj = AdStub.get()
        val userId = adObj.ownerId
        val adId = adObj.id
        val repo = AdRepoInMemory(initObjects = listOf(adObj))
        val processor = AdProcessor(
            settings = CorSettings(
                repoTest = repo
            )
        )
        val context = AdContext(
            command = AdCommand.READ,
            workMode = WorkMode.TEST,
            adRequest = Ad(id = adId),
            principal = PrincipalModel(
                id = userId,
                groups = setOf(
                    UserGroups.USER,
                    UserGroups.TEST,
                )
            )
        )

        processor.exec(context)

        context.state shouldBe State.FINISHING
        context.adResponse.id shouldBe adId
        context.adResponse.permissionsClient.shouldContainAll(
            AdPermissionClient.READ,
            AdPermissionClient.UPDATE,
            AdPermissionClient.DELETE,
        )
    }
})
