package ltd.matrixstudios.alchemist.models.profile.notes

import java.util.*

data class ProfileNote(
    val author: UUID,
    val content: String,
    val createdAt: Long
)