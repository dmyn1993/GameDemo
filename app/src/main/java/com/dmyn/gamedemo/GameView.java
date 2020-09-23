package com.dmyn.gamedemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class GameView extends View {
    public int gate=Game.gate;
    private int manx;
    private int many;
    private float xoff=10;
    private float yoff=20;
    private int tileSize;
    public int mapRow=0;
    public int mapCol=0;
    private int width=0;
    private int height=0;
    private Bitmap pic[]=null;
    final int WALL=1;
    final int GOAL=2;
    final int ROAD=3;
    final int BOX=4;
    final int BOXATGOAL=5;
    final int MAN=6;
    private Paint paint;
    private Game game=null;
    public int [][]map=null;
    private int [][]tem;//原来的地图
    float currentx;
    float currenty;

    public GameView(Context context, AttributeSet attrs) {
        super(context,attrs);
        //实例化game，后面用
        game=(Game)context;

        //获取当前游戏屏幕的宽和高
        DisplayMetrics dm=getResources().getDisplayMetrics();
        width=dm.widthPixels;
        height=dm.heightPixels;
        this.setFocusable(true);

        if (game.continue_game){
            map=game.stored;
            getMapDetail();
            getManPosition();
        }else{
            initMap();
        }
        initPic();
    }

    public void initMap(){
        map=getMap(gate);
        getMapDetail();
        getManPosition();
    }

    public int[] [] getMap(int grate){
        return MapList.getMap(grate);
    }

    private void getMapDetail(){
        mapRow=map.length;
        mapCol=map[gate].length;
        xoff=30;
        yoff=60;
        int t=mapRow>mapCol?mapRow:mapCol;
        int s1=(int)Math.floor((width-yoff)/t);
        int s2=(int)Math.floor((height-xoff)/t);
        tileSize=s1<s2?s1:s2;
        tem=MapList.getMap(gate);
    }

    public void getManPosition(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]==MAN){
                    manx=i;
                    many=j;
                    break;
                }
            }
        }
    }

    public void initPic(){
        pic=new Bitmap[7];
        Resources r=this.getContext().getResources();
        loadPic(WALL,r.getDrawable(R.drawable.wall));
        loadPic(GOAL,r.getDrawable(R.drawable.goal));
        loadPic(ROAD,r.getDrawable(R.drawable.road));
        loadPic(BOX,r.getDrawable(R.drawable.box));
        loadPic(BOXATGOAL,r.getDrawable(R.drawable.box_at_goal));
        loadPic(MAN,r.getDrawable(R.drawable.man));
    }

    public void loadPic(int key, Drawable tile){
        Bitmap bitmap=Bitmap.createBitmap(tileSize,tileSize,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        tile.setBounds(0,0,tileSize,tileSize);
        tile.draw(canvas);
        pic[key]=bitmap;
    }

    public boolean onTouchEvent(MotionEvent event) {
        currentx=event.getX();
        currenty=event.getY();
        float x=(float)(xoff+many*tileSize);
        float y=(float)(yoff+manx*tileSize);
        if(currenty>y&&(currenty<y+tileSize)) {
            if(currentx>x+tileSize) {
                moveLeft();
            }
            if(currentx<x) {
                moveRight();
            }
        }
        if(currentx>x&&(currentx<x+tileSize)) {
            if(currenty>y+tileSize) {
                moveUp();
            }
            if(currenty<y) {
                moveDown();
            }
        }
        if(finished()) {
            nextGate();
        }
        this.invalidate();
        return super.onTouchEvent(event);
    }

    private void moveUp() {
        if(map[manx-1][many]==BOX||map[manx-1][many]==BOXATGOAL) {
            if(map[manx-2][many]==GOAL||map[manx-2][many]==ROAD) {
                storyMap(map);
                map[manx-2][many]=map[manx-2][many]==GOAL?BOXATGOAL:BOX;
                map[manx-1][many]=MAN;
                map[manx][many]=roadOrGoal(manx,many);
                manx--;
            }
        } else {
            if(map[manx-1][many]==ROAD||map[manx-1][many]==GOAL) {
                storyMap(map);
                map[manx-1][many]=MAN;
                map[manx][many]=roadOrGoal(manx,many);
                manx--;
            }
        }
    }

    private void moveDown() {
        if(map[manx+1][many]==BOX||map[manx+1][many]==BOXATGOAL) {
            if(map[manx+2][many]==GOAL||map[manx+2][many]==ROAD) {
                storyMap(map);
                map[manx+2][many]=map[manx+2][many]==GOAL?BOXATGOAL:BOX;
                map[manx+1][many]=MAN;
                map[manx][many]=roadOrGoal(manx,many);
                manx++;
            }
        } else {
            if(map[manx+1][many]==ROAD||map[manx+1][many]==GOAL) {
                storyMap(map);
                map[manx+1][many]=MAN;
                map[manx][many]=roadOrGoal(manx,many);
                manx++;
            }
        }
    }

    private void moveLeft() {
        if (map[manx][many - 1] == BOX || map[manx][many - 1] == BOXATGOAL) {
            if (map[manx][many - 2] == GOAL || map[manx][many - 2] == ROAD) {
                storyMap(map);
                map[manx][many - 2] = map[manx][many - 2] == GOAL ? BOXATGOAL: BOX;
                map[manx][many - 1] = MAN;
                map[manx][many] = roadOrGoal(manx, many);
                many--;
            }
        } else {
            if (map[manx][many - 1] == ROAD || map[manx][many - 1] == GOAL) {
                storyMap(map);
                map[manx][many - 1] = MAN;
                map[manx][many] = roadOrGoal(manx, many);
                many--;
            }
        }
    }

    private void moveRight() {
        if(map[manx][many+1]==BOX||map[manx][many+1]==BOXATGOAL) {
            if(map[manx][many+2]==GOAL||map[manx][many+2]==ROAD) {
                storyMap(map);
                map[manx][many+2]=map[manx][many+2]==GOAL?BOXATGOAL:BOX;
                map[manx][many+1]=MAN;
                map[manx][many]=roadOrGoal(manx,many);
                many++;
            }
        } else {
            if(map[manx][many+1]==ROAD||map[manx][many+1]==GOAL) {
                storyMap(map);
                map[manx][many+1]=MAN;
                map[manx][many]=roadOrGoal(manx,many);
                many++;
            }
        }
    }

    private boolean finished() {
        boolean finish=true;
        for(int i=0; i<mapRow; i++){
            for(int j=0; j<mapCol; j++) {
                if(map[i][j]==GOAL||map[i][j]==BOX)
                    finish= false;
            }
        }
        return finish;
    }

    public void nextGate() {
        if(gate<MapList.getCount()-1) {
            gate++;
        } else
            Toast.makeText(this.getContext(), "目前是最后一关", Toast.LENGTH_SHORT).show();
        reInitMap();
    }

    public int roadOrGoal(int x, int y) {
        int result=ROAD;
        if(tem[x][y]==GOAL) {
            result=GOAL;
        }
        return result;
    }

    private void reInitMap() {
        initMap();
        initPic();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mapRow; i++)
            for (int j = 0; j < mapCol; j++) {
                if (map[i][j] != 0)
                    canvas.drawBitmap(pic[map[i][j]], xoff + j * tileSize,yoff + i * tileSize, paint);
            }
    }

    class CurrentMap {
        int[][]currMap;
        public CurrentMap(int[][] maps) {
            int row=maps.length;
            int column=maps[0].length;
            int [][]temp=new int [row][column];
            for(int i=0; i<row; i++)
                for(int j=0; j<column; j++) {
                    temp[i][j]=maps[i][j];
                }
            this.currMap=temp;
        }
    }

    public ArrayList<CurrentMap> list=new ArrayList<GameView.CurrentMap>();
    public void storyMap(int map[][]) {
        CurrentMap cMap=new CurrentMap(map);
        list.add(cMap);
        if(list.size()>10){
            list.remove(0);
        }
    }

}
