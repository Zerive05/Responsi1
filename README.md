# Responsi1
<img src="https://raw.githubusercontent.com/Zerive05/Responsi1main/assets/Responsi1.gif" width="200">

## Deskripsi
## 📱 Fitur Aplikasi

* **Halaman Utama (Dinamis):** Menampilkan halaman selamat datang dengan logo, nama klub, dan deskripsi singkat. Logo dan nama klub diambil secara dinamis dari API. Halaman ini juga berfungsi sebagai menu navigasi utama.
* **Club History (Statis):** Menampilkan halaman statis yang berisi foto tim dan teks sejarah lengkap klub AC Milan.
* **Head Coach (Semi-Dinamis):** Menampilkan halaman yang berisi foto pelatih (statis, dari *drawable*) dan data detail pelatih seperti nama, tanggal lahir, dan kebangsaan (dinamis, dari API).
* **Team Squad (Dinamis):** 🏃‍♂️ Menampilkan daftar lengkap seluruh pemain di tim utama menggunakan `RecyclerView`. Data ini sepenuhnya diambil dari API.
* **Pewarnaan Posisi:** 🎨 Setiap item pemain di halaman *Team Squad* diberi warna latar belakang yang berbeda sesuai dengan kategori posisinya (Goalkeeper, Defender, Midfielder, Forward) untuk identifikasi visual yang mudah.
* **Detail Pemain (Pop-up):** ℹ️ Ketika seorang pemain di daftar *Team Squad* diklik, sebuah *pop-up* (Alert Dialog) akan muncul, menampilkan detail lebih lanjut tentang pemain tersebut (Nama, Tanggal Lahir, Kebangsaan, dan Posisi).

## ⚙️ Alur Data Aplikasi

Berikut adalah penjelasan alur data aplikasi, mulai dari pemanggilan API hingga data tersebut disajikan di layar pengguna.

### 1. Pemuatan Data Awal (App Start)

1.  **Inisialisasi:** Saat aplikasi pertama kali dibuka, `MainActivity` akan dibuat. `MainActivity` kemudian segera menginisialisasi `MainViewModel`.
2.  **Pemanggilan API:** Begitu `MainViewModel` dibuat, blok `init`-nya langsung memicu fungsi `fetchTeamData()`.
3.  **Loading:** `MainViewModel` menyetel status `_isLoading` menjadi `true`, yang kemudian "diamati" (observed) oleh `MainActivity` untuk menampilkan `ProgressBar`.
4.  **Network Request:** ☁️ Menggunakan *Coroutines* (`viewModelScope`), `MainViewModel` memanggil `RetrofitClient` untuk mengirimkan *request* GET ke *endpoint* API `https://api.football-data.org/v4/teams/98` (ID untuk AC Milan).
5.  **Parsing Data:** API mengembalikan data dalam format JSON. *Library* Retrofit dan GSON secara otomatis mengubah (parse) JSON tersebut menjadi *data class* Kotlin yang telah kita buat, yaitu `TeamResponse`.
6.  **Penyimpanan Data:** Jika *request* sukses, objek `TeamResponse` disimpan ke dalam `MutableLiveData` bernama `_teamData`. Status `_isLoading` diubah menjadi `false`.
7.  **Penyajian di UI:** `MainActivity` (yang sedang "mengamati" *LiveData*) mendeteksi perubahan ini:
    * `ProgressBar` disembunyikan.
    * Data dari `teamData` digunakan untuk menampilkan logo (`crestUrl`) menggunakan *library* Glide dan nama klub (`name`) ke `TextView`.
    * Data pelatih (`team.coach`) dan daftar skuad (`team.squad`) disimpan dalam variabel lokal di `MainActivity` untuk digunakan nanti.

### 2. Navigasi ke Halaman Menu (History & Coach)

* **Club History:** Saat menu "Club History" diklik, `MainActivity` memulai `HistoryActivity` baru. Halaman ini **tidak** menggunakan data dari API; ia hanya memuat gambar dan teks statis dari *resource* (drawable dan string).
* **Head Coach:** Saat menu "Head Coach" diklik:
    1.  `MainActivity` membuat `Intent` untuk `CoachActivity`.
    2.  Objek `coachData` (yang berjenis `Coach` dan sudah `Parcelable`) **dikirimkan** melalui `intent.putExtra("COACH_DATA", coachData)`.
    3.  `CoachActivity` mengambil objek `Coach` ini dari *intent*.
    4.  Data (seperti `coach.name`, `coach.dateOfBirth`) kemudian ditampilkan di `TextView` yang sesuai.

### 3. Navigasi ke Team Squad & Detail Pemain

1.  **Mengirim Data Skuad:** Saat menu "Team Squad" diklik:
    1.  `MainActivity` membuat `Intent` untuk `SquadActivity`.
    2.  Seluruh daftar pemain (`squadData` yang berjenis `ArrayList<SquadMember>`) **dikirimkan** melalui `intent.putParcelableArrayListExtra("SQUAD_DATA", squadData)`.
2.  **Menampilkan Daftar:**
    1.  `SquadActivity` menerima `ArrayList` tersebut.
    2.  Daftar ini kemudian diserahkan ke `SquadAdapter` untuk diproses.
    3.  `RecyclerView` menggunakan `SquadAdapter` untuk menampilkan setiap pemain satu per satu.
3.  **Logika di Adapter:** Untuk setiap pemain, fungsi `onBindViewHolder` di dalam `SquadAdapter` dipanggil. Fungsi ini:
    * Mengisi `TextView` dengan nama dan posisi pemain.
    * Mengecek nilai `player.position` dalam sebuah *statement* `when`.
    * Berdasarkan hasil `when`, `CardView` item tersebut diberi warna latar yang sesuai (Kuning, Biru, Hijau, atau Merah).
4.  **Menampilkan Detail Pop-up:**
    1.  `SquadAdapter` memiliki *lambda function* (`onPlayerClicked`) yang diteruskan dari `SquadActivity`.
    2.  Saat item `RecyclerView` diklik, *lambda* ini dipanggil, mengirimkan objek `SquadMember` yang diklik kembali ke `SquadActivity`.
    3.  `SquadActivity` kemudian memanggil fungsi `showPlayerDetailsDialog(player)`.
    4.  Fungsi ini membuat `AlertDialog` kustom yang menggunakan *layout* `dialog_player_detail.xml` dan mengisinya dengan data dari objek `player` yang diterima.