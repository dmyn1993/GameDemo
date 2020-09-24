package com.dmyn.gamedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent in = null;
        switch (view.getId()) {
            case R.id.btn_about:
                in = new Intent(MainActivity.this, about.class);
                startActivity(in);
                break;
            case R.id.btn_continue:
                in = new Intent(MainActivity.this, Game.class);
                in.putExtra("continue", "yes");
                startActivity(in);
                break;
            case R.id.btn_newGame:
                in = new Intent(MainActivity.this, Game.class);
                in.putExtra("continue", "no");
                startActivity(in);
                break;
            case R.id.btn_exit:
                isFinish();
                break;
        }
    }

    public void isFinish() {
        AlertDialog.Builder dl1 = new AlertDialog.Builder(this);
        dl1.setTitle("退出确认");
        dl1.setMessage("确定要退出吗？");
        dl1.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        dl1.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dl1.create();
        dl1.show();
    }

}
