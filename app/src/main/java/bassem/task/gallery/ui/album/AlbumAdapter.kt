package bassem.task.gallery.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bassem.task.gallery.data.model.MediaItem
import bassem.task.gallery.databinding.ItemImageLayoutBinding
import com.bumptech.glide.Glide

/**
 * A [ListAdapter] for [MediaItem]s.
 */
internal class AlbumAdapter :
    ListAdapter<MediaItem, AlbumAdapter.ImageViewHolder>(MediaItem.DiffCallback) {

    /**
     * Basic [RecyclerView.ViewHolder] for our album.
     */
    internal class ImageViewHolder(private var binding: ItemImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaItem: MediaItem) {
            with(binding) {
                root.tag = mediaItem
                Glide.with(image)
                    .load(mediaItem.contentUri)
                    .thumbnail(0.33f)
                    .centerCrop()
                    .into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            ItemImageLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(getItem(position))

}

