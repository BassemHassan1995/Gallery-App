package bassem.task.gallery.utils

import android.content.ContentUris
import android.database.Cursor
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import bassem.task.gallery.R
import bassem.task.gallery.data.model.MediaItem
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.concurrent.TimeUnit

fun Fragment.showSnackbar(
    @StringRes messageStringId: Int,
    @StringRes actionStringId: Int = R.string.action_settings,
    function: () -> Unit
) {
    Snackbar.make(
        requireView(),
        messageStringId,
        Snackbar.LENGTH_LONG
    )
        .setAction(actionStringId) { function() }
        .show()
}


fun Fragment.showToast(stringId: Int) {
    Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
}


fun Cursor.getImage(): MediaItem {
    val idColumn = getColumnIndexOrThrow(MediaStore.Images.Media._ID)
    val nameColumn = getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
    val dateAddedColumn = getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

    val id = getLong(idColumn)
    val name = getDisplayName(nameColumn)
    val dateAdded = getLong(dateAddedColumn).toDate()

    val contentUri =
        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

    return MediaItem(id, name, contentUri, dateAdded)
}

fun Cursor.getVideo(): MediaItem {
    val idColumn = getColumnIndexOrThrow(MediaStore.Video.Media._ID)
    val nameColumn = getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
    val dateAddedColumn = getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

    val id = getLong(idColumn)
    val name = getDisplayName(nameColumn)
    val dateAdded = getLong(dateAddedColumn).toDate()

    val contentUri =
        ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)

    return MediaItem(id, name, contentUri, dateAdded)
}

fun Cursor.getImageAlbumName(): String =
    getDisplayName(getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))

fun Cursor.getVideoAlbumName(): String =
    getDisplayName(getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))

private fun Cursor.getDisplayName(columnIndex: Int): String =
    when (getType(columnIndex)) {
        Cursor.FIELD_TYPE_STRING -> getString(columnIndex)
        else -> "Others"
    }

fun Long.toDate(): Date =
    Date(TimeUnit.SECONDS.toMillis(this))