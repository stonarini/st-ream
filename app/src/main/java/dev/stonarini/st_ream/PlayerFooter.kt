
package dev.stonarini.st_ream

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stonarini.st_ream.player.*

@Composable
fun PlayerFooter(id:  MutableState<Int>, isPlaying: MutableState<Boolean>, name: MutableState<String>, artist: MutableState<String>) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    if (id.value > 0) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp, 5.dp)
        .clickable {
            val intent = Intent(context, PlayerActivity::class.java)
            launcher.launch(intent)
        }) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name.value,
                color = Color.White,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Text(text = artist.value, color = Color.White, style = TextStyle(fontSize = 15.sp))
        }
        if (isPlaying.value) {
            Icon(imageVector = Icons.Filled.Pause, contentDescription = "Stop", tint = Color.White,
                modifier = Modifier
                    .align(CenterVertically)
                    .clickable {
                        isPlaying.value = false
                        stop()
                    })
        } else {
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.White,
                modifier = Modifier
                    .align(CenterVertically)
                    .clickable {
                        isPlaying.value = true
                        play(id.value)
                    })
        }
    }
    }
}