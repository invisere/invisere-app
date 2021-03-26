package dam.invisere.gtidic.udl.cat.invisereapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import dam.invisere.gtidic.udl.cat.invisereapp.R;

public class EULA extends DialogFragment {

    /**
     * This file provides simple End User License Agreement
     * It shows a simple dialog with the license text, and two buttons.
     * If user clicks on 'cancel' button, app unselect the checkbox.
     * If user clicks on 'accept' button, app select the checkbox.
     * so next time this will not show, until next upgrade.
     */

    private CheckBox checkBox;

    public EULA(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(requireContext())
                .setMessage(Html.fromHtml(getString(R.string.eula_string), Build.VERSION.SDK_INT))
                .setPositiveButton(R.string.accept,
                        (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            checkBox.setChecked(true);
                        })
                .setNegativeButton(android.R.string.cancel,
                        (dialog, which) -> checkBox.setChecked(false))
                .create();
    }
    public static String TAG = "EULAConfirmationDialog";
}
