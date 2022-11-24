package com.example.e_commerce.utils

import android.content.Context
import android.net.Uri
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
class NewProductUtilTest{

    lateinit var context : Context

    // Initialize instantExecutorRule
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `Name Of Product is Empty returns Name Of Product Is Required`() {
        val result = NewProductUtil.checkCreateProductValid("","Description","30",true,"20", listOf(Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.Name_Of_Product_Is_Required))
    }

    @Test
    fun `Description is Empty returns Description Is Required`() {
        val result = NewProductUtil.checkCreateProductValid("Name","","30",true,"20", listOf(Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.Description_Is_Required))
    }

    @Test
    fun `Price is Empty returns Price Is Required`() {
        val result = NewProductUtil.checkCreateProductValid("Name","Description","",true,"20", listOf(Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.Price_Is_Required))
    }

    @Test
    fun `Price is Equal 0 returns Price Must Not To Be Equal Zero`() {
        val result = NewProductUtil.checkCreateProductValid("Name","Description","0",true,"20", listOf(Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.Price_Must_Not_To_Be_Equal_Zero))
    }

    @Test
    fun `Offer Price is Empty And Has Offer Is True returns Offer Price Is Required`() {
        val result = NewProductUtil.checkCreateProductValid("Name","Description","20",true,"", listOf(Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.Offer_Price_Is_Required))
    }

    @Test
    fun `Offer Price is Equal 0 And Has Offer Is True returns Offer Price Must Not To Be Equal Zero`() {
        val result = NewProductUtil.checkCreateProductValid("Name","Description","20",true,"0", listOf(Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.Offer_Price_Must_Not_To_Be_Equal_Zero))
    }

    @Test
    fun `Offer Price is Greater Than Price And Has Offer Is True returns Offer Price Must Be Less than Price`() {
        val result = NewProductUtil.checkCreateProductValid("Name","Description","20",true,"40", listOf(Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.Offer_Price_Must_Be_Less_than_Price))
    }

    @Test
    fun `List Of Images Is Less than 3 Images or Greater Than 7 returns You Must Upload Between Three And Seven Images`() {
        val result = NewProductUtil.checkCreateProductValid("Name","Description","40",true,"30", listOf(Uri.parse("https")))
        val mutableList = mutableListOf<Uri>()
        for (i in 0..7){ mutableList.add(Uri.parse("https"))}
        val result2 = NewProductUtil.checkCreateProductValid("Name","Description","40",true,"30", mutableList.toList())

        assertEquals(context.getString(result), context.getString(R.string.You_Must_Upload_Between_Three_And_Seven_Images))
        assertEquals(context.getString(result2), context.getString(R.string.You_Must_Upload_Between_Three_And_Seven_Images))
    }

    @Test
    fun `All Data Valid returns Success`() {
        val result = NewProductUtil.checkCreateProductValid("Name","Description","40",true,"30", listOf(Uri.parse("https"),Uri.parse("https"),Uri.parse("https")))
        assertEquals(context.getString(result), context.getString(R.string.success))
    }

}