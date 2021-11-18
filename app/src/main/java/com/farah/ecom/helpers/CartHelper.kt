package com.farah.ecom.helpers

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.farah.ecom.MainActivity
import com.farah.ecom.interfaces.ApiInterface
import com.farah.ecom.model.CartModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CartHelper(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,  2) {

    val context1 = context

    //add items to cart, view , delete, update
    companion object {
        val DATABASE_NAME = "cart7.db"
        val TABLE_NAME = "my_table"
        val PRODUCT_ID = "product_id"
        val PRODUCT_NAME = "product_name"
        val PRODUCT_QTTY = "product_qtty"
        val UNIT_PRICE = "product_cost"
        val TOTAL_PRICE = "total_price"
        val IMAGE = "image_url"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($PRODUCT_ID Integer primary key autoincrement, $PRODUCT_NAME varchar, $PRODUCT_QTTY integer, $UNIT_PRICE integer, $TOTAL_PRICE integer, $IMAGE varchar )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db?.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME)
        onCreate(db)
    }

    //Insert
    fun insert(product_id: String?, product_name: String?,
               product_qtty: Int, product_cost:Int?, image_url: String?){
        //call function

        val cursor: Cursor = findbyid(product_id!!)
        if (cursor.count>0){//this means that the product is there so we add the new quantity
            var qtty: Int = 0
            var price : Int = 0
            var total : Int = 0
            var total1 : Int = 0

            while (cursor.moveToNext()){
                qtty = cursor.getInt(2)//is in the second row
                price = cursor.getInt(3)

            }

            //qtty +the original qtty to get total
            total = qtty + product_qtty
            total1 = total * price

            updateqtty(product_id, total)
            updateprice(product_id,total1 )


            Toast.makeText(context1, "Updated", Toast.LENGTH_SHORT).show()
            val i = Intent(context1, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context1.startActivity(i)


        }//else if it is not in the cart now u add it
        else{val total_price = product_qtty * product_cost!!
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(PRODUCT_ID, product_id)
            contentValues.put(PRODUCT_NAME, product_name)
            contentValues.put(PRODUCT_QTTY, product_qtty)
            contentValues.put(UNIT_PRICE,product_cost)
            contentValues.put(TOTAL_PRICE, total_price)
            contentValues.put(IMAGE, image_url)
            //now post content values to table
            val result:Long  = db.insert(TABLE_NAME, null, contentValues)
            if (result < 1){
                Toast.makeText(context1, "Not Added", Toast.LENGTH_SHORT).show()
                val i = Intent(context1, MainActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context1.startActivity(i)
            }else{
                Toast.makeText(context1, " Added", Toast.LENGTH_SHORT).show()
                val i = Intent(context1, MainActivity::class.java)
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context1.startActivity(i)
            }
        }


    }//end



    fun findbyid(product_id: String?): Cursor{// here we are finding the product id

        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select * from "+ TABLE_NAME+ " where product_id = '"+product_id+"'",
            null)


        return result

    }

    fun getNumItems(): Int{
        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select * from "+ TABLE_NAME,
            null)
        //We get the result from sqlite
        //cursor object
        return result.count

    }//end

    // Now lets fetch as array list
    fun getAllItem(): ArrayList<CartModel>{

        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select * from " + TABLE_NAME, null)
        //you create a list
        //takes one product and places it in the array list
        val productList = ArrayList<CartModel>()
        while (result.moveToNext()){
            val productmodel = CartModel()
            productmodel.product_id = result.getString(0)
            productmodel.product_name = result.getString(1)
            productmodel.product_qtty = result.getString(2)
            productmodel.product_cost = result.getString(3)
            productmodel.total_price = result.getString(4)
            productmodel.image_url = result.getString(5)
            productList.add(productmodel)

        }
        return productList
    }

        fun updateqtty(product_id: String?, total: Int, ): Boolean{
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(PRODUCT_QTTY, total)

            db.update(TABLE_NAME, contentValues, "$PRODUCT_ID = ?", arrayOf(product_id))
            return true


        }
//updating the total price for the new added quantity
    fun updateprice(product_id: String?,total1: Int): Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TOTAL_PRICE, total1)
        db.update(TABLE_NAME, contentValues, "$PRODUCT_ID = ?", arrayOf(product_id))
        return true


    }

    fun clearcart(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null,null)
        Toast.makeText(context1, "Cart Cleared ", Toast.LENGTH_SHORT).show()
        val i = Intent(context1, MainActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context1.startActivity(i)


    }

    fun clearbyid(product_id: String?):Int{
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$PRODUCT_ID= ?", arrayOf(product_id))
    }


    fun finalprice(): String{
        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select SUM($TOTAL_PRICE) from "+ TABLE_NAME,
            null)
        var x = ""
        while(result.moveToNext()){
            x = result.getString(0)
        }
        return x

    }

    fun getAllProcess(){

        val db = this.readableDatabase
        val result: Cursor = db.rawQuery("select * from " + TABLE_NAME, null)
    //so i first got the products from the SQLite Database

        while (result.moveToNext()){
            val product_name = result.getString(1)
            val product_qtty = result.getString(2)
            val product_cost = result.getString(3)
            val total_price = result.getString(4)
            val all_total_price = finalprice()


            Retromakeorder(product_name,product_qtty,product_cost,total_price,all_total_price)
            //Placed the values in a function which i am going to call retrofit for the posting
            // use retrofit to send API
            // To call a retrofit function ******
        }



    }
    //so this function i put the values as a parameter now that i have them
    fun Retromakeorder(product_name:String,product_qtty:String,product_cost:String,total_price:String,all_total_price:String){
            val order_code = "ARF54S"//created an order code

        val prefs: SharedPreferences = context1.getSharedPreferences("usser",
            AppCompatActivity.MODE_PRIVATE//the tel was in the preference so i get hem from the preference
        )
        val tel = prefs.getString("tel","")
        val apiInterface = ApiInterface.create()
        val jsonObject = JSONObject()
        //and now i insert the product in the cart that i have extracted from SQLite and the tel from prefs into an object now for posting i to the dbase
        jsonObject.put("product_name", product_name)
        jsonObject.put("product_qtty", product_qtty)
        jsonObject.put("product_cost", product_cost)
        jsonObject.put("total_price", total_price)
        jsonObject.put("all_total_price", all_total_price)
        jsonObject.put("order_code", order_code)
        jsonObject.put("tel", tel)


        //convert above json object to string
        val jsonObjectString = jsonObject.toString()

        //we use kotlin coroutines...used to run asynchronous services
        //A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
        //we create a request body
        val requestBody = jsonObjectString.toRequestBody("appication/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            //above request body post it to make payment

            val response = apiInterface.processorder(requestBody)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    Toast.makeText(context1,"Check Your Phone to Complete Payment "+response, Toast.LENGTH_LONG).show()

                }
                else{
                    Toast.makeText(context1,response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }

        }


    }











}

