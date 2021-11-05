package com.farah.ecom

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.farah.ecom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
//floating action button
        binding.appBarMain.fab.setOnClickListener { view ->
        val i = Intent(applicationContext, query::class.java)
            startActivity(i)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView




        val header : View = navView.getHeaderView(0)
        val mNameTextView = header.findViewById(R.id.header) as TextView

        //load preferences
        val prefs: SharedPreferences = applicationContext.getSharedPreferences("usser", MODE_PRIVATE)

        val fname = prefs.getString("fname","")
        Toast.makeText(applicationContext, ""+fname, Toast.LENGTH_LONG).show()
        // put it in below text ...checking if user is loged in
        if (fname != null){
            mNameTextView.text = "Welcome $fname "
            navView.menu.findItem(R.id.nav_login).setVisible(false)
        }
        else{
            navView.menu.findItem(R.id.nav_login).setVisible(true)
        }


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_category, R.id.nav_profile,R.id.nav_contact,R.id.nav_orders,R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
//handle options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout){

            val prefs: SharedPreferences = applicationContext.getSharedPreferences("usser", MODE_PRIVATE)

            val editor : SharedPreferences.Editor = prefs.edit()
            editor.clear()
            editor.apply()
            val i = Intent(applicationContext, MainActivity:: class.java)
            startActivity(i)
            finish()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}