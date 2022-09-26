package bassem.task.gallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import bassem.task.gallery.databinding.FragmentGalleryBinding
import bassem.task.gallery.ui.SharedViewModel
import bassem.task.gallery.ui.base.BaseFragment

class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    override val viewModel by activityViewModels<SharedViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGalleryBinding = FragmentGalleryBinding.inflate(inflater, container, false)
}