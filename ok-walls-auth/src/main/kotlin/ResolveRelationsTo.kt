package ru.otus.kotlin.walls.auth

import ru.otus.kotlin.walls.common.models.Ad
import ru.otus.kotlin.walls.common.models.AdId
import ru.otus.kotlin.walls.common.permissions.PrincipalModel
import ru.otus.kotlin.walls.common.permissions.PrincipalRelations

fun Ad.resolveRelationsTo(principal: PrincipalModel): Set<PrincipalRelations> = setOfNotNull(
    PrincipalRelations.NONE,
    PrincipalRelations.NEW.takeIf { id == AdId.NONE },
    PrincipalRelations.OWN.takeIf { principal.id == ownerId },
    PrincipalRelations.MODERATABLE,
    PrincipalRelations.PUBLIC,
)
