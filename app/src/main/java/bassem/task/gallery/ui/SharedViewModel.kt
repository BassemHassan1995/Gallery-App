package bassem.task.gallery.ui

import bassem.task.gallery.data.model.Album
import bassem.task.gallery.data.model.MediaItem
import bassem.task.gallery.ui.base.BaseViewModel
import bassem.task.gallery.ui.base.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : BaseViewModel() {

    private val _galleryAlbums: MutableStateFlow<List<Album>> = MutableStateFlow(emptyList())
    val galleryAlbums: StateFlow<List<Album>> = _galleryAlbums

    private val _selectedAlbum: MutableStateFlow<List<MediaItem>> = MutableStateFlow(emptyList())
    val selectedAlbum: StateFlow<List<MediaItem>> = _selectedAlbum

    init {
        sendEvent(Event.CheckPermissionEvent)
    }

    private fun getGallery() {
        //TODO: Replace this dummy list with actual list
        _galleryAlbums.value = listOf(
            Album(null, "First", 10),
            Album(null, "Second", 5),
            Album(null, "Third", 2),
            Album(null, "Forth", 7),
        )
    }

    fun getAlbumMediaItems(album: Album) {
        //TODO: Replace this dummy list with actual list
        val dummyList = mutableListOf<MediaItem>()
        repeat(album.count) {
            dummyList.add(MediaItem("$it"))
        }
        _selectedAlbum.value = dummyList
    }

    override fun handleOnPermissionGranted() {
        getGallery()
    }

}