package com.farah.ecom.helpers

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.farah.ecom.MainActivity
import com.farah.ecom.model.CartModel

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












}

