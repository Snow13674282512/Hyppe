package com.quyum.hyppe.weight;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quyum.hyppe.R;

public class VerifyCodeView extends RelativeLayout {
    public static int MAX = 6;
    public EditText editText;
    public TextView[] textViews;
    private String inputContent;
    private InputCompleteListener inputCompleteListener;
    public Boolean isComplete = false;

    public VerifyCodeView(Context context) {
        this(context, null);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_verify_code, this);

        textViews = new TextView[MAX];
        textViews[0] = findViewById(R.id.tv_0);
        textViews[1] = findViewById(R.id.tv_1);
        textViews[2] = findViewById(R.id.tv_2);
        textViews[3] = findViewById(R.id.tv_3);
        textViews[4] = findViewById(R.id.tv_4);
        textViews[5] = findViewById(R.id.tv_5);
        editText = findViewById(R.id.edit_text_view);

        editText.setCursorVisible(false);//隐藏光标
        setEditTextListener();
    }

    public void ClearInput() {
        editText.setText("");
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText("");
        }
    }

    public void setIsVisibility(Boolean isVisibility) {
        if (isVisibility) {
            for (int i = 0; i < MAX; i++) {
                textViews[i].setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        } else {
            for (int i = 0; i < MAX; i++) {
                textViews[i].setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    private void setEditTextListener() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputContent = editText.getText().toString();

                if (inputCompleteListener != null) {
                    if (inputContent.length() >= MAX) {
                        inputCompleteListener.inputComplete();
                        isComplete = true;
                    } else {
                        inputCompleteListener.invalidContent();
                        isComplete = false;
                    }
                }

                for (int i = 0; i < MAX; i++) {
                    if (i < inputContent.length()) {
                        textViews[i].setText(String.valueOf(inputContent.charAt(i)));
                    } else {
                        textViews[i].setText("");
                    }
                }
            }
        });
    }

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public String getEditContent() {
        return inputContent;
    }

    public interface InputCompleteListener {

        void inputComplete();

        void invalidContent();
    }
}
