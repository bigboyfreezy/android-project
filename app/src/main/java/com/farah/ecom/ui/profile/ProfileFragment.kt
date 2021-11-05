package com.farah.ecom.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.farah.ecom.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val root = inflater.inflate(R.layout.fragment_profile2, container, false)

        val prefs: SharedPreferences? = activity?.getSharedPreferences("usser",
            Context.MODE_PRIVATE)

        val customer_id = prefs?.getString("customer_id","")
        val client = AsyncHttpClient(true,80,443)
        val jsonParams = JSONObject()

        //convert json params to string but the format is still key and value
        val data = StringEntity(jsonParams.toString())
        client.get(
            activity,
            "http://10.0.2.2:6060/profile/"+customer_id,
            data,
            "application/json",
            object : JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject
                ) {

                    Toast.makeText(activity, ""+response, Toast.LENGTH_LONG).show()
                    val fname = response.optString("fname")
                    val textfname = root.findViewById(R.id.fname) as TextView
                    textfname.text = fname


                } //end on success

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?
                ) {
                    Toast.makeText(activity, ""+errorResponse, Toast.LENGTH_LONG).show()
                }
            }//end response handler

        )//end post





        return root

    }


}