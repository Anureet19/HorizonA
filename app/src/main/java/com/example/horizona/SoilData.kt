package com.example.horizona

import kotlinx.serialization.Serializable

@Serializable
data class SoilData(
    val Category: String,
    val Type: String,
    val Composition: CompositionData?,
    val Color: String?,
    val pHRange: String?,
    val AssociatedNutrients: String?,
    val FertilizerRecommendation: String?,
    val CropPlantRecommendation: String?,
    val GeneralRecommendation: String?
)
@Serializable
data class CompositionData(
    val Gravel: Double,
    val Sand: Double,
    val Silt: Double
)