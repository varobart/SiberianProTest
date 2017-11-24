package pro.siberian.siberianproapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Вараздат on 20.11.2017.
 */

public class NewCityDialogFragment extends DialogFragment  implements DialogInterface.OnClickListener{

    private NewCityDialogFragmentListener mListener;
    private EditText mEditText;
    private String mCity;


    public interface NewCityDialogFragmentListener{
        void onDialogPositiveClick(DialogFragment dialogFragment);
        void onDialogNegativeClick(DialogFragment dialogFragment);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListener = (NewCityDialogFragmentListener) getTargetFragment();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch(which) {
            case DialogInterface.BUTTON_NEGATIVE:
                mListener.onDialogNegativeClick(NewCityDialogFragment.this);
                break;
            case DialogInterface.BUTTON_POSITIVE:
                mListener.onDialogPositiveClick(NewCityDialogFragment.this);
                break;

            default:
        }
    }





    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_dialog, null);

        mEditText = (EditText) view.findViewById(R.id.dialog_edit_text);

        builder.setView(view)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.OK, this)
                .setNegativeButton(R.string.cancel, this);


        return builder.create();
    }


    public String getCity(){
        return mEditText.getText().toString();
    }





}
