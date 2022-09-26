package bassem.task.gallery.ui

import android.app.Application
import android.provider.MediaStore
import bassem.task.gallery.data.model.Album
import bassem.task.gallery.data.model.MediaItem
import bassem.task.gallery.ui.base.BaseViewModel
import bassem.task.gallery.ui.base.Event
import bassem.task.gallery.utils.getAlbumName
import bassem.task.gallery.utils.getMediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "SharedViewModel"

class SharedViewModel(application: Application) : BaseViewModel(application) {

    private val _galleryAlbums: MutableStateFlow<List<Album>> = MutableStateFlow(emptyList())
    val galleryAlbums: StateFlow<List<Album>> = _galleryAlbums

    private val _albumsMap: MutableStateFlow<Map<String, List<MediaItem>>> =
        MutableStateFlow(emptyMap())

    private val _selectedAlbumImages: MutableStateFlow<List<MediaItem>> =
        MutableStateFlow(emptyList())
    val selectedAlbumImages: StateFlow<List<MediaItem>> = _selectedAlbumImages

    init {
        sendEvent(Event.CheckPermissionEvent)
    }

    private fun getGallery() {
        launchCoroutine {
            _albumsMap.value = getGalleryInfo()
            _galleryAlbums.value = _albumsMap.value.entries.map {
                Album(
                    name = it.key,
                    count = it.value.size,
                    thumbnail = it.value.firstOrNull()?.contentUri
                )
            }
        }
    }

    private fun getAlbumImages(albumName: String): List<MediaItem> =
        _albumsMap.value.getOrElse(albumName) { emptyList() }

    private fun getGalleryInfo(): Map<String, List<MediaItem>> =
        mutableMapOf<String, List<MediaItem>>().apply {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,                        //Image Id
                MediaStore.Images.Media.DISPLAY_NAME,               //Image name
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,        //Album name
            )

            getApplication<Application>().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {

                    val mediaItem = cursor.getMediaItem()
                    val albumName = cursor.getAlbumName()

                    set(albumName, get(albumName).orEmpty().plus(mediaItem))
                }
            }
        }

    fun getAlbumMediaItems(album: Album) {
        launchCoroutine {
            _selectedAlbumImages.emit(getAlbumImages(album.name))
        }
    }

    override fun handleOnPermissionGranted() {
        getGallery()
    }

}