package com.abadi.responsi1mobileh1d023041.ui.adapter // Sesuaikan nama package Anda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abadi.responsi1mobileh1d023041.R
import com.abadi.responsi1mobileh1d023041.data.model.SquadMember
import com.google.android.material.card.MaterialCardView

// Adapter ini menerima 2 parameter:
// 1. Daftar pemain (List<SquadMember>)
// 2. Sebuah "fungsi" yang akan dipanggil saat item diklik (onPlayerClicked)
class SquadAdapter(
    private val playerList: List<SquadMember>,
    private val onPlayerClicked: (SquadMember) -> Unit
) : RecyclerView.Adapter<SquadAdapter.PlayerViewHolder>() {

    /**
     * ViewHolder bertugas "memegang" view dari item_player.xml
     */
    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Ambil view dari layout item_player.xml
        val playerName: TextView = itemView.findViewById(R.id.tv_player_name)
        val playerPosition: TextView = itemView.findViewById(R.id.tv_player_position)
        val playerCard: MaterialCardView = itemView.findViewById(R.id.player_card)
    }

    /**
     * Dipanggil saat RecyclerView perlu membuat ViewHolder baru (baris baru)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        // Buat view dari layout item_player.xml
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    /**
     * Dipanggil untuk menghubungkan data (pemain) ke ViewHolder (tampilan)
     */
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        // Ambil data pemain di posisi ini
        val player = playerList[position]

        // Set data ke view
        holder.playerName.text = player.name
        holder.playerPosition.text = player.position

        // --- Logika WAJIB untuk Pewarnaan (SUDAH DIPERBARUI) ---
        val context = holder.itemView.context
        val colorRes = when (player.position) {
            // Kategori Goalkeeper (Kuning)
            "Goalkeeper" -> R.color.colorGoalkeeper

            // Kategori Defender (Biru)
            "Defender", "Defence",
            "Left-Back", "Right-Back",
            "Centre-Back", "Center-Back"
                -> R.color.colorDefender

            // Kategori Midfielder (Hijau)
            "Midfielder", "Midfield",
            "Defensive Midfield", "Defensive-Midfield",
            "Central Midfield",
            "Attacking Midfield"
                -> R.color.colorMidfielder

            // Kategori Forward (Merah)
            "Forward", "Attacker", "Offence",
            "Centre-Forward", "Center-Forward",
            "Left Winger", "Right Winger"
                -> R.color.colorForward

            // Default
            else -> R.color.colorOtherPosition
        }
        val color = ContextCompat.getColor(context, colorRes)
        holder.playerCard.setCardBackgroundColor(color)
        // --- Selesai Logika Pewarnaan ---

        // Atur OnClickListener untuk item ini
        holder.itemView.setOnClickListener {
            onPlayerClicked(player) // Panggil fungsi "lambda" dengan data pemain yg diklik
        }
    }

    /**
     * Memberi tahu RecyclerView ada berapa total item di daftar
     */
    override fun getItemCount(): Int {
        return playerList.size
    }
}