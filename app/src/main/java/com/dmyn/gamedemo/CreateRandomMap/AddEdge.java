package com.dmyn.gamedemo.CreateRandomMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddEdge {

    /**
     * 把数组格式的地图转换成集合格式的地图
     *
     * @param arrayMap 数组格式的地图
     * @return 集合格式的地图
     */
    public List<List<Integer>> arrayToList(int[][] arrayMap) {
        List<List<Integer>> listMap = new ArrayList<>();
        for (int i = 0; i < arrayMap.length; i++) {
            List<Integer> tmpListMap = new ArrayList<>();
            for (int j = 0; j < arrayMap[i].length; j++) {
                tmpListMap.add(arrayMap[i][j]);
            }
            listMap.add(tmpListMap);
        }
        return listMap;
    }

    /**
     * 把集合格式的地图转换成数组格式的地图
     *
     * @param listMap 集合格式的地图
     * @return 数组格式的地图
     */
    public int[][] listToArray(List<List<Integer>> listMap) {
        int[][] arrayMap = new int[listMap.size()][listMap.get(0).size()];
        for (int i = 0; i < listMap.size(); i++) {
            int[] tmpArrayMap = new int[listMap.get(i).size()];
            for (int j = 0; j < listMap.get(i).size(); j++) {
                tmpArrayMap[j] = listMap.get(i).get(j);
            }
            arrayMap[i] = tmpArrayMap;
        }
        return arrayMap;
    }

    /**
     * 为地图上边界添加空行
     *
     * @param listMap 集合格式的地图
     */
    public void addUp(List<List<Integer>> listMap) {
        int sum = 0;
        for (int i : listMap.get(0)) {
            sum = sum + i;
            if (sum != 0)
                break;
        }
        List<Integer> lHang = new ArrayList<>();
        for (int i = 0; i < listMap.get(0).size(); i++) {
            lHang.add(0);
        }
        if (sum != 0) {
            listMap.add(0, lHang);
        }
    }

    /**
     * 为地图下边界添加空行
     *
     * @param listMap 集合格式的地图
     */
    public void addDown(List<List<Integer>> listMap) {
        int sum = 0;
        for (int i : listMap.get(listMap.size() - 1)) {
            sum = sum + i;
            if (sum != 0)
                break;
        }
        List<Integer> lHang = new ArrayList<>();
        for (int i = 0; i < listMap.get(0).size(); i++) {
            lHang.add(0);
        }
        if (sum != 0) {
            listMap.add(lHang);
        }
    }

    /**
     * 为地图左边界添加空列
     *
     * @param listMap 集合格式的地图
     */
    public void addLeft(List<List<Integer>> listMap) {
        int sum = 0;
        for (int i = 0; i < listMap.size(); i++) {
            sum = sum + listMap.get(i).get(0);
            if (sum != 0)
                break;
        }
        if (sum != 0) {
            for (int i = 0; i < listMap.size(); i++) {
                listMap.get(i).add(0, 0);
            }
        }
    }

    /**
     * 为地图右边界添加空列
     *
     * @param listMap 集合格式的地图
     */
    public void addRight(List<List<Integer>> listMap) {
        int sum = 0;
        for (int i = 0; i < listMap.size(); i++) {
            sum = sum + listMap.get(i).get(listMap.get(i).size() - 1);
            if (sum != 0)
                break;
        }
        if (sum != 0) {
            for (int i = 0; i < listMap.size(); i++) {
                listMap.get(i).add(0);
            }
        }
    }

    /**
     * 替换地图边界的值为3
     *
     * @param listMap 集合格式的地图
     */
    public void replaceMapEdge(List<List<Integer>> listMap) {
        Collections.replaceAll(listMap.get(0), 0, 3);
        Collections.replaceAll(listMap.get(listMap.size() - 1), 0, 3);
        for (int i = 1; i < listMap.size() - 1; i++) {
            listMap.get(i).set(0, 3);
            listMap.get(i).set(listMap.get(i).size() - 1, 3);
        }
    }

}
