package com.zoomcar.uikit.driverscore.performancemeter

enum class ScoreCategoryType(val category: String) {
    GOOD("good"),
    AVERAGE("average"),
    BAD("bad"),
    UNKNOWN("unknown");

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