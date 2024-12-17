package com.dogs.data.models

internal data class BreedRemote(
    val name: String,
    private val bred_for: String?,
    private val life_span: String,
    val temperament: String?,
    val image: ImageRemote?
) {
    val bredFor: String? get() = bred_for
    val lifeSpan: String get() = life_span
}
