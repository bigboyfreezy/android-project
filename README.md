# STEPS TO SET UP A RECYCLER VIEW
RecyclerView is the ViewGroup that contains the views corresponding to your data.
## Steps For reating a recycler view
1.Create A model. A model is a file that recieves and holds data from the API/Server.
Copy this model


```data class productmodel (
//This model as per your data in the api
    var names: String = "",
    var category : String = "",
    var cost : String = "",
    var phone : String = ""
    )
```

2.Create A sample reslayout to be used by the activity or fragment
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingTop="20dp"
    android:elevation="20dp"


    >
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="10dp"

        android:orientation="horizontal">


        <TextView
            android:id="@+id/names"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"

            android:text="Product Name"
            android:layout_marginTop="0dp"

            android:padding="10dp"
            android:textColor="#FF5722"
            android:textSize="20dp"
            android:textStyle="bold"

            />

        <TextView
            android:id="@+id/category"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="10dp"
            android:text="Product Category"
            android:layout_marginTop="20dp"
            android:textColor="#533131"
            android:textAlignment="textEnd"
            android:textSize="20dp"
            android:textStyle="bold"
            android:layout_gravity="end"

            android:layout_marginRight="20dp"

            />


    </LinearLayout>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="10dp"

        android:orientation="horizontal">


        <TextView
            android:id="@+id/cost"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"

            android:text="Product Cost"
            android:layout_marginTop="0dp"

            android:padding="10dp"
            android:textColor="#FF5722"
            android:textSize="20dp"
            android:textStyle="bold"

            />

        <TextView
            android:id="@+id/phone"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="10dp"
            android:text="Product Phone"
            android:layout_marginTop="20dp"
            android:textColor="#533131"
            android:textAlignment="textEnd"
            android:textSize="20dp"
            android:textStyle="bold"
            android:layout_gravity="end"

            android:layout_marginRight="20dp"

            />


    </LinearLayout>
    </LinearLayout>


</androidx.cardview.widget.CardView>
```
3. Create now an activity for the products and inside the activity layout paste this code
```
<androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/recyclerView"
        tools:listitem="@layout/product_single"
        />
```

4. Create an adapter named RecyclerAdapter and paste this
```

class RecyclerAdapter(var context: Context)://When you want to toast smthg without intent or activities
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    //View holder holds the views in single item.xml

    var productList : List<productmodel> = listOf() // empty product list


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {}

    //Note below code returns above class and pass the view
    override fun onCreateViewHolder(
        parent : ViewGroup,
        viewType : Int
    ) : RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_single, parent, false)
        return ViewHolder(view)
    }

    //so far item view is same as single item
    override fun onBindViewHolder(holder : RecyclerAdapter.ViewHolder, position : Int) {
        val names = holder.itemView.findViewById(R.id.names) as TextView
        val category = holder.itemView.findViewById(R.id.category) as TextView
        val cost = holder.itemView.findViewById(R.id.cost) as TextView
        val phone = holder.itemView.findViewById(R.id.phone) as TextView

        //bind
        val item = productList[position]
        names.text = item.names
        category.text = item.category
        cost.text = item.cost
        phone.text = item.phone





    }


    override fun getItemCount() : Int {
        return productList.size
    }

    //we will call this function in our loop j json array response
    fun setProductListItems(productList : List<productmodel>) {
        this.productList = productList
        notifyDataSetChanged()
    }


//here we will receive a product list and update
//bring data from server and update to put the list upto products
//then notify the other data that there is a change


}
```
5. Inside the activity paste this code

```
  val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        RecyclerAdapter = RecyclerAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)






        //Here is where you use loop j o retrivethe data from the api and pass it into the recycler




        //pass the product list to adapter
        RecyclerAdapter = RecyclerAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = RecyclerAdapter
        recyclerView.setHasFixedSize(true)
```


