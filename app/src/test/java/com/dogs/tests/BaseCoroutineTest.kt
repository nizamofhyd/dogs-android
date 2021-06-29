package com.dogs.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseCoroutineTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    open fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}