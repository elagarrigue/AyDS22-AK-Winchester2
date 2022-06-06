package ayds.winchester.songinfo.moredetails.model.repository.local.sqldb

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import ayds.winchester.songinfo.moredetails.model.entities.*


internal class DataBase(context: Context) : SQLiteOpenHelper(context, DICTIONARY_DB, null, DATABASE_VERSION) {
    private val dataBaseColumns = arrayOf(
        ID,
        ARTIST,
        INFO,
        URL_PAGE,
        SOURCE
    )

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String, info: String, urlPage: String, source: Int) {
        val dataBase = this.writableDatabase
        val values = createValueMap(artist,info,urlPage, source)
        dataBase.insert(ARTISTS, null, values)
    }

    private fun createValueMap (artist: String, info: String, urlPage : String, source : Int): ContentValues {
        val values = ContentValues()
        values.put(ARTIST, artist)
        values.put(INFO, info)
        values.put(SOURCE, source)
        values.put(URL_PAGE, urlPage)
        return values
    }

    fun getInfo(dbHelper: DataBase, artist: String): List<Card>{
        val cursor = createCursor(dbHelper,artist)
        val cards = createDescription(cursor)
        cursor.close()
        return cards
    }

    fun updateArtistTerm(query : String,pageId : String){
        val values = ContentValues().apply {
            put(ARTIST, query)
        }
        writableDatabase?.update(ARTISTS, values, "$URL_PAGE LIKE ?", arrayOf(pageId))
    }

    private fun createCursor (dbHelper: DataBase, artist: String) : Cursor{
        val dataBase = dbHelper.readableDatabase
        val selectionArgs = arrayOf(artist)
        return dataBase.query(ARTISTS, dataBaseColumns, SELECTION, selectionArgs, null, null, SORT_ORDER)
    }

    private fun createDescription(cursor: Cursor): List<Card> {
        var cards : MutableList<Card> = mutableListOf()
        with(cursor) {
            while (moveToNext()) {
                val info = cursor.getString(
                    cursor.getColumnIndexOrThrow(INFO)
                )
                val urlPage = cursor.getString(
                    cursor.getColumnIndexOrThrow(URL_PAGE)
                )
                val source = Source.values()[getInt((getColumnIndexOrThrow(SOURCE)))]

                cards.add(CardDescription(info, urlPage, source, ""))
            }
        }
        return cards
    }
}