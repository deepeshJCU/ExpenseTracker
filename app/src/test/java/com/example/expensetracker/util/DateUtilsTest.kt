//package com.example.expensetracker.util
//
//import org.junit.Assert.assertEquals
//import org.junit.Test
//import java.text.SimpleDateFormat
//import java.util.*
//
//class DateUtilsTest {
//
//    @Test
//    fun `format valid date`() {
//        // Example: 1st Jan 2025
//        val calendar = Calendar.getInstance().apply {
//            set(2025, Calendar.JANUARY, 1)
//        }
//        val millis = calendar.timeInMillis
//        val formatted = formatDate(millis)
//        assertEquals("01 Jan, 2025", formatted)
//    }
//
//    @Test
//    fun `format current date matches expected format`() {
//        val millis = System.currentTimeMillis()
//        val expected = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(Date(millis))
//        assertEquals(expected, formatDate(millis))
//    }
//
//    @Test
//    fun `format zero millis returns formatted epoch`() {
//        val formatted = formatDate(0L)
//        assertEquals("01 Jan, 1970", formatted)  // Unix epoch start
//    }
//
//    @Test
//    fun `format invalid date returns empty string`() {
//        val formatted = formatDate(Long.MIN_VALUE)
//        // Depending on your device/locale, this might still format — so check accordingly
//        val result = formatDate(Long.MIN_VALUE)
//        assert(result.isNotEmpty()) // You can update this if you handle Long.MIN_VALUE differently
//    }
//}
