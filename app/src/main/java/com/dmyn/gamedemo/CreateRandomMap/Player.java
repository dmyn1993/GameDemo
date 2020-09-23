package com.dmyn.gamedemo.CreateRandomMap;

public class Player {
    public Map map=null;
    public int playerXHang;
    public int playerYLie;
    public int playerFloor;
    private final static int playerFlag=9;

    public Player(Map map) {
        this.map=map;
    }

    public void createPlayer() {
        while(true) {
            int pXHang = (int) (Math.random() * ((this.map.mapHang) - 0) + 0);
            int pYLie = (int) (Math.random() * ((this.map.mapLie) - 0) + 0);
            if(this.map.gameMap[pXHang][pYLie]==1 || this.map.gameMap[pXHang][pYLie]==2) {
                this.playerXHang=pXHang;
                this.playerYLie=pYLie;
                this.playerFloor=this.map.gameMap[pXHang][pYLie];
                this.map.gameMap[pXHang][pYLie]=playerFlag;
                break;
            }
        }
    }

    public void up() {
        int newXH=this.playerXHang-1<0?0:this.playerXHang-1;
        if(this.map.gameMap[newXH][this.playerYLie]!=0) {
            int tempPlayerfloor=this.map.gameMap[newXH][this.playerYLie];
            this.map.gameMap[newXH][this.playerYLie]=playerFlag;
            this.map.gameMap[this.playerXHang][this.playerYLie]=this.playerFloor;
            this.playerFloor=tempPlayerfloor;
        }
    }

    public void down() {

    }

    public void left() {

    }

    public void right() {

    }

}

