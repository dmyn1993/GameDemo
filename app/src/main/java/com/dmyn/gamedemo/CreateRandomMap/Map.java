package com.dmyn.gamedemo.CreateRandomMap;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public int mapHang;
    public int mapLie;
    public int[][] gameMap = null;
    public List<List<Integer>> listMap = new ArrayList<>();

    /**
     * 实例化对象时需指定地图大小
     *
     * @param mapHang 地图有几横行
     * @param mapLie  地图有几竖列
     */
    public Map(int mapHang, int mapLie) {
        this.mapHang = mapHang;
        this.mapLie = mapLie;
        this.gameMap = new int[this.mapHang][this.mapLie];
    }

    /**
     * 把数组格式的地图转换成集合格式的地图
     */
    public void arrayToList() {
        for (int i = 0; i < this.gameMap.length; i++) {
            List<Integer> tmpListMap = new ArrayList<>();
            for (int j = 0; j < this.gameMap[i].length; j++) {
                tmpListMap.add(this.gameMap[i][j]);
            }
            this.listMap.add(tmpListMap);
        }
    }

    /**
     * 把集合格式的地图转换成数组格式的地图
     */
    public void listToArray() {
        int[][] tmpArrayMap = new int[this.listMap.size()][this.listMap.get(0).size()];
        for (int i = 0; i < this.listMap.size(); i++) {
            int[] tmpArrayMapHang = new int[this.listMap.get(i).size()];
            for (int j = 0; j < this.listMap.get(i).size(); j++) {
                tmpArrayMapHang[j] = this.listMap.get(i).get(j);
            }
            tmpArrayMap[i]=tmpArrayMapHang;
        }
        this.gameMap=tmpArrayMap;
    }

}

