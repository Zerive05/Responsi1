package com.abadi.responsi1mobileh1d023041 // Sesuaikan dengan nama package Anda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Mengatur judul di ActionBar
        supportActionBar?.title = "Club History"

        // Menampilkan tombol kembali (panah) di ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Fungsi ini akan dipanggil ketika tombol kembali di ActionBar ditekan
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Kembali ke halaman sebelumnya
        return true
    }
}