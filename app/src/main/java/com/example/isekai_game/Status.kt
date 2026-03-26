package com.example.isekai_game

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Status : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_status)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        val hp = findViewById<TextView>(R.id.status_hp)
        val backbtn = findViewById<Button>(R.id.backBtn)
        val ep = findViewById<TextView>(R.id.status_ep)
        val mp = findViewById<TextView>(R.id.status_mp)
        val strength = findViewById<TextView>(R.id.status_strength)
        val agility = findViewById<TextView>(R.id.status_agility)
        val power = findViewById<TextView>(R.id.status_power)
        val equip = findViewById<TextView>(R.id.status_equip)

        ep.text = intent.getStringExtra("ep") ?: "100"
        mp.text = intent.getStringExtra("mp") ?: "50"
        strength.text = intent.getStringExtra("strength") ?: "10"
        hp.text = intent.getStringExtra("hp") ?: "100"
        agility.text = intent.getStringExtra("agility") ?: "5"
        equip.text = intent.getStringExtra("equip") ?: "Knife"
        power.text = intent.getStringExtra("power") ?: "20"
        
        backbtn.setOnClickListener {
            finish()
        }
    }
}
