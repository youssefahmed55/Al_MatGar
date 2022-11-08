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
class RegisterUtilTest {

    lateinit var context : Context

    // Initialize instantExecutorRule
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `FullName is Empty returns FullName Is Required`() {
        val result = RegisterUtil.checkSignUpValid("","youssefahmed505505@gmail.com","01229651459","123456","123456")
        assertEquals(context.getString(result),context.getString(R.string.FullName_Is_Required))
    }

    @Test
    fun `Email is Empty returns Email Is Required`() {
        val result = RegisterUtil.checkSignUpValid("Youssef Ahmed","","01229651459","123456","123456")
        assertEquals(context.getString(result),context.getString(R.string.Email_Is_Required))
    }

    @Test
    fun `Phone Number is Empty returns Phone Number Is Required`() {
        val result = RegisterUtil.checkSignUpValid("Youssef Ahmed","youssefahmed505505@gmail.com","","123456","123456")
        assertEquals(context.getString(result),context.getString(R.string.Phone_Number_Is_Required))
    }

    @Test
    fun `Password is Empty returns Password Is Required`() {
        val result = RegisterUtil.checkSignUpValid("Youssef Ahmed","youssefahmed505505@gmail.com","01229651459","","123456")
        assertEquals(context.getString(result),context.getString(R.string.Password_Is_Required))
    }

    @Test
    fun `Confirm Password is Empty returns Confirm Password Is Required`() {
        val result = RegisterUtil.checkSignUpValid("Youssef Ahmed","youssefahmed505505@gmail.com","01229651459","123456","")
        assertEquals(context.getString(result),context.getString(R.string.Confirm_Password_Is_Required))
    }

    @Test
    fun `Password is Not Match Confirm Password returns Password Don't match`() {
        val result = RegisterUtil.checkSignUpValid("Youssef Ahmed","youssefahmed505505@gmail.com","01229651459","12345","123456")
        assertEquals(context.getString(result),context.getString(R.string.Password_Donot_match))
    }

    @Test
    fun `All EditTexts Valid returns Success`() {
        val result = RegisterUtil.checkSignUpValid("Youssef Ahmed","youssefahmed505505@gmail.com","01229651459","123456","123456")
        assertEquals(context.getString(result),context.getString(R.string.success))
    }
}