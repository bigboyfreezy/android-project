package com.farah.ecom.interfaces

import com.farah.ecom.model.productmodel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    //Get products
    @GET("products")
    fun getproducts() : Call<List<productmodel>>

    //this is the link between your app and api
    companion object{
        var BASEURL = "https://modcom.pythonanywhere.com/api/"
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASEURL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}