package acom.example.roombasic

import androidx.room.Database
import androidx.room.RoomDatabase
//版本改变后需要修改version
@Database(entities = [Word::class], version = 5, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun getWordDao() : WordDao
}