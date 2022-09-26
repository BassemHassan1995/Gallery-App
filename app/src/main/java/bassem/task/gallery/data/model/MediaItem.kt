package bassem.task.gallery.data.model

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class MediaItem(
    val name: String,
    val id: Long = 1L,
    val contentUri: Uri? = null
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
