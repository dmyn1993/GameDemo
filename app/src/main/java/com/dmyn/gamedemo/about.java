package com.dmyn.gamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class about extends Activity {
    RelativeLayout about;
    CustomMap customMap;
    Button up,down,left,right;
    public boolean isUpOnLongClick=false;
    public boolean isDownOnLongClick=false;
    public boolean isLeftOnLongClick=false;
    public boolean isRightOnLongClick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        createCustomMap();
        up=findViewById(R.id.up);
        down=findViewById(R.id.down);
        left=findViewById(R.id.left);
        right=findViewById(R.id.right);
        MyOnTouchListener mtl=new MyOnTouchListener();
        up.setOnTouchListener(mtl);
        down.setOnTouchListener(mtl);
        left.setOnTouchListener(mtl);
        right.setOnTouchListener(mtl);

    }

    public void createCustomMap() {
        about = findViewById(R.id.about);
        customMap = new CustomMap(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        customMap.setLayoutParams(layoutParams);
        about.addView(customMap);
    }

    public class MyOnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                switch (v.getId()){
                    case R.id.up:
                        LongUp longUp=new LongUp();
                        isUpOnLongClick=true;
                        longUp.start();
                        break;
                    case R.id.down:
                        LongDown longDown=new LongDown();
                        isDownOnLongClick=true;
                        longDown.start();
                        break;
                    case R.id.left:
                        LongLeft longLeft=new LongLeft();
                        isLeftOnLongClick=true;
                        longLeft.start();
                        break;
                    case R.id.right:
                        LongRight longRight =new LongRight();
                        isRightOnLongClick=true;
                        longRight.start();
                        break;
                }
            }else if (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_OUTSIDE){
                isUpOnLongClick=false;
                isDownOnLongClick=false;
                isLeftOnLongClick=false;
                isRightOnLongClick=false;
            }
            return false;
        }
    }

    public class LongUp extends Thread{
        public void run(){
            while(isUpOnLongClick){
                try {
                    customMap.player.up();
                    customMap.invalidate();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class LongDown extends Thread{
        public void run(){
            while(isDownOnLongClick){
                try {
                    customMap.player.down();
                    customMap.invalidate();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class LongLeft extends Thread{
        public void run(){
            while(isLeftOnLongClick){
                try {
                    customMap.player.left();
                    customMap.invalidate();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class LongRight extends Thread{
        public void run(){
            while(isRightOnLongClick){
                try {
                    customMap.player.right();
                    customMap.invalidate();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
