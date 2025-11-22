package dev.stonarini.st_ream.player

import android.media.MediaPlayer

private val mediaPlayer = MediaPlayer();
private var currSong = 0;

fun play(id: Int) {
    if (currSong == id) {
        mediaPlayer.start();
        return;
    }
    currSong = id;
    mediaPlayer.reset();
    mediaPlayer.setDataSource("http://192.168.xx.xx:8000/stream/$id");
    mediaPlayer.prepare();
    mediaPlayer.start();
}

fun start() {
    mediaPlayer.start();
}

fun stop() {
    mediaPlayer.pause();
}

fun release() {
    mediaPlayer.release();
}

fun isPlaying(): Boolean {
    return mediaPlayer.isPlaying;
}

