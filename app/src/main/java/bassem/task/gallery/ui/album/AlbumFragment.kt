package bassem.task.gallery.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import bassem.task.gallery.databinding.FragmentAlbumBinding
import bassem.task.gallery.ui.SharedViewModel
import bassem.task.gallery.ui.base.BaseFragment

class AlbumFragment : BaseFragment<FragmentAlbumBinding>() {

    override val viewModel by activityViewModels<SharedViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAlbumBinding = FragmentAlbumBinding.inflate(inflater, container, false)
}