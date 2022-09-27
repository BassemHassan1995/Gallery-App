package bassem.task.gallery.data.model

import android.net.Uri
import android.provider.ContactsContract.Data
import androidx.recyclerview.widget.DiffUtil
import java.util.*

data class MediaItem(
    val id: Long = 1L,
    val name: String,
    val contentUri: Uri? = null,
    val dateAdded: Date
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MediaItem>() {
            override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem) =
                oldItem == newItem
        }
    }
}
