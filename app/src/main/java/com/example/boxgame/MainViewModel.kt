package com.example.boxgame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val totalBoxes: MutableLiveData<Int> = MutableLiveData()
    val _totalBoxes: LiveData<Int> = totalBoxes

    val gameFinished: MutableLiveData<Boolean> = MutableLiveData(false)
    val _gameFinished: LiveData<Boolean> = gameFinished

    val list: ArrayList<BoxItem> = ArrayList()
    var itemsSelected: ArrayList<Int> = ArrayList()

    var lastIndex: Int = -1

    fun setTotalBoxes(total: Int) {
        totalBoxes.postValue(total)
    }

    fun setupList(totalItems: Int) {
        if(list.isEmpty()){
            for (i in 1..totalItems) {
                list.add(
                    BoxItem(
                        i.toString(),
                        isSelected = false,
                        redCompleted = false,
                        greenCompleted = false,
                        blueCompleted = false
                    )
                )
            }
            selectRandomElement()
        }
    }


    fun checkBlueCompleted(): Boolean {
        for (i in list) {
            if (!i.blueCompleted) {
                return false
            }
        }
        return true
    }

    fun checkGreenCompleted(): Boolean {
        for (i in list) {
            if (!i.greenCompleted) {
                return false
            }
        }
        return true
    }

    fun selectRandomElement() {

        var randomIndex = getRandomIndex()

        if (randomIndex == null) {
            itemsSelected = ArrayList()

            if (checkGreenCompleted()) {
                if (checkBlueCompleted()) {
                    gameFinished.postValue(true)
                } else {
                    Log.i("CompletedGame", "BlueNull")
                    randomIndex = getRandomIndex()
                    list[randomIndex!!].blueCompleted = true
                }
            } else {
                Log.i("CompletedGame", "Green")
                randomIndex = getRandomIndex()
                list[randomIndex!!].greenCompleted = true
            }

        } else {
            list[randomIndex].isSelected = true
            if (list[randomIndex].redCompleted) {
                if (list[randomIndex].greenCompleted) {
                    if (list[randomIndex].blueCompleted) {
                        if (checkBlueCompleted() && checkGreenCompleted()) {
                            gameFinished.postValue(true)
                        }
                    } else {
                        Log.i("CompletedGame", "Blue")
                        if (checkGreenCompleted()) {
                            list[randomIndex].blueCompleted = true
                            if (checkBlueCompleted()) {
                                gameFinished.postValue(true)
                            }
                        }else{
                            list[randomIndex].greenCompleted = true
                        }
                    }
                } else {
                    list[randomIndex].greenCompleted = true
                }
            } else {
                list[randomIndex].redCompleted = true
            }
            itemsSelected.add(randomIndex)
            lastIndex = randomIndex
        }

    }

    fun getRandomIndex(): Int? {

        if (itemsSelected.count() == list.count()) {
            return null
        }
        val randomIndex = (0..<list.count()).random()
        if (itemsSelected.contains(randomIndex)) {
            return getRandomIndex()
        }
        return randomIndex
    }

}