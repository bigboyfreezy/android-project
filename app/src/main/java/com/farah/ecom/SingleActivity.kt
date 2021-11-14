package com.farah.ecom

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.farah.ecom.helpers.CartHelper

class SingleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        val prefs: SharedPreferences = getSharedPreferences("store",
            Context.MODE_PRIVATE)
        val qtty = findViewById(R.id.qtty) as EditText
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            val cart_adapter = CartHelper(applicationContext)
            //get this item details from prefs
            val product_id = prefs.getString("product_id", "")
            val product_name = prefs.getString("title","")
            val product_cost = prefs.getString("product_cost","")
            val image_url = prefs.getString("image_url","")

            val product_qtty = qtty.text.toString()
            val con_qtty = Integer.parseInt(product_qtty);
            val con_cost = Integer.parseInt(product_cost)
            //convert cost to int

            cart_adapter.insert(product_id,
                product_name, con_qtty, con_cost,image_url)





        }

        //here


        val title = prefs.getString("title", "")
        val text_title = findViewById(R.id.title) as TextView
        text_title.text = title

        val desc = prefs.getString("description", "")
        val text_desc = findViewById(R.id.desc) as TextView
        text_desc.text = desc

        val cost = prefs.getString("product_cost", "")
        val text_cost= findViewById(R.id.cost) as TextView
        text_cost.text = cost



        val image_url = prefs.getString("image_url", "")
        val image = findViewById(R.id.image_url) as ImageView
        Glide.with(applicationContext).load("https://bigboyfreezy.pythonanywhere.com/static/"+ image_url)
            .apply(RequestOptions().centerCrop())
            .into(image)



    }
}
