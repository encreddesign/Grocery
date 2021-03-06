package com.encreddesign.grocery.utils.forms;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.encreddesign.grocery.db.AbstractEntity;
import com.encreddesign.grocery.db.category.CategoryEntity;
import com.encreddesign.grocery.db.category.CategoryMapper;
import com.encreddesign.grocery.db.items.GroceryEntity;

import java.util.ArrayList;

/**
 * Created by Joshua on 14/05/2017.
 */

public class FormValidation {

    private final Context mContext;
    private final AbstractEntity entity;

    public FormValidation (Context context, AbstractEntity entity) {

        this.entity = entity;
        this.mContext = context;

    }

    /*
    * @method validateText
    * @params EditText edit
    * */
    public void validateText (EditText edit) throws ValidationException {

        String text = edit.getText().toString();

        if(text.length() == 0) {
            throw new ValidationException("Field " + edit.getTag().toString() + " cannot be empty");
        }

        if(text.startsWith("_") || text.startsWith(".") || text.startsWith("&")) {
            throw new ValidationException("Field " + edit.getTag().toString() + " should not start with [_|.|&]");
        }

        if(this.entity instanceof GroceryEntity) {
            ((GroceryEntity) this.entity).setGroceryItemName(text);
        } else if(this.entity instanceof CategoryEntity) {
            ((CategoryEntity) this.entity).setCategoryName(text);
        }

    }

    /*
    * @method validateNumber
    * @params EditText edit
    * */
    public void validateNumber (EditText edit) throws ValidationException {

        String text = edit.getText().toString();

        if(text.length() == 0) {
            throw new ValidationException("Field " + edit.getTag().toString() + " cannot be empty");
        }

        if(!text.matches("\\d+")) {
            throw new ValidationException("Field " + edit.getTag().toString() + " is not valid number");
        }

        if(this.entity instanceof GroceryEntity) {
            ((GroceryEntity) this.entity).setGroceryItemQuantity(Integer.valueOf(text));
        }

    }

    /*
    * @method validateSpinner
    * @params Spinner spinner
    * */
    public void validateSpinner (Spinner spinner) throws ValidationException {

        String text = spinner.getSelectedItem().toString();

        if(text.length() == 0) {
            throw new ValidationException("Field " + spinner.getTag().toString() + " cannot be empty");
        }

        if(this.entity instanceof GroceryEntity) {
            final CategoryEntity ent = new CategoryMapper(this.mContext).findCategoryByName(text);
            if(ent != null) {
                ((GroceryEntity) this.entity).setGroceryItemCategory(ent.getCategoryId());
            }
        }

    }

    /*
    * @method validateTags
    * @params ViewGroup group
    * @description Throws no error as field optional
    * */
    public void validateTags (ViewGroup group) {

        int childCount = group.getChildCount();
        ArrayList<String> tags = new ArrayList<>();

        for(int i = 0; i < childCount; i++) {

            String tag = ((ViewGroup) group.getChildAt(i)).getChildAt(0).getTag().toString();

            if(tag.length() > 0) {
                tags.add(tag);
            }

        }

        if(this.entity instanceof GroceryEntity) {
            if(tags.size() > 0) {
                ((GroceryEntity) this.entity).setGroceryItemTags(TextUtils.join(",", tags));
            }
        }

    }

}
