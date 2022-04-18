package ayds.winchester.songinfo.home.model.repository.external.spotify.tracks

import ayds.winchester.songinfo.home.model.entities.DatePrecision

interface SongPrecisionDateHelper {
    fun getPrecisionDate(precision: String) : DatePrecision
}

private const val DAY = "day"
private const val MONTH = "month"
private const val YEAR = "year"

internal class SongPrecisionDateHelperImpl : SongPrecisionDateHelper{

    override fun getPrecisionDate(precision: String): DatePrecision {
        return when (precision){
            DAY -> DatePrecision.valueOf(DAY)
            MONTH -> DatePrecision.valueOf(MONTH)
            YEAR -> DatePrecision.valueOf(YEAR)
            else -> {throw Exception("unidentified date precision")}
        }
    }
}