package acom.example.roombasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import androidx.room.migration.Migration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wordDatabase = Room.databaseBuilder(this, WordDatabase::class.java, "word_database")
            .allowMainThreadQueries()
            //.fallbackToDestructiveMigration()
            //将现有的数据全部清空 创建新的数据库
            .addMigrations(MIGRATION_4_5)
            .build()
        val wordDao = wordDatabase!!.getWordDao()

        val textView = findViewById<TextView>(R.id.textView)
        updateView(wordDao, textView)
        val buttonInsert = findViewById<Button>(R.id.button_insert)
        buttonInsert.setOnClickListener {
            Log.d("xxx","xxx")
            val word1 = Word("Hello", "你好")
            val word2 = Word("World", "世界")
            wordDao?.insertWords(word1, word2)
            updateView(wordDao, textView)
        }
        val buttonClear = findViewById<Button>(R.id.button_clear)
        buttonClear.setOnClickListener {
            val word1 = Word("Hi", "你好啊")
            word1.setId(17)
            wordDao?.deleteWords(word1)
            updateView(wordDao, textView)
        }
        val buttonDelete = findViewById<Button>(R.id.button_delete)
        buttonDelete.setOnClickListener {
            wordDao?.deleteAllWords()
            updateView(wordDao, textView)
        }
        val buttonUpdate = findViewById<Button>(R.id.button_update)
        buttonUpdate.setOnClickListener {
            val word1 = Word("Hi", "你好啊")
            word1.setId(20)
            wordDao?.updateWords(word1)
            updateView(wordDao, textView)
        }
    }


    fun updateView(wordDao: WordDao, textView: TextView) {
        val list = wordDao.getAllWords()
        var str = ""
        if (list != null) {
            for(word in list) {
                str += "${word.getId()} : ${word.getWord()} = ${word.getChineseMeaning()} \n"
            }
        }
        textView.text = str
    }

    companion object {
        var MIGRATION_3_4 : Migration = Migration(3, 4) {
            it.execSQL("ALTER TABLE word ADD COLUMN num INTEGER NOT NULL DEFAULT 1")
        }

        var MIGRATION_4_5 : Migration = Migration(4, 5) {
            //第一步 创建一个新的表 并写成想要的字段
            it.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL, english_word TEXT," +
                    "chinese_meaning TEXT)")
            //将原来数据库中的数据复制过去
            it.execSQL("INSERT INTO word_temp (id, english_word, chinese_meaning) " +
                    "SELECT id, english_word, chinese_meaning FROM word")
            //删除掉旧的数据库
            it.execSQL("DROP TABLE word")
            //将新数据库重新命名
            it.execSQL("ALTER TABLE word_temp RENAME TO word")
        }
    }

}