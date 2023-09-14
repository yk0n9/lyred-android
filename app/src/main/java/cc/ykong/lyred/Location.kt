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
        1 to zero(),
        2 to zero(),
        3 to zero(),
        4 to zero(),
        5 to zero(),
        6 to zero(),
        7 to zero(),
        8 to zero(),
        9 to zero(),
        10 to zero(),
        11 to zero(),
        12 to zero(),
        13 to zero(),
        14 to zero(),
        15 to zero(),
        16 to zero(),
        17 to zero(),
        18 to zero(),
        19 to zero(),
        20 to zero(),
        21 to zero(),
    )
}

@Serializable
data class Pos(var x: Float, var y: Float)

fun zero() = Pos(0f, 0f)

fun setMap(i: Int, event: MotionEvent) {
    Map.mapping[i]?.x = event.rawX
    Map.mapping[i]?.y = event.rawY
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
        24 -> Map.mapping[15]?.let { Map.click(it) }
        26 -> Map.mapping[16]?.let { Map.click(it) }
        28 -> Map.mapping[17]?.let { Map.click(it) }
        29 -> Map.mapping[18]?.let { Map.click(it) }
        31 -> Map.mapping[19]?.let { Map.click(it) }
        33 -> Map.mapping[20]?.let { Map.click(it) }
        35 -> Map.mapping[21]?.let { Map.click(it) }
        36 -> Map.mapping[15]?.let { Map.click(it) }
        38 -> Map.mapping[16]?.let { Map.click(it) }
        40 -> Map.mapping[17]?.let { Map.click(it) }
        41 -> Map.mapping[18]?.let { Map.click(it) }
        43 -> Map.mapping[19]?.let { Map.click(it) }
        45 -> Map.mapping[20]?.let { Map.click(it) }
        47 -> Map.mapping[21]?.let { Map.click(it) }
        48 -> Map.mapping[15]?.let { Map.click(it) }
        50 -> Map.mapping[16]?.let { Map.click(it) }
        52 -> Map.mapping[17]?.let { Map.click(it) }
        53 -> Map.mapping[18]?.let { Map.click(it) }
        55 -> Map.mapping[19]?.let { Map.click(it) }
        57 -> Map.mapping[20]?.let { Map.click(it) }
        59 -> Map.mapping[21]?.let { Map.click(it) }
        60 -> Map.mapping[8]?.let { Map.click(it) }
        62 -> Map.mapping[9]?.let { Map.click(it) }
        64 -> Map.mapping[10]?.let { Map.click(it) }
        65 -> Map.mapping[11]?.let { Map.click(it) }
        67 -> Map.mapping[12]?.let { Map.click(it) }
        69 -> Map.mapping[13]?.let { Map.click(it) }
        71 -> Map.mapping[14]?.let { Map.click(it) }
        72 -> Map.mapping[1]?.let { Map.click(it) }
        74 -> Map.mapping[2]?.let { Map.click(it) }
        76 -> Map.mapping[3]?.let { Map.click(it) }
        77 -> Map.mapping[4]?.let { Map.click(it) }
        79 -> Map.mapping[5]?.let { Map.click(it) }
        81 -> Map.mapping[6]?.let { Map.click(it) }
        83 -> Map.mapping[7]?.let { Map.click(it) }
        84 -> Map.mapping[1]?.let { Map.click(it) }
        86 -> Map.mapping[2]?.let { Map.click(it) }
        88 -> Map.mapping[3]?.let { Map.click(it) }
        89 -> Map.mapping[4]?.let { Map.click(it) }
        91 -> Map.mapping[5]?.let { Map.click(it) }
        93 -> Map.mapping[6]?.let { Map.click(it) }
        95 -> Map.mapping[7]?.let { Map.click(it) }
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