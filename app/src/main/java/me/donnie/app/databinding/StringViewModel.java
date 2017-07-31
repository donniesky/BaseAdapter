package me.donnie.app.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * @author donnieSky
 * @created_at 2017/7/31.
 * @description
 */

public class StringViewModel extends BaseObservable {

    private String txt;

    public StringViewModel(String txt) {
        this.txt = txt;
    }

    @Bindable
    public String getTxt() {
        return txt;
    }
}
