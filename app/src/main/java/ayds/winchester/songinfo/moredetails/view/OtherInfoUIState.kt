package ayds.winchester.songinfo.moredetails.view

data class OtherInfoUIState(
    var artistName : String = "",
    var description : String = "",
    var actionsEnabled: MutableList<Boolean> = mutableListOf(false, false, false),
    var urlWikipedia : String = "",
    var urlNYTimes : String = "",
    var urlLastFM : String = "",
) {

    companion object {
        const val URL_IMAGE_WIKIPEDIA = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val URL_IMAGE_LASTFM = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
        const val URL_IMAGE_TIMES = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/NYC_Montage_2014_4_-_Jleon.jpg/456px-NYC_Montage_2014_4_-_Jleon.jpg"
        const val URL_IMAGE_NOT_FOUND = "https://alfabetajuega.com/hero/2018/10/167687.alfabetajuega-problemas-tecnicos.jpg"
    }
}