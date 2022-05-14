package ayds.winchester.songinfo.home.view

import DateText
import ayds.winchester.songinfo.home.model.entities.EmptySong
import ayds.winchester.songinfo.home.model.entities.Song
import ayds.winchester.songinfo.home.model.entities.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl(private val dateConverter: DateText) : SongDescriptionHelper{

    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release date: ${dateConverter.getTextDate(song.releaseDate,song.releasedPrecisionDate)}"
            else -> "Song not found"
        }
    }
}