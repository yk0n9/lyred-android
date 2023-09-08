package cc.ykong.lyred

import android.os.Environment
import com.lzf.easyfloat.EasyFloat
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
    if (sd.exists()) {
        val jsonString = jsonFile.readText()
        val mapping = Json.decodeFromString<MutableMap<Char, Pos>>(jsonString)
        Map.mapping = mapping

        for (tag in Control.TAG) {
            when (tag.value) {
                "x1" -> {
                    Map.mapping['Q']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['Q']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "x2" -> {
                    Map.mapping['W']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['W']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "x3" -> {
                    Map.mapping['E']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['E']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "x4" -> {
                    Map.mapping['R']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['R']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "x5" -> {
                    Map.mapping['T']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['T']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "x6" -> {
                    Map.mapping['Y']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['Y']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "x7" -> {
                    Map.mapping['U']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['U']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "y2" -> {
                    Map.mapping['A']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['A']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                "y3" -> {
                    Map.mapping['Z']?.x?.let { EasyFloat.updateFloat(tag.value, x = it) }
                    Map.mapping['Z']?.y?.let { EasyFloat.updateFloat(tag.value, y = it) }
                }

                else -> {}
            }
        }
    }
}

fun saveLocation() {
    for (tag in Control.TAG) {
        val pos = IntArray(2)
        EasyFloat.getFloatView(tag.value)?.getLocationOnScreen(pos)
        val x = pos[X]
        val y = pos[Y]
        when (tag.value) {
            "x1" -> {
                Map.mapping['Q']?.y = y
                Map.mapping['W']?.y = y
                Map.mapping['E']?.y = y
                Map.mapping['R']?.y = y
                Map.mapping['T']?.y = y
                Map.mapping['Y']?.y = y
                Map.mapping['U']?.y = y
                Map.mapping['Q']?.x = x
                Map.mapping['A']?.x = x
                Map.mapping['Z']?.x = x
            }

            "x2" -> {
                Map.mapping['W']?.x = x
                Map.mapping['S']?.x = x
                Map.mapping['X']?.x = x
            }

            "x3" -> {
                Map.mapping['E']?.x = x
                Map.mapping['D']?.x = x
                Map.mapping['C']?.x = x
            }

            "x4" -> {
                Map.mapping['R']?.x = x
                Map.mapping['F']?.x = x
                Map.mapping['V']?.x = x
            }

            "x5" -> {
                Map.mapping['T']?.x = x
                Map.mapping['G']?.x = x
                Map.mapping['B']?.x = x
            }

            "x6" -> {
                Map.mapping['Y']?.x = x
                Map.mapping['H']?.x = x
                Map.mapping['N']?.x = x
            }

            "x7" -> {
                Map.mapping['U']?.x = x
                Map.mapping['J']?.x = x
                Map.mapping['M']?.x = x
            }

            "y2" -> {
                Map.mapping['A']?.y = y
                Map.mapping['S']?.y = y
                Map.mapping['D']?.y = y
                Map.mapping['F']?.y = y
                Map.mapping['G']?.y = y
                Map.mapping['H']?.y = y
                Map.mapping['J']?.y = y
            }

            "y3" -> {
                Map.mapping['Z']?.y = y
                Map.mapping['X']?.y = y
                Map.mapping['C']?.y = y
                Map.mapping['V']?.y = y
                Map.mapping['B']?.y = y
                Map.mapping['N']?.y = y
                Map.mapping['M']?.y = y
            }

            else -> {}
        }
    }
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