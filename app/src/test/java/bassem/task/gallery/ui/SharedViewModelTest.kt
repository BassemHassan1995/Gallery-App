package bassem.task.gallery.ui

import android.app.Application
import bassem.task.gallery.data.model.MediaItem
import bassem.task.gallery.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

private const val VALID_IMAGE_ALBUM_NAME = "All Images"
private const val VALID_VIDEO_ALBUM_NAME = "All Videos"
private const val VALID_COMMON_ALBUM_NAME = "Camera"

private val VALID_IMAGE_MAP: Map<String, List<MediaItem>> =
    mapOf(
        VALID_IMAGE_ALBUM_NAME to listOf(spy(), spy(), spy()), VALID_COMMON_ALBUM_NAME to listOf(
            spy()
        )
    )

private val VALID_VIDEO_MAP: Map<String, List<MediaItem>> =
    mapOf(
        VALID_VIDEO_ALBUM_NAME to listOf(spy(), spy()), VALID_COMMON_ALBUM_NAME to listOf(
            spy(), spy()
        )
    )

@ExperimentalCoroutinesApi
internal class SharedViewModelTest {

    private lateinit var application: Application
    private lateinit var viewModel: SharedViewModel

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        application = spy()
        viewModel = spy(SharedViewModel(application))
    }

    @Test
    fun testGetGalleryMedia_returnData() {
        runTest {
            with(viewModel)
            {
                whenever(getGalleryImages()).doReturn(VALID_IMAGE_MAP)
                whenever(getGalleryVideos()).doReturn(VALID_VIDEO_MAP)

                getGalleryMedia()

                assert(this.galleryAlbums.value.size == (VALID_IMAGE_MAP.size + VALID_IMAGE_MAP.size - 1))
            }
        }
    }

}