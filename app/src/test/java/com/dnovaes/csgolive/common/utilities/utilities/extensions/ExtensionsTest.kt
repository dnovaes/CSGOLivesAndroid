package com.dnovaes.csgolive.common.utilities.utilities.extensions

import com.dnovaes.csgolive.common.utilities.extensions.getSummaryMatchTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `check cases for getSummaryMatchTime`() {

        val input = listOf(
            ZonedDateTime.of(
                LocalDate.of(2023, 5, 16),
                LocalTime.of(1, 14, 0),
                ZoneId.of("America/Bahia")
            ),
            ZonedDateTime.of(
                LocalDate.of(2023, 5, 6),
                LocalTime.of(2, 9, 8),
                ZoneId.of("America/Bahia")
            ),
            ZonedDateTime.of(
                LocalDate.of(2023, 5, 6),
                LocalTime.of(2, 9, 8),
                ZoneId.of("UTC")
            ).withZoneSameInstant(ZoneId.of("Europe/London")),
            ZonedDateTime.of(
                LocalDate.of(2023, 5, 6),
                LocalTime.of(2, 9, 8),
                ZoneId.of("UTC")
            ).withZoneSameInstant(ZoneId.of("America/New_York")),
            LocalDateTime.of(2023, 4, 11, 23, 1)
                .atZone(ZoneId.of("Europe/London"))
        )

        val expected = listOf(
            "16/05 01:14",
            "06/05 02:09",
            "06/05 03:09",
            "05/05 22:09",
            "11/04 23:01",
        )

        input.forEachIndexed { i, zonedDateTime ->
            println("Comparing expected `${expected[i]}` with ${zonedDateTime.getSummaryMatchTime()}")
           assertEquals(expected[i], zonedDateTime.getSummaryMatchTime())
        }
    }
}