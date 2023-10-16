package ru.otus.kotlin.walls.app.kafka

import ru.otus.kotlin.walls.api.v1.apiV1RequestDeserialize
import ru.otus.kotlin.walls.api.v1.apiV1ResponseSerialize
import ru.otus.kotlin.walls.api.v1.models.IRequest
import ru.otus.kotlin.walls.api.v1.models.IResponse
import ru.otus.kotlin.walls.common.AdContext
import ru.otus.kotlin.walls.mappers.v1.fromTransport
import ru.otus.kotlin.walls.mappers.v1.toTransport

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: AdContext): String {
        val response: IResponse = source.toTransport()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: AdContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
