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

    fun getYearMonth(date: String):String{
        var new_year = date.substringBeforeLast("-")
        var new_month = date.substringAfterLast("-")

        return (getMonthName(new_month.toInt()) + ", " + new_year)
    }

    fun getYear(date: String):String{
        var new_year = date.substringBeforeLast("-")
        return (new_year +" "+ esBisiesto(new_year.toInt()))
    }

    fun getMonthName (month:Int):String{
        val month_name = when (month) {
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
        return month_name;
    }

    fun esBisiesto(year: Int) : String{
        var result = "(not a leap year)"
        if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0))
            result = "(it's a leap year)"
        return result
    }
}
