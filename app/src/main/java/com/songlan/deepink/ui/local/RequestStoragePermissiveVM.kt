package com.songlan.deepink.ui.local

import android.content.UriPermission
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.songlan.deepink.repository.DatabaseRepository

class RequestStoragePermissiveVM : ViewModel() {

    private val pLoadPersistedPermissionsLiveData = MutableLiveData<Any?>()

    val persistedPermissions = mutableListOf<UriPermission>()

    val loadPersistedPermissionsLiveData =
        Transformations.switchMap(pLoadPersistedPermissionsLiveData) {
            DatabaseRepository.loadPersistedUriPermissions()
        }

    fun loadPersistedPermissions() {
        pLoadPersistedPermissionsLiveData.value = pLoadPersistedPermissionsLiveData.value
    }

}