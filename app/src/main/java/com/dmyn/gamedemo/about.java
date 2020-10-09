package com.dmyn.gamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Calendar;

public class about extends Activity {
    RelativeLayout about;
    CustomMap customMap;
    Button up, down, left, right;
    public long clickTime = 0;
    public Handler handler = new Handler();
    public LongPressDirection longPressDirection = new LongPressDirection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        createCustomMap();
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        MyOnTouchListener mtl = new MyOnTouchListener();
        up.setOnTouchListener(mtl);
        down.setOnTouchListener(mtl);
        left.setOnTouchListener(mtl);
        right.setOnTouchListener(mtl);

    }

    public void createCustomMap() {
        about = findViewById(R.id.about);
        //实例化自定义视图
        customMap = new CustomMap(this);
        //设置由控件内容决定当前控件的大小
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置控件位于父控件的顶端
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        //设置垂直居中
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //把规则应用于自定义视图
        customMap.setLayoutParams(layoutParams);
        //把自定义视图添加到布局中
        about.addView(customMap);
    }

    //设置控件触摸监听
    public class MyOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //如果触摸处于按下状态
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //设置当前点击时间
                clickTime = Calendar.getInstance().getTimeInMillis();
                //判断按下了哪个按钮，0=上，1=下，2=左，3=右
                switch (v.getId()) {
                    case R.id.up:
                        //设置移动方向
                        longPressDirection.setDirection(0);
                        break;
                    case R.id.down:
                        longPressDirection.setDirection(1);
                        break;
                    case R.id.left:
                        longPressDirection.setDirection(2);
                        break;
                    case R.id.right:
                        longPressDirection.setDirection(3);
                        break;
                }
                //500毫秒后开始执行线程
                handler.postDelayed(longPressDirection, 500);
                //如果触摸处于抬起或移走状态
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                //取消线程
                handler.removeCallbacks(longPressDirection);
                //方向清零
                longPressDirection.setDirection(4);
                //如果触摸抬起时间小于等于500毫秒
                if (Calendar.getInstance().getTimeInMillis() - clickTime <= 500) {
                    //只移动一格
                    switch (v.getId()) {
                        case R.id.up:
                            customMap.player.up();
                            break;
                        case R.id.down:
                            customMap.player.down();
                            break;
                        case R.id.left:
                            customMap.player.left();
                            break;
                        case R.id.right:
                            customMap.player.right();
                            break;
                    }
                    customMap.invalidate();
                }
            }
            return false;
        }
    }


    //长按方向键线程
    public class LongPressDirection implements Runnable {

        //默认方向设为0-3以外的数字
        private int direction = 4;

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        @Override
        public void run() {

            //根据direction来判断该向哪个方向移动
            switch (this.getDirection()) {
                case 0:
                    customMap.player.up();
                    break;
                case 1:
                    customMap.player.down();
                    break;
                case 2:
                    customMap.player.left();
                    break;
                case 3:
                    customMap.player.right();
                    break;
            }
            customMap.invalidate();
            //200毫秒后再次执行该线程
            handler.postDelayed(longPressDirection, 200);
        }
    }

}
