package ayds.winchester.songinfo.moredetails.model.repository.local.sqldb

const val ID = "id"
const val PAGE_ID = "pageId"
const val ARTIST = "artist"
const val ARTISTS = "artists"
const val INFO = "info"
const val SOURCE = "source"
const val SELECTION = "artist  = ?"
const val SELECTION_BY_ID = "pageId  = ?"
const val SORT_ORDER = "artist DESC"
const val DICTIONARY_DB = "dictionary.db"
const val DATABASE_VERSION = 1
const val CREATE_QUERY =
    "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer, pageId string)"