package bassem.task.gallery.ui.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import bassem.task.gallery.R
import bassem.task.gallery.utils.showSnackbar
import bassem.task.gallery.utils.showToast
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    protected abstract val viewModel: BaseViewModel

    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
        bindMenu()
    }

    private fun bindMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
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

    abstract fun onChangeViewSelected(menuItem: MenuItem)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setupViews() {
        setupPermissions()
    }

    protected fun navigateTo(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    @CallSuper
    open fun observeData() {
        with(viewModel)
        {
            viewLifecycleOwner.lifecycleScope.launch {
                eventsFlow.flowWithLifecycle(lifecycle)
                    .collect {
                        when (it) {
                            is Event.ShowErrorEvent -> this@BaseFragment.showToast(it.errorStringId)
                            Event.CheckPermissionEvent -> checkPermissions()
                        }
                    }
            }
        }
    }

    private fun setupPermissions() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            viewModel.handlePermission(isGranted)
        }
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED ->
                viewModel.handleOnPermissionGranted()
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showSnackbar(
                    messageStringId = R.string.permission_rationale,
                    actionStringId = R.string.allow
                ) { requestPermission() }
            }
            else -> {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}