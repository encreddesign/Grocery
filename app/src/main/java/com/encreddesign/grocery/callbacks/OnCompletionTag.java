package com.encreddesign.grocery.callbacks;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.encreddesign.grocery.BaseActivity;
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

            TextView textView = new TextView(this.mContext);

            this.setViewProperties(textView);
            textView.setText(tagName);
            textView.setTag(tagName);

            this.mInsertView.addView(textView);
            Toasty.success(this.mContext, "Tag added", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Log.e(BaseActivity.LOG_TAG, "Error", ex);
            Toasty.error(this.mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    void setViewProperties (TextView textView) {

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0, 0, 10, 0);

        textView.setLayoutParams(params);
        textView.setPadding(
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingWidth),
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingHeight),
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingWidth),
                (int) this.mResources.getDimension(R.dimen.item_tagPaddingHeight)
        );
        textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.colorWhite));
        textView.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.tag_bg));

    }

    boolean isAlreadyInTags (String tag) {

        boolean found = false;

        for(int i = 0; i < this.mInsertView.getChildCount(); i++) {
            if(this.mInsertView.getChildAt(i).getTag().toString().equals(tag)) {

                found = true;
                break;

            }
        }

        return found;

    }

}
