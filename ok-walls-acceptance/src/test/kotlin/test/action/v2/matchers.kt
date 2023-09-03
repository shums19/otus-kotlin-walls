package ru.otus.otuskotlin.walls.blackbox.test.action.v2

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult

val haveNoErrors = Matcher<String> {
    MatcherResult(
        it.contains(""""result":"SUCCESS""""),
        { "result should be success" },
        { "result should not be success" }
    )
}
