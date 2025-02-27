package com.alexp.vkfast.samples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class InstagramProfileViewModel :ViewModel() {


    private val initialList = mutableListOf<InstagramModel>().apply {
        repeat(500)
        {
            add(
                InstagramModel(id = it, title = "Title:$it",
                isFollowed = Random.nextBoolean())
            )
        }
    }

    private val _isFollowing = MutableLiveData<Boolean>()

    val isFollowing: LiveData<Boolean> = _isFollowing


    fun changeFollowingStatus()
    {
        val wasFollowing = _isFollowing.value?:false
        _isFollowing.value = !wasFollowing
    }


    private val _models = MutableLiveData<List<InstagramModel>>(initialList)
    val models: LiveData<List<InstagramModel>> = _models


    fun changeFollowingStatus(model: InstagramModel)
    {
        val modifiedList = _models.value?.toMutableList()?: mutableListOf()
        modifiedList.replaceAll {
            if(it == model)
            {
                it.copy(isFollowed = !it.isFollowed)
            }
            else
            {
                it
            }
        }

        _models.value = modifiedList
    }

    fun delete(model: InstagramModel)
    {
        val modifedList = _models.value?.toMutableList()?: mutableListOf()
        modifedList.remove(model)
        _models.value = modifedList
    }
}