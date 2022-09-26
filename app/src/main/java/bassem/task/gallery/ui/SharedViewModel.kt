package bassem.task.gallery.ui

import bassem.task.gallery.data.model.Album
import bassem.task.gallery.ui.base.BaseViewModel
import bassem.task.gallery.ui.base.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : BaseViewModel() {

    private val _galleryAlbums: MutableStateFlow<List<Album>> = MutableStateFlow(emptyList())
    val galleryAlbums: StateFlow<List<Album>> = _galleryAlbums


    init {
        sendEvent(Event.CheckPermissionEvent)
    }

    private fun getGallery() {
        //TODO: Replace this dummy list with actual list
        _galleryAlbums.value = listOf(
            Album(null, "First", "10"),
            Album(null, "Second", "5"),
            Album(null, "Third", "2"),
            Album(null, "Forth", "70"),
        )
    }

    override fun handleOnPermissionGranted() {
        getGallery()
    }

}