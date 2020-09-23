package com.dmyn.gamedemo;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {
    public static int gate=0;
    GameView gameView;
    public boolean continue_game;
    private String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        continue_game=false;
        s=getIntent().getStringExtra("continue");
        if (s.equals("yes")){
            continue_game=true;
        }
        if(continue_game){
            loadMap();
        }
        setContentView(R.layout.game);
        gameView=findViewById(R.id.myGameView);
    }

    String first="0011100000121000001311111114342112346111111141000001210000011100";
    int[][] stored;
    public void loadMap(){
        gate=(int)getPreferences(MODE_PRIVATE).getLong("gate",0);
        String mapString=getPreferences(MODE_PRIVATE).getString("maps",first);
        int ii=(int)getPreferences(MODE_PRIVATE).getLong("mapRow",8);
        int jj=(int)getPreferences(MODE_PRIVATE).getLong("mapCol",8);
        int[] maps=new int[mapString.length()];
        for(int i=0;i<maps.length;i++){
            maps[i]=mapString.charAt(i)-'0';
        }
        stored=new int[ii][jj];
        int a=0;
        for(int m=0;m<ii;m++){
            for(int n=0;n<jj;n++){
                stored[m][n]=maps[a];
                a++;
            }
        }
    }

    protected void onPause() {
        super.onPause();
        getPreferences(MODE_PRIVATE).edit().putLong("gate",gameView.gate).commit();
        getPreferences(MODE_PRIVATE).edit().putString("maps",saveGame()).commit();
        getPreferences(MODE_PRIVATE).edit().putLong("mapRow",gameView.mapRow).commit();
        getPreferences(MODE_PRIVATE).edit().putLong("mapCol",gameView.mapCol).commit();
    }

    public String saveGame(){
        int[] saved=new int[gameView.mapRow * gameView.mapCol];
        for (int i=0;i<gameView.mapRow;i++){
            for(int j=0;j<gameView.mapCol;j++){
                saved[i * gameView.mapCol + j]=gameView.map[i][j];
            }
        }
        StringBuilder buf=new StringBuilder();
        for(int element:saved){
            buf.append(element);
        }
        String s=buf.toString();
        return s;
    }

}
