package com.encreddesign.grocery.fragments;

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
                actionConfirm(fragment);
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

    public static GroceryDialogFragment newInstance (String title, String message, int resIcon, String fragmentLabel) {

        final GroceryDialogFragment dialog = new GroceryDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putInt("icon", resIcon);
        bundle.putString("fragment", fragmentLabel);

        dialog.setArguments(bundle);

        return dialog;

    }

}
