package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[][] edges = {
                {1, 3},
                {1, 4},
                {3, 5},
                {4, 6},
                {5, 6},
                {6, 7},
                {2, 3},
                {2, 5},
                {7, 8},
                {10,11},
                {11,8}
        };
        HashMap<Integer, ArrayList<Integer>> graph = builder(edges);

        System.out.println(graph);
        int res = bfs(1,8, graph);
        System.out.println(res);

        int[][] water = {
                {1, 0, 0},
                {1, 0, 1},
                {0, 0, 1},
                {1, 0, 0},
        };
        int[][] visited = new int[water.length][water[0].length];
        int res2 = 0;
        for(int i = 0; i < water.length; i++){
            for(int j = 0; j < water[0].length; j++){
                if(water[i][j] == 1 && visited[i][j] == 0){
                    if(dfs(i,j,water,visited)){
                        res2 += 1;
                    }
                }
            }
        }
        System.out.println(res2);

        System.out.println(bfsPath(1,10,graph));
        int[][] paths = new int[][]{{0,0,0,0},{0,0,0,0},{0,0,0,1}};
        System.out.println(dfsPaths(paths,0,0));
    }

    public static boolean dfs(int row, int col, int[][] water, int[][] visited){
        if(row < 0 || row == water.length){
            return false;
        }
        if(col < 0 || col == water[0].length){
            return false;
        }
        if(visited[row][col] == 1 || water[row][col] == 0){
            return false;
        }
        visited[row][col] = 1;
        dfs(row + 1, col,water,visited); //down
        dfs(row - 1, col,water,visited); // up
        dfs(row, col - 1,water,visited); // left
        dfs(row, col + 1,water,visited); // right
        return true;
    }
    // X X X
    // X X X
    // X X O
    public static int dfsPaths(int[][] matrix, int row, int col){
        if(row >= matrix.length  || col >= matrix[0].length){
            return 0;
        }
        if(matrix[row][col] == 1){
            return 1;
        }
        int down = dfsPaths(matrix, row + 1, col);
        int right = dfsPaths(matrix, row , col+ 1);
        return down + right;
    }

    public static int bfs(int start, int target, HashMap<Integer, ArrayList<Integer>> graph){
        HashSet<Integer> seen = new HashSet<>();
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{start, 0});
        seen.add(start);
        while(!q.isEmpty()){
            int [] cur = q.poll();
            ArrayList<Integer> curNodes = graph.get(cur[0]);
            for(int i = 0; i < curNodes.size(); i++){
                if(seen.contains(curNodes.get(i))){
                    continue;
                }
                if(curNodes.get(i) == target){
                    return cur[1] + 1;
                }else{
                    q.add(new int[]{curNodes.get(i), cur[1] + 1});
                    seen.add(curNodes.get(i));
                }
            }
        }
        return -1;
    }

    public static String bfsPath(int start, int target, HashMap<Integer, ArrayList<Integer>> graph){
        HashSet<String> seen = new HashSet<>();
        Queue<String[]> q = new LinkedList<>();
        q.add(new String[]{Integer.toString(start), ""});
        seen.add(Integer.toString(start));
        while(!q.isEmpty()){
            String [] cur = q.poll();
            ArrayList<Integer> curNodes = graph.get(Integer.parseInt(cur[0]));
            for(int i = 0; i < curNodes.size(); i++){
                if(seen.contains(Integer.toString(curNodes.get(i)))){
                    continue;
                }
                if(curNodes.get(i) == target){
                    return cur[1] + " " + cur[0] + " " + target;
                }else{
                    q.add(new String[]{Integer.toString(curNodes.get(i)), cur[1] + " " + cur[0]});
                    seen.add(Integer.toString(curNodes.get(i)));
                }
            }
        }
        return "-1";
    }
    public static HashMap<Integer, ArrayList<Integer>> builder(int[][] edges){
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for(int[] edge: edges){
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }
}