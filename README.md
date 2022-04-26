# STEPS TO SET UP A RECYCLER VIEW
RecyclerView is the ViewGroup that contains the views corresponding to your data.
##Steps For reating a recycler view
1.Create A model. A model is a file that recieves and holds data from the API/Server.
Copy this model
'''android
data class productmodel (
//This model as per your data in the api
    var names: String = "",
    var category : String = "",
    var cost : String = "",
    var phone : String = ""
    )


