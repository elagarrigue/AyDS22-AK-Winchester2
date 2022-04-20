package ayds.winchester.songinfo.moredetails.fulllogic

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.ArrayList

class DataBase(context: Context?) : SQLiteOpenHelper(context, "dictionary.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
        )
        Log.i("DB", "DB created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?) {
        val dataBase = this.writableDatabase
        val values = createValueMap(artist,info)
        val newRowId = dataBase.insert("artists", null, values)
    }

    private fun createValueMap (artist: String?, info: String?): ContentValues {
        val values = ContentValues()
        values.put("artist", artist)
        values.put("info", info)
        values.put("source", 1)
        return values
    }

    fun getInfo(dbHelper: DataBase, artist: String): String? {
        val cursor = createCursor(dbHelper,artist)
        var items = createItemsList(cursor)
        cursor.close()
        return if (items.isEmpty()) null else items[0]
    }

    private fun createCursor (dbHelper: DataBase, artist: String) : Cursor{
        val dataBase = dbHelper.readableDatabase
        val dataBaseColumns = arrayOf(
            "id",
            "artist",
            "info"
        )
        val selection = "artist  = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = "artist DESC"
        return dataBase.query("artists", dataBaseColumns, selection, selectionArgs, null, null, sortOrder)
    }

    private fun createItemsList(cursor: Cursor): MutableList<String> {
        val items: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            val info = cursor.getString(
                cursor.getColumnIndexOrThrow("info")
            )
            items.add(info)
        }
        return items
    }
}