package com.example.medevac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tester.medaData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class SecondActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private val packageCollectionRef = Firebase.firestore.collection("persons")
    private fun savePackage(pack: Package) = CoroutineScope(Dispatchers.IO).launch {
        try {
            packageCollectionRef.add(pack).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@SecondActivity, "Successfully Saved Data", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@SecondActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        todoAdapter = TodoAdapter(mutableListOf())
        rvMDItems.adapter = todoAdapter
        rvMDItems.layoutManager = LinearLayoutManager(this)


        btnAddMD.setOnClickListener {
            val todoTitle = etMDTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = medaData(todoTitle)
                todoAdapter.addTodo(todo)
                etMDTitle.text.clear()
            }
        }
        btnDeleteDoneMDS.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
        btnUploadMDS.setOnClickListener{
            val datapack = etMDTitle.text.toString()
            val pack = Package(datapack)
            savePackage(pack)
        }

    }
}

