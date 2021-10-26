package com.ff.compose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel {

    public lateinit var players : MutableLiveData<List<Player>>

    public constructor()
    {
        players = MutableLiveData<List<Player>>()
    }

}