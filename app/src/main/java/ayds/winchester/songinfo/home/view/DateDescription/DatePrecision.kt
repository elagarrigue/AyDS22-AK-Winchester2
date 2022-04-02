package ayds.winchester.songinfo.home.view

import PrecisionDate


interface DateText{
    fun getTextDate(date: String, precision: PrecisionDate): String
}

object DateTextImpl : DateText{
    override fun getTextDate(date: String, precision: PrecisionDate): String{
        var resp="-"

        when (precision) {
            PrecisionDate.Day -> resp=getYearMonthDay(date)
            PrecisionDate.Month -> resp=getYearMonth(date)
            PrecisionDate.Year -> resp=getYear(date)
            PrecisionDate.Empty -> {}
        }

        return resp
    }


    fun getYearMonthDay(date: String):String{
        return "-"
    }

    fun getYearMonth(date: String):String{
        return "-"
    }

    fun getYear(date: String):String{
        return "-"
    }

}
