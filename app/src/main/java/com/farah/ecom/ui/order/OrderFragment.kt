package com.farah.ecom.ui.order

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farah.ecom.R
import com.farah.ecom.adapters.OrderAdapter
import com.farah.ecom.adapters.RecyclerAdapter

import com.farah.ecom.databinding.FragmentOrderBinding
import com.farah.ecom.helpers.CartHelper
import com.farah.ecom.interfaces.ApiInterface
import com.farah.ecom.model.ordermodel
import com.farah.ecom.model.productmodel
import com.farah.ecom.model.userinfo
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderFragment : Fragment() {
    lateinit var orderAdapter: OrderAdapter //call the adapter
    private var _binding: FragmentOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView

            val prefs: SharedPreferences = requireActivity().getSharedPreferences("usser",
                AppCompatActivity.MODE_PRIVATE//the tel was in the preference so i get hem from the preference
            )
            val tel = prefs.getString("tel","")

            Toast.makeText(activity, "tel"+tel, Toast.LENGTH_LONG).show()


            //convert above json object to string
            val Userinfo = userinfo(tel= tel)

            //we use kotlin coroutines...used to run asynchronous services
            //A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
            //we create a request body
//            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
//            CoroutineScope(Dispatchers.IO).launch {
                //above request body post it to make payment
                //Now we are going to use the interface we created
                val apInterface = ApiInterface.create().myorder(Userinfo)
                apInterface.enqueue(object : Callback<List<ordermodel>>
                {

                    override fun onResponse(
                        call: Call<List<ordermodel>>,
                        response: Response<List<ordermodel>>
                    ) {
                        if(response.code() == 404){
                            Toast.makeText(activity, "No Orders" , Toast.LENGTH_LONG).show()
                        }
                        else {
                            orderAdapter.setProductListItems(response.body()!!)
                        }


                    }

                    override fun onFailure(call: Call<List<ordermodel>>, t: Throwable) {
                        Toast.makeText(activity, "ERROR" , Toast.LENGTH_LONG).show()


                    }
                }
                )




//            }



        //pass the product list to adapter
        orderAdapter = OrderAdapter(this.requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = orderAdapter
        recyclerView.setHasFixedSize(true)




        return  root

    }

}