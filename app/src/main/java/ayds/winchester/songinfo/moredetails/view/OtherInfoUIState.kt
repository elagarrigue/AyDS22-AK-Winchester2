package ayds.winchester.songinfo.moredetails.view

data class OtherInfoUIState(
    val artistName : String = "",
    val description : String = "",
    val WikipediaUrl : String = "",
    val actionsEnabled: Boolean = false,
) {

    companion object {
        const val URL_IMAGE_WIKIPEDIA = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val URL_IMAGE_LASTFM = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
        const val URL_IMAGE_TIMES = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/NYC_Montage_2014_4_-_Jleon.jpg/456px-NYC_Montage_2014_4_-_Jleon.jpg"
    }
}