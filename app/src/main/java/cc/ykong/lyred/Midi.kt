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
    var events: ArrayList<Event> = ArrayList()

    fun init(file: File) {
        val smf = MidiSystem.getSequence(file)
        val events = ArrayList<MidiEvent>()
        for (track in smf.tracks) {
            Log.d("t", track.toString())
            for (i in 0 until track.size()) {
                val e = track.get(i)
                events.add(e)
            }
        }
        events.sortWith(Comparator.comparing(MidiEvent::getTick))

        var tempo = 500000L
        var tick = 0L
        val result = ArrayList<Event>()
        for (e in events) {
            if (e.message is MetaMessage && (e.message as MetaMessage).type == 0x51) {
                val data = e.message.message
                tempo = (data[3].toInt().and(255).toLong()).shl(16)
                    .or((data[4].toInt().and(255).toLong()).shl(8))
                    .or(data[5].toInt().and(255).toLong())
            } else if (e.message is ShortMessage && e.message.status == ShortMessage.NOTE_ON) {
                val press = (e.message as ShortMessage).data1
                val delay = ((e.tick - tick) * (tempo / 1000.0 / smf.resolution)).toLong()
                tick = e.tick
                result.add(Event(press, delay))
            }
        }
        this.events = result
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun resetPlay(): Runnable {
        val events = this.events.toList()
        return Runnable {
            Control.playing = true
            var startTime = System.currentTimeMillis()
            var inputTime = 0.0
            for (e in events) {
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

                when (Control.is_play) {
                    true -> press(e.press)
                    false -> break
                }
            }
            Control.playing = false
            Control.is_play = false
        }
    }
}

class Event(val press: Int, val delay: Long) {
    override fun toString(): String {
        return "click: " + this.press + "delay: " + this.delay
    }
}