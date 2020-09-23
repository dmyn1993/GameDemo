package com.dmyn.gamedemo.CreateRandomMap;

import java.util.ArrayList;
import java.util.List;

public class Road {
    public int mapHang;
    public int mapLie;
    public int[][] roadMap = null;
    private final static int roadFlag=1;

    /**
     * 实例化对象时需指定地图大小
     *
     * @param mapHang 地图有几横行
     * @param mapLie  地图有几竖列
     */
    public Road(int mapHang, int mapLie) {
        this.mapHang = mapHang;
        this.mapLie = mapLie;
        this.roadMap = new int[this.mapHang][this.mapLie];
        this.randomRoadStart();
    }

    /**
     * 随机道路起点
     */
    public void randomRoadStart() {
        int xHang = (int) (Math.random() * ((this.mapHang) - 0) + 0);
        int yLie = (int) (Math.random() * ((this.mapLie) - 0) + 0);
        // 设置道路起点
        this.roadMap[xHang][yLie] = roadFlag;
        // 传入起点位置，向下一个方向移动
        this.randomOrientationCreateRoad(xHang, yLie);
    }

    /**
     * 随机方向生成道路
     *
     * @param startX 起点横行X坐标
     * @param startY 起点纵列Y坐标
     */
    public void randomOrientationCreateRoad(int startX, int startY) {
        int x = startX;
        int y = startY;
        List<int[]> roadXY = new ArrayList<int[]>();
        while (true) {
            // 随机指定一个方向（0=上，1=下，2=左，3=右）
            int orientation = (int) (Math.random() * (4 - 0) + 0);
            // 判断当前位置的四个方向是否有空位
            if (this.isAlive(x, y)) {
                // 如果有空位则向指定方向移动两格
                int[] xy = this.move(orientation, x, y);
                // 第三位数字为1代表移动成功
                if (xy[2] == 1) {
                    x = xy[0];
                    y = xy[1];
                    int[] movedXY = { x, y };
                    // 把移动后的坐标存入道路坐标集合
                    roadXY.add(movedXY);
                }
            } else {
                // 如果没有空位则返回上一个道路坐标集合
                roadXY.remove(roadXY.size() - 1);
                // 如果道路坐标List为空代表整个地图已没有空位，循环结束
                if (roadXY.size() == 0) {
                    break;
                }
                // 把当前坐标设为道路坐标集合最后一组
                x = roadXY.get(roadXY.size() - 1)[0];
                y = roadXY.get(roadXY.size() - 1)[1];
            }
        }
    }

    /**
     * 当前位置的四个方向是否有活路
     *
     * @param x 当前横行X坐标
     * @param y 当前纵列Y坐标
     * @return 是否有活路
     */
    public boolean isAlive(int x, int y) {
        boolean alive = false;
        //（上第二格为空且x不等于1）或（下第二为空且x不等于地图行数-1）或（左第二为空且y不等于1）或（右第二为空且y不等于地图列数-1）
        if ((this.roadMap[x - 2 < 0 ? 0 : x - 2][y] == 0 && x != 1)
                || (this.roadMap[x + 2 >= this.mapHang - 1 ? this.mapHang - 1 : x + 2][y] == 0 && x != this.mapHang - 2)
                || (this.roadMap[x][y - 2 < 0 ? 0 : y - 2] == 0 && y != 1)
                || (this.roadMap[x][y + 2 >= this.mapLie - 1 ? this.mapLie - 1 : y + 2] == 0 && y != this.mapLie - 2)) {
            alive = true;
        }
        return alive;
    }

    /**
     * 向指定方向移动两格
     *
     * @param orientation 移动方向（0=上，1=下，2=左，3=右）
     * @param x           当前横行X坐标
     * @param y           当前纵列Y坐标
     * @return 下标0是移动后的横行X坐标；下标1是移动后的纵列Y坐标；下标2是0代表移动失败，1代表移动成功
     */
    public int[] move(int orientation, int x, int y) {
        int[] xy = new int[3];
        if (orientation == 0) {
            //上第一格为空
            if (this.roadMap[x - 1 < 0 ? 0 : x - 1][y] == 0) {
                //上第二格为空
                if (this.roadMap[x - 2 < 0 ? 0 : x - 2][y] == 0) {
                    //x向上移动一格后和向上移动两格后是否为同一个位置，用来判断x是否处于地图边缘或地图边缘-1的位置
                    if ((x - 1 < 0 ? 0 : x - 1) != (x - 2 < 0 ? 0 : x - 2)) {
                        //x向上移动一格
                        x = x - 1 < 0 ? 0 : x - 1;
                        this.roadMap[x][y] = roadFlag;
                        //x再向上移动一格
                        x = x - 1 < 0 ? 0 : x - 1;
                        this.roadMap[x][y] = roadFlag;
                        //当前方法的返回值下标2设为1，代表移动成功
                        xy[2] = 1;
                    }
                }
            }
        } else if (orientation == 1) {
            if (this.roadMap[x + 1 >= this.mapHang - 1 ? this.mapHang - 1 : x + 1][y] == 0) {
                if (this.roadMap[x + 2 >= this.mapHang - 1 ? this.mapHang - 1 : x + 2][y] == 0) {
                    if ((x + 1 >= this.mapHang - 1 ? this.mapHang - 1 : x + 1) != (x + 2 >= this.mapHang - 1 ? this.mapHang - 1 : x + 2)) {
                        x = x + 1 >= this.mapHang - 1 ? this.mapHang - 1 : x + 1;
                        this.roadMap[x][y] = roadFlag;
                        x = x + 1 >= this.mapHang - 1 ? this.mapHang - 1 : x + 1;
                        this.roadMap[x][y] = roadFlag;
                        xy[2] = 1;
                    }
                }
            }
        } else if (orientation == 2) {
            if (this.roadMap[x][y - 1 < 0 ? 0 : y - 1] == 0) {
                if (this.roadMap[x][y - 2 < 0 ? 0 : y - 2] == 0) {
                    if ((y - 1 < 0 ? 0 : y - 1) != (y - 2 < 0 ? 0 : y - 2)) {
                        y = y - 1 < 0 ? 0 : y - 1;
                        this.roadMap[x][y] = roadFlag;
                        y = y - 1 < 0 ? 0 : y - 1;
                        this.roadMap[x][y] = roadFlag;
                        xy[2] = 1;
                    }
                }
            }
        } else if (orientation == 3) {
            if (this.roadMap[x][y + 1 >= this.mapLie - 1 ? this.mapLie - 1 : y + 1] == 0) {
                if (this.roadMap[x][y + 2 >= this.mapLie - 1 ? this.mapLie - 1 : y + 2] == 0) {
                    if ((y + 1 >= this.mapLie - 1 ? this.mapLie - 1 : y + 1) != (y + 2 >= this.mapLie - 1 ? this.mapLie - 1 : y + 2)) {
                        y = y + 1 >= this.mapLie - 1 ? this.mapLie - 1 : y + 1;
                        this.roadMap[x][y] = roadFlag;
                        y = y + 1 >= this.mapLie - 1 ? this.mapLie - 1 : y + 1;
                        this.roadMap[x][y] = roadFlag;
                        xy[2] = 1;
                    }
                }
            }
        }
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

}
