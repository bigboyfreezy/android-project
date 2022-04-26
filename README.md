# android-project
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
            android:id="@+id/prod_name"
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
            android:id="@+id/prod_category"
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
            android:id="@+id/prod_cost"
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
            android:id="@+id/prod_phone"
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
