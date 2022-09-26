package bassem.task.gallery.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bassem.task.gallery.data.model.Album
import bassem.task.gallery.databinding.ItemAlbumLayoutBinding
import com.bumptech.glide.Glide

/**
 * A [ListAdapter] for [Album]s.
 */
internal class GalleryAdapter(private val onClick: (Album) -> Unit) :
    ListAdapter<Album, GalleryAdapter.ViewHolder>(Album.DiffCallback) {

    internal class ViewHolder(private var binding: ItemAlbumLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album, onClick: (Album) -> Unit) {
            with(binding) {
                root.setOnClickListener { onClick(album) }

                count.text = album.count.toString()
                name.text = album.name
                Glide.with(image)
                    .load(album.thumbnail)
                    .centerCrop()
                    .into(image)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemAlbumLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), onClick)
}
