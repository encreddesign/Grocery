package com.encreddesign.grocery.fragments.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.ItemsMapper;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 02/06/2017.
 */

public class DeleteDialogFragment extends DialogFragment {

    public static final int DELETE_ITEM = 0;
    public static final int DELETE_CATEGORY = 1;

    private static Listener mListener;

    public DeleteDialogFragment () {}

    public interface Listener {
        void onSuccessRemoval (int viewIdx);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        final int type = getArguments().getInt("type");
        final int viewIdx = getArguments().getInt("viewIdx");

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);

        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(type == DELETE_ITEM) {
                    deleteItem(viewIdx);
                } else if(type == DELETE_CATEGORY) {
                    deleteCategory(viewIdx);
                }

                dialogInterface.dismiss();

            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return dialog.create();

    }

    void deleteItem (int idx) {

        final ItemsMapper mapper = new ItemsMapper(getActivity().getBaseContext());
        final int dbId = ((BaseActivity) getActivity()).mGroceryPrefs.getInt(BaseActivity.DB_KEY);

        try {

            mapper.deleteItem(dbId);

            Toasty.success(getActivity().getBaseContext(), "Deleted Item", Toast.LENGTH_SHORT).show();

            if(mListener != null) {
                mListener.onSuccessRemoval(idx);
            }

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            Toasty.error(getActivity().getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    void deleteCategory (int idx) {

        final CategoryMapper mapper = new CategoryMapper(getActivity().getBaseContext());
        final int dbId = ((BaseActivity) getActivity()).mGroceryPrefs.getInt(BaseActivity.DB_KEY);

        try {

            mapper.deleteCategory(dbId);

            Toasty.success(getActivity().getBaseContext(), "Deleted Category", Toast.LENGTH_SHORT).show();

            if(mListener != null) {
                mListener.onSuccessRemoval(idx);
            }

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            Toasty.error(getActivity().getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public static DeleteDialogFragment newInstance (String title, String message, int viewIdx, int type, Listener listener) {

        final DeleteDialogFragment dialog = new DeleteDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putInt("viewIdx", viewIdx);
        bundle.putInt("type", type);

        dialog.setArguments(bundle);

        mListener = listener;

        return dialog;

    }

}
