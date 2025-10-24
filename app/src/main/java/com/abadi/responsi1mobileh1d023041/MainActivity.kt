package com.abadi.responsi1mobileh1d023041

import android.content.Intent // Pastikan Intent sudah diimpor
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.abadi.responsi1mobileh1d023041.data.model.Coach
import com.abadi.responsi1mobileh1d023041.data.model.SquadMember
import com.abadi.responsi1mobileh1d023041.ui.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

// Pastikan Anda sudah membuat file-file Activity ini:
// import com.abadi.responsi1mobileh1d023041.ui.activity.HistoryActivity
// import com.abadi.responsi1mobileh1d023041.ui.activity.CoachActivity
// import com.abadi.responsi1mobileh1d023041.ui.activity.SquadActivity


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var ivClubLogo: ImageView
    private lateinit var tvClubName: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var cardHistory: MaterialCardView
    private lateinit var cardCoach: MaterialCardView
    private lateinit var cardSquad: MaterialCardView

    private var coachData: Coach? = null
    private var squadData: ArrayList<SquadMember>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initViews()
        observeViewModel()
        setupClickListeners()
    }

    private fun initViews() {
        ivClubLogo = findViewById(R.id.iv_club_logo)
        tvClubName = findViewById(R.id.tv_club_name)
        progressBar = findViewById(R.id.progress_bar)
        cardHistory = findViewById(R.id.card_club_history)
        cardCoach = findViewById(R.id.card_head_coach)
        cardSquad = findViewById(R.id.card_team_squad)
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.teamData.observe(this) { team ->
            tvClubName.text = team.name
            Glide.with(this)
                .load(team.crestUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivClubLogo)

            coachData = team.coach
            squadData = ArrayList(team.squad)
        }
    }

    private fun setupClickListeners() {
        // --- FITUR HISTORY DIAKTIFKAN ---
        cardHistory.setOnClickListener {
            // Ganti com.abadi.responsi1mobileh1d023041.HistoryActivity::class.java
            // dengan path yang benar jika berbeda, misal: ui.activity.HistoryActivity::class.java
            val intent = Intent(this, com.abadi.responsi1mobileh1d023041.HistoryActivity::class.java)
            startActivity(intent)
        }

        // --- FITUR COACH DIAKTIFKAN ---
        cardCoach.setOnClickListener {
            if (coachData != null) {
                val intent = Intent(this, com.abadi.responsi1mobileh1d023041.CoachActivity::class.java)
                intent.putExtra("COACH_DATA", coachData)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Data pelatih belum dimuat", Toast.LENGTH_SHORT).show()
            }
        }

        // --- FITUR SQUAD DIAKTIFKAN ---
        cardSquad.setOnClickListener {
            if (squadData?.isNotEmpty() == true) {
                val intent = Intent(this, com.abadi.responsi1mobileh1d023041.SquadActivity::class.java)
                intent.putParcelableArrayListExtra("SQUAD_DATA", squadData)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Data skuad belum dimuat", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
