package com.encreddesign.grocery.callbacks;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.encreddesign.grocery.App;
import com.encreddesign.grocery.activity.BaseActivity;
import com.encreddesign.grocery.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Joshua on 14/05/2017.
 */

public class OnCompletionTag implements TextView.OnEditorActionListener {

    private final ViewGroup mInsertView;
    private final EditText mEditText;

    private final Context mContext;
    private final Resources mResources;

    public OnCompletionTag (Activity activity, EditText edit, ViewGroup view) {

        this.mEditText = edit;
        this.mInsertView = view;

        this.mContext = activity.getBaseContext();
        this.mResources = activity.getResources();

    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

        if(id == EditorInfo.IME_ACTION_DONE) {

            this.insertTag(this.mEditText.getText().toString().trim());
            this.mEditText.setText("");
            return true;

        }

        return false;
    }

    void insertTag (String tagName) {

        try {

            if(tagName.length() == 0) {
                throw new Exception("Tag cannot be empty");
            }

            if(this.isAlreadyInTags(tagName)) {
                throw new Exception("Tag already exists");
            }

            LayoutInflater inflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            GridLayout parent = (GridLayout) inflater.inflate(R.layout.tag_layout, null);

            TextView textView = (TextView) parent.getChildAt(0);
            textView.setText(tagName);
            textView.setTag(tagName);

            TextView tagClear = (TextView) parent.getChildAt(1);
            tagClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInsertView.removeView(((View) view.getParent()));
                }
            });

            this.mInsertView.addView(parent);
            Toasty.success(this.mContext, "Tag added", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Log.e(App.TAG, "Error", ex);
            Toasty.error(this.mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    boolean isAlreadyInTags (String tag) {

        boolean found = false;

        for(int i = 0; i < this.mInsertView.getChildCount(); i++) {
            if(((ViewGroup) this.mInsertView.getChildAt(i)).getChildAt(0).getTag().toString().equals(tag)) {

                found = true;
                break;

            }
        }

        return found;

    }

}
