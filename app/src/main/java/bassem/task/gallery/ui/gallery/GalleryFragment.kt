package bassem.task.gallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import bassem.task.gallery.data.model.Album
import bassem.task.gallery.databinding.FragmentGalleryBinding
import bassem.task.gallery.ui.SharedViewModel
import bassem.task.gallery.ui.base.BaseFragment

class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    override val viewModel by activityViewModels<SharedViewModel>()
    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter {
            navigateTo(GalleryFragmentDirections.actionGalleryToAlbum())
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGalleryBinding = FragmentGalleryBinding.inflate(inflater, container, false)

    override fun setupViews() {
        binding.galleryRecyclerView.adapter = galleryAdapter

        galleryAdapter.submitList(
            listOf(
                Album(null, "First", "10"),
                Album(null, "Second", "5"),
                Album(null, "Third", "2"),
                Album(null, "Forth", "70"),
            )
        )
    }
}