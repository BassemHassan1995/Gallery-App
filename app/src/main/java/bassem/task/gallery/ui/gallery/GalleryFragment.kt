package bassem.task.gallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import bassem.task.gallery.databinding.FragmentGalleryBinding
import bassem.task.gallery.ui.SharedViewModel
import bassem.task.gallery.ui.base.BaseFragment
import kotlinx.coroutines.launch

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
        super.setupViews()
        binding.galleryRecyclerView.adapter = galleryAdapter
    }

    override fun observeData() {
        super.observeData()
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                galleryAlbums.flowWithLifecycle(lifecycle)
                    .collect {
                        galleryAdapter.submitList(it)
                    }
            }
        }
    }

}