package ru.otus.kotlin.walls.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class=$clazz cannot be mapped to AdContext")
