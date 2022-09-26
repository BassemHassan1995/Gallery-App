package bassem.task.gallery.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import bassem.task.gallery.R
import com.google.android.material.snackbar.Snackbar

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
