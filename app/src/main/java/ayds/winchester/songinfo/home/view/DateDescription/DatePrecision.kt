
interface DateText{
    fun getTextDate(date: String, precision: String): String
}

object DateTextImpl : DateText{
    private const val year_month_day : String = "day"
    private const val year_month : String = "month"
    private const val year : String = "year"

    override fun getTextDate(date: String, precision: String): String {
        var value_answer = ""
        when (precision) {
            year_month_day -> value_answer = getYearMonthDay(date)
            year_month -> value_answer = getYearMonth(date)
            year -> value_answer = getYear(date)
        }
        return value_answer
    }

    fun getYearMonthDay(date: String):String{
        return date.replace("-", "/")
    }

    fun getYearMonth(date: String):String{
        var new_year = date.substringBeforeLast("-")
        var new_month = date.substringAfterLast("-")

        return (getMonthName(new_month.toInt()) + ", " + new_year)
    }

    fun getYear(date: String):String{
        return date.substringBeforeLast("-")
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
}
