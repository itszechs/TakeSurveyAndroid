package zechs.takesurvey.utils.ext

import android.app.Dialog
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import zechs.takesurvey.R

fun MaterialAlertDialogBuilder.addInput(
    @StringRes hint: Int
): Dialog {
    setView(R.layout.dialog_text_input)
    val dialog = this.show()
    val inputLayout = dialog.findViewById<TextInputLayout>(R.id.dialog_text_input_layout)
    inputLayout!!.hint = context.getString(hint)
    return dialog
}

fun DialogInterface.inputText(textInputLayout: Int): String {
    return (this as AlertDialog)
        .findViewById<TextInputLayout>(textInputLayout)
        ?.editText?.text?.toString().orEmpty()
}