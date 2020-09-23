package com.dmyn.gamedemo.CreateRandomMap;

import java.util.List;

public class Room {
    public int mapHang;
    public int mapLie;
    public int[][] roomMap = null;
    public int minRoomSize = 3;
    public int maxRoomSize = 8;
    private final static int roomFlag = 2;
    private final static int maxCreateRoomCount = 1000;

    /**
     * 实例化对象时需指定地图大小
     *
     * @param mapHang 地图有几横行
     * @param mapLie  地图有几竖列
     */
    public Room(int mapHang, int mapLie,int roomCount) {
        this.mapHang = mapHang;
        this.mapLie = mapLie;
        Road rd = new Road(mapHang, mapLie);
        this.roomMap=rd.roadMap;
        this.createManyRooms(roomCount);
        //为地图添加边界，防止道路或房间紧挨地图边缘
        AddEdge addEdge = new AddEdge();
        List<List<Integer>> listMap = addEdge.arrayToList(this.roomMap);
        addEdge.addUp(listMap);
        addEdge.addDown(listMap);
        addEdge.addLeft(listMap);
        addEdge.addRight(listMap);
        addEdge.replaceMapEdge(listMap);
        this.roomMap = addEdge.listToArray(listMap);
    }

    /**
     * 随机生成房间坐标和大小
     *
     * @return 横X轴坐标；竖Y轴坐标；房间行数；房间列数
     */
    public int[] randomPositionSize() {
        /*
         * 随机数范围举例
         * r1范围包含max和min： int r1=(int)(Math.random()*((max+1)-min)+min)
         * r2范围不包含max，包含min： int r2=(int)(Math.random()*(max-min)+min)
         */
        // 最小房间限制
        int minRS = this.minRoomSize + 2;
        // 最大房间限制
        int maxRS = this.maxRoomSize + 2;
        // 随机生成房间左上角坐标在地图第几行，最大不能超过地图行数减去房间最小限制
        int xHang = (int) (Math.random() * (((this.mapHang - minRS) + 1) - 0) + 0);
        // 随机生成房间左上角坐标在地图第几列，最大不能超过地图列数减去房间最小限制
        int yLie = (int) (Math.random() * (((this.mapLie - minRS) + 1) - 0) + 0);
        // 随机生成房间行数，最大不能超过房间最大限制且不能超出地图范围
        int roomHang = (int) (Math.random() * (((this.mapHang - xHang > maxRS ? maxRS : this.mapHang - xHang) + 1) - minRS) + minRS);
        // 随机生成房间列数，最大不能超过房间最大限制且不能超出地图范围
        int roomLie = (int) (Math.random() * (((this.mapLie - yLie > maxRS ? maxRS : this.mapLie - yLie) + 1) - minRS) + minRS);
//		System.out.println("xHang="+xHang+", yLie="+yLie+", roomHang="+roomHang+", roomLie="+roomLie);    //打印房间坐标和大小
        int[] rps = { xHang, yLie, roomHang, roomLie };
        return rps;
    }

    /**
     * 创建单个房间
     *
     * @param xHang    横X轴坐标
     * @param yLie     竖Y轴坐标
     * @param roomHang 房间行数
     * @param roomLie  房间列数
     * @return 房间是否重叠
     */
    public boolean createSingleRoom(int xHang, int yLie, int roomHang, int roomLie) {
        boolean overlap = false;
        // 第一次遍历房间，判断是否和上个房间重叠
        int firstTempX = xHang;
        for (int i = 1; i <= roomHang; i++) {
            int firstTempY = yLie;
            for (int j = 1; j <= roomLie; j++) {
                // 如果当前位置的值大于3则房间有重叠
                if (this.roomMap[firstTempX][firstTempY] + roomFlag > 3) {
                    overlap = true;
                }
                firstTempY = firstTempY + 1;
            }
            firstTempX = firstTempX + 1;
        }
        // 第二次遍历房间，如果没有重叠则创建房间
        int secondTempX = xHang;
        for (int i = 1; i <= roomHang; i++) {
            int secondTempY = yLie;
            for (int j = 1; j <= roomLie; j++) {
                if (!overlap) {
                    // 不创建房间最外一圈
                    if (i != 1 && i != roomHang && j != 1 && j != roomLie) {
                        this.roomMap[secondTempX][secondTempY] = roomFlag;
                    }
                }
                secondTempY = secondTempY + 1;
            }
            secondTempX = secondTempX + 1;
        }
        return overlap;
    }

    /**
     * 创建多个房间
     *
     * @param roomCount 房间数量
     */
    public void createManyRooms(int roomCount) {
        int count = 0;
        while (true) {
            count++;
            int[] rps = this.randomPositionSize();
            // 如果房间没有重叠则代表创建房间成功，房间剩余数量减1
            if (!this.createSingleRoom(rps[0], rps[1], rps[2], rps[3])) {
                roomCount--;
            }
            // 如果房间剩余数量为0或已达到最大房间创建次数则跳出循环
            if (roomCount == 0 || count == maxCreateRoomCount) {
                break;
            }
        }
    }

}
