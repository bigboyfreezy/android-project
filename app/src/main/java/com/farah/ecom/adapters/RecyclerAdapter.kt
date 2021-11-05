package com.farah.ecom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.farah.ecom.R
import com.farah.ecom.model.productmodel

class RecyclerAdapter(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    //View holder holds the views in single item.xml

    var productList : List<productmodel> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }
//so far item view is same as single item
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
    val title = holder.itemView.findViewById(R.id.title) as TextView
    val desc = holder.itemView.findViewById(R.id.desc) as TextView
    val date = holder.itemView.findViewById(R.id.cost) as TextView
    val image = holder.itemView.findViewById(R.id.image) as ImageView

    //bind
    val item = productList[position]
    title.text = item.title
    desc.text = item.desc
    date.text = item.cost

    Glide.with(context).load(item.image_url)
        .apply(RequestOptions().centerCrop())
        .into(image)
    //image.setImageResource(item.image)




    }



    override fun getItemCount(): Int {
        return productList.size
    }

    //we will call this function o Retrofit response
    fun setProductListItems(productList: List<productmodel>){
        this.productList = productList
        notifyDataSetChanged()
    }


    //here we will receive a product list and update
    //bring data from server and update to put he list uptop products
    //then notify the other data that there is a change


}//end class