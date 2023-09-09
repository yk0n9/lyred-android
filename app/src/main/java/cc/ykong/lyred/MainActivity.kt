package cc.ykong.lyred

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.obsez.android.lib.filechooser.ChooserDialog

object Control {
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
        EasyFloat.with(this).setLayout(R.layout.float_play) {
            // 播放 暂停
            val play = it.findViewById<Button>(R.id.play)
            play.setOnClickListener {
                if (!Control.pause) {
                    Control.pause = true
                    play.text = "播放"
                } else {
                    Control.pause = false
                    play.text = "暂停"
                    if (!Control.playing) {
                        Control.is_play = true
                        midi.play()
                    }
                }
            }
            it.findViewById<Button>(R.id.stop).setOnClickListener {
                Control.is_play = false
                Control.pause = false
                play.text = "播放"
            }

            it.findViewById<CheckBox>(R.id.show).setOnCheckedChangeListener { _, b ->
                when (b) {
                    true -> {}
                    false -> {}
                }
            }
            it.findViewById<Button>(R.id.save).setOnClickListener {

            }
        }.setShowPattern(ShowPattern.BACKGROUND).show()

        val fileName = findViewById<TextView>(R.id.name)
        findViewById<Button>(R.id.open).setOnClickListener {
            ChooserDialog(this).withFilter(false, false, "mid").withChosenListener { _, dirFile ->
                midi.init(dirFile)
                fileName.text = dirFile.name
            }.build().show()
        }
    }
}

