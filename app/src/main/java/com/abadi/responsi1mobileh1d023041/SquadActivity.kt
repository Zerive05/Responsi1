package com.abadi.responsi1mobileh1d023041 // Sesuaikan nama package Anda

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abadi.responsi1mobileh1d023041.data.model.SquadMember
import com.abadi.responsi1mobileh1d023041.ui.adapter.SquadAdapter

class SquadActivity : AppCompatActivity() {

    private lateinit var rvSquad: RecyclerView
    private lateinit var squadAdapter: SquadAdapter
    private var squadList: ArrayList<SquadMember> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad)

        // --- Atur ActionBar ---
        supportActionBar?.title = "Team Squad"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvSquad = findViewById(R.id.rv_squad)

        // --- 1. Ambil data ArrayList<SquadMember> dari Intent ---
        val list = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra("SQUAD_DATA", SquadMember::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra("SQUAD_DATA")
        }

        if (list != null) {
            squadList = list
        }

        // --- 2. Setup RecyclerView ---
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Tentukan apa yang terjadi saat item diklik (memanggil fungsi showPlayerDetailsDialog)
        squadAdapter = SquadAdapter(squadList) { player ->
            showPlayerDetailsDialog(player)
        }

        // Pasang adapter dan layout manager ke RecyclerView
        rvSquad.layoutManager = LinearLayoutManager(this)
        rvSquad.adapter = squadAdapter
    }

    /**
     * Fungsi untuk menampilkan Pop-up Detail Pemain
     */
    private fun showPlayerDetailsDialog(player: SquadMember) {
        // Buat view dari layout dialog_player_detail.xml
        val view = LayoutInflater.from(this)
            .inflate(R.layout.dialog_player_detail, null)

        // Ambil semua TextView dari layout dialog
        val tvDetailName: TextView = view.findViewById(R.id.tv_detail_name)
        val tvDetailDob: TextView = view.findViewById(R.id.tv_detail_dob)
        val tvDetailNationality: TextView = view.findViewById(R.id.tv_detail_nationality)
        val tvDetailPosition: TextView = view.findViewById(R.id.tv_detail_position)

        // Isi data pemain ke TextView
        tvDetailName.text = player.name
        tvDetailDob.text = player.dateOfBirth ?: "N/A"
        tvDetailNationality.text = player.nationality ?: "N/A"
        tvDetailPosition.text = player.position ?: "N/A"

        // Buat dan tampilkan AlertDialog
        AlertDialog.Builder(this)
            .setView(view)
            .setPositiveButton("OK", null) // Tombol OK
            .create()
            .show()
    }

    // Fungsi untuk tombol kembali di ActionBar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}