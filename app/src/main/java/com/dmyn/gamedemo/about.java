package com.dmyn.gamedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class about extends Activity {
    RelativeLayout about;
    CustomMap customMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        createCustomMap();
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.up:
                Toast.makeText(this, "up", Toast.LENGTH_SHORT).show();
                customMap.player.up();
                break;
            case R.id.down:
                Toast.makeText(this, "down", Toast.LENGTH_SHORT).show();
                break;
            case R.id.left:
                Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right:
                Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
                break;
        }
        customMap.invalidate();
    }

}
