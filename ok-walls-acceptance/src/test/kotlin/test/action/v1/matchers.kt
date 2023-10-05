package ru.otus.otuskotlin.walls.blackbox.test.action.v1

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult

val haveErrors = Matcher<String> {
    MatcherResult(
        it.contains(""""result":"ERROR""""),
        { "result should be error" },
        { "result should not be error" }
    )
}

val haveNoErrors = Matcher<String> {
    MatcherResult(
        it.contains(""""result":"SUCCESS""""),
        { "result should be success" },
        { "result should not be success" }
    )
}

fun haveId(id: String) = Matcher<String> {
    MatcherResult(
        it.contains(""""id":"$id""""),
        { "result should have id=$id" },
        { "result should not have id=$id" }
    )
}
