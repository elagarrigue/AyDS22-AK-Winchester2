package ayds.winchester.songinfo.home.model.repository.local.spotify.sqldb

const val SONGS_TABLE = "songs"
const val ID_COLUMN = "id"
const val TERM_COLUMN = "term"
const val NAME_COLUMN = "name"
const val ARTIST_COLUMN = "artist"
const val ALBUM_COLUMN = "album"
const val RELEASE_DATE_COLUMN = "release_date"
const val SPOTIFY_URL_COLUMN = "spotify_url"
const val IMAGE_URL_COLUMN = "image_url"
const val PRECISION_DATE_COLUMN = "precision_date"

const val createSongsTableQuery: String =
    "create table $SONGS_TABLE (" +
            "$ID_COLUMN string PRIMARY KEY, " +
            "$TERM_COLUMN string, " +
            "$NAME_COLUMN string, " +
            "$ARTIST_COLUMN integer, " +
            "$ALBUM_COLUMN string, " +
            "$RELEASE_DATE_COLUMN string, " +
            "$SPOTIFY_URL_COLUMN string, " +
            "$IMAGE_URL_COLUMN string)"

const val addReleaseDataQuery: String =
    "ALTER TABLE $SONGS_TABLE ADD COLUMN $PRECISION_DATE_COLUMN string DEFAULT('year')"

const val removeDeleteSongsTableQuery: String =
    "DROP TABLE $SONGS_TABLE"