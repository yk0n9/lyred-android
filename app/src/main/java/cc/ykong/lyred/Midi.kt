package cc.ykong.lyred

import libmidi.midi.MidiEvent
import libmidi.midi.MidiSystem
import libmidi.midi.ShortMessage
import libmidi.sound.MidiUtils
import java.io.InputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val MAP: List<Int> = listOf(
    24, 26, 28, 29, 31, 33, 35, 36, 38, 40, 41, 43, 45, 47, 48, 50, 52, 53, 55, 57, 59, 60, 62, 64,
    65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95
)

object Pool {
    val pool: ExecutorService = Executors.newFixedThreadPool(1)
}

class Midi {
    var events: ArrayList<Event> = ArrayList()
    var offset = 0

    private val playback = sequence {
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
            yield(e.press + offset)
        }
    }

    fun init(file: InputStream) {
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
        file.close()
    }

    fun play() {
        Control.playing = true
        Pool.pool.execute {
            for (i in playback) {
                when (Control.is_play) {
                    true -> press(i)
                    false -> break
                }
            }
            Control.playing = false
            Control.is_play = false
        }
    }

    fun detect(offset: Int): Float {
        val all = this.events.size.toFloat()
        var count = 0
        this.events.forEach {
            if (MAP.contains(it.press + offset)) {
                count++
            }
        }
        return count.toFloat() / all
    }
}

class Event(val press: Int, val delay: Long)