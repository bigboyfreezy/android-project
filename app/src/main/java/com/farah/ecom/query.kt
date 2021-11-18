package com.farah.ecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.farah.ecom.interfaces.ApiInterface
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class query : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        val fname = findViewById(R.id.fname) as EditText
        val email = findViewById(R.id.email) as EditText
        val tel = findViewById(R.id.tel) as EditText
        val addition = findViewById(R.id.ask) as EditText
        val submit = findViewById(R.id.submit) as Button



        submit.setOnClickListener {
            val apiInterface = ApiInterface.create()
            val jsonObject = JSONObject()
            jsonObject.put("fname",fname.text.toString())
            jsonObject.put("email", email.text.toString())
            jsonObject.put("tel", tel.text.toString())
            jsonObject.put("ask", addition.text.toString())

            val jsonObjectString = jsonObject.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            CoroutineScope(Dispatchers.IO).launch {
              val response = apiInterface.makePayment(requestBody)
                withContext(Dispatchers.Main){

                    if(response.isSuccessful){
                        Toast.makeText(applicationContext,"Query submited "+response, Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(applicationContext,response.code().toString(), Toast.LENGTH_LONG).show()
                    }


                }
            }


        }
    }

    fun processPayment(){

    }
}