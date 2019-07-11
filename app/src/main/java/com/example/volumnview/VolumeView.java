package com.example.volumnview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class VolumeView extends View {

    private static final String TAG = "zhu";
    // 增加音量图片
    private Bitmap addBitmap;
    // 减少音量图片
    private Bitmap reduceBitmap;
    //画笔
    private Paint paint = new Paint();
    // 控件高度
    private int height = 200;
    // 控件宽度
    private int width = 1200;
    // 最大音量
    private int MAX = 30;
    // 两个音量矩形最左侧之间的间隔
    private int rectMargen = 30;
    // 音量矩形高
    private int rectH = 80;
    // 音量矩形宽
    private int recW = 20;
    // 当前选中的音量
    private int current = 0;
    // 最左侧音量矩形距离控件最左侧距离
    private int leftMargen = 0;

    public VolumeView(Context context) {
        super(context);
        init();
    }

    public VolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        addBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volumeview_add);
        reduceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.volumeview_redece);
        leftMargen = reduceBitmap.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制背景颜色
//        paint.setColor(Color.parseColor("#b0000000"));
//        canvas.drawRect(0, 0, width, height, paint);

        // 绘制没有被选中的白色音量矩形
        paint.setColor(Color.parseColor("#ffffff"));
        for (int i = current; i < MAX; i++) {
            canvas.drawRect(leftMargen + (i + 2) * rectMargen, (height - rectH) / 2, leftMargen + (i + 2) * rectMargen + recW, (height - rectH) / 2 + rectH, paint);
        }

        // 绘制被选中的音量矩形
        paint.setColor(Color.parseColor("#ADFF2F"));
        for (int i = 0; i < current; i++) {
            canvas.drawRect(leftMargen + (i + 2) * rectMargen, (height - rectH) / 2, leftMargen + (i + 2) * rectMargen + recW, (height - rectH) / 2 + rectH, paint);
        }

        canvas.drawBitmap(reduceBitmap, reduceBitmap.getWidth() / 2, (height - reduceBitmap.getHeight()) / 2, paint);

        // 绘制音量增加图片
        canvas.drawBitmap(addBitmap, leftMargen + (MAX + 2) * rectMargen, (height - addBitmap.getHeight()) / 2, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                // 当触摸位置在音量矩形之内时，获取当前选中的音量矩形数量
                if ((event.getX() > leftMargen + rectMargen && event.getX() < leftMargen + (MAX + 1) * rectMargen + recW)
                        && (event.getY() > (height - rectH) / 2 && event.getY() < (height - rectH) / 2 + rectH)) {
                    current = (int) ((event.getX() - (leftMargen)) / (rectMargen)) - 1;
                    if (onChangeListener != null) {
                        onChangeListener.onChange(current);
                    }
                    Log.d(TAG, "current:" + current);
                    Log.d("zhu", "current:" + current);
                }
                if ((event.getX() < leftMargen + rectMargen) && (event.getY() > (height - rectH) / 2 && event.getY() < (height - rectH) / 2 + rectH)) {    //左边，音量减少
                    Log.d("zhu", "音量减少");
                    if (current > 0) {
                        current--;
                    } else {
                        current = 0;
                    }
                    if (onChangeListener != null) {
                        onChangeListener.onReduce(current);
                    }
                } else if ((event.getX() > leftMargen + (MAX + 1) * rectMargen + recW) && (event.getY() > (height - rectH) / 2 && event.getY() < (height - rectH) / 2 + rectH)) { //右边，音量增加
                    Log.d("zhu", "音量增加");
                    if (current < MAX) {
                        current++;
                    } else {
                        current = MAX;
                    }
                    if (onChangeListener != null) {
                        onChangeListener.onAdd(current);
                    }
                }
                break;
        }
        // 通知界面刷新
        invalidate();
        // 拦截触摸事件
        return true;
    }

    // 高度父布局要占用的位置大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    public interface OnChangeListener {
        public void onChange(int count);

        public void onAdd(int count);

        public void onReduce(int count);
    }

    private OnChangeListener onChangeListener;

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

}
