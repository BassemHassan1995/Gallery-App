package bassem.task.gallery.ui

import android.app.Application
import android.provider.MediaStore
import androidx.annotation.VisibleForTesting
import bassem.task.gallery.data.model.Album
import bassem.task.gallery.data.model.MediaItem
import bassem.task.gallery.ui.base.BaseViewModel
import bassem.task.gallery.ui.base.Event
import bassem.task.gallery.utils.getAlbumName
import bassem.task.gallery.utils.getImage
import bassem.task.gallery.utils.getVideo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val ALL_VIDEOS_ALBUM = "All Videos"
private const val ALL_IMAGES_ALBUM = "All Images"

class SharedViewModel(application: Application) : BaseViewModel(application) {

    private val _albumsMap: MutableStateFlow<Map<String, List<MediaItem>>> =
        MutableStateFlow(emptyMap())

    private val _galleryAlbums: MutableStateFlow<List<Album>> = MutableStateFlow(emptyList())
    val galleryAlbums: StateFlow<List<Album>> = _galleryAlbums

    private val _selectedAlbumMedia: MutableStateFlow<List<MediaItem>> =
        MutableStateFlow(emptyList())
    val selectedAlbumImages: StateFlow<List<MediaItem>> = _selectedAlbumMedia

    init {
        sendEvent(Event.CheckPermissionEvent)
    }

    private fun getGallery() {
        launchCoroutine {
            _albumsMap.value = getGalleryMedia()
            _galleryAlbums.value = _albumsMap.value.entries.map {
                Album(
                    name = it.key,
                    count = it.value.size,
                    thumbnail = it.value.firstOrNull()?.contentUri
                )
            }.sortedBy { it.name }
        }
    }

    fun getAlbumMediaItems(albumName: String) {
        launchCoroutine {
            val albumMedia = _albumsMap.value.getOrElse(albumName) { emptyList() }
            _selectedAlbumMedia.emit(albumMedia)
        }
    }

    @VisibleForTesting
    internal fun getGalleryMedia(): Map<String, List<MediaItem>> =
        getGalleryImages().plus(getGalleryVideos())

    @VisibleForTesting
    internal fun getGalleryImages(): Map<String, List<MediaItem>> =
        mutableMapOf<String, List<MediaItem>>().apply {
            val imagesProjection = arrayOf(
                MediaStore.Images.Media._ID,                        //Image Id
                MediaStore.Images.Media.DISPLAY_NAME,               //Image name
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,        //Album name
            )

            getApplication<Application>().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imagesProjection,
                null,
                null,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"

            )?.use { cursor ->
                while (cursor.moveToNext()) {

                    val mediaItem = cursor.getImage()
                    val albumName = cursor.getAlbumName()

                    set(albumName, get(albumName).orEmpty().plus(mediaItem))
                    set(ALL_IMAGES_ALBUM, get(ALL_IMAGES_ALBUM).orEmpty().plus(mediaItem))
                }
            }
        }

    @VisibleForTesting
    internal fun getGalleryVideos(): Map<String, List<MediaItem>> =
        mutableMapOf<String, List<MediaItem>>().apply {
            val videoProjection = arrayOf(
                MediaStore.Video.Media._ID,                        //Video Id
                MediaStore.Video.Media.DISPLAY_NAME,               //Video name
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,        //Album name
            )

            getApplication<Application>().contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videoProjection,
                null,
                null,
                "${MediaStore.Video.Media.DATE_ADDED} DESC"

            )?.use { cursor ->
                while (cursor.moveToNext()) {

                    val mediaItem = cursor.getVideo()
                    val albumName = cursor.getAlbumName()

                    set(albumName, get(albumName).orEmpty().plus(mediaItem))
                    set(ALL_VIDEOS_ALBUM, get(ALL_VIDEOS_ALBUM).orEmpty().plus(mediaItem))
                }
            }
        }

    override fun handleOnPermissionGranted() {
        getGallery()
    }

}