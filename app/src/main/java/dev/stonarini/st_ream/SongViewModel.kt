package dev.stonarini.st_ream

import androidx.lifecycle.ViewModel

class SongViewModel : ViewModel() {
    var id: Int = 0
    var songTitle: String = ""
    var artistName: String = ""
    var duration: Long = 0
    var isPlaying: Boolean = false
}