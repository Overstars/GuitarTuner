package com.example.guitartune.utils

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object FFTUtil {
    // 复数结构升级为双精度
    private data class Complex(val re: Double, val im: Double = 0.0) {
        operator fun plus(other: Complex) = Complex(re + other.re, im + other.im)
        operator fun times(other: Complex) = Complex(
            re * other.re - im * other.im,
            re * other.im + im * other.re
        )
    }

    // 迭代FFT算法升级
    fun fft(signal: ShortArray): DoubleArray {
        val n = nextPowerOfTwo(signal.size)
        val complex = Array(n) { i ->
            Complex(if (i < signal.size) signal[i].toDouble() else 0.0)
        }

        // 位反转重排序（兼容双精度）
        var j = 0
        for (i in 0 until n) {
            if (j > i) complex.swap(i, j)
            var m = n
            while (m >= 2 && j >= m) {
                j -= m
                m = m shr 1
            }
            j += m
        }

        // 蝶形运算双精度优化
        var mmax = 1
        while (n > mmax) {
            val istep = mmax shl 1
            val theta = -Math.PI / mmax  // 直接使用Java标准库的Double精度PI[1](@ref)
            val wTemp = sin(theta / 2)
            val wpr = -2 * wTemp * wTemp
            val wpi = sin(theta)
            var wr = 1.0
            var wi = 0.0

            for (m in 0 until mmax) {
                for (i in m until n step istep) {
                    val j = i + mmax
                    val tr = wr * complex[j].re - wi * complex[j].im
                    val ti = wr * complex[j].im + wi * complex[j].re
                    complex[j] = Complex(complex[i].re - tr, complex[i].im - ti)
                    complex[i] = Complex(complex[i].re + tr, complex[i].im + ti)
                }
                val wtemp = wr
                wr += wr * wpr - wi * wpi
                wi += wi * wpr + wtemp * wpi
            }
            mmax = istep
        }

        return complex.map { it.re * it.re + it.im * it.im }.toDoubleArray()
    }

    // 实用函数保持Int类型
    private fun log2(n: Int) = (31 - Integer.numberOfLeadingZeros(n))
    private fun nextPowerOfTwo(n: Int) = 1 shl (32 - Integer.numberOfLeadingZeros(n - 1))
    private fun <T> Array<T>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }
}