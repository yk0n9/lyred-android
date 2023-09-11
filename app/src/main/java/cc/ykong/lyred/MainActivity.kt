package cc.ykong.lyred

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.obsez.android.lib.filechooser.ChooserDialog
import java.math.BigDecimal

object Control {
    var speed: Double = 1.0
    var is_play: Boolean = false
    var playing: Boolean = false
    var pause: Boolean = false
    var pos: Boolean = false
    var pos_count: Int = 0
}

class MainActivity : AppCompatActivity() {

    val midi: Midi = Midi()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fileName = findViewById<TextView>(R.id.name)
        val div = findViewById<Button>(R.id.div)
        val add = findViewById<Button>(R.id.add)
        val speed = findViewById<TextView>(R.id.speed)
        findViewById<Button>(R.id.open).setOnClickListener {
            ChooserDialog(this).withFilter(false, false, "mid").withChosenListener { _, dirFile ->
                midi.init(dirFile)
                fileName.text = dirFile.name
            }.build().show()
        }
        div.setOnClickListener {
            if (Control.speed > 0.1) {
                Control.speed = BigDecimal(Control.speed).subtract(BigDecimal(0.1)).toDouble()
                speed.text = Control.speed.toString()
            }
        }
        add.setOnClickListener {
            Control.speed = BigDecimal(Control.speed).add(BigDecimal(0.1)).toDouble()
            speed.text = Control.speed.toString()
        }

        findViewById<CheckBox>(R.id.open_float).setOnCheckedChangeListener { _, b ->
            when (b) {
                true -> {
                    EasyFloat.with(this)
                        .setTag("play")
                        .setLayout(R.layout.float_play) {
                            val play = it.findViewById<Button>(R.id.play)
                            val save = it.findViewById<Button>(R.id.save)
                            if (readLocation()) {
                                save.text = "已读取"
                            }
                            play.setOnClickListener {
                                if (!Control.pause && Control.is_play) {
                                    Control.pause = true
                                    play.text = "播放"
                                } else {
                                    Control.pause = false
                                    play.text = "暂停"
                                    if (!Control.playing) {
                                        Control.is_play = true
                                        Thread(midi.resetPlay(play)).start()
                                    }
                                }
                            }
                            it.findViewById<Button>(R.id.stop).setOnClickListener {
                                Control.is_play = false
                                Control.pause = false
                                play.text = "播放"
                            }

                            it.findViewById<Switch>(R.id.pos).setOnCheckedChangeListener { _, b ->
                                when (b) {
                                    true -> createLocation(this, "location")
                                    false -> EasyFloat.dismiss("location", true)
                                }
                            }
                            it.findViewById<CheckBox>(R.id.show).setOnCheckedChangeListener { a, b ->
                                when (b) {
                                    true -> {
                                        Control.pos = true
                                        Control.pos_count = 0
                                        a.text = "正在设置+1"
                                    }

                                    false -> {
                                        Control.pos = false
                                        Control.pos_count = 0
                                        a.text = "设置坐标"
                                    }
                                }
                            }
                            it.findViewById<Button>(R.id.save).setOnClickListener {
                                Control.pos_count = 0
                                saveLocation()
                                save.text = "保存成功"
                            }
                        }
                        .setShowPattern(ShowPattern.ALL_TIME)
                        .show()
                }

                false -> EasyFloat.dismiss("play", true)
            }
        }
    }
}

