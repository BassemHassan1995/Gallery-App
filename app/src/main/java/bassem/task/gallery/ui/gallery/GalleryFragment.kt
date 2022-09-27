package bassem.task.gallery.ui.gallery

import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bassem.task.gallery.R
import bassem.task.gallery.databinding.FragmentGalleryBinding
import bassem.task.gallery.ui.SharedViewModel
import bassem.task.gallery.ui.base.BaseFragment
import kotlinx.coroutines.launch

class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    override val viewModel by activityViewModels<SharedViewModel>()
    private val galleryAdapter: GalleryAdapter by lazy {
        GalleryAdapter {
            navigateTo(GalleryFragmentDirections.actionGalleryToAlbum(it.name))
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGalleryBinding = FragmentGalleryBinding.inflate(inflater, container, false)

    override fun setupViews() {
        super.setupViews()
        binding.galleryRecyclerView.adapter = galleryAdapter
        bindMenu()
    }

    private fun bindMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.menu_view) {
                    onChangeViewSelected(menuItem)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onChangeViewSelected(menuItem: MenuItem) {
        with(binding.galleryRecyclerView) {
            if (layoutManager is GridLayoutManager) {
                layoutManager = LinearLayoutManager(context)
                menuItem.setIcon(R.drawable.ic_grid_view)
            } else {
                layoutManager = GridLayoutManager(context, 2)
                menuItem.setIcon(R.drawable.ic_list_view)
            }
        }
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
            viewLifecycleOwner.lifecycleScope.launch {
                isLoading.flowWithLifecycle(lifecycle)
                    .collect {
                        binding.progressCircular.visibility = when (it) {
                            true -> View.VISIBLE
                            false -> View.GONE
                        }
                    }
            }
        }
    }

}