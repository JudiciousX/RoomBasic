package acom.example.roombasic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Word (){
    @PrimaryKey(autoGenerate = true)
    private var id:Int = 0

    @ColumnInfo(name = "english_word")
    private var word: String? = null


    @ColumnInfo(name = "chinese_meaning")
    private var chineseMeaning : String? = null






    constructor(word: String, chineseMeaning: String) : this(){
        this.word = word
        this.chineseMeaning = chineseMeaning
    }



    fun setWord(word: String){
        this.word = word
    }

    fun getWord() : String? {
        return word
    }

    fun setChineseMeaning(chineseMeaning : String) {
        this.chineseMeaning = chineseMeaning
    }

    fun getChineseMeaning() : String? {
        return chineseMeaning
    }

    fun getId() : Int {
        return id
    }

    fun setId(id : Int) {
        this.id = id
    }




}