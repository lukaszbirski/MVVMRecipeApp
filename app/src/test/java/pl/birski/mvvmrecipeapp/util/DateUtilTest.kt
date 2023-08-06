package pl.birski.mvvmrecipeapp.util

import org.junit.jupiter.api.Test
import java.util.Date

class DateUtilTest {

    @Test
    fun `map date to splash text, date is correctly mapper`() {
        // tested
        val result = DateUtil.dateToSlashText(dateFake)

        // assert
        assert(result == dateTextSlash)
    }

    @Test
    fun `map date to dash text, date is correctly mapper`() {
        // tested
        val result = DateUtil.dateToDashText(dateFake)

        // assert
        assert(result == dateTextDash)
    }

    @Test
    fun `map long to date, long is correctly mapper`() {
        // tested
        val result = DateUtil.longToDate(dateAsLongFake)

        // assert
        assert(result == dateFake)
    }

    @Test
    fun `map date to long in secs, date is correctly mapper`() {
        // tested
        val result = DateUtil.dateToLongInSecs(dateFake)

        // assert
        assert(result == dateAsLongInSecsFake)
    }

    companion object {
        private const val dateTextSlash = "06/08/2023"
        private const val dateTextDash = "06-08-2023"
        private const val dateAsLongFake = 1691355453178L
        private const val dateAsLongInSecsFake = dateAsLongFake / 1000
        private val dateFake = Date(dateAsLongFake)
    }
}
