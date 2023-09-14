package cc.ykong.lyred

import libmidi.midi.MidiEvent
import libmidi.midi.MidiSystem
import libmidi.midi.ShortMessage
import libmidi.sound.MidiUtils
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Pool {
    val pool: ExecutorService = Executors.newFixedThreadPool(1)
}

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

        var tempo = MidiUtils.DEFAULT_TEMPO_MPQ
        var tick = 0L
        val result = ArrayList<Event>()
        for (e in events) {
            if (MidiUtils.isMetaTempo(e.message)) {
                tempo = MidiUtils.getTempoMPQ(e.message)
            } else if (e.message is ShortMessage && (e.message as ShortMessage).command == ShortMessage.NOTE_ON) {
                val press = (e.message as ShortMessage).data1
                val delay = MidiUtils.ticks2microsec(e.tick - tick, tempo.toDouble(), smf.resolution) / 1000
                tick = e.tick
                result.add(Event(press, delay))
            }
        }
        this.events = result
    }

    fun play() {
        val events = this.events.toList()
        Pool.pool.execute {
            Control.playing = true
            var startTime = System.currentTimeMillis()
            var inputTime = 0L

            for (e in events) {
                if (Control.pause) {
                    while (Control.pause) {
                    }
                    inputTime = e.delay
                    startTime = System.currentTimeMillis()
                }

                inputTime += (e.delay / Control.speed).toLong()
                val currentTime = inputTime - (System.currentTimeMillis() - startTime)

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