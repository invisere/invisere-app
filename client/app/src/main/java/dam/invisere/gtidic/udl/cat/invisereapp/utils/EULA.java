package dam.invisere.gtidic.udl.cat.invisereapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import dam.invisere.gtidic.udl.cat.invisereapp.R;
import dam.invisere.gtidic.udl.cat.invisereapp.SignupFragment;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.eula_string))
                .setPositiveButton(R.string.accept,
                        new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Close dialog
                                dialogInterface.dismiss();

                                // Enable orientation changes based on
                                // device's sensor
//                                mContext.setRequestedOrientation(
//                                        ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                                checkBox.setChecked(true);

                            }


                        })
                .setNegativeButton(android.R.string.cancel,
                new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the activity as they have declined
                        // the EULA
//                        mContext.setRequestedOrientation(
//                                ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        checkBox.setChecked(false);
                    }
                })
                .create();
    }
    public static String TAG = "EULAConfirmationDialog";
}
