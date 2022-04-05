
interface DateText{
    fun getTextDate(date: String, precision: String): String
}

object DateTextImpl : DateText{
    override fun getTextDate(date: String, precision: String): String {
        var resp="-"

        when (precision) {
            "day" -> resp = date
            "month" -> resp = date
            "year" -> resp = getYear(date)
            else -> resp = date
        }

        return resp
    }


    fun getYear(date: String):String{
        var  resp = date

        if(!esBisiesto((date.substringBeforeLast("-")).toInt()))
            resp += " (not a leap year)"

        return resp
    }

    fun esBisiesto(year : Int) : Boolean{
        return false
    }

}
