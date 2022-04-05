package ayds.winchester.songinfo.home.model.entities

import PrecisionDate

interface Song {
    val id: String
    val songName: String
    val artistName: String
    val albumName: String
    val releaseDate: String
    val spotifyUrl: String
    val imageUrl: String
    var isLocallyStored: Boolean
    var releasedPrecisionDate: String
}

data class SpotifySong(
  override val id: String,
  override val songName: String,
  override val artistName: String,
  override val albumName: String,
  override val releaseDate: String,
  override val spotifyUrl: String,
  override val imageUrl: String,
  override var isLocallyStored: Boolean = false,
  override var releasedPrecisionDate: String
) : Song


object EmptySong : Song {
    override val id: String = ""
    override val songName: String = ""
    override val artistName: String = ""
    override val albumName: String = ""
    override val releaseDate: String = ""
    override val spotifyUrl: String = ""
    override val imageUrl: String = ""
    override var isLocallyStored: Boolean = false
    override var releasedPrecisionDate: String = ""
}