package ru.otus.kotlin.walls.common.exceptions

import ru.otus.kotlin.walls.common.models.AdLock

class RepoConcurrencyException(expectedLock: AdLock, actualLock: AdLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
