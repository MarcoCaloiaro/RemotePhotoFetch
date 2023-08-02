package com.marcocaloiaro.remotephotofetch.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcocaloiaro.remotephotofetch.data.OnceTasks
import com.marcocaloiaro.remotephotofetch.data.local.LocalPhoto
import com.marcocaloiaro.remotephotofetch.data.local.LocalPhotoRepository
import com.marcocaloiaro.remotephotofetch.data.remote.PhotoApiService
import com.marcocaloiaro.remotephotofetch.model.toLocalPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import jonathanfinerty.once.Once
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val photoApiService: PhotoApiService,
    private val localPhotoRepository: LocalPhotoRepository
) : ViewModel() {

    val viewState = mutableStateOf(MainViewState.empty)

    private val effects: MutableSharedFlow<MainEffect> = MutableSharedFlow()

    init {
        getAllPhotos()
    }

    fun onIntentTriggered(intent: MainIntent) {
        when (intent) {
            MainIntent.ScreenLaunched -> checkIfFirstPhotoWasFetched()
            MainIntent.AddPhotoClicked -> getRandomPhoto()
            is MainIntent.PhotoSelected -> selectPhoto(intent.photo)
            MainIntent.CloseSheetClicked -> closeSheet()
        }
    }

    private fun checkIfFirstPhotoWasFetched() {
        if (!Once.beenDone(OnceTasks.FIRST_PICTURE_FETCHED)) {
            getRandomPhoto()
            Once.markDone(OnceTasks.FIRST_PICTURE_FETCHED)
        }
    }

    private fun closeSheet() {
        viewModelScope.launch {
            effects.emit(MainEffect.CloseSheet)
        }
    }

    fun processEffects(): SharedFlow<MainEffect> {
        return effects.asSharedFlow()
    }

    private fun selectPhoto(photo: LocalPhoto) {
        viewModelScope.launch {
            viewState.value = viewState.value.copy(selectedPhoto = photo)
        }
    }

    private fun getRandomPhoto() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val photoResult = photoApiService.getRandomPhoto()
                localPhotoRepository.insertPhoto(photoResult.toLocalPhoto())
            }
        }
    }

    private fun getAllPhotos() {
        viewModelScope.launch {
            viewState.value = viewState.value.copy(shouldLoad = true)
            withContext(Dispatchers.IO) {
                localPhotoRepository.getAllPhotos().collect {
                    withContext(Dispatchers.Main) {
                        viewState.value = viewState.value.copy(photos = it, shouldLoad = false)
                    }
                }
            }
        }
    }

}

