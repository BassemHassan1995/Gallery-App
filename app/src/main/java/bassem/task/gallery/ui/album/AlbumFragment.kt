package bassem.task.gallery.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import bassem.task.gallery.databinding.FragmentAlbumBinding
import bassem.task.gallery.ui.SharedViewModel
import bassem.task.gallery.ui.base.BaseFragment
import kotlinx.coroutines.launch

class AlbumFragment : BaseFragment<FragmentAlbumBinding>() {

    override val viewModel by activityViewModels<SharedViewModel>()
    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter() }
    private val navigationArgs : AlbumFragmentArgs by navArgs()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAlbumBinding = FragmentAlbumBinding.inflate(inflater, container, false)

    override fun setupViews() {
        binding.albumRecyclerView.adapter = albumAdapter

        viewModel.getAlbumMediaItems(navigationArgs.albumName)
    }

    override fun observeData() {
        super.observeData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedAlbumImages.flowWithLifecycle(lifecycle).collect {
                albumAdapter.submitList(it)
            }
        }
    }
}