package dev.stonarini.st_ream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.stonarini.st_ream.player.play
import dev.stonarini.st_ream.player.stop
import dev.stonarini.st_ream.ui.theme.StreamTheme

class PlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val app = applicationContext as App
        val songViewModel = app.getSongViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            StreamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedVisibility(
                        visible = true,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        val id = remember { mutableStateOf(songViewModel.id) }
                        val isPlaying = remember { mutableStateOf(songViewModel.isPlaying) }
                        val name = remember { mutableStateOf(songViewModel.songTitle) }

                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(text = name.value, modifier = Modifier.align(CenterHorizontally))
                            if (isPlaying.value) {
                                Icon(imageVector = Icons.Filled.Pause, contentDescription = "Stop", tint = Color.White,
                                    modifier = Modifier.fillMaxHeight().align(CenterHorizontally).clickable {
                                        isPlaying.value = false
                                        stop()
                                    })
                            } else {
                                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.White,
                                    modifier = Modifier.fillMaxHeight().align(CenterHorizontally).clickable {
                                        isPlaying.value = true
                                        play(id.value)
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}