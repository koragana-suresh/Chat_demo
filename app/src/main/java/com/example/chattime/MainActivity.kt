package com.example.chattime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var  userRecyclerView: RecyclerView
    private lateinit var userList : ArrayList<user>
    private  lateinit var adapter: userAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth= FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance().getReference()
        userList =ArrayList()
        adapter= userAdapter(this,userList)
        userRecyclerView=findViewById(R.id.userRecycletView)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.adapter=adapter

        mDbRef.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children){
                    val currentuser =  postSnapshot.getValue(user::class.java)
                if(mAuth.currentUser?.uid!=currentuser?.uid){
                    userList.add(currentuser!!)
                }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.log_out){
            mAuth.signOut()
            val intent =Intent(this@MainActivity,login::class.java)
            finish()
            startActivity(intent)

            return true
        }
        return true
    }
}
