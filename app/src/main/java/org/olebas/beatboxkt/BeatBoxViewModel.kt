package org.olebas.beatboxkt

import androidx.lifecycle.ViewModel

class BeatBoxViewModel : ViewModel() {

    var beatBox: BeatBox? = null

    override fun onCleared() {
        super.onCleared()
        beatBox?.release()
    }
}