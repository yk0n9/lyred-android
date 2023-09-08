package cc.ykong.lyred

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.obsez.android.lib.filechooser.ChooserDialog

object Control {
    val TAG = mapOf(
        R.layout.float_x1 to "x1",
        R.layout.float_x2 to "x2",
        R.layout.float_x3 to "x3",
        R.layout.float_x4 to "x4",
        R.layout.float_x5 to "x5",
        R.layout.float_x6 to "x6",
        R.layout.float_x7 to "x7",
        R.layout.float_y2 to "y2",
        R.layout.float_y3 to "y3",
    )
    var speed: Double = 1.0
    var is_play: Boolean = false
    var playing: Boolean = false
    var pause: Boolean = false
}

class MainActivity : AppCompatActivity() {

    val midi: Midi = Midi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        createPoint(this)
        EasyFloat.with(this).setLayout(R.layout.float_play) {
            it.findViewById<Button>(R.id.play).setOnClickListener {
                readLocation()
            }
            it.findViewById<Button>(R.id.stop).setOnClickListener {

            }
            it.findViewById<CheckBox>(R.id.show).setOnCheckedChangeListener { _, b ->
                when (b) {
                    true -> showPoints()
                    false -> hidePoints()
                }
            }
            it.findViewById<Button>(R.id.save).setOnClickListener {
                saveLocation()
            }
        }.setShowPattern(ShowPattern.BACKGROUND).show()

        val fileName = findViewById<TextView>(R.id.name)
        findViewById<Button>(R.id.open).setOnClickListener {
            ChooserDialog(this).withFilter(false, false, "mid").withChosenListener { _, dirFile ->
                fileName.text = dirFile.name
                midi.init(dirFile)
            }.build().show()
        }
    }
}

fun createPoint(ctx: Context) {
    for (tag in Control.TAG) {
        EasyFloat.with(ctx).setTag(tag.value).setLayout(tag.key).setShowPattern(ShowPattern.BACKGROUND).show()
    }
}

fun showPoints() {
    for (tag in Control.TAG) {
        EasyFloat.show(tag.value)
    }
}

fun hidePoints() {
    for (tag in Control.TAG) {
        EasyFloat.hide(tag.value)
    }
}

