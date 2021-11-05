package com.farah.ecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
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
            val client = AsyncHttpClient(true, 80, 443)
            val jsonParams = JSONObject()
            jsonParams.put("fname",fname.text.toString())
            jsonParams.put("email", email.text.toString())
            jsonParams.put("tel", tel.text.toString())
            jsonParams.put("ask", addition.text.toString())

            val data = StringEntity(jsonParams.toString())
            client.post(
                this,
                "http://10.0.2.2:6060/query",
                data,
                "application/json",
                object : JsonHttpResponseHandler(){
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, ""+response, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, ""+errorResponse, Toast.LENGTH_SHORT).show()
                    }
                }

            )
        }
    }
}