package com.encreddesign.grocery.fragments.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.encreddesign.grocery.BaseActivity;
import com.encreddesign.grocery.R;
import com.encreddesign.grocery.callbacks.ResetDatabase;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.ItemsMapper;

/**
 * Created by Joshua on 26/05/2017.
 */

public class GroceryDialogFragment extends DialogFragment {

    private TextView mIconView;
    private TextView mMessageView;

    private Button mCancelButton;
    private Button mConfirmButton;

    public GroceryDialogFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mIconView = (TextView) view.findViewById(R.id.dialogIcon);
        this.mMessageView = (TextView) view.findViewById(R.id.dialogMessage);

        this.mCancelButton = (Button) view.findViewById(R.id.dialogCancelButton);
        this.mConfirmButton = (Button) view.findViewById(R.id.dialogConfirmButton);

        int icon = getArguments().getInt("icon");
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        final String fragment = getArguments().getString("fragment");

        getDialog().setTitle(title);

        this.mMessageView.setText(message);
        this.mIconView.setBackground(ContextCompat.getDrawable(getActivity().getBaseContext(), icon));

        this.mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionCancel();
            }
        });

        this.mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getArguments().getBoolean("resetDb")) {
                    resetDatabase();
                } else {
                    actionConfirm(fragment);
                }

            }
        });

    }

    void actionCancel () {

        getDialog().dismiss();

    }

    void actionConfirm (String fragment) {

        getDialog().dismiss();
        if(fragment != "") {
            ((BaseActivity) getActivity()).mFragmentManager.replaceFragment(fragment, true, true);
        }

    }

    void resetDatabase () {

        getDialog().dismiss();
        ((BaseActivity) getActivity()).mTaskHandler.bg(new ResetDatabase(((BaseActivity) getActivity()),
                ((BaseActivity) getActivity()).mTaskHandler, new ItemsMapper(getActivity().getBaseContext()), new CategoryMapper(getActivity().getBaseContext())));

    }

    private static GroceryDialogFragment setup (String title, String message, int resIcon, String fragmentLabel, boolean resetDb) {

        final GroceryDialogFragment dialog = new GroceryDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putInt("icon", resIcon);
        bundle.putString("fragment", fragmentLabel);
        bundle.putBoolean("resetDb", resetDb);

        dialog.setArguments(bundle);

        return dialog;

    }

    public static GroceryDialogFragment newInstance (String title, String message, int resIcon, String fragmentLabel) {
        return setup(title, message, resIcon, fragmentLabel, false);
    }

    public static GroceryDialogFragment newInstance (String title, String message, int resIcon, String fragmentLabel, boolean resetDb) {
        return setup(title, message, resIcon, fragmentLabel, resetDb);
    }

}
