package com.zoomcar.uikit.driverscore

object DriverScoreCategoryKey {
    const val GOOD = "good"
    const val AVERAGE = "average"
    const val BAD = "bad"
    const val UNKNOWN = "unknown"
}

enum class DriverScoreCategoryType(val category: String) {
    GOOD(DriverScoreCategoryKey.GOOD),
    AVERAGE(DriverScoreCategoryKey.AVERAGE),
    BAD(DriverScoreCategoryKey.BAD),
    UNKNOWN(DriverScoreCategoryKey.UNKNOWN);

    companion object {
        fun fromType(category: String?) = try {
            values().first { it.category == category }
        } catch (e: IllegalArgumentException) {
            UNKNOWN
        } catch (e: NoSuchElementException) {
            UNKNOWN
        }
    }
}
