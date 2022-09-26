package bassem.task.gallery.ui.base

import androidx.annotation.StringRes
import bassem.task.gallery.R

sealed class Event {
    object CheckPermissionEvent : Event()
    data class ShowErrorEvent(@StringRes val errorStringId: Int = R.string.something_went_wrong) :
        Event()

}
