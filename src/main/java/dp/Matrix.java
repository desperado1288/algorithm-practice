package dp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Matrix {
    private final Logger logger = LogManager.getLogger(Matrix.class);


    // 1. 任何一个状态不互相依赖; 2. dfs的参数跟dp状态一致
    // ----------------------------------------------------------------
    // 有墙矩阵从第一行到最后一行的最长距离
    int[][][] dp;
    boolean[][] visited;
    int[] directions = {0, 1, 0, -1};
    int maxI = 0, timeComplexityCount = 0;
    // dfs with memorization f(i,j,d). d: 3 directions (0,1,2).
    // time complexity: O(3MN), space: O(3MN)
    public int longestPathInDiretion3dfs(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        maxI = 0;
        timeComplexityCount = 0;
        dp = new int[m][n][3];
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

    // visited no need because dfs with direction
    private int dfs(int[][] matrix, int i, int j, int d) {
        if (i < 0 || j < 0 || i == matrix.length || j == matrix[0].length || matrix[i][j] != 0)
            return 0;
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

    // dp solution.  each row go left and right once, then move to next row.
    // time complexity: O(5MN), space: O(MN) can be reduced to O(N)
    public int longestPathInDiretion3dp(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        timeComplexityCount = 0;
        int[][] dp = new int[m][n+2];
        int max = 0;
        // initialize first line as 1
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0)
                dp[0][i+1] = 1;
        }
        for (int i = 0; i < m; i++) {
            int[] left = Arrays.copyOf(dp[i], n + 2), right = Arrays.copyOf(dp[i], n + 2);
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0)
                    right[j+1] = Math.max(right[j+1], right[j] + 1);
                if (matrix[i][n-1-j] == 0)
                    left[n-j] = Math.max(left[n-j], left[n-j+1] + 1);
            }
            for (int j = 0; j < n; j++) {
                dp[i][j + 1] = Math.max(left[j + 1], right[j + 1]);
                if (i < m - 1 && matrix[i+1][j] == 0)
                    dp[i+1][j+1] = dp[i][j+1] + 1;
                else if (i == m - 1)
                    max = Math.max(max, dp[i][j+1]);
            }
        }
        return max;
    }
    // -----------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("ko");
        Matrix matrix = new Matrix();
        int[][] m = {{0,0,0,1,0,0}, {0,1,0,0,0,1}, {0,0,0,1,1,1}};
        System.out.println(matrix.longestPathInDiretion3dfs(m) == 8);
        System.out.println(matrix.longestPathInDiretion3dp(m) == 8);
        m = new int[][] {{0,0,0,1,0,0}, {0,1,0,0,0,1}, {1,1,1,1,1,1}};
        System.out.println(matrix.longestPathInDiretion3dfs(m) == 0);
        System.out.println(matrix.longestPathInDiretion3dp(m) == 0);
        m = new int[][] {{0,0,0,1,0,0}, {0,1,0,0,0,1}, {0,0,0,1,1,1}, {1,1,0,0,0,0}, {0,0,1,0,0,0}};
        System.out.println(matrix.longestPathInDiretion3dfs(m) == 14);
        System.out.println(matrix.longestPathInDiretion3dp(m) == 14);
    }
}
