package app.pychen.testofnext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    var outputTxt : TextView? = null

    var board : ArrayList<Array<Array<String>>> = arrayListOf()

    val N = 4
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        outputTxt = findViewById(R.id.output)

        for (i in 0 until N) createBoard(i)

    }

    fun createBoard(index: Int){
        board.add(arrayOf(   //初始棋盤
            arrayOf(".", ".", ".", "."),
            arrayOf(".", ".", ".", "."),
            arrayOf(".", ".", ".", "."),
            arrayOf(".", ".", ".", "."))
        )

        if (solveNQUtil(board[index], 0, index)) outputTxt?.text = printSolution(board[index])
    }

    fun solveNQUtil(board: Array<Array<String>>, col: Int, row: Int): Boolean {
        if (col >= N) return true  //皇后全部放置完成

        for (i in row until N) {
            if (isSafe(board, i, col)) { //如果可以將皇后安全放置在這個位置上
                board[i][col] = "Q"  //放上皇后

                if (solveNQUtil(board, col + 1, 0) == true) return true  //嘗試以遞迴方式一個一個將皇后放置

                board[i][col] = "." //如果遞迴到沒辦法解決的地方 就將皇后全部收回來
                return false //為求多解法因此這邊失敗直接return false (僅N=4適用)
            }
        }

        return false  //該皇后沒辦法在該column上的任一row位置上safe 就return false
    }

    fun isSafe(board: Array<Array<String>>, row: Int, col: Int): Boolean {
        var i: Int
        var j: Int

        /* 檢查正左手邊是否有皇后 */
        i = 0
        while (i < col) {
            if (board[row][i] == "Q") return false
            i++
        }

        /* 檢查左上角是否有皇后*/
        i = row
        j = col
        while (i >= 0 && j >= 0) {
            if (board[i][j] == "Q") return false
            i--
            j--
        }

        /* 檢查左下角是否有皇后 */
        i = row
        j = col
        while (j >= 0 && i < N) {
            if (board[i][j] == "Q") return false
            i++
            j--
        }
        return true
    }

    fun printSolution(board: Array<Array<String>>) :String {
        var result = outputTxt?.text.toString() + "\n"
        for (i in 0 until 4) {
            for (j in 0 until 4) result += " ${board[i][j]} "
            result += "\n"
        }
        return result
    }

}