package com.example.labtwo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), CustomAdapter.myClickListener {
    lateinit var myRec: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myRec = findViewById(R.id.rv)
        progressBar = findViewById(R.id.progressBar)
        image = findViewById(R.id.image)
        myRec.layoutManager = GridLayoutManager(this, 3)
        val allFunctions = RetroClient().getRetrofitClients()
        allFunctions.getImages().enqueue(
            object : Callback<ImagesResponse> {
                override fun onResponse(
                    call: Call<ImagesResponse>,
                    response: Response<ImagesResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.size==0){
                            image.isVisible=true
                            myRec.isVisible=false
                            progressBar.isVisible=false
                            return
                        }
                        image.isVisible=false
                        myRec.isVisible=true
                        progressBar.isVisible=false
                        myRec.adapter = CustomAdapter(response.body(), this@MainActivity)
                    }

                }

                override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                    image.isVisible=true
                    myRec.isVisible=false
                    progressBar.isVisible=false
                    Toast.makeText(this@MainActivity, "Failed to Load", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }

    override fun onClick(position: Int) {
        CustomDialog(position)
    }

    fun CustomDialog(pos: Int) {
        val allFunctions = RetroClient().getRetrofitClients()
        val viewXML = layoutInflater.inflate(R.layout.custom_dialog, null)
        val builder = AlertDialog.Builder(this@MainActivity)
        var id = "${pos + 1}"
        builder.setView(viewXML)
        val dialog = builder.create()
        val Txt: TextView = viewXML.findViewById(R.id.title)
        val progressBar: ProgressBar = viewXML.findViewById(R.id.progressBar)
        val image: ImageView = viewXML.findViewById(R.id.imageVw)
        allFunctions.getSpecificPhoto(id).enqueue(
            object : Callback<ImagesResponseItem> {
                override fun onResponse(
                    call: Call<ImagesResponseItem>,
                    response: Response<ImagesResponseItem>
                ) {
                    if (response.isSuccessful) {
                        progressBar.isVisible = false
                        image.isVisible = false
                        Txt.isVisible = true
                        var valueToShow =
                            "Title: " + response.body()!!.title + "\n" + "albumId: " + response.body()!!.albumId + "\n" + "id: " + response.body()!!.id
                        Txt.text = valueToShow
                    } else {
                        image.isVisible = true
                        progressBar.isVisible = false
                        Txt.isVisible = false
                        Toast.makeText(
                            this@MainActivity,
                            "Something Wrong Happened!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ImagesResponseItem>, t: Throwable) {
                    image.isVisible = true
                    progressBar.isVisible = false
                    Txt.isVisible = false
                    Toast.makeText(this@MainActivity, "Failed to Load", Toast.LENGTH_SHORT).show()
                }
            }
        )
        dialog.show()
    }
}