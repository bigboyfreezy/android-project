package com.farah.ecom.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.farah.ecom.MainActivity
import com.farah.ecom.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject


class login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val prefs: SharedPreferences? = activity?.getSharedPreferences(
            "usser",
            AppCompatActivity.MODE_PRIVATE
        )
        val customer_id = prefs?.getString("customer_id", "")
        if (customer_id != null) {
            if (customer_id.length > 0) {
                val i = Intent(activity, MainActivity::class.java)
                startActivity(i)
            } else {

            }


        }
            // Inflate the layout for this fragment
            val root = inflater.inflate(R.layout.fragment_login, container, false)
            val email = root.findViewById(R.id.email) as EditText
            val password = root.findViewById(R.id.password) as EditText
            val submit = root.findViewById(R.id.submit) as Button


            submit.setOnClickListener {
                val client = AsyncHttpClient(true, 80, 443)
                val jsonParams = JSONObject()
                jsonParams.put("email", email.text.toString())
                jsonParams.put("password", password.text.toString())

                val data = StringEntity(jsonParams.toString())

                client.post(
                    activity,
                    "http://10.0.2.2:6060/login",
                    data,
                    "application/json",
                    object : JsonHttpResponseHandler() {
                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            response: JSONObject
                        ) {
                            if (statusCode == 200) {
                                Toast.makeText(activity, "" + response, Toast.LENGTH_LONG).show()
                                val customer_id = response.optString("customer_id")
                                val fname = response.optString("fname")
                                val lname = response.optString("lname")

                                val prefs: SharedPreferences? = activity?.getSharedPreferences(
                                    "usser",
                                    AppCompatActivity.MODE_PRIVATE
                                )

                                val editor: SharedPreferences.Editor = prefs!!.edit()
                                editor.putString("customer_id", customer_id)
                                editor.putString("fname", fname)
                                editor.putString("lname", lname)
                                editor.apply()
                                val i = Intent(activity, MainActivity::class.java)
                                startActivity(i)
                            } else {
                                Toast.makeText(activity, "" + response, Toast.LENGTH_LONG).show()

                            }


                        } //end on success

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<out Header>?,
                            throwable: Throwable?,
                            errorResponse: JSONObject?
                        ) {
                            Toast.makeText(activity, "" + errorResponse, Toast.LENGTH_LONG).show()
                        }
                    }//end response handler

                )//end post


            }
            return root
        }

}