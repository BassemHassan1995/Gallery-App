package bassem.task.gallery.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import bassem.task.gallery.data.model.MediaItem
import bassem.task.gallery.databinding.FragmentAlbumBinding
import bassem.task.gallery.ui.SharedViewModel
import bassem.task.gallery.ui.base.BaseFragment

class AlbumFragment : BaseFragment<FragmentAlbumBinding>() {

    override val viewModel by activityViewModels<SharedViewModel>()
    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAlbumBinding = FragmentAlbumBinding.inflate(inflater, container, false)

    override fun setupViews() {
        binding.albumRecyclerView.adapter = albumAdapter

        albumAdapter.submitList(
            listOf(
                MediaItem(1L, "First"),
                MediaItem(2L, "Second"),
                MediaItem(3L, "Third"),
                MediaItem(4L, "Forth"),
                MediaItem(5L, "Fifth"),
                MediaItem(6L, "Sixth"),
            )
        )

    }
}