package com.farah.ecom

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farah.ecom.adapters.CartAdapter
import com.farah.ecom.adapters.OrderAdapter
import com.farah.ecom.adapters.SingleAdapter
import com.farah.ecom.interfaces.ApiInterface
import com.farah.ecom.model.ordermodel
import com.farah.ecom.model.proinfo
import com.farah.ecom.model.singlemodel
import com.farah.ecom.model.userinfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class orderpro : AppCompatActivity() {
    lateinit var SingleAdapter: SingleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderpro)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        SingleAdapter = SingleAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        val prefs: SharedPreferences =getSharedPreferences("store",
            Context.MODE_PRIVATE//the tel was in the preference so i get hem from the preference
        )
        val order_code = prefs.getString("order_code","")
        Toast.makeText(applicationContext, "Order"+order_code, Toast.LENGTH_LONG).show()

        val Proinfo = proinfo(order_code = order_code)
        val apInterface = ApiInterface.create().single(Proinfo)
        apInterface.enqueue(object : Callback<List<singlemodel>>
        {

            override fun onResponse(
                call: Call<List<singlemodel>>,
                response: Response<List<singlemodel>>
            ) {
               SingleAdapter.setProductListItems(response.body()!!)

            }

            override fun onFailure(call: Call<List<singlemodel>>, t: Throwable) {
                Toast.makeText(applicationContext, "ERROR" , Toast.LENGTH_LONG).show()


            }
        }
        )




//            }



        //pass the product list to adapter
        SingleAdapter = SingleAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = SingleAdapter
        recyclerView.setHasFixedSize(true)





    }



}