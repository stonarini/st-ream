package dev.stonarini.st_ream

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class App : Application(), ViewModelStoreOwner {
    private lateinit var songViewModel: SongViewModel
    override lateinit var viewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()
        viewModelStore = ViewModelStore()
        songViewModel = ViewModelProvider(this)[SongViewModel::class.java]
    }

    fun getSongViewModel(): SongViewModel {
        return songViewModel
    }
}