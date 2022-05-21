package com.farah.ecom.interfaces

import android.service.autofill.UserData
import com.farah.ecom.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    //Get products
    @GET("products")
    fun getproducts() : Call<List<productmodel>>



    @POST("payment")
    suspend fun makePayment(@Body requestBody: RequestBody):Response<ResponseBody>

    @POST("query")//POST
    suspend fun ask(@Body requestBody: RequestBody):Response<ResponseBody>

    @POST("order")
    suspend fun processorder(@Body requestBody: RequestBody):Response<ResponseBody>

    @POST("changepassword")
    suspend fun change(@Body requestBody: RequestBody):Response<ResponseBody>

    @POST("orderlist")
    fun myorder(@Body userData: userinfo):Call<List<ordermodel>>

    @POST("ordersingle")
    fun single(@Body userData: proinfo):  Call<List<singlemodel>>


    //this is the response we are going to call

    //this is the link between your app and api
    companion object{
        var BASEURL = "https://bigboyfreezy.pythonanywhere.com/"
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

    }
}