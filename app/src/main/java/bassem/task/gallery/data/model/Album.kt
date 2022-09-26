package bassem.task.gallery.data.model

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class Album(val thumbnail: Uri? = null, val name: String, val count: String) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Album, newItem: Album) =
                oldItem == newItem
        }
    }

}