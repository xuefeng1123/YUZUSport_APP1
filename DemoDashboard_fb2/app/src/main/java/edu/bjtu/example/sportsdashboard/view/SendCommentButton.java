package edu.bjtu.example.sportsdashboard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewAnimator;

import edu.bjtu.example.sportsdashboard.R;

public class SendCommentButton extends ViewAnimator implements View.OnClickListener {
    public static final int STATE_SEND = 0;
    public static final int STATE_DONE = 1;

    private static final long RESET_STATE_DELAY_MILLIS = 2000;

    private int currentState;

    private OnSendClickListener onSendClickListener;

    private Runnable revertStateRunnable = new Runnable() {
        @Override
        public void run() {
            setCurrentState(STATE_SEND);
        }
    };//状态监听

    public SendCommentButton(Context context) {
        super(context);
        init();
    }

    public SendCommentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_send_comment_button, this, true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        currentState = STATE_SEND;
        super.setOnClickListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(revertStateRunnable);
        super.onDetachedFromWindow();
    }

    /**
     * 根据当前状态设置动画：
     * @param state
     */
    public void setCurrentState(int state) {
        if (state == currentState) {
            return;
        }

        currentState = state;
        if (state == STATE_DONE) {
            setEnabled(false);
            postDelayed(revertStateRunnable, RESET_STATE_DELAY_MILLIS);//隔delayMillis的时间后去执行一次runable（不循环，只有一次）
            setInAnimation(getContext(), R.anim.slide_in_done);//设置View进入屏幕时候使用的动画
            setOutAnimation(getContext(), R.anim.slide_out_send);//设置View离开屏幕时候使用的动画
        } else if (state == STATE_SEND) {
            setEnabled(true);
            setInAnimation(getContext(), R.anim.slide_in_send);//设置View进入屏幕时候使用的动画
            setOutAnimation(getContext(), R.anim.slide_out_done);//设置View离开屏幕时候使用的动画
        }
        showNext();
    }

    @Override
    public void onClick(View v) {
        if (onSendClickListener != null) {
            onSendClickListener.onSendClickListener(this);
        }
    }

    public void setOnSendClickListener(OnSendClickListener onSendClickListener) {
        this.onSendClickListener = onSendClickListener;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        //Do nothing, you have you own onClickListener implementation (OnSendClickListener)
    }

    public interface OnSendClickListener {
        public void onSendClickListener(View v);
    }
}