package bassem.task.gallery.ui

import bassem.task.gallery.ui.base.BaseViewModel
import bassem.task.gallery.ui.base.Event

class SharedViewModel : BaseViewModel() {

    init {
        sendEvent(Event.CheckPermissionEvent)
    }

    private fun getGallery() {
        //TODO("Not yet implemented")
    }

    override fun handleOnPermissionGranted() {
        getGallery()
    }

}