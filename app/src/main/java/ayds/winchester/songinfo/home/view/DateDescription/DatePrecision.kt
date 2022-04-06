
interface DateText{
    fun getTextDate(date: String, precision: String): String
}

object DateTextImpl : DateText{
    override fun getTextDate(date: String, precision: String): String {
        var resp="-"

        when (precision) {
            "day" -> resp = getYearMonthDay(date)
            "month" -> resp = getYearMonth(date)
            "year" -> resp = getYear(date)
            "" -> {}
        }

        return resp
    }


    fun getYearMonthDay(date: String):String{
        return date
    }

    fun getYearMonth(date: String):String{
        val year: String = date.substringBeforeLast("-")
        return year
    }

    fun getYear(date: String):String{
        return "3"
    }

}
