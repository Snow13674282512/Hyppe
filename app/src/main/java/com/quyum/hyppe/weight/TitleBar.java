package com.quyum.hyppe.weight;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quyum.hyppe.R;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TitleBar extends RelativeLayout {

    public ImageView mRightImageTwo;
    private RelativeLayout mNaviTitleContent;
    private RelativeLayout mFrameLeft;
    private RelativeLayout mFrameRight;
    private Button mNaviButtonLeft;
    private TextView mNaviButtonRight;
    private TextView mNaviTitle;
    private ImageView mMiddleImage, mLeftImage, mRightImage;
    private Activity mActivity;
    private View topView;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public TitleBar(Context context) {
        super(context);
        initialize();
    }

    /**
     *
     */
    private void initialize() {
        inflate();
        findViews();
    }

    /**
     *
     */
    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title_bar, this, true);
    }

    /**
     *
     */
    private void findViews() {
        topView = findViewById(R.id.top_view);
        mNaviTitle = findViewById(R.id.title);
        mNaviTitleContent = findViewById(R.id.titleContent);
        mNaviButtonLeft = findViewById(R.id.naviButtonLeft);
        mNaviButtonRight = findViewById(R.id.ButtonRight);
        mFrameLeft = findViewById(R.id.naviFrameLeft);
        mFrameRight = findViewById(R.id.naviFrameRight);
        mMiddleImage = findViewById(R.id.imageView_titleContent);
        mLeftImage = findViewById(R.id.imageView_left_titlebar);
        mRightImage = findViewById(R.id.imageView_right_titleBar);
        mRightImageTwo = findViewById(R.id.imageView_right_titleBar_two);
    }

    public View getTopView() {
        return topView;
    }

    /**
     * @param res
     */
    public void setRightText(int res) {
        mNaviButtonRight.setText(res);
    }

    public TextView getRightText() {
        return mNaviButtonRight;
    }

    public void setRightText(CharSequence text) {
        mNaviButtonRight.setText(text);
    }

    /**
     * @param res
     */
    public void setTitleText(int res) {
        mNaviTitle.setText(res);
    }

    public void setTitleText(SpannableString textSpan) {
        mNaviTitle.setText(textSpan);
    }

    /**
     * @param text
     */
    public void setTitleText(String text) {
        mNaviTitle.setText(text);
    }

    public void setTitleTextColor(int color) {
        mNaviTitle.setTextColor(color);
    }

    public void setMiddleImageResource(int res) {
        mMiddleImage.setImageResource(res);
    }

    public void setRightImageResource(int res) {
        mRightImage.setImageResource(res);
    }

    public void setLeftImageResource(int res) {
        mLeftImage.setImageResource(res);
    }

    /**
     *
     */
    public void hideLeft() {
        mNaviButtonLeft.setVisibility(View.INVISIBLE);
        mLeftImage.setVisibility(INVISIBLE);
    }

    /**
     *
     */
    public void showTitle() {
        mNaviTitle.setVisibility(View.VISIBLE);
    }

    /**
     * @param l
     */
    public void setLeftClick(OnClickListener l) {
        mFrameLeft.setOnClickListener(l);
    }

    /**
     * @param l
     */
    public void setRightClick(OnClickListener l) {
        mFrameRight.setOnClickListener(l);
    }

    /**
     * @param l
     */
    public void setTitleClick(OnClickListener l) {
        mNaviTitle.setOnClickListener(l);
    }

    public void setLeftImageClick(OnClickListener l) {
        mLeftImage.setOnClickListener(l);
    }

    public void setRightImageClick(OnClickListener l) {
        mRightImage.setOnClickListener(l);
    }

    public void enableLeftImageBackOnClick() {
        if (mFrameLeft != null)
            mFrameLeft.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Activity act = mActivity;
                    if (act != null)
                        act.finish();
                }
            });
    }

    public void setActivity(Activity act) {
        this.mActivity = act;
    }

}
