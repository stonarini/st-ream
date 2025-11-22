package dev.stonarini.st_ream

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stonarini.st_ream.player.*

@Composable
fun PlayBack(songViewModel: SongViewModel) {
    val id = remember { mutableStateOf(songViewModel.id) }
    val isPlaying = remember { mutableStateOf(songViewModel.isPlaying) }
    val name = remember { mutableStateOf(songViewModel.songTitle) }
    val artist = remember { mutableStateOf(songViewModel.artistName) }

    val songs = remember { mutableStateListOf<Song>() }

    LaunchedEffect(Unit) {
        Api.getSongs { retrievedSongs ->
            songs.addAll(retrievedSongs)
        }
    }

    LazyColumn() {
        item { Text(text = if (isPlaying.value) "Music is playing" else "Music is stopped") }
        items(songs) { song ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 5.dp)
                .clickable {
                    if (id.value == song.id) {
                        isPlaying.value = false
                        stop()
                    } else {
                        id.value = song.id
                        name.value = song.name
                        artist.value = song.artist
                        isPlaying.value = true
                        play(song.id)
                    }
                }) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = song.name, color = Color.White, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
                    Text(text = song.artist, color = Color.White, style = TextStyle(fontSize = 15.sp))
                }
            }
        }
        item {
            PlayerFooter(id = id, isPlaying = isPlaying, name = name, artist = artist)
        }
    }
}
