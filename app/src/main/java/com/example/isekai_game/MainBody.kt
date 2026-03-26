package com.example.isekai_game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainBody : AppCompatActivity() {
    private var mpbgm: MediaPlayer? = null
    private var mploss: MediaPlayer? = null
    private var mpwronghit: MediaPlayer? = null
    private var mplimit: MediaPlayer? = null
    private var mpdamage: MediaPlayer? = null
    private var mpwin: MediaPlayer? = null
    private var mpend: MediaPlayer? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_body)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            mploss = MediaPlayer.create(this, R.raw.voicykhatamgoodbye)
            mpdamage = MediaPlayer.create(this, R.raw.ahh)
            mpwin = MediaPlayer.create(this, R.raw.iwin)
            mpend = MediaPlayer.create(this, R.raw.endwin)
            mpwronghit = MediaPlayer.create(this, R.raw.faa)
            mplimit = MediaPlayer.create(this, R.raw.modi)
            mpbgm = MediaPlayer.create(this, R.raw.nature)

            mpbgm?.isLooping = true
            mpbgm?.setVolume(0.1f, 0.1f)
            mpbgm?.start()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error initializing audio", Toast.LENGTH_SHORT).show()
        }

        val monsterimg = findViewById<ImageView>(R.id.monsterImg)
        val monstername = findViewById<TextView>(R.id.monsterName)
        val info = findViewById<TextView>(R.id.info)
        val hint1 = findViewById<TextView>(R.id.hint1)
        val hint2 = findViewById<TextView>(R.id.hint2)
        val inputhit = findViewById<TextView>(R.id.inputHit)
        val hitbtn = findViewById<TextView>(R.id.hitBtn)
        val forfiet = findViewById<TextView>(R.id.forfeit)
        val status = findViewById<TextView>(R.id.status)
        val name = findViewById<TextView>(R.id.name)

        val imageViews = listOf(
            listOf(R.drawable.slime1, R.drawable.slime2, R.drawable.slime3),
            listOf(R.drawable.goblin1, R.drawable.goblin2, R.drawable.goblin3),
            listOf(R.drawable.troll1, R.drawable.troll2, R.drawable.troll3),
            listOf(R.drawable.orc1, R.drawable.orc2, R.drawable.orc3),
            listOf(R.drawable.minataur1, R.drawable.minataur2, R.drawable.minataur3),
            listOf(R.drawable.dragon1, R.drawable.dragon2, R.drawable.dragon3)
        )

        val monster = listOf(
            "Slim Slime!!!",
            "Genz Goblin!!!",
            "Hulk The Troll!!!",
            "German Orc!!!",
            "Ancestor Minotaur!!!",
            "Sexy Dragon!!!"
        )

        val weapon = listOf(
            "knife",
            "ToothPick",
            "Sneeze",
            "Bangali Curse",
            "Girls Nails",
            "PokeBall"
        )

        var power = 20
        var hp = 100
        var ep = 100
        var mp = 50
        var strength = 10
        var agility = 5
        var set = 0

        info.text = "Attack with the ${weapon.getOrElse(set) { "None" }}"
        name.text = intent.getStringExtra("name") ?: "Player"

        hitbtn.setOnClickListener {
            val hitText = inputhit.text.toString()
            if (hitText.isEmpty()) {
                Toast.makeText(this, "Enter Your Hit", Toast.LENGTH_SHORT).show()
                playSound(mpdamage)
                hitbtn.isEnabled = false
                hitbtn.text = "Bro ( '-' ) Why"
                hitbtn.alpha = 0.8f
                inputhit.isEnabled = false
                hitbtn.postDelayed(
                    {
                        hitbtn.isEnabled = true
                        hitbtn.text = "Hit"
                        hitbtn.alpha = 1f
                        inputhit.isEnabled = true
                    },
                    1000
                )
                return@setOnClickListener
            }

            // Fixed splitting logic to handle both en-dash and hyphen
            val rangeText = hint2.text.toString()
            val parts = if (rangeText.contains("–")) rangeText.split("–") else rangeText.split("-")
            
            if (parts.size < 2) {
                Toast.makeText(this, "Invalid range format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val start = parts[0].trim().toIntOrNull() ?: 1
            val end = parts[1].trim().toIntOrNull() ?: 10
            val random = (start..end).random()

            val hitVal = hitText.toIntOrNull() ?: 0
            if (hitVal !in start..end) {
                Toast.makeText(this, "Please Enter Hit Between $start-$end", Toast.LENGTH_SHORT).show()
                playSound(mplimit)
                hitbtn.isEnabled = false
                hitbtn.text = "StAy iN yOuR LiMiT"
                hitbtn.alpha = 0.8f
                inputhit.isEnabled = false
                hitbtn.postDelayed(
                    {
                        hitbtn.isEnabled = true
                        hitbtn.text = "Hit"
                        hitbtn.alpha = 1f
                        inputhit.isEnabled = true
                    },
                    2000
                )
                return@setOnClickListener
            }

            if (hitVal == random) {
                Toast.makeText(this, "Hit Successfully", Toast.LENGTH_SHORT).show()
                monstername.text = "Monster Defeated!!"
                set += 1
                hp += 50
                ep += 50
                mp += 25
                strength += 10
                agility += 6
                power += 20
                hitbtn.isEnabled = false
                inputhit.isEnabled = false
                if (set == 5) {
                    playSound(mpend)
                    info.text = "You Won The Game"
                    hint1.text = ""
                    hint2.text = ""
                    inputhit.visibility = View.GONE
                    hitbtn.visibility = View.GONE
                    monsterimg.setImageResource(R.drawable.dragon2)
                    monsterimg.postDelayed({
                        monsterimg.setImageResource(R.drawable.endgame)
                        if (!isFinishing) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }, 12000)
                } else {
                    playSound(mpwin)
                    hitbtn.isEnabled = false
                    hitbtn.text = "YOu BEat him!"
                    hitbtn.alpha = 0.8f
                    inputhit.isEnabled = false
                    hitbtn.postDelayed(
                        {
                            hitbtn.isEnabled = true
                            hitbtn.text = "Hit"
                            hitbtn.alpha = 1f
                            inputhit.isEnabled = true
                        },
                        3000
                    )
                    monsterimg.setImageResource(imageViews[set][1])
                    monstername.text = "Next Monster Appears!"
                    monsterimg.postDelayed({
                        if (!isFinishing) {
                            monsterimg.setImageResource(imageViews[set][0])
                            monstername.text = monster[set]
                            info.text = "Attack with the ${weapon.getOrElse(set) { "None" }}"

                            hitbtn.isEnabled = true
                            hitbtn.text = "Hit"

                            inputhit.isEnabled = true
                            inputhit.text = ""
                        }
                    }, 3000)
                }
            } else if (hp > random) {
                if (power <= 0){
                    playSound(mploss)
                    Toast.makeText(this, "You Died", Toast.LENGTH_SHORT).show()
                    hitbtn.isEnabled = false
                    inputhit.isEnabled = false
                    hitbtn.text = "You Died"
                    monsterimg.setImageResource(imageViews[set][2])
                    monstername.text = "Game Over"
                    info.text = "Please Restart The Game"
                    hint1.text = ""
                    hint2.text = ""
                    inputhit.visibility = View.GONE
                    monsterimg.postDelayed({
                        if (!isFinishing) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }, 6000)

                }else{
                    playSound(mpwronghit)
                    Toast.makeText(this, "got injured with $random \nHP is $hp", Toast.LENGTH_SHORT).show()
                    hp -= random
                    ep -= random
                    mp -= random / 2
                    strength -= random / 10
                    agility -= random / 20
                    power -= 5
                    hitbtn.isEnabled = false
                    hitbtn.text = "You got injured!"
                    hitbtn.alpha = 0.8f
                    inputhit.isEnabled = false
                    hitbtn.postDelayed(
                        {
                            hitbtn.isEnabled = true
                            hitbtn.text = "Hit"
                            hitbtn.alpha = 1f
                            inputhit.isEnabled = true
                        },
                        1000
                    )
                }
            } else {
                playSound(mploss)
                Toast.makeText(this, "You Died", Toast.LENGTH_SHORT).show()
                monsterimg.setImageResource(imageViews[set][2])
                hitbtn.isEnabled = false
                inputhit.isEnabled = false
                hitbtn.text = "You Died"
                monstername.text = "Game Over"
                info.text = "Please Restart The Game"
                hint1.text = ""
                hint2.text = ""
                inputhit.visibility = View.GONE

                monsterimg.postDelayed({
                    if (!isFinishing) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }, 6000)

            }
        }

        status.setOnClickListener {
            val intent = Intent(this, Status::class.java)
            intent.putExtra("hp", hp.toString())
            intent.putExtra("ep", ep.toString())
            intent.putExtra("mp", mp.toString())
            intent.putExtra("strength", strength.toString())
            intent.putExtra("agility", agility.toString())
            intent.putExtra("power", power.toString())
            intent.putExtra("equip", weapon.getOrElse(set) { "None" })
            startActivity(intent)
        }

        forfiet.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you sure you want to forfeit?")
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun playSound(player: MediaPlayer?) {
        try {
            player?.let {
                if (it.isPlaying) {
                    it.stop()
                    it.prepare()
                }

                 if (mpbgm?.isPlaying == true) {
                    mpbgm?.pause()
                }

                it.setOnCompletionListener {
                    if (!isAnyEffectPlaying()) {
                        mpbgm?.start()
                    }
                }
                it.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isAnyEffectPlaying(): Boolean {
        return (mploss?.isPlaying == true) || (mpdamage?.isPlaying == true) || (mpwin?.isPlaying == true) || (mpend?.isPlaying == true) || (mpwronghit?.isPlaying == true) || (mplimit?.isPlaying == true)
    }

    override fun onPause() {
        super.onPause()
        if (mpbgm?.isPlaying == true) {
            mpbgm?.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isAnyEffectPlaying()) {
            mpbgm?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mploss?.release()
        mpdamage?.release()
        mpwin?.release()
        mpend?.release()
        mpwronghit?.release()
        mplimit?.release()
        mpbgm?.release()
    }
}
