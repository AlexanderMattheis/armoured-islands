package com.gdx.game.system.media

import com.badlogic.gdx.audio.Music

object MusicPlayer {
    var lastMusic: Music? = null

    fun play(music: Music) {
        val last = lastMusic

        if (last != null && last != music) {
            stop(last)
        }

        music.play()
        music.isLooping = true
        lastMusic = music
    }

    fun stop(music: Music) {
        music.stop()
        music.dispose()
    }
}