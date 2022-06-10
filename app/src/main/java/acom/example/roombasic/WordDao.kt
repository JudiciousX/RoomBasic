package acom.example.roombasic

import androidx.room.*

@Dao //Database access object  访问数据库操作的接口
//增删改查在这里面声明
interface WordDao {
    @Insert
    fun insertWords(vararg word: Word)

    @Update
    fun updateWords(vararg word: Word)

    @Delete
    fun deleteWords(vararg word: Word)

    //清空所有的数据
    @Query("DELETE FROM WORD")
    fun deleteAllWords()

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    fun getAllWords() : List<Word>
}