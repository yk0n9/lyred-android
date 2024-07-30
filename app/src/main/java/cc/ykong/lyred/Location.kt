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
        path.lineTo(it.x, it.y)
        service?.dispatchGesture(
            GestureDescription
                .Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0, 1))
                .build(),
            null,
            null
        )
    }

    var mapping = mutableMapOf<Int, Pos>()
}

@Serializable
data class Pos(var x: Float, var y: Float)

fun setMap(i: Int, event: MotionEvent) {
    Map.mapping[i] = Pos(event.rawX, event.rawY)
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
        24 -> Map.click(Map.mapping[15]!!)
        26 -> Map.click(Map.mapping[16]!!)
        28 -> Map.click(Map.mapping[17]!!)
        29 -> Map.click(Map.mapping[18]!!)
        31 -> Map.click(Map.mapping[19]!!)
        33 -> Map.click(Map.mapping[20]!!)
        35 -> Map.click(Map.mapping[21]!!)
        36 -> Map.click(Map.mapping[15]!!)
        38 -> Map.click(Map.mapping[16]!!)
        40 -> Map.click(Map.mapping[17]!!)
        41 -> Map.click(Map.mapping[18]!!)
        43 -> Map.click(Map.mapping[19]!!)
        45 -> Map.click(Map.mapping[20]!!)
        47 -> Map.click(Map.mapping[21]!!)
        48 -> Map.click(Map.mapping[15]!!)
        50 -> Map.click(Map.mapping[16]!!)
        52 -> Map.click(Map.mapping[17]!!)
        53 -> Map.click(Map.mapping[18]!!)
        55 -> Map.click(Map.mapping[19]!!)
        57 -> Map.click(Map.mapping[20]!!)
        59 -> Map.click(Map.mapping[21]!!)
        60 -> Map.click(Map.mapping[8]!!)
        62 -> Map.click(Map.mapping[9]!!)
        64 -> Map.click(Map.mapping[10]!!)
        65 -> Map.click(Map.mapping[11]!!)
        67 -> Map.click(Map.mapping[12]!!)
        69 -> Map.click(Map.mapping[13]!!)
        71 -> Map.click(Map.mapping[14]!!)
        72 -> Map.click(Map.mapping[1]!!)
        74 -> Map.click(Map.mapping[2]!!)
        76 -> Map.click(Map.mapping[3]!!)
        77 -> Map.click(Map.mapping[4]!!)
        79 -> Map.click(Map.mapping[5]!!)
        81 -> Map.click(Map.mapping[6]!!)
        83 -> Map.click(Map.mapping[7]!!)
        84 -> Map.click(Map.mapping[1]!!)
        86 -> Map.click(Map.mapping[2]!!)
        88 -> Map.click(Map.mapping[3]!!)
        89 -> Map.click(Map.mapping[4]!!)
        91 -> Map.click(Map.mapping[5]!!)
        93 -> Map.click(Map.mapping[6]!!)
        95 -> Map.click(Map.mapping[7]!!)
    }
}

@SuppressLint("ClickableViewAccessibility", "UseSwitchCompatOrMaterialCode", "SetTextI18n")
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
                                        setMap(1, motionEvent)
                                        show?.text = "正在设置+2"
                                    }
                                    2 -> {
                                        setMap(2, motionEvent)
                                        show?.text = "正在设置+3"
                                    }
                                    3 -> {
                                        setMap(3, motionEvent)
                                        show?.text = "正在设置+4"
                                    }
                                    4 -> {
                                        setMap(4, motionEvent)
                                        show?.text = "正在设置+5"
                                    }
                                    5 -> {
                                        setMap(5, motionEvent)
                                        show?.text = "正在设置+6"
                                    }
                                    6 -> {
                                        setMap(6, motionEvent)
                                        show?.text = "正在设置+7"
                                    }
                                    7 -> {
                                        setMap(7, motionEvent)
                                        show?.text = "正在设置1"
                                    }
                                    8 -> {
                                        setMap(8, motionEvent)
                                        show?.text = "正在设置2"
                                    }
                                    9 -> {
                                        setMap(9, motionEvent)
                                        show?.text = "正在设置3"
                                    }
                                    10 -> {
                                        setMap(10, motionEvent)
                                        show?.text = "正在设置4"
                                    }
                                    11 -> {
                                        setMap(11, motionEvent)
                                        show?.text = "正在设置5"
                                    }
                                    12 -> {
                                        setMap(12, motionEvent)
                                        show?.text = "正在设置6"
                                    }
                                    13 -> {
                                        setMap(13, motionEvent)
                                        show?.text = "正在设置7"
                                    }
                                    14 -> {
                                        setMap(14, motionEvent)
                                        show?.text = "正在设置-1"
                                    }
                                    15 -> {
                                        setMap(15, motionEvent)
                                        show?.text = "正在设置-2"
                                    }
                                    16 -> {
                                        setMap(16, motionEvent)
                                        show?.text = "正在设置-3"
                                    }
                                    17 -> {
                                        setMap(17, motionEvent)
                                        show?.text = "正在设置-4"
                                    }
                                    18 -> {
                                        setMap(18, motionEvent)
                                        show?.text = "正在设置-5"
                                    }
                                    19 -> {
                                        setMap(19, motionEvent)
                                        show?.text = "正在设置-6"
                                    }
                                    20 -> {
                                        setMap(20, motionEvent)
                                        show?.text = "正在设置-7"
                                    }
                                    21 -> {
                                        setMap(21, motionEvent)
                                        show?.isChecked = false
                                        show?.text = "设置完毕"
                                        pos?.isChecked = false
                                        EasyFloat.dismiss(tag)
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