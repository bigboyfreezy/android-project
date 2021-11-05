package com.farah.ecom

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById(R.id.email) as EditText
        val password = findViewById(R.id.password) as EditText
        val submit = findViewById(R.id.submit) as Button

        submit.setOnClickListener {
            val client = AsyncHttpClient(true, 80, 443)
            val jsonParams = JSONObject()
            jsonParams.put("email", email.text.toString())
            jsonParams.put("password", password.text.toString())

            val data = StringEntity(jsonParams.toString())

            client.post(
                this,
                "http://10.0.2.2:6060/login",
                data,
                "application/json",
                object : JsonHttpResponseHandler(){
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject
                    ) {
                        if(statusCode == 200){
                            Toast.makeText(applicationContext, ""+response, Toast.LENGTH_LONG).show()
                            val customer_id = response.optString("customer_id")
                            val fname = response.optString("fname")
                            val lname = response.optString("lname")

                            val prefs: SharedPreferences = applicationContext.getSharedPreferences("usser", MODE_PRIVATE)

                            val editor : SharedPreferences.Editor = prefs.edit()
                            editor.putString("customer_id", customer_id)
                            editor.putString("fname", fname)
                            editor.putString("lname", lname)
                            editor.apply()
                            val i = Intent(applicationContext, MainActivity::class.java)
                            startActivity(i)
                        }

                        else {
                            Toast.makeText(applicationContext, ""+response, Toast.LENGTH_LONG).show()

                        }


                    } //end on success

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, ""+errorResponse, Toast.LENGTH_LONG).show()
                    }
                }//end response handler

            )//end post






        }

    }
}