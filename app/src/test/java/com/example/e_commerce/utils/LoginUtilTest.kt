package com.example.e_commerce.utils

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.e_commerce.R
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginUtilTest {

    private lateinit var context : Context

    // Initialize instantExecutorRule
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Email is Empty returns Email Is Required`() {
        val result = LoginUtil.checkSignInValid("","123456")
        assertEquals(context.getString(result),context.getString(R.string.Email_Is_Required))
    }

    @Test
    fun `Password is Empty returns Password Is Required`() {
        val result = LoginUtil.checkSignInValid("youssefahmed505505@gmail.com","")
        assertEquals(context.getString(result),context.getString(R.string.Password_Is_Required))
    }

    @Test
    fun `Email and Password Are Not Empty returns Success`() {
        val result = LoginUtil.checkSignInValid("youssefahmed505505@gmail.com","123456")
        assertEquals(context.getString(result),context.getString(R.string.success))
    }

}