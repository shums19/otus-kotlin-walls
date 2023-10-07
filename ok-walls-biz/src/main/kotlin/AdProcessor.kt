import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.common.models.AdCommand
import ru.otus.kotlin.walls.common.models.WorkMode
import ru.otus.kotlin.walls.stubs.AdStub

class AdProcessor {
    suspend fun exec(ctx: AdContext) {
        require(ctx.workMode == WorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            AdCommand.SEARCH -> {
                ctx.adsResponse.addAll(AdStub.prepareSearchList("квартира"))
            }
            AdCommand.NONE,
            AdCommand.READ,
            AdCommand.CREATE,
            AdCommand.UPDATE,
            AdCommand.DELETE -> {
                ctx.adResponse = AdStub.get()
            }
        }
    }
}
