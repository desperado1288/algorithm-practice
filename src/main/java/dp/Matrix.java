package dp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Matrix {
    private final Logger logger = LogManager.getLogger(Matrix.class);

    // 1. 任何一个状态互相不依赖; 2. dfs的参数跟dp状态一致
    int[][][] dp;
    boolean[][] visited;
    int[] directions = {0, 1, 0, -1};
    int maxI = 0, timeComplexityCount = 0;
    // 有墙矩阵从第一行到最后一行的最长距离
    public int longestPathInDiretion3dfs(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        dp = new int[m][n][3];
        visited = new boolean[m][n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0)
                for (int d = 0; d < 3; d++) {
                    max = Math.max(max, dfs(matrix, 0, i, d));
                }
        }
        logger.info("node visited count: {}; total {} nodes", timeComplexityCount, m * n);
        return maxI == m - 1 ? max : 0;
    }

    private int dfs(int[][] matrix, int i, int j, int d) {
        if (i < 0 || j < 0 || i == matrix.length || j == matrix[0].length || visited[i][j] || matrix[i][j] != 0)
            return 0;
        // visited no need because dfs with direction
//        visited[i][j] = true;
        if (dp[i][j][d] > 0)    return dp[i][j][d];
        timeComplexityCount++;
        maxI = Math.max(maxI, i);
        int res;
        if (d == 1)
            res = Math.max(dfs(matrix, i, j + 1, 1), dfs(matrix, i, j + 1, 2));
        else if (d == 0)
            res = Math.max(dfs(matrix, i, j - 1, 0), dfs(matrix, i, j - 1, 2));
        else
            res = Math.max(dfs(matrix, i + 1, j, 0), Math.max(dfs(matrix, i + 1, j, 1), dfs(matrix, i + 1, j, 2)));
        dp[i][j][d] = res + 1;
        return res + 1;
    }

    public static void main(String[] args) {
        System.out.println("ko");
        Matrix matrix = new Matrix();
        int[][] m = {{0,0,0,1,0,0}, {0,1,0,0,0,1}, {0,0,0,1,1,1}};
        System.out.println(matrix.longestPathInDiretion3dfs(m) == 8);
        m = new int[][] {{0,0,0,1,0,0}, {0,1,0,0,0,1}, {1,1,1,1,1,1}};
        System.out.println(matrix.longestPathInDiretion3dfs(m) == 0);
    }
}
