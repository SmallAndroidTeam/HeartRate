package com.ofilm.test.rxl.ecgview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.ofilm.test.rxl.R;

import java.util.List;

public class TextBannerView extends RelativeLayout {
    private ViewFlipper mViewFlipper;
    private int mInterval = 3000;/**文字切换时间间隔,默认3s*/
    private boolean isSingleLine = false;/**文字是否为单行,默认false*/
    private int textColor = Color.WHITE;/**设置文字颜色,默认颜色*/
    private static  final int mTextColor0 = 0;
    private static final int mTextColor1 = 1;
    private static  final int mTextSize0 = 0;
    private static  final int mTextSize1 =1;
    private float textSize = mTextSize0 ;
    private int mGravity = Gravity.CENTER;/**文字显示位置,默认居中*/
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;

    private boolean hasSetDirection = false;
    private int direction = DIRECTION_BOTTOM_TO_TOP;
    private static final int DIRECTION_BOTTOM_TO_TOP = 0;
    private static final int DIRECTION_TOP_TO_BOTTOM = 1;
    private static final int DIRECTION_RIGHT_TO_LEFT = 2;
    private static final int DIRECTION_LEFT_TO_RIGHT = 3;
    @AnimRes
    private int inAnimResId = R.anim.anim_right_in;
    @AnimRes
    private int outAnimResId = R.anim.anim_left_out;
    private boolean hasSetAnimDuration = false;
    private int animDuration = 1500;/**默认1.5s*/

    private List<String> mDatas;
    private boolean isStarted;
    private boolean isDetachedFromWindow;


    public TextBannerView(Context context) {
        this(context,null);
    }

    public TextBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**初始化控件*/
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextBannerViewStyle, defStyleAttr, 0);
        mInterval = typedArray.getInteger(R.styleable.TextBannerViewStyle_setInterval, mInterval);//文字切换时间间隔
        isSingleLine = typedArray.getBoolean(R.styleable.TextBannerViewStyle_setSingleLine, false);//文字是否为单行
       int  mTextColor = typedArray.getColor(R.styleable.TextBannerViewStyle_setTextColor,mTextColor0);//设置文字颜色
        switch (mTextColor){
            case mTextColor0:
                textColor = getResources().getColor(R.color.colorPrimaryDark);
                break;
            case mTextColor1:
                textColor = getResources().getColor(R.color.rate);
                break;
        }
        int mTextSizeType =  typedArray.getInt(R.styleable.TextBannerViewStyle_setTextSize,mTextSize0);
        switch (mTextSizeType) {
            case mTextSize0:
                textSize = 25;
//                textSize =  typedArray.getInt(R.styleable.TextBannerViewStyle_setTextSize, textSize);
//                textSize = DisplayUtils.px2sp(context, textSize);
                break;
            case mTextSize1:
                textSize = 10;
//               textSize =  typedArray.getInt(R.styleable.TextBannerViewStyle_setTextSize, textSize);
//                textSize = DisplayUtils.px2sp(context, textSize);
                break;
        }
        int gravityType = typedArray.getInt(R.styleable.TextBannerViewStyle_setGravity, GRAVITY_LEFT);//显示位置
        switch (gravityType) {
            case GRAVITY_LEFT:
                mGravity = Gravity.BOTTOM | Gravity.CENTER;
                break;
            case GRAVITY_CENTER:
                mGravity = Gravity.CENTER;
                break;
            case GRAVITY_RIGHT:
                mGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        hasSetAnimDuration = typedArray.hasValue(R.styleable.TextBannerViewStyle_setAnimDuration);
        animDuration = typedArray.getInt(R.styleable.TextBannerViewStyle_setAnimDuration, animDuration);//动画时间
        hasSetDirection = typedArray.hasValue(R.styleable.TextBannerViewStyle_setDirection);
        direction = typedArray.getInt(R.styleable.TextBannerViewStyle_setDirection, direction);//方向
        if (hasSetDirection) {
            switch (direction) {
                case DIRECTION_BOTTOM_TO_TOP:
                    inAnimResId = R.anim.anim_bottom_in;
                    outAnimResId = R.anim.anim_top_out;
                    break;
                case DIRECTION_TOP_TO_BOTTOM:
                    inAnimResId = R.anim.anim_top_in;
                    outAnimResId = R.anim.anim_bottom_out;
                    break;
                case DIRECTION_RIGHT_TO_LEFT:
                    inAnimResId = R.anim.anim_right_in;
                    outAnimResId = R.anim.anim_left_out;
                    break;
                case DIRECTION_LEFT_TO_RIGHT:
                    inAnimResId = R.anim.anim_left_in;
                    outAnimResId = R.anim.anim_right_out;
                    break;
            }
        } else {
            inAnimResId = R.anim.anim_right_in;
            outAnimResId = R.anim.anim_left_out;
        }

        mViewFlipper = new ViewFlipper(getContext());//new 一个ViewAnimator
        mViewFlipper.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mViewFlipper);
        startViewAnimator();

    }
//
//    /**暂停动画*/
//    public void stopViewAnimator(){
//        if (isStarted){
//            removeCallbacks(mRunnable);
//            isStarted = false;
//        }
//    }

    /**开始动画*/
    public void startViewAnimator(){
//        if (!isStarted){
//            if (!isDetachedFromWindow){
//                isStarted = true;
//                postDelayed(mRunnable,mInterval);
//            }
//        }
        setInAndOutAnimation(inAnimResId, outAnimResId);
    }

//    /**
//     * 设置延时间隔
//     */
//    private AnimRunnable mRunnable = new AnimRunnable();
//    private class AnimRunnable implements Runnable {
//
//        @Override
//        public void run() {
//            if (isStarted){
//                setInAndOutAnimation(inAnimResId, outAnimResId);
//                mViewFlipper.showNext();//手动显示下一个子view。
//                postDelayed(this,mInterval + animDuration);
//            }else {
//                stopViewAnimator();
//            }
//
//        }
//    }


    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        inAnim.setDuration(animDuration);
        mViewFlipper.setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        outAnim.setDuration(animDuration);
        mViewFlipper.setOutAnimation(outAnim);
    }


    /**设置数据集合*/
    public void setDatas(List<String> datas){
        this.mDatas = datas;
        if (DisplayUtils.notEmpty(mDatas)){
            mViewFlipper.removeAllViews();
            for (int i = 0; i < mDatas.size(); i++) {
                TextView textView = new TextView(getContext());
                textView.setText(mDatas.get(i));
                //任意设置你的文字样式，在这里
                textView.setSingleLine(isSingleLine);
                textView.setTextColor(textColor);
                textView.setTextSize(textSize);
                textView.setGravity(mGravity);
                mViewFlipper.addView(textView,i);//添加子view,并标识子view位置
            }
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isDetachedFromWindow=true;
      //  stopViewAnimator();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isDetachedFromWindow=false;
        startViewAnimator();

    }
}
