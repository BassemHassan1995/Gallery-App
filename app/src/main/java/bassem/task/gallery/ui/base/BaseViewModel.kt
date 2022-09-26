package bassem.task.gallery.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bassem.task.gallery.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    protected fun sendEvent(event: Event) = launchCoroutine { eventChannel.send(event) }

    private fun launchCoroutine(eventBlock: suspend CoroutineScope.() -> Unit) {
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

}