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
        //Given Empty email and valid password
        val email = ""
        val password = "123456"

        //When check the result of SignIn Valid
        val result = LoginUtil.checkSignInValid(email,password)

        //Then
        assertEquals(context.getString(result),context.getString(R.string.Email_Is_Required))
    }

    @Test
    fun `Password is Empty returns Password Is Required`() {
        //Given valid email and Empty password
        val email = "youssefahmed505505@gmail.com"
        val password = ""

        //When check the result of SignIn Valid
        val result = LoginUtil.checkSignInValid(email,password)

        //Then
        assertEquals(context.getString(result),context.getString(R.string.Password_Is_Required))
    }

    @Test
    fun `Email and Password Are Not Empty returns Success`() {
        //Given valid email and valid password
        val email = "youssefahmed505505@gmail.com"
        val password = "123456"

        //When check the result of SignIn Valid
        val result = LoginUtil.checkSignInValid(email,password)

        //Then
        assertEquals(context.getString(result),context.getString(R.string.success))
    }

    @Test
    fun `Email is Empty and Password is Empty returns No data exists`() {
        //Given Empty email and Empty password
        val email = ""
        val password = ""

        //When check the result of SignIn Valid
        val result = LoginUtil.checkSignInValid(email,password)

        //Then
        assertEquals(context.getString(result),context.getString(R.string.no_data_exists))
    }

}