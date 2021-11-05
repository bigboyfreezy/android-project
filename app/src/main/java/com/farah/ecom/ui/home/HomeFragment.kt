package com.farah.ecom.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farah.ecom.R
import retrofit2.Callback
import com.farah.ecom.adapters.RecyclerAdapter
import com.farah.ecom.databinding.FragmentHomeBinding
import com.farah.ecom.interfaces.ApiInterface
import com.farah.ecom.model.productmodel
import retrofit2.Call
import retrofit2.Response


class HomeFragment : Fragment() {
//declaration
    lateinit var recyclerAdapter: RecyclerAdapter //call the adapter
    lateinit var productList:ArrayList<productmodel>
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView

        //productList = ArrayList()
        //productList.add(productmodel("UNGA", "2kg unga for Ugali", "2020/02/22", R.drawable.ic_action_home))
        //productList.add(productmodel("JERSEY", "Blue Jersey", "2020/02/22", R.drawable.ic_action_home))
        //productList.add(productmodel("BALL", "White Jersey", "2020/02/22", R.drawable.ic_action_home))




        //Now we are going to use the interface we created
        val apiInterface = ApiInterface.create().getproducts()
        apiInterface.enqueue(object :   Callback<List<productmodel>>
        {

            override fun onResponse(
                call: Call<List<productmodel>>,
                response: Response<List<productmodel>>
            ) {
                recyclerAdapter.setProductListItems(response.body()!!)
            }

            override fun onFailure(call: Call<List<productmodel>>, t: Throwable) {
                Toast.makeText(activity, "ERROR" , Toast.LENGTH_LONG).show()
            }
        }
        )


        //pass the product list to adapter
        recyclerAdapter = RecyclerAdapter(this.requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerAdapter
        recyclerView.setHasFixedSize(true)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}