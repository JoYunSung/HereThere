package com.pie.herethere;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by PrographerJ on 2015-07-17.
 * Notofont 설정
 */
public class NotoBoldText extends AppCompatTextView {
    public NotoBoldText(Context context) {
        super(context);
        setType(context);
    }

    public NotoBoldText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setType(context);
    }

    public NotoBoldText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setType(context);
    }

    private void setType(Context context) {
        //asset에 폰트 복사
        //NotoSnat 경령화된 폰트 위치: https://github.com/theeluwin/NotoSansKR-Hestia
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "bold.otf"));
    }
}