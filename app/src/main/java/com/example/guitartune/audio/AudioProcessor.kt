package com.example.guitartune.audio

// 核心音频处理类
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.example.guitartune.utils.FFTUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos

class AudioProcessor(
    private val sampleRate: Int = 44100,
    bufferSize: Int = 4096
) {
    private val hanningWindow = DoubleArray(bufferSize).apply {
        for (i in indices) this[i] = 0.5 * (1 - cos(2 * Math.PI * i / size)) // 双精度窗函数[4](@ref)
    }

    private fun calculateFrequency(buffer: ShortArray): Double {
        val spectrum = FFTUtil.fft(buffer)
        var maxIndex = 0
        var maxVal = 0.0

        for (i in 1 until spectrum.size / 2) {
            if (spectrum[i] > maxVal) { // 双精度比较无类型冲突[6](@ref)
                maxVal = spectrum[i]
                maxIndex = i
            }
        }
        return maxIndex.toDouble() * sampleRate / buffer.size
    }
}
