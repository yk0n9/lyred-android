package cc.ykong.lyred

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Path
import android.os.Environment
import android.view.MotionEvent
import android.view.accessibility.AccessibilityEvent
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

    fun click(x: Float, y: Float) {
        val path = Path()
        path.moveTo(x, y)
        path.lineTo(x, y)
        val builder = GestureDescription.Builder()
        builder.addStroke(GestureDescription.StrokeDescription(path, 0, 1))
        Clicker.Click.clicker.dispatchGesture(builder.build(), null, null)
    }
}

@Serializable
data class Pos(var x: Int, var y: Int)

fun zero() = Pos(0, 0)

fun setMap(c: Char, event: MotionEvent) {
    Map.mapping[c]?.x = event.rawX.toInt()
    Map.mapping[c]?.y = event.rawY.toInt()
}

class Clicker : AccessibilityService() {
    object Click {
        lateinit var clicker: Clicker
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {}
    override fun onInterrupt() {}
    override fun onServiceConnected() {
        super.onServiceConnected()
        Click.clicker = this
    }
}

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