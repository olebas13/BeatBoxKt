package org.olebas.beatboxkt

class Sound(val assetPath: String) {

    val name = assetPath.split("/").last().removeSuffix(WAV)

    companion object {
        private const val WAV = ".wav"
    }
}