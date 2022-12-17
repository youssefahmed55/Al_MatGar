package com.example.e_commerce.utils

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.e_commerce.R
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewUserUtilTest {

    private lateinit var context : Context

    // Initialize instantExecutorRule
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `FullName is Empty returns FullName Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("","youssefahmed505505@gmail.com",1,1,"4-6-1999","01229651459","Helwan","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.FullName_Is_Required))
    }

    @Test
    fun `Email is Empty returns Email Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","",1,1,"4-6-1999","01229651459","Helwan","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.Email_Is_Required))
    }

    @Test
    fun `Gender is Equal 0 returns Gender Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","youssefahmed505505@gmail.com",0,1,"4-6-1999","01229651459","Helwan","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.Gender_Is_Required))
    }

    @Test
    fun `Type is Equal 0 returns Type Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","youssefahmed505505@gmail.com",1,0,"4-6-1999","01229651459","Helwan","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.Type_Is_Required))
    }

    @Test
    fun `Birthday is Empty returns Birthday Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","youssefahmed505505@gmail.com",1,1,"","01229651459","Helwan","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.Birthday_Is_Required))
    }

    @Test
    fun `Phone is Empty returns Phone Number Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","youssefahmed505505@gmail.com",1,1,"4-6-1999","","Helwan","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.Phone_Number_Is_Required))
    }

    @Test
    fun `Location is Empty returns Location Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","youssefahmed505505@gmail.com",1,1,"4-6-1999","01229651459","","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.Location_Is_Required))
    }

    @Test
    fun `Password is Empty returns Password Is Required`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","youssefahmed505505@gmail.com",1,1,"4-6-1999","01229651459","Helwan","")
        Assert.assertEquals(context.getString(result), context.getString(R.string.Password_Is_Required))
    }

    @Test
    fun `All Data Valid returns Success`() {
        val result = NewUserUtil.checkCreateAccountValid("Youssef Ahmed","youssefahmed505505@gmail.com",1,1,"4-6-1999","01229651459","Helwan","123456")
        Assert.assertEquals(context.getString(result), context.getString(R.string.success))

    }

}