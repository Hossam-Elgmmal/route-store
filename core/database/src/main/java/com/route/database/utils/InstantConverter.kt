package com.route.database.utils

import androidx.room.TypeConverter
import kotlinx.datetime.Instant


class InstantConverter {

    @TypeConverter
    fun longToInstant(value: Long): Instant =
        Instant.fromEpochMilliseconds(value)

    @TypeConverter
    fun instantToLong(instant: Instant): Long =
        instant.toEpochMilliseconds()
}