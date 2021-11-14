package com.farah.ecom.adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.farah.ecom.MainActivity
import com.farah.ecom.R
import com.farah.ecom.SingleActivity
import com.farah.ecom.helpers.CartHelper
import com.farah.ecom.model.CartModel
import com.farah.ecom.model.productmodel

class CartAdapter(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    //View holder holds the views in single item.xml

    var productList : List<CartModel> = listOf() // empty product list


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_single, parent, false)
        return ViewHolder(view)
    }
//so far item view is same as single item
    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
    val title = holder.itemView.findViewById(R.id.title) as TextView
    val qtty = holder.itemView.findViewById(R.id.qtty) as TextView
    val unit_price = holder.itemView.findViewById(R.id.unit_price) as TextView
    val total_price= holder.itemView.findViewById(R.id.total_price) as TextView
    val remove = holder.itemView.findViewById(R.id.remove)as TextView
    val image = holder.itemView.findViewById(R.id.image) as ImageView



    //bind
    val item = productList[position]
    title.text = item.product_name
    unit_price.text = "product_cost: "+item.product_cost
    total_price.text = "Total Price: "+item.total_price
    qtty.text = "Qtty: "+item.product_qtty



    Glide.with(context).load("https://bigboyfreezy.pythonanywhere.com/static/"+item.image_url)
        .apply(RequestOptions().centerCrop())
        .into(image)


    remove.setOnClickListener{
        //this is the code for the removing of a product from the cart
        val helper = CartHelper(context);
        val result : Int = helper.clearbyid(item.product_id)
        if (result<1){
            Toast.makeText(context, "Product Not Removed ", Toast.LENGTH_SHORT).show()
            val i = Intent(context, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)

        }
        else{
            Toast.makeText(context, "Product Removed ", Toast.LENGTH_SHORT).show()
            val i = Intent(context, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }




    holder.itemView.setOnClickListener {
        val prefs: SharedPreferences = context.getSharedPreferences(
            "store",
            Context.MODE_PRIVATE
        )

        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("title", item.product_name)
        editor.putString("product_cost", item.product_cost)
        editor.putString("total_price", item.total_price)
        editor.putString("image_url", item.image_url)
        editor.putString("qtty", item.product_qtty)
        editor.apply()

        val i = Intent(context, SingleActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)


    }
}



    override fun getItemCount(): Int {
        return productList.size
    }

    //we will call this function o Retrofit response
    fun setProductListItems(productList: List<CartModel>){
        this.productList = productList
        notifyDataSetChanged()
    }


    //here we will receive a product list and update
    //bring data from server and update to put he list uptop products
    //then notify the other data that there is a change


}//end class