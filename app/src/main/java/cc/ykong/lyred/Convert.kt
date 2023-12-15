package cc.ykong.lyred

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

fun convert(ctx: Context, events: List<Event>) {
    val cm = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    var result = ""
    var cache = ""
    var count = 0
    events.forEach {
        if (it.delay != 0L) {
            if (cache.isNotEmpty()) {
                result += if (count > 1) "[$cache] " else "$cache "
            }
            count = 1
            cache = "${getKey(it.press)} "
        } else {
            cache += "${getKey(it.press)} "
            count++
        }
    }
    val data = ClipData.newPlainText("mid", result)
    cm.setPrimaryClip(data)
    Toast.makeText(ctx, "已生成谱子到剪切板, 请粘贴至任何文本框", Toast.LENGTH_LONG).show()
}

fun getKey(key: Int): String {
    return when (key) {
        24 -> "-1"
        26 -> "-2"
        28 -> "-3"
        29 -> "-4"
        31 -> "-5"
        33 -> "-6"
        35 -> "-7"
        36 -> "-1"
        38 -> "-2"
        40 -> "-3"
        41 -> "-4"
        43 -> "-5"
        45 -> "-6"
        47 -> "-7"
        48 -> "-1"
        50 -> "-2"
        52 -> "-3"
        53 -> "-4"
        55 -> "-5"
        57 -> "-6"
        59 -> "-7"
        60 -> "1"
        62 -> "2"
        64 -> "3"
        65 -> "4"
        67 -> "5"
        69 -> "6"
        71 -> "7"
        72 -> "+1"
        74 -> "+2"
        76 -> "+3"
        77 -> "+4"
        79 -> "+5"
        81 -> "+6"
        83 -> "+7"
        84 -> "+1"
        86 -> "+2"
        88 -> "+3"
        89 -> "+4"
        91 -> "+5"
        93 -> "+6"
        95 -> "+7"
        else -> ""
    }
}

