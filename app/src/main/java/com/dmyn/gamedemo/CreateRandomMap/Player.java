package com.dmyn.gamedemo.CreateRandomMap;

import android.util.Log;

public class Player {
    public Map map = null;
    public int playerXHang;
    public int playerYLie;
    public int playerFloor;
    private final static int playerFlag = 9;

    public Player(Map map) {
        this.map = map;
    }

    public void createPlayer() {
        while (true) {
            int pXHang = (int) (Math.random() * ((this.map.mapHang) - 0) + 0);
            int pYLie = (int) (Math.random() * ((this.map.mapLie) - 0) + 0);
            if (this.map.gameMap[pXHang][pYLie] != 0 && this.map.gameMap[pXHang][pYLie] != 3) {
                this.playerXHang = pXHang;
                this.playerYLie = pYLie;
                this.playerFloor = this.map.gameMap[pXHang][pYLie];
                this.map.gameMap[pXHang][pYLie] = playerFlag;
                break;
            }
        }
    }

    public void up() {
        int newXH = this.playerXHang - 1 < 0 ? 0 : this.playerXHang - 1;
        Log.e("dmyn", "xh=" + newXH + ", yl=" + this.playerYLie + ", new=" + this.map.gameMap[newXH][this.playerYLie]);
        if (this.map.gameMap[newXH][this.playerYLie] != 0 && this.map.gameMap[newXH][this.playerYLie] != 3 && this.map.gameMap[newXH][this.playerYLie] != playerFlag) {
            int tempPlayerFloor = this.map.gameMap[newXH][this.playerYLie];
            this.map.gameMap[newXH][this.playerYLie] = playerFlag;
            this.map.gameMap[this.playerXHang][this.playerYLie] = this.playerFloor;
            this.playerFloor = tempPlayerFloor;
            this.playerXHang = newXH;
        }
    }

    public void down() {
        int newXH = this.playerXHang + 1 >= this.map.mapHang - 1 ? this.map.mapHang - 1 : this.playerXHang + 1;
        Log.e("dmyn", "xh=" + newXH + ", yl=" + this.playerYLie + ", new=" + this.map.gameMap[newXH][this.playerYLie]);
        if (this.map.gameMap[newXH][this.playerYLie] != 0 && this.map.gameMap[newXH][this.playerYLie] != playerFlag) {
            int tempPlayerFloor = this.map.gameMap[newXH][this.playerYLie];
            this.map.gameMap[newXH][this.playerYLie] = playerFlag;
            this.map.gameMap[this.playerXHang][this.playerYLie] = this.playerFloor;
            this.playerFloor = tempPlayerFloor;
            this.playerXHang = newXH;
        }
    }

    public void left() {
        int newYL = this.playerYLie - 1 < 0 ? 0 : this.playerYLie - 1;
        Log.e("dmyn", "xh=" + this.playerXHang + ", yl=" + newYL + ", new=" + this.map.gameMap[this.playerXHang][newYL]);
        if (this.map.gameMap[this.playerXHang][newYL] != 0 && this.map.gameMap[this.playerXHang][newYL] != 3 && this.map.gameMap[this.playerXHang][newYL] != playerFlag) {
            int tempPlayerFloor = this.map.gameMap[this.playerXHang][newYL];
            this.map.gameMap[this.playerXHang][newYL] = playerFlag;
            this.map.gameMap[this.playerXHang][this.playerYLie] = this.playerFloor;
            this.playerFloor = tempPlayerFloor;
            this.playerYLie = newYL;
        }
    }

    public void right() {
        int newYL = this.playerYLie + 1 >= this.map.mapLie - 1 ? this.map.mapLie - 1 : this.playerYLie + 1;
        Log.e("dmyn", "xh=" + this.playerXHang + ", yl=" + newYL + ", new=" + this.map.gameMap[this.playerXHang][newYL]);
        if (this.map.gameMap[this.playerXHang][newYL] != 0 && this.map.gameMap[this.playerXHang][newYL] != playerFlag) {
            int tempPlayerFloor = this.map.gameMap[this.playerXHang][newYL];
            this.map.gameMap[this.playerXHang][newYL] = playerFlag;
            this.map.gameMap[this.playerXHang][this.playerYLie] = this.playerFloor;
            this.playerFloor = tempPlayerFloor;
            this.playerYLie = newYL;
        }
    }

}

