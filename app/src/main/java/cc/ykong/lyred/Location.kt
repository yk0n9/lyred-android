package cc.ykong.lyred

import android.os.Environment
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

object Map {
    var mapping = mutableMapOf(
        'Z' to zero(),
        'X' to zero(),
        'C' to zero(),
        'V' to zero(),
        'B' to zero(),
        'N' to zero(),
        'M' to zero(),
        'A' to zero(),
        'S' to zero(),
        'D' to zero(),
        'F' to zero(),
        'G' to zero(),
        'H' to zero(),
        'J' to zero(),
        'Q' to zero(),
        'W' to zero(),
        'E' to zero(),
        'R' to zero(),
        'T' to zero(),
        'Y' to zero(),
        'U' to zero(),
    )
}

@Serializable
data class Pos(var x: Int, var y: Int)

fun zero() = Pos(0, 0)

const val X = 0
const val Y = 1

fun readLocation() {
    val sd = Environment.getExternalStorageDirectory()
    val jsonFile = File(sd, "mapping.json")
}

fun saveLocation() {
    val jsonString = Json.encodeToString(Map.mapping)
    val sd = Environment.getExternalStorageDirectory()
    val file = File(sd, "mapping.json")
    if (sd.exists()) {
        val fos = FileOutputStream(file)
        val osw = OutputStreamWriter(fos, "UTF-8")
        osw.write(jsonString)

        osw.flush()
        fos.flush()

        osw.close()
        fos.close()
    }
}

fun press(key: Int) {
    when (key) {
        24 -> Map.mapping['Z']
        26 -> Map.mapping['X']
        28 -> Map.mapping['C']
        29 -> Map.mapping['V']
        31 -> Map.mapping['B']
        33 -> Map.mapping['N']
        35 -> Map.mapping['M']
        36 -> Map.mapping['Z']
        38 -> Map.mapping['X']
        40 -> Map.mapping['C']
        41 -> Map.mapping['V']
        43 -> Map.mapping['B']
        45 -> Map.mapping['N']
        47 -> Map.mapping['M']
        48 -> Map.mapping['Z']
        50 -> Map.mapping['X']
        52 -> Map.mapping['C']
        53 -> Map.mapping['V']
        55 -> Map.mapping['B']
        57 -> Map.mapping['N']
        59 -> Map.mapping['M']
        60 -> Map.mapping['A']
        62 -> Map.mapping['S']
        64 -> Map.mapping['D']
        65 -> Map.mapping['F']
        67 -> Map.mapping['G']
        69 -> Map.mapping['H']
        71 -> Map.mapping['J']
        72 -> Map.mapping['Q']
        74 -> Map.mapping['W']
        76 -> Map.mapping['E']
        77 -> Map.mapping['R']
        79 -> Map.mapping['T']
        81 -> Map.mapping['Y']
        83 -> Map.mapping['U']
        84 -> Map.mapping['Q']
        86 -> Map.mapping['W']
        88 -> Map.mapping['E']
        89 -> Map.mapping['R']
        91 -> Map.mapping['T']
        93 -> Map.mapping['Y']
        95 -> Map.mapping['U']
        else -> {}
    }
}