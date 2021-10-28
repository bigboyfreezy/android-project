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

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fname = findViewById(R.id.fname) as EditText
        val lname = findViewById(R.id.lname) as EditText
        val email = findViewById(R.id.email) as EditText
        val password = findViewById(R.id.password) as EditText
        val confirm_password = findViewById(R.id.confirm_password) as EditText
        val tel = findViewById(R.id.tel) as EditText
        val submit = findViewById(R.id.submit) as Button

        //set the loopj to get the api and put them in form of json key and value
        submit.setOnClickListener{

            val client = AsyncHttpClient(true,80,443)
            val jsonParams = JSONObject()
            jsonParams.put("fname", fname.text.toString())
            jsonParams.put("lname", lname.text.toString())
            jsonParams.put("email", email.text.toString())
            jsonParams.put("password", password.text.toString())
            jsonParams.put("confirm_password", confirm_password.text.toString())
            jsonParams.put("tel", tel.text.toString())


            //convert json params to string but the format is still key and value
            val data = StringEntity(jsonParams.toString())
            client.post(
                this,
                "http://10.0.2.2:6060/register",
                data,
                "application/json",
                object : JsonHttpResponseHandler(){
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, ""+response, Toast.LENGTH_LONG).show()
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