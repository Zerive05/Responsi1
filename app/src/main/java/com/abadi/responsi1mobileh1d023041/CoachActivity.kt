package com.abadi.responsi1mobileh1d023041 // Sesuaikan dengan nama package Anda

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.abadi.responsi1mobileh1d023041.data.model.Coach // Import data class Coach
import com.bumptech.glide.Glide

class CoachActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach)

        // --- Atur ActionBar ---
        supportActionBar?.title = "Head Coach"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // --- 1. Temukan View dari layout XML ---
        val ivCoachPhoto: ImageView = findViewById(R.id.iv_coach_photo)
        val tvCoachName: TextView = findViewById(R.id.tv_coach_name)
        val tvCoachDob: TextView = findViewById(R.id.tv_coach_dob)
        val tvCoachNationality: TextView = findViewById(R.id.tv_coach_nationality)

        // --- 2. Ambil data 'Coach' yang dikirim dari MainActivity ---
        val coach: Coach? = if (Build.VERSION.SDK_INT >= 33) {
            // Cara baru untuk Android 13 (Tiramisu) ke atas
            intent.getParcelableExtra("COACH_DATA", Coach::class.java)
        } else {
            // Cara lama yang sudah deprecated
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("COACH_DATA")
        }

        // --- 3. Masukkan data API ke View ---
        if (coach != null) {
            // Data ini WAJIB dari API
            tvCoachName.text = coach.name
            tvCoachDob.text = coach.dateOfBirth
            tvCoachNationality.text = coach.nationality
        }

        // --- 4. Muat gambar statis (pengecualian dari soal) ---
        Glide.with(this)
            .load(R.drawable.img_coach_allegri) // Memuat dari drawable
            .into(ivCoachPhoto)
    }

    // Fungsi untuk tombol kembali di ActionBar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}