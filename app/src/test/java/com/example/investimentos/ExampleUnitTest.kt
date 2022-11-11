package com.example.investimentos

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {
    open class MockCls {
        open fun sum (a: Int, b: Int) = a + b
    }

    @Test
    fun testMock() {
        val mock = mockk<MockCls>()
        every { mock.sum(2, 2) } returns 4
        assertEquals(4, mock.sum(2,2))
    }
}