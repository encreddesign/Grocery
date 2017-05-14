package com.encreddesign.grocery.callbacks;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Joshua on 14/05/2017.
 */

public class OnCompletionTag implements TextView.OnEditorActionListener {

    private final View mInsertView;
    private final EditText mEditText;

    private final Context mContext;

    public OnCompletionTag (Context context, EditText edit, View view) {

        this.mEditText = edit;
        this.mInsertView = view;

        this.mContext = context;

    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

        if(id == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_UP) {

            this.insertTag(this.mEditText.getText().toString());
            return true;

        }

        return false;
    }

    void insertTag (String tagName) {

        TextView tag = new TextView(this.mContext);

        tag.setText(tagName);

    }

}
