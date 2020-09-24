package com.dmyn.gamedemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.dmyn.gamedemo.CreateRandomMap.Player;
import com.dmyn.gamedemo.CreateRandomMap.Room;

public class CustomMap extends View {
    public float LR_margin = 0;//左右边距
    public float UD_margin = 0;//上下边距
    public int tileSize = 0;
    public int mapRow = 0;
    public int mapCol = 0;
    public Bitmap pic[] = null;
    public Paint paint = null;
    public int[][] map = null;
    public Player player = null;
    public Context context;

    /**
     * xml创建时调用
     *
     * @param context
     * @param attrs
     */
    public CustomMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        cm();
    }

    /**
     * java代码创建时调用
     *
     * @param context
     */
    public CustomMap(Context context) {
        super(context);
        this.context = context;
        cm();
    }

    public void cm() {
        //获取当前游戏屏幕的宽和高
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //创建随机道路和房间
        Room rm = new Room(30, 30, 8);
        player = new Player(rm.rd.map);
        //创建玩家
        player.createPlayer();
        //把创建好的地图传给当前类的map变量
        map = rm.rd.map.gameMap;
        mapRow = map.length;
        mapCol = map[0].length;
        //获取地图的行列数，地图边距，每个图片的大小
        int t = mapRow > mapCol ? mapRow : mapCol;
        int s1 = (int) Math.floor((width - UD_margin) / t);
        int s2 = (int) Math.floor((height - LR_margin) / t);
        tileSize = s1 < s2 ? s1 : s2;
        //把地图的值改为对应的图片
        pic = new Bitmap[10];
        Resources r = this.getContext().getResources();
        loadPic(1, r.getDrawable(R.drawable.wall));
        loadPic(2, r.getDrawable(R.drawable.road));
        loadPic(3, r.getDrawable(R.drawable.box));
        loadPic(9, r.getDrawable(R.drawable.man));
    }

    public void loadPic(int key, Drawable tile) {
        Bitmap bitmap = Bitmap.createBitmap(tileSize, tileSize, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, tileSize, tileSize);
        tile.draw(canvas);
        pic[key] = bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mapRow; i++) {
            for (int j = 0; j < mapCol; j++) {
                if (map[i][j] != 0) {
                    canvas.drawBitmap(pic[map[i][j]], LR_margin + j * tileSize, UD_margin + i * tileSize, paint);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽度
        int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        switch (specModeWidth) {
            case MeasureSpec.UNSPECIFIED://默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
                widthMeasureSpec = Math.max(widthMeasureSpec, specSizeWidth);
                break;
            case MeasureSpec.EXACTLY://表示父控件已经确切的指定了子View的大小。
                widthMeasureSpec = specSizeWidth;
                break;
            case MeasureSpec.AT_MOST://表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
                widthMeasureSpec = (int) (tileSize * mapCol) + getPaddingLeft() + getPaddingRight();
                break;
        }
        //获取高度
        int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int specSizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        switch (specModeHeight) {
            case MeasureSpec.UNSPECIFIED:
                heightMeasureSpec = Math.max(heightMeasureSpec, specSizeHeight);
                break;
            case MeasureSpec.EXACTLY:
                heightMeasureSpec = specSizeHeight;
                break;
            case MeasureSpec.AT_MOST:
                heightMeasureSpec = (int) (tileSize * mapRow) + getPaddingTop() + getPaddingBottom();
                break;
        }
        //设置当前控件的宽高
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
