package com.example.isekai_game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri

class playerinfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_playerinfo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = findViewById<TextView>(R.id.name)
        val start1 = findViewById<Button>(R.id.start)
        val donate = findViewById<Button>(R.id.donate)

        start1.setOnClickListener {
            if(name.text.toString().isEmpty()){
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, MainBody::class.java)
                intent.putExtra("name", name.text.toString())
                startActivity(intent)
            }
        }

        donate.setOnClickListener {
            val upiId = "7310347742@ptsbi"
            val payeeName = "Shubham Singh Chauhan"
            val transactionNote = "Donation for Isekai Game"
            val amount = "10.00"
            val currency = "INR"

            val uriString = "upi://pay?pa=$upiId&pn=$payeeName&tn=$transactionNote&am=$amount&cu=$currency"
            val uri = uriString.toUri()

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri

            try {
                val chooser = Intent.createChooser(intent, "Pay with UPI")
                startActivity(chooser)
            } catch (_: Exception) {
                Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
