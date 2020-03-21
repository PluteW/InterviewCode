package Interview.InterViewSolved;

import java.util.*;

public class BitDance_Zoumigong_200321 {
    public static void main(String[] args) {
        int[][] nums = {
                {0,0,1,0,0,0,1,0},
                {0,0,1,0,0,0,1,0},
                {0,0,1,0,1,1,0,1},
                {0,1,1,1,0,0,1,0},
                {0,0,0,1,0,0,0,0},
                {0,1,0,0,0,1,0,1},
                {0,1,1,1,1,0,0,1},
                {1,1,0,0,0,1,0,1},
                {1,1,0,0,0,0,0,0}
        };
        BitDance_Zoumigong_200321 Solution = new BitDance_Zoumigong_200321();
        List<int[]> ret = Solution.solutionDFS(nums);
        for (int[] r: ret) {
            System.out.println(r[0]+ " "+r[1]);
        }
        int retBFS = Solution.solutionBFS(nums);
        System.out.println(retBFS);

        int[][] transNums = {
                {1,0,-1,1},
                {-2,0,-1,-3},
                {2,2,0,0}
        };
        int transRet = Solution.solutionTransfer(transNums);
        System.out.println(transRet);
    }
    /**
     * @Description：  获得一条走出迷宫的路径
     * @Params:     int[] nums  迷宫的布置图
     * @return:    List ret 保存有结果的list
     * @author: Mr.Wang
     * @create: 9:50
    */
    public List<int[]> solutionDFS(int[][] nums){
        int rows = nums.length;
        int cows = nums[0].length;
        int[][] visited = new int[rows][cows];
        Stack<Position> stack = new Stack<>();
        Position p = new Position(0,0);
        stack.add(p);
        visited[0][0] = 1;
        Position temp;
        while (!stack.isEmpty()&& !(p.row == rows-1 && p.cow == cows-1)){
            p = stack.peek();
            if (p.cow+1<cows && nums[p.row][p.cow+1] == 0 && visited[p.row][p.cow+1] != 1){
                temp = new Position(p.row,p.cow+1);
                stack.add(temp);
                visited[temp.row][temp.cow] = 1;
            }else if (p.row+1<rows && nums[p.row+1][p.cow] == 0 && visited[p.row+1][p.cow] != 1){
                temp = new Position(p.row+1,p.cow);
                stack.add(temp);
                visited[temp.row][temp.cow] = 1;
            }else if (p.cow-1>-1 && nums[p.row][p.cow-1] == 0 && visited[p.row][p.cow-1] != 1) {
                temp = new Position(p.row,p.cow-1);
                stack.add(temp);
                visited[temp.row][temp.cow] = 1;
            }else if (p.row-1 >-1 && nums[p.row-1][p.cow] == 0 && visited[p.row-1][p.cow] != 1){
                temp = new Position(p.row-1,p.cow);
                stack.add(temp);
                visited[temp.row][temp.cow] = 1;
            }else {
                stack.pop();
            }
        }
        if (stack.isEmpty()) return new LinkedList<>();
        Deque<int[]> deque = new LinkedList<>();
        for (Position po:stack) {
            deque.addLast(new int[]{po.row,po.cow});
        }
        return (List)deque;
    }

    /**
     * @Description： 获得走出迷宫的最小步数
     * @Params:     int[] nums 迷宫的布置图
     * @return:     int ret 走出迷宫需要的最小步骤
     * @author: Mr.Wang
     * @create: 10:28
    */
    public int solutionBFS(int[][] nums){
        int rows = nums.length;
        int cows = nums[0].length;
        int[][] count = new int[rows][cows];
        for (int i = 0;i<rows;i++){
            Arrays.fill(count[i],Integer.MAX_VALUE);
        }
        count[0][0] = 0;
        Deque<Position> deque = new LinkedList<>();
        Position p = new Position(0,0);
        deque.add(p);
        int[] r = {0,1,0,-1};
        int[] c = {1,0,-1,0};
        while (!deque.isEmpty()){
            p = deque.pollLast();
            for (int i = 0; i<4;i++){
                int tempR = p.row+r[i];
                int tempC = p.cow+c[i];
                if (tempR>-1 && tempR<rows && tempC>-1 && tempC<cows && nums[tempR][tempC] == 0){
                    if (count[tempR][tempC] > count[p.row][p.cow]+1){
                        count[tempR][tempC] = count[p.row][p.cow]+1;
                        Position temp = new Position(tempR,tempC);
                        deque.addFirst(temp);
                    }
                }
            }
        }
        return count[rows-1][cows-1];
    }


    /**
     * @Description：  在有传送门的迷宫中寻找最少的步数
     * @Params:         int[] nums  包含有传送门的迷宫
     * @return:         int  ret    最少移动次数
     * @author: Mr.Wang
     * @create: 11:21
    */
    public int solutionTransfer(int[][] nums){
        int rows = nums.length;
        int cows = nums[0].length;
        HashMap<Integer,List<int[]>> hashMap = new HashMap<>();
        int endRow=0,endCow=0,startRow=0,startCow = 0;
//        先获得起始位置、终点位置，以及各个传送门的位置
//        将传送门的代号和位置保存到hashmap中
        for (int i = 0; i<rows;i++){
            for (int j = 0;j<cows;j++){
                if (nums[i][j] == -2){
                    startRow = i;
                    startCow = j;
                }else if (nums[i][j] == -3){
                    endRow = i;
                    endCow = j;
                }else {
                    if (nums[i][j]>0){
                        if ( !hashMap.containsKey(nums[i][j])){
                            List<int[]> list = new LinkedList<>();
                            hashMap.put(nums[i][j],list);
                        }
                        hashMap.get(nums[i][j]).add(new int[]{i,j});
                    }
                }
            }
        }

        int[][] count = new int[rows][cows];
        for (int i = 0;i<rows;i++){
            Arrays.fill(count[i],Integer.MAX_VALUE);
        }
        count[startRow][startCow] = 0;
        Deque<Position> deque = new LinkedList<>();
        Position p = new Position(startRow,startCow);
        deque.add(p);
        int[] r = {0,1,0,-1};
        int[] c = {1,0,-1,0};
        while (!deque.isEmpty()){
            p = deque.pollLast();
            for (int i = 0; i<4;i++){
                int tempR = p.row+r[i];
                int tempC = p.cow+c[i];
                if (tempR>-1 && tempR<rows && tempC>-1 && tempC<cows && nums[tempR][tempC] != -1){
                    if (count[tempR][tempC] > count[p.row][p.cow]+1){
                        count[tempR][tempC] = count[p.row][p.cow]+1;
                        Position temp = new Position(tempR,tempC);
                        deque.addFirst(temp);
                    }
                }
            }
        }
        for (int targ : hashMap.keySet()){
            List<int[]> list = hashMap.get(targ);
            int[] in = list.get(0);
            int[] out = list.get(1);
            if (count[in[0]][in[1]] < count[out[0]][out[1]]){
                count[out[0]][out[1]] = count[in[0]][in[1]];
            }else {
                count[in[0]][in[1]] = count[out[0]][out[1]];
            }
            deque.addFirst(new Position(in[0],in[1]));
            deque.addFirst(new Position(out[0],out[1]));
        }
        while (!deque.isEmpty()){
            p = deque.pollLast();
            for (int i = 0; i<4;i++){
                int tempR = p.row+r[i];
                int tempC = p.cow+c[i];
                if (tempR>-1 && tempR<rows && tempC>-1 && tempC<cows && nums[tempR][tempC] != -1){
                    if (count[tempR][tempC] > count[p.row][p.cow]+1){
                        count[tempR][tempC] = count[p.row][p.cow]+1;
                        Position temp = new Position(tempR,tempC);
                        deque.addFirst(temp);
                    }
                }
            }
        }
        return count[endRow][endCow];
    }


    class Position{
        int row;
        int cow;
        public Position(int row,int cow){
            this.cow = cow;
            this.row = row;
        }
        public void show(){
            System.out.println(this.row + "    " + this.cow);
        }
    }
}
