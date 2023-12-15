package cc.ykong.lyred

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

const val Stop = 0
const val Playing = 1
const val Pause = 2

object Control {
    var speed: Double = 1.0
    var state: Int = Stop
    var pos: Boolean = false
    var pos_count: Int = 0
}

class MainActivity : AppCompatActivity() {

    val midi: Midi = Midi()
    private val format = DecimalFormat("0.#")
    private val startActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val data = it.data?.data
        processFile(data!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            open()
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
                true -> createFloat()
                false -> EasyFloat.dismiss("play", true)
            }
        }

        if (intent?.type == "audio/midi") {
            val data = intent?.data
            processFile(data!!)
        }
    }

    @SuppressLint("SetTextI18n")
    fun open() {
        Control.state = Stop
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("audio/midi")
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        this.startActivity.launch(intent)
    }

    @SuppressLint("SetTextI18n")
    fun processFile(data: Uri) {
        val fileName = findViewById<TextView>(R.id.file)
        val hit = findViewById<TextView>(R.id.hit)
        val openFloat = findViewById<CheckBox>(R.id.open_float)
        val path = data.path
        if (path != null) {
            EasyFloat.dismiss("play", true)
            val name = path.substring(path.lastIndexOf('/') + 1)
            contentResolver.openInputStream(data)?.let { this.midi.init(it) }
            this.midi.offset = 0
            fileName.text = "你选择的是: $name"
            setHit(hit)
            openFloat.isChecked = true
            createFloat()
            Toast.makeText(this, "Midi: $name", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setHit(hit: TextView) {
        hit.text =
            "偏移量: " + this.midi.offset + " - " + "命中率: " + this.format.format(this.midi.detect(this.midi.offset) * 100) + "%"
    }

    private fun createFloat() {
        EasyFloat.with(this)
            .setTag("play")
            .setLayout(R.layout.float_play) {
                val play = it.findViewById<Button>(R.id.play)
                val save = it.findViewById<Button>(R.id.save)
                if (readLocation()) {
                    save.text = "已读取"
                }
                play.setOnClickListener {
                    when (Control.state) {
                        Stop -> {
                            Control.state = Playing
                            play.text = "暂停"
                            midi.play(play)
                        }

                        Pause -> {
                            Control.state = Playing
                            play.text = "暂停"
                        }

                        Playing -> {
                            Control.state = Pause
                            play.text = "播放"
                        }
                    }
                }
                it.findViewById<Button>(R.id.stop).setOnClickListener {
                    Control.state = Stop
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
                it.findViewById<Button>(R.id.convert).setOnClickListener {
                    convert(this, this.midi.events.toList())
                }
            }
            .setShowPattern(ShowPattern.ALL_TIME)
            .show()
    }
}

