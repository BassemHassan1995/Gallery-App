package bassem.task.gallery.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import bassem.task.gallery.R
import bassem.task.gallery.data.model.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    protected val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    protected fun sendEvent(event: Event) = launchCoroutine { eventChannel.send(event) }

    protected fun launchCoroutine(eventBlock: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = eventBlock)
    }

    abstract fun handleOnPermissionGranted()

    fun handlePermission(isGranted: Boolean) {
        when (isGranted) {
            true -> handleOnPermissionGranted()
            false -> {
                sendEvent(Event.ShowErrorEvent(R.string.permission_denied_explanation))
            }
        }
    }

    protected fun startLoading(){
        _isLoading.value = true
    }

    protected fun stopLoading(){
        _isLoading.value = false
    }
}