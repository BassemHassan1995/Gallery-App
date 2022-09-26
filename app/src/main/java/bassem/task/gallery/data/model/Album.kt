package bassem.task.gallery.data.model

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

data class Album(
    val name: String, val count: Int, val thumbnail: Uri?
) : Serializable {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Album, newItem: Album) =
                oldItem == newItem
        }
    }

}