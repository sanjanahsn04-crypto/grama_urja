package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var powerListener: ValueEventListener
    private var userName = "Farmer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Receive the name passed from LoginActivity
        userName = intent.getStringExtra("USER_NAME") ?: "Farmer"

        val tvWelcome   = findViewById<TextView>(R.id.tvWelcome)
        val tvIcon      = findViewById<TextView>(R.id.tvIcon)
        val tvStatus    = findViewById<TextView>(R.id.tvStatus)
        val tvFreshness = findViewById<TextView>(R.id.tvFreshness)
        val tvReporter  = findViewById<TextView>(R.id.tvReporter)
        val cardPower   = findViewById<android.view.View>(R.id.cardPower)
        val btnOn       = findViewById<Button>(R.id.btnOn)
        val btnOff      = findViewById<Button>(R.id.btnOff)
        val btnPump     = findViewById<Button>(R.id.btnPump)

        // Show "Welcome, Raju!" on the home screen
        tvWelcome.text = "Welcome, $userName!"

        // Firebase path: zones → zone_1 → powerStatus
        dbRef = FirebaseDatabase.getInstance().reference
            .child("zones")
            .child("zone_1")
            .child("powerStatus")

        // ── REAL-TIME LISTENER ─────────────────────────────────────
        // This fires instantly when any farmer updates power status
        powerListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val isOn      = snapshot.child("isOn").getValue(Boolean::class.java) ?: true
                val timestamp = snapshot.child("timestamp").getValue(Long::class.java)
                    ?: System.currentTimeMillis()
                val reporter  = snapshot.child("reporter").getValue(String::class.java)
                    ?: "Community"

                if (isOn) {
                    tvIcon.text   = "⚡"
                    tvStatus.text = "POWER IS ON"
                    tvStatus.setTextColor(Color.parseColor("#2ECC71"))
                    cardPower.setBackgroundColor(Color.parseColor("#0d2e0d"))
                } else {
                    tvIcon.text   = "✖"
                    tvStatus.text = "POWER IS OFF"
                    tvStatus.setTextColor(Color.parseColor("#E74C3C"))
                    cardPower.setBackgroundColor(Color.parseColor("#2e0d0d"))
                }

                // Show how old the data is
                val mins = TimeUnit.MILLISECONDS.toMinutes(
                    System.currentTimeMillis() - timestamp
                )
                tvFreshness.text = when {
                    mins < 1  -> "Updated just now"
                    mins < 60 -> "Updated $mins min ago"
                    else      -> "Updated ${mins / 60} hr ago"
                }
                tvReporter.text = "by $reporter"
            }

            override fun onCancelled(error: DatabaseError) {
                tvStatus.text = "Connection error"
            }
        }

        // Start listening to Firebase
        dbRef.addValueEventListener(powerListener)

        // ── BUTTONS ────────────────────────────────────────────────
        btnOn.setOnClickListener {
            dbRef.setValue(
                mapOf(
                    "isOn"      to true,
                    "timestamp" to System.currentTimeMillis(),
                    "reporter"  to userName
                )
            )
        }

        btnOff.setOnClickListener {
            dbRef.setValue(
                mapOf(
                    "isOn"      to false,
                    "timestamp" to System.currentTimeMillis(),
                    "reporter"  to userName
                )
            )
        }

        btnPump.setOnClickListener {
            startActivity(Intent(this, PumpTimerActivity::class.java))
        }
    }

    // IMPORTANT: always remove the listener when the screen closes
    override fun onDestroy() {
        super.onDestroy()
        dbRef.removeEventListener(powerListener)
    }
}
