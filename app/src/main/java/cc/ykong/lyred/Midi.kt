package cc.ykong.lyred

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.File
import javax.sound.midi.MetaMessage
import javax.sound.midi.MidiEvent
import javax.sound.midi.MidiSystem
import javax.sound.midi.ShortMessage

class Midi : Service() {
    val events: ArrayList<Event> = ArrayList()
    private var fps = 0

    fun init(file: File) {
        val smf = MidiSystem.getSequence(file)
        this.fps = smf.resolution
        val events: ArrayList<MidiEvent> = ArrayList()
        smf.tracks.iterator().forEach {
            for (i in 0 until it.size()) {
                val e = it.get(i)
                events.add(e)
            }
        }
        events.sortWith(Comparator.comparing(MidiEvent::getTick))

        var tempo = 500000L
        var tick = 0L
        for (e in events) {
            if (e.message is MetaMessage && (e.message as MetaMessage).type == 81) {
                val data = e.message.message
                tempo = data[3].toInt().and(255).toLong().shl(16)
                    .or(data[4].toInt().and(255).toLong().shl(8))
                    .or(data[5].toInt().and(255).toLong())
            } else if (e.message is ShortMessage && e.message.status == ShortMessage.NOTE_ON) {
                val press = (e.message as ShortMessage).data1
                val delay = ((e.tick - tick) * (tempo / 1000.0 / this.fps)).toLong()
                this.events.add(Event(press, delay))
                tick = e.tick
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun resetPlay(): Runnable {
        return Runnable {
            Control.playing = true
            var startTime = System.currentTimeMillis()
            var inputTime = 0.0
            for (e in this.events) {
                if (Control.pause) {
                    while (true) {
                        if (!Control.pause) {
                            inputTime = e.delay.toDouble()
                            startTime = System.currentTimeMillis()
                            break
                        }
                    }
                }
                inputTime += e.delay / Control.speed
                val playbackTime = System.currentTimeMillis() - startTime
                val currentTime = (inputTime - playbackTime).toLong()

                if (currentTime > 0) {
                    Thread.sleep(currentTime)
                }

                Log.d("send", "" + e.press)
                when (Control.is_play) {
                    true -> continue
                    false -> break
                }
            }
            Control.playing = false
            Control.is_play = false
        }
    }
}

class Event(val press: Int, val delay: Long)