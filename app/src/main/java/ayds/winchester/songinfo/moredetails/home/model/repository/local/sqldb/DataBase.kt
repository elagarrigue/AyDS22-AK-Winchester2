package ayds.winchester.songinfo.moredetails.home.model.repository.local.sqldb

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import ayds.winchester.songinfo.moredetails.home.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.home.model.entities.Description
import java.util.ArrayList


class DataBase(context: Context) : SQLiteOpenHelper(context, DICTIONARY_DB, null, DATABASE_VERSION) {
    private val dataBaseColumns = arrayOf(
        ID,
        ARTIST,
        INFO,
        PAGE_ID
    )

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String, info: String, pageId: String) {
        val dataBase = this.writableDatabase
        val values = createValueMap(artist,info,pageId)
        dataBase.insert(ARTISTS, null, values)
    }

    private fun createValueMap (artist: String, info: String, pageId : String): ContentValues {
        val values = ContentValues()
        values.put(ARTIST, artist)
        values.put(INFO, info)
        values.put(SOURCE, 1)
        values.put(PAGE_ID, pageId)
        return values
    }

    fun getInfo(dbHelper: DataBase, artist: String): Description?{
        val cursor = createCursor(dbHelper,artist)
        val items = createItemsList(cursor)
        cursor.close()
        return if (items.isEmpty()) null else ArtistDescription(items[1],items[0])
    }

    fun getInfoById(dbHelper: DataBase, id: String): String?{
        val cursor = createCursorById(dbHelper,id)
        val items = createItemsList(cursor)
        cursor.close()
        return if (items.isEmpty()) null else items[0]
    }

    fun updateArtistTerm(query : String,pageId : String){
        val values = ContentValues().apply {
            put(ARTIST, query)
        }
        writableDatabase?.update(ARTISTS, values, "$PAGE_ID LIKE ?", arrayOf(pageId))
    }

    private fun createCursorById (dbHelper: DataBase, id: String) : Cursor{
        val dataBase = dbHelper.readableDatabase
        val selectionArgs = arrayOf(id)
        return dataBase.query(ARTISTS, dataBaseColumns, SELECTION_BY_ID, selectionArgs, null, null, SORT_ORDER)
    }

    private fun createCursor (dbHelper: DataBase, artist: String) : Cursor{
        val dataBase = dbHelper.readableDatabase
        val selectionArgs = arrayOf(artist)
        return dataBase.query(ARTISTS, dataBaseColumns, SELECTION, selectionArgs, null, null, SORT_ORDER)
    }

    private fun createItemsList(cursor: Cursor): MutableList<String> {
        val items: MutableList<String> = ArrayList()
        with(cursor) {
            if (moveToNext()) {
                val info = cursor.getString(
                    cursor.getColumnIndexOrThrow(INFO)
                )
                val pageId = cursor.getString(
                    cursor.getColumnIndexOrThrow(PAGE_ID)
                )
                items.add(info)
                items.add(pageId)
            }
        }
        return items
    }
}