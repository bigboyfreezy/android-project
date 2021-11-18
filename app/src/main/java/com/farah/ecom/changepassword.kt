package com.farah.ecom

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.farah.ecom.interfaces.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class changepassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword)


        val currentpassword = findViewById(R.id.currentpassword)as EditText
        val newpassword = findViewById(R.id.newpassword)as EditText
        val confirmpassword = findViewById(R.id.confirmpassword)as EditText
        val submit = findViewById(R.id.submit) as Button


        submit.setOnClickListener {
            val prefs: SharedPreferences? = applicationContext?.getSharedPreferences("usser",
                Context.MODE_PRIVATE)
            val customer_id = prefs?.getString("customer_id", "")
            val apiInterface = ApiInterface.create()
            val jsonObject = JSONObject()
            jsonObject.put("customer_id", customer_id)
            jsonObject.put("currentpassword", currentpassword.text.toString())
            jsonObject.put("newpassword", newpassword.text.toString())
            jsonObject.put("confirmpassword", confirmpassword.text.toString())

            //convert above json object to string
            val jsonObjectString = jsonObject.toString()

            //we use kotlin coroutines...used to run asynchronous services
            //A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
            //we create a request body
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            CoroutineScope(Dispatchers.IO).launch {
                //above request body post it to make payment

                val response = apiInterface.change(requestBody)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        Toast.makeText(applicationContext,"Password Changed "+response, Toast.LENGTH_LONG).show()

                    }
                    else{
                        Toast.makeText(applicationContext,response.code().toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }


        }//end
    }
}