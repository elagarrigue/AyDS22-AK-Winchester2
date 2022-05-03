package ayds.winchester.songinfo.moredetails.home.view


interface MoreDetailsUiEvent {
    val name: String
}

data class MoreDetailsUiEventImpl(
    override val name: String
) : MoreDetailsUiEvent