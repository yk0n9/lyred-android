package cc.ykong.lyred

import libmidi.midi.MetaMessage
import libmidi.midi.MidiEvent
import libmidi.midi.MidiSystem
import libmidi.midi.ShortMessage
import java.io.File
import kotlin.concurrent.thread

class Midi {
    var events: ArrayList<Event> = ArrayList()

    fun init(file: File) {
        val smf = MidiSystem.getSequence(file)
        val events = ArrayList<MidiEvent>()
        for (track in smf.tracks) {
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
            } else if (e.message is ShortMessage && (e.message as ShortMessage).command == ShortMessage.NOTE_ON) {
                val press = (e.message as ShortMessage).data1
                val delay = ((e.tick - tick) * (tempo / 1000.0 / smf.resolution)).toLong()
                tick = e.tick
                result.add(Event(press, delay))
            }
        }
        this.events = result
    }

    fun play(): Thread {
        val events = this.events.toList()
        return thread {
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

class Event(val press: Int, val delay: Long)