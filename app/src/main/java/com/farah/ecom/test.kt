package com.farah.ecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class test : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        for (i in 1..10){
            val prod = findViewById<TextView>(R.id.product)
            prod.text = "printing $i"
        }

    }
}