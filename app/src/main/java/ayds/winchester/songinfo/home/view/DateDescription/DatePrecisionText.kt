import ayds.winchester.songinfo.home.model.entities.DatePrecision

interface DateText{
    fun getTextDate(date: String, precision: DatePrecision): String
}

internal class DateTextImpl : DateText{

    override fun getTextDate(date: String, precision: DatePrecision) =
        when (precision) {
            DatePrecision.DAY -> getYearMonthDay(date)
            DatePrecision.MONTH -> getYearMonth(date)
            DatePrecision.YEAR -> getYear(date)
        }

    private fun getYearMonthDay(date: String):String{
        return date.replace("-", "/")
    }

    private fun getYearMonth(date: String):String{
        val newYear = date.substringBeforeLast("-")
        val newMonth = date.substringAfterLast("-")

        return (getMonthName(newMonth.toInt()) + ", " + newYear)
    }

    private fun getYear(date: String):String{
        val newYear = date.substringBeforeLast("-")
        return (newYear +" "+ itsLeap(newYear.toInt()))
    }

    private fun getMonthName (month:Int):String{
        val monthName = when (month) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> {""}
        }
        return monthName
    }

    private fun itsLeap(year: Int) : String {
        return (if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)) "(it's a leap year)" else "(not a leap year)")
    }
}
