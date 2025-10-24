package com.abadi.responsi1mobileh1d023041.ui.viewmodel // Package ini sudah benar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// IMPORT YANG DIPERBAIKI:
import com.abadi.responsi1mobileh1d023041.data.model.TeamResponse
import com.abadi.responsi1mobileh1d023041.data.network.RetrofitClient
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    // ⚠️ PENTING: GANTI TOKEN INI
    // Dapatkan token Anda sendiri dari https://football-data.org/client/register
    // Token di bawah ini dari file JSON Anda, gunakan HANYA untuk tes jika token Anda belum bisa.
    private val API_TOKEN = "393609ed85a14e1ea4628dd707a7bb90"

    // --- LiveData untuk Data Tim ---
    // _teamData bersifat private, hanya bisa diubah dari dalam ViewModel ini
    private val _teamData = MutableLiveData<TeamResponse>()
    // teamData bersifat public, hanya bisa dibaca (diamati) oleh Activity
    val teamData: LiveData<TeamResponse> = _teamData

    // --- LiveData untuk Status Loading ---
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // --- LiveData untuk Pesan Error ---
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Blok 'init' akan otomatis berjalan saat ViewModel ini pertama kali dibuat
    init {
        fetchTeamData()
    }

    /**
     * Mengambil data tim dari API menggunakan Coroutines
     */
    private fun fetchTeamData() {
        _isLoading.value = true // Mulai loading
        // viewModelScope otomatis menjalankan ini di background thread
        viewModelScope.launch {
            try {
                // Memanggil fungsi 'suspend' dari ApiService
                val response = RetrofitClient.instance.getTeamData(API_TOKEN)

                if (response.isSuccessful && response.body() != null) {
                    // Jika sukses, masukkan datanya ke LiveData
                    _teamData.postValue(response.body())
                } else {
                    // Jika gagal (misal: token salah, server error)
                    _error.postValue("Gagal memuat data: ${response.message()}")
                    Log.e("MainViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                // Jika error (misal: tidak ada internet)
                _error.postValue("Terjadi kesalahan: ${e.message}")
                Log.e("MainViewModel", "Exception: ${e.message}")
            } finally {
                _isLoading.value = false // Selesai loading
            }
        }
    }
}