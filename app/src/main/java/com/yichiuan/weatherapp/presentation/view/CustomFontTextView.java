package com.yichiuan.weatherapp.presentation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yichiuan.weatherapp.R;
import com.yichiuan.weatherapp.util.FontCache;

public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(attrs);
    }

    private void applyCustomFont(AttributeSet attrs) {

        TypedArray attributeArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);

        Typeface tf = FontCache.getTypeface(getContext(), fontName);
        setTypeface(tf);

        attributeArray.recycle();
    }
}
