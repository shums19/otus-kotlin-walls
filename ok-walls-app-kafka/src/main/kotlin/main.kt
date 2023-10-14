package ru.otus.kotlin.walls.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config = config, consumerStrategies = listOf(ConsumerStrategyV1()))
    consumer.run()
}
