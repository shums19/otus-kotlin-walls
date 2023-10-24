package ru.otus.kotlin.walls.app

import ru.otus.kotlin.walls.biz.AdProcessor
import ru.otus.kotlin.walls.common.CorSettings

data class AppSettings(
    val corSettings: CorSettings = CorSettings(),
    val processor: AdProcessor = AdProcessor(settings = corSettings),
)
