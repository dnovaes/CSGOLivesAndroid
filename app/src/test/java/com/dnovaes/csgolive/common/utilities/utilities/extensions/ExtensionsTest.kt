package com.dnovaes.csgolive.common.utilities.utilities.extensions

import com.dnovaes.csgolive.common.utilities.Constants
import com.dnovaes.csgolive.common.utilities.extensions.formatUsingLocalZoneId
import com.dnovaes.csgolive.common.utilities.extensions.getSummaryMatchTime
import com.dnovaes.csgolive.common.utilities.extensions.isSameWeek
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
                ZoneId.of(Constants.ZoneId.UTC)
            ).withZoneSameInstant(ZoneId.of("Europe/London")),
            ZonedDateTime.of(
                LocalDate.of(2023, 5, 6),
                LocalTime.of(2, 9, 8),
                ZoneId.of(Constants.ZoneId.UTC)
            ).withZoneSameInstant(ZoneId.of("America/New_York")),
            LocalDateTime.of(2023, 4, 11, 23, 1)
                .atZone(ZoneId.of("Europe/London"))
        )

        val expected = listOf(
            "16.05 01:14",
            "06.05 02:09",
            "06.05 03:09",
            "05.05 22:09",
            "11.04 23:01",
        )

        input.forEachIndexed { i, zonedDateTime ->
            println("Comparing expected `${expected[i]}` with ${zonedDateTime.getSummaryMatchTime()}")
           assertEquals(expected[i], zonedDateTime.getSummaryMatchTime())
        }
    }

    @Test
    fun `check if two dates are in the same week`() {
        val inputs = listOf(
            LocalDate.of(2023, 2, 26),
            LocalDate.of(2023, 2, 27),
            LocalDate.of(2023, 2, 28),
            LocalDate.of(2023, 3, 1),
            LocalDate.of(2023, 3, 2),
            LocalDate.of(2023, 3, 3),
            LocalDate.of(2023, 3, 4),
            LocalDate.of(2023, 3, 5),
            LocalDate.of(2023, 3, 6),
            LocalDate.of(2023, 3, 7),
            LocalDate.of(2023, 3, 8),
            LocalDate.of(2022, 3, 4),
            LocalDate.of(2023, 2, 4),
            LocalDate.of(2023, 4, 4),
        )
        val expected = listOf(
            false,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            false,
            false,
            false,
            false,
            false,
            false,
        )

        inputs.forEachIndexed { i, input ->
            val sampleDate = LocalDate.of(2023, 3, 2)
            val result = input.isSameWeek(sampleDate)
            println("Comparing [$i] expected `${input.dayOfWeek}` with ${sampleDate.dayOfWeek}" +
                    " as result: $result")
            assertEquals(expected[i], result)
        }
    }

    @Test
    fun `check if mapping to localDateTime works`() {
        val inputs = listOf<LocalDateTime>(
            LocalDateTime.of(2023, 5, 20, 20, 0)
        )
        val expected = listOf(
            LocalDateTime.of(2023, 5, 20, 17, 0)
        )

        inputs.forEachIndexed { i, input ->
            val result = input.formatUsingLocalZoneId()
            println("Comparing [$i] input: ${input}, expected `${expected[i]}` as result: $result")
            assertEquals(expected[i], result)
        }
    }
}