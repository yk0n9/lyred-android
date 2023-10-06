package cc.ykong.lyred

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.obsez.android.lib.filechooser.ChooserDialog
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

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
    private val format = DecimalFormat("0.#")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fileName = findViewById<TextView>(R.id.file)
        val div = findViewById<Button>(R.id.div)
        val add = findViewById<Button>(R.id.add)
        val speed = findViewById<TextView>(R.id.speed)
        val hit = findViewById<TextView>(R.id.hit)
        val up = findViewById<Button>(R.id.up)
        val down = findViewById<Button>(R.id.down)
        val reset = findViewById<Button>(R.id.reset)
        val openFloat = findViewById<CheckBox>(R.id.open_float)
        this.format.roundingMode = RoundingMode.FLOOR
        findViewById<Button>(R.id.open).setOnClickListener {
            open(fileName, hit)
        }
        div.setOnClickListener {
            if (Control.speed > 0.1) {
                Control.speed = BigDecimal(Control.speed).subtract(BigDecimal(0.1)).toDouble()
                speed.text = this.format.format(Control.speed)
            }
        }
        add.setOnClickListener {
            Control.speed = BigDecimal(Control.speed).add(BigDecimal(0.1)).toDouble()
            speed.text = this.format.format(Control.speed)
        }
        up.setOnClickListener {
            this.midi.offset++
            setHit(hit)
        }
        down.setOnClickListener {
            this.midi.offset--
            setHit(hit)
        }
        reset.setOnClickListener {
            this.midi.offset = 0
            setHit(hit)
        }

        openFloat.setOnCheckedChangeListener { _, b ->
            when (b) {
                true -> createFloat(fileName, hit)
                false -> EasyFloat.dismiss("play", true)
            }
        }

        val data = intent?.data
        if (intent?.type == "audio/midi") {
            val path = data?.path
            if (path != null) {
                EasyFloat.dismiss("play", true)
                val name = path.substring(path.lastIndexOf('/') + 1)
                contentResolver.openInputStream(data)?.let { this.midi.init(it) }
                this.midi.offset = 0
                fileName.text = "你选择的是: $name"
                setHit(hit)
                openFloat.isChecked = true
                createFloat(fileName, hit)
                Toast.makeText(this, "Midi: $name", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun open(text: TextView, hit: TextView) {
        Control.is_play = false
        Control.pause = false
        ChooserDialog(this).withFilter(false, false, "mid").withChosenListener { _, dirFile ->
            this.midi.init(dirFile.inputStream())
            this.midi.offset = 0
            text.text = "你选择的是: " + dirFile.name
            setHit(hit)
        }.build().show()
    }

    @SuppressLint("SetTextI18n")
    private fun setHit(hit: TextView) {
        hit.text =
            "偏移量: " + this.midi.offset + " - " + "命中率: " + this.format.format(this.midi.detect(this.midi.offset) * 100) + "%"
    }

    private fun createFloat(fileName: TextView, hit: TextView) {
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
                            midi.play()
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
                it.findViewById<Button>(R.id.open_f).setOnClickListener {
                    play.text = "播放"
                    open(fileName, hit)
                }
            }
            .setShowPattern(ShowPattern.ALL_TIME)
            .show()
    }
}

