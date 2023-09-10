package cc.ykong.lyred

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
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
    var pos: Boolean = false
    var pos_count: Int = 0
}

class MainActivity : AppCompatActivity() {

    val midi: Midi = Midi()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 打开文件 初始化
        setContentView(R.layout.activity_main)
        val fileName = findViewById<TextView>(R.id.name)
        findViewById<Button>(R.id.open).setOnClickListener {
            ChooserDialog(this).withFilter(false, false, "mid").withChosenListener { _, dirFile ->
                midi.init(dirFile)
                fileName.text = dirFile.name
            }.build().show()
        }

        // 主要悬浮窗
        EasyFloat.with(this)
            .setTag("play")
            .setLayout(R.layout.float_play) {
                // 播放 暂停
                val play = it.findViewById<Button>(R.id.play)
                play.setOnClickListener {
                    if (!Control.pause && Control.is_play) {
                        Control.pause = true
                        play.text = "播放"
                    } else {
                        Control.pause = false
                        play.text = "暂停"
                        if (!Control.playing) {
                            Control.is_play = true
                            midi.play(play)
                        }
                    }
                }
                it.findViewById<Button>(R.id.stop).setOnClickListener {
                    Control.is_play = false
                    Control.pause = false
                    play.text = "播放"
                }

                // 创建坐标面板
                it.findViewById<Switch>(R.id.pos).setOnCheckedChangeListener { _, b ->
                    when (b) {
                        true -> createLocation(this, "location")
                        false -> EasyFloat.dismiss("location", true)
                    }
                }
                // 固定坐标面板/设置
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
                // 保存值本地文件
                it.findViewById<Button>(R.id.save).setOnClickListener {
                    Control.pos_count = 0
                    saveLocation()
                }
            }
            .setShowPattern(ShowPattern.BACKGROUND)
            .show()
    }
}

