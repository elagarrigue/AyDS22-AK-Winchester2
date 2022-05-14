package ayds.winchester.songinfo.moredetails.model.repository.local.sqldb

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import ayds.winchester.songinfo.moredetails.model.entities.ArtistDescription
import ayds.winchester.songinfo.moredetails.model.entities.Description


internal class DataBase(context: Context) : SQLiteOpenHelper(context, DICTIONARY_DB, null, DATABASE_VERSION) {
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
        val description = createDescription(cursor)
        cursor.close()
        return description
    }

    fun getInfoById(dbHelper: DataBase, id: String): Description?{
        val cursor = createCursorById(dbHelper,id)
        val description = createDescription(cursor)
        cursor.close()
        return description
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

    private fun createDescription(cursor: Cursor): Description? {

        with(cursor) {
            if (moveToNext()) {

                val info = cursor.getString(
                    cursor.getColumnIndexOrThrow(INFO)
                )
                val pageId = cursor.getString(
                    cursor.getColumnIndexOrThrow(PAGE_ID)
                )

                return ArtistDescription(pageId,info)

            }
        }
        return null
    }
}