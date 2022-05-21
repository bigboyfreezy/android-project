package com.farah.ecom.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.farah.ecom.R
import com.farah.ecom.SingleActivity
import com.farah.ecom.model.ordermodel
import com.farah.ecom.model.productmodel
import com.farah.ecom.model.singlemodel
import com.farah.ecom.orderpro

class SingleAdapter(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<SingleAdapter.ViewHolder>() {
    //View holder holds the views in single item.xml

    var productList: List<singlemodel> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product, parent, false)
        return ViewHolder(view)
    }

    //so far item view is same as single item
    override fun onBindViewHolder(holder: SingleAdapter.ViewHolder, position: Int) {
        val product_name = holder.itemView.findViewById(R.id.product_name) as TextView
        val product_qtty = holder.itemView.findViewById(R.id.product_qtty) as TextView
        val product_cost = holder.itemView.findViewById(R.id.product_cost) as TextView


        //bind
        val item = productList[position]
        product_name.text = item.product_name
        product_qtty.text = item.product_qtty
        product_cost.text = item.product_cost


        //image.setImageResource(item.image)

    }


    override fun getItemCount(): Int {
        return productList.size
    }

    //we will call this function o Retrofit response
    fun setProductListItems(productList: List<singlemodel>) {
        this.productList = productList
        notifyDataSetChanged()
    }


    //here we will receive a product list and update
    //bring data from server and update to put he list uptop products
    //then notify the other data that there is a change


}//end class