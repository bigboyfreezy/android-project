package com.farah.ecom

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farah.ecom.adapters.CartAdapter
import com.farah.ecom.adapters.RecyclerAdapter
import com.farah.ecom.helpers.CartHelper
import com.loopj.android.http.AsyncHttpClient.log

class CartActivity : AppCompatActivity() {
    lateinit var CartAdapter: CartAdapter //call the adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val checkout = findViewById(R.id.checkout) as Button
        checkout.visibility = View.GONE

        val login = findViewById(R.id.login) as Button
        login.visibility = View.GONE



        val prefs: SharedPreferences = applicationContext.getSharedPreferences("usser", MODE_PRIVATE)
        val customer_id : String = prefs.getString("customer_id", "").toString()

        if (customer_id.length==0){
            checkout.visibility = View.GONE
            login.visibility = View.VISIBLE
        }
        else{
            checkout.visibility = View.VISIBLE
            login.visibility = View.GONE
        }

        login.setOnClickListener {
            val i = Intent(applicationContext, com.farah.ecom.login::class.java)
            startActivity(i)
            finish()
        }
        checkout.setOnClickListener {
            val i = Intent(applicationContext, Payment::class.java)
            startActivity(i)
            finish()
        }


        //the adapter does not have a data
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        CartAdapter = CartAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val helper = CartHelper(applicationContext)
        val productList = helper.getAllItem()
        CartAdapter.setProductListItems(productList)
        val total = helper.finalprice()
        val txtotal = findViewById(R.id.total)  as TextView
        txtotal.visibility = View.GONE


        recyclerView.adapter = CartAdapter
        recyclerView.setHasFixedSize(true)
        //cart clearance
        val empty = findViewById(R.id.empty)  as TextView
        empty.visibility = View.GONE
        val helper1 = CartHelper(applicationContext)
        val count: Int = helper1.getNumItems()//counting the number of products
        if(count ==0){
            empty.visibility = View.VISIBLE
            empty.text = "Your Cart is Empty"
        }
        else{
            empty.visibility = View.GONE
            txtotal.visibility = View.VISIBLE
            txtotal.text = "TOTAL KES :"+ total
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear){
            val helper = CartHelper(applicationContext)
            helper.clearcart()


        }
        return super.onOptionsItemSelected(item)
    }
}