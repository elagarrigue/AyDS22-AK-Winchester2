package ayds.winchester.songinfo.moredetails.home.model.repository.local

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.util.ArrayList

private const val ID = "id"
private const val ARTIST = "artist"
private const val ARTISTS = "artists"
private const val INFO = "info"
private const val SOURCE = "source"
private const val SELECTION = "artist  = ?"
private const val SORT_ORDER = "artist DESC"
private const val DICTIONARY_DB = "dictionary.db"
private const val DATABASE_VERSION = 1
private const val CREATE_QUERY = "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"

class DataBase(context: Context) : SQLiteOpenHelper(context, DICTIONARY_DB, null, DATABASE_VERSION) {
    private val dataBaseColumns = arrayOf(
        ID,
        ARTIST,
        INFO
    )

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String, info: String) {
        val dataBase = this.writableDatabase
        val values = createValueMap(artist,info)
        dataBase.insert(ARTISTS, null, values)
    }

    private fun createValueMap (artist: String, info: String): ContentValues {
        val values = ContentValues()
        values.put(ARTIST, artist)
        values.put(INFO, info)
        values.put(SOURCE, 1)
        return values
    }

    fun getInfo(dbHelper: DataBase, artist: String): String?{
        val cursor = createCursor(dbHelper,artist)
        val items = createItemsList(cursor)
        cursor.close()
        return if (items.isEmpty()) null else items[0]
    }

    fun getInfoById(dbHelper: DataBase, id: String): String?{
        val cursor = createCursorById(dbHelper,id)
        val items = createItemsList(cursor)
        cursor.close()
        return if (items.isEmpty()) null else items[0]
    }

    private fun createCursorById (dbHelper: DataBase, id: String) : Cursor{
        val dataBase = dbHelper.readableDatabase
        val selectionArgs = arrayOf(id)
        return dataBase.query(ID, dataBaseColumns, SELECTION, selectionArgs, null, null, SORT_ORDER)
    }

    private fun createCursor (dbHelper: DataBase, artist: String) : Cursor{
        val dataBase = dbHelper.readableDatabase
        val selectionArgs = arrayOf(artist)
        return dataBase.query(ARTISTS, dataBaseColumns, SELECTION, selectionArgs, null, null, SORT_ORDER)
    }

    private fun createItemsList(cursor: Cursor): MutableList<String> {
        val items: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            val info = cursor.getString(
                cursor.getColumnIndexOrThrow(INFO)
            )
            items.add(info)
        }
        return items
    }
}