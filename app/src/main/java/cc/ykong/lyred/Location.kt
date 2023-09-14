package cc.ykong.lyred

import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Path
import android.view.MotionEvent
import android.widget.*
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import kotlin.math.max

object Map {
    private var path = Path()

    fun click(it: Pos) {
        path.moveTo(it.x, it.y)
        service?.dispatchGesture(
            GestureDescription
                .Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0, 50))
                .build(),
            null,
            null
        )
    }

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
data class Pos(var x: Float, var y: Float)

fun zero() = Pos(0f, 0f)

fun setMap(c: Char, event: MotionEvent) {
    Map.mapping[c]?.x = event.rawX
    Map.mapping[c]?.y = event.rawY
}

@SuppressLint("SdCardPath")
fun readLocation(): Boolean {
    val jsonFile = File("/data/data/cc.ykong.lyred/mapping.json")
    if (jsonFile.exists()) {
        Map.mapping = Json.decodeFromString(jsonFile.readText())
        return true
    }
    return false
}

@SuppressLint("SdCardPath")
fun saveLocation() {
    val jsonString = Json.encodeToString(Map.mapping)
    val file = File("/data/data/cc.ykong.lyred/mapping.json")
    if (!file.exists()) {
        file.createNewFile()
    }
    val fos = FileOutputStream(file)
    val osw = OutputStreamWriter(fos, "UTF-8")
    osw.write(jsonString)

    osw.flush()
    fos.flush()

    osw.close()
    fos.close()
}

fun press(key: Int) {
    when (key) {
        24 -> Map.mapping['Z']?.let { Map.click(it) }
        26 -> Map.mapping['X']?.let { Map.click(it) }
        28 -> Map.mapping['C']?.let { Map.click(it) }
        29 -> Map.mapping['V']?.let { Map.click(it) }
        31 -> Map.mapping['B']?.let { Map.click(it) }
        33 -> Map.mapping['N']?.let { Map.click(it) }
        35 -> Map.mapping['M']?.let { Map.click(it) }
        36 -> Map.mapping['Z']?.let { Map.click(it) }
        38 -> Map.mapping['X']?.let { Map.click(it) }
        40 -> Map.mapping['C']?.let { Map.click(it) }
        41 -> Map.mapping['V']?.let { Map.click(it) }
        43 -> Map.mapping['B']?.let { Map.click(it) }
        45 -> Map.mapping['N']?.let { Map.click(it) }
        47 -> Map.mapping['M']?.let { Map.click(it) }
        48 -> Map.mapping['Z']?.let { Map.click(it) }
        50 -> Map.mapping['X']?.let { Map.click(it) }
        52 -> Map.mapping['C']?.let { Map.click(it) }
        53 -> Map.mapping['V']?.let { Map.click(it) }
        55 -> Map.mapping['B']?.let { Map.click(it) }
        57 -> Map.mapping['N']?.let { Map.click(it) }
        59 -> Map.mapping['M']?.let { Map.click(it) }
        60 -> Map.mapping['A']?.let { Map.click(it) }
        62 -> Map.mapping['S']?.let { Map.click(it) }
        64 -> Map.mapping['D']?.let { Map.click(it) }
        65 -> Map.mapping['F']?.let { Map.click(it) }
        67 -> Map.mapping['G']?.let { Map.click(it) }
        69 -> Map.mapping['H']?.let { Map.click(it) }
        71 -> Map.mapping['J']?.let { Map.click(it) }
        72 -> Map.mapping['Q']?.let { Map.click(it) }
        74 -> Map.mapping['W']?.let { Map.click(it) }
        76 -> Map.mapping['E']?.let { Map.click(it) }
        77 -> Map.mapping['R']?.let { Map.click(it) }
        79 -> Map.mapping['T']?.let { Map.click(it) }
        81 -> Map.mapping['Y']?.let { Map.click(it) }
        83 -> Map.mapping['U']?.let { Map.click(it) }
        84 -> Map.mapping['Q']?.let { Map.click(it) }
        86 -> Map.mapping['W']?.let { Map.click(it) }
        88 -> Map.mapping['E']?.let { Map.click(it) }
        89 -> Map.mapping['R']?.let { Map.click(it) }
        91 -> Map.mapping['T']?.let { Map.click(it) }
        93 -> Map.mapping['Y']?.let { Map.click(it) }
        95 -> Map.mapping['U']?.let { Map.click(it) }
        else -> {}
    }
}

@SuppressLint("ClickableViewAccessibility", "UseSwitchCompatOrMaterialCode")
fun createLocation(context: Context, tag: String) {
    EasyFloat.with(context)
        .setTag(tag)
        .setLayout(R.layout.location) {
            val show = EasyFloat.getFloatView("play")?.findViewById<CheckBox>(R.id.show)
            val pos = EasyFloat.getFloatView("play")?.findViewById<Switch>(R.id.pos)
            it.setOnTouchListener { _, motionEvent ->
                when (Control.pos) {
                    true -> {
                        when (motionEvent.action) {
                            MotionEvent.ACTION_DOWN -> {
                                Control.pos_count++
                                when (Control.pos_count) {
                                    1 -> {
                                        setMap('Q', motionEvent)
                                        show?.text = "正在设置+2"
                                    }

                                    2 -> {
                                        setMap('W', motionEvent)
                                        show?.text = "正在设置+3"
                                    }

                                    3 -> {
                                        setMap('E', motionEvent)
                                        show?.text = "正在设置+4"
                                    }

                                    4 -> {
                                        setMap('R', motionEvent)
                                        show?.text = "正在设置+5"
                                    }

                                    5 -> {
                                        setMap('T', motionEvent)
                                        show?.text = "正在设置+6"
                                    }

                                    6 -> {
                                        setMap('Y', motionEvent)
                                        show?.text = "正在设置+7"
                                    }

                                    7 -> {
                                        setMap('U', motionEvent)
                                        show?.text = "正在设置1"
                                    }

                                    8 -> {
                                        setMap('A', motionEvent)
                                        show?.text = "正在设置2"
                                    }

                                    9 -> {
                                        setMap('S', motionEvent)
                                        show?.text = "正在设置3"
                                    }

                                    10 -> {
                                        setMap('D', motionEvent)
                                        show?.text = "正在设置4"
                                    }

                                    11 -> {
                                        setMap('F', motionEvent)
                                        show?.text = "正在设置5"
                                    }

                                    12 -> {
                                        setMap('G', motionEvent)
                                        show?.text = "正在设置6"
                                    }

                                    13 -> {
                                        setMap('H', motionEvent)
                                        show?.text = "正在设置7"
                                    }

                                    14 -> {
                                        setMap('J', motionEvent)
                                        show?.text = "正在设置-1"
                                    }

                                    15 -> {
                                        setMap('Z', motionEvent)
                                        show?.text = "正在设置-2"
                                    }

                                    16 -> {
                                        setMap('X', motionEvent)
                                        show?.text = "正在设置-3"
                                    }

                                    17 -> {
                                        setMap('C', motionEvent)
                                        show?.text = "正在设置-4"
                                    }

                                    18 -> {
                                        setMap('V', motionEvent)
                                        show?.text = "正在设置-5"
                                    }

                                    19 -> {
                                        setMap('B', motionEvent)
                                        show?.text = "正在设置-6"
                                    }

                                    20 -> {
                                        setMap('N', motionEvent)
                                        show?.text = "正在设置-7"
                                    }

                                    21 -> {
                                        setMap('M', motionEvent)
                                        show?.isChecked = false
                                        show?.text = "设置完毕"
                                    }
                                }
                            }
                        }
                        true
                    }

                    false -> false
                }
            }
            val content = it.findViewById<RelativeLayout>(R.id.rlContent)
            val params = content.layoutParams as FrameLayout.LayoutParams
            it.findViewById<ScaleImage>(R.id.ivScale).onScaledListener =
                object : ScaleImage.OnScaledListener {
                    override fun onScaled(x: Float, y: Float, event: MotionEvent) {
                        params.width = max(params.width + x.toInt(), 400)
                        params.height = max(params.height + y.toInt(), 300)
                        EasyFloat.updateFloat(tag, width = params.width, height = params.height)
                    }
                }
            it.findViewById<ImageView>(R.id.ivClose).setOnClickListener {
                show?.isChecked = false
                pos?.isChecked = false
                EasyFloat.dismiss(tag)
            }
        }.setShowPattern(ShowPattern.BACKGROUND).show()
}