package com.zzzh.akhalteke_shipper

import com.zzzh.akhalteke_shipper.bean.StringInfo
import com.zzzh.akhalteke_shipper.utils.ToolUtils
import org.junit.Test

import org.junit.Assert.*
import java.io.*
import java.lang.Thread.sleep
import kotlin.math.log

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    enum class Color(val rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
    }

    @Test
    fun stringTest() {
//        print(phoneEncry("12344445555"))
//        print(readFile("D:\\city.txt"))
        writeFile("D:\\citys.txt", readFile("D:\\city"))
    }

    fun phoneEncry(phoneStr: String): String {
        if (ToolUtils.isEmpty(phoneStr)) {
            return ""
        }

        if (phoneStr.length != 11) {
            return phoneStr
        }

        val str01 = phoneStr.substring(0, 3)
        val str02 = phoneStr.substring(7, 11)
        return "$str01****$str02"
    }


    @Throws(IOException::class)
    fun readFile(path: String): String {
        val file = File(path)
        if (!file.exists() || file.isDirectory) {
            throw FileNotFoundException()
        }
        val br = BufferedReader(InputStreamReader(FileInputStream(path), "GBK"))
        val sb = StringBuffer()
        sb.append("{")
        var temp: String? = null
        while ((br.readLine().also { temp = it }) != null) {
//            sb.append(temp)
            if (temp != null && temp != "") {
                val temps = temp!!.split("=")
                if (temps.size == 2) {
                    sb.append("\"${temps[0]}\":\"${temps[1].trim()}\",")
                }
            }
        }
        br.close()
        sb.append("}")
        return sb.toString()
    }

    @Throws(IOException::class)
    fun writeFile(path: String, content: String) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        val fw = FileWriter(file)
        fw.write(content)
        fw.flush()
        fw.close()
    }

    @Test
    fun getRandom() {
        for (i in 0..99){
            val oneStr = ToolUtils.getOneInt()
            sleep(1000)
            println(oneStr)
        }

    }

}
