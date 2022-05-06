public class Knapsack {
              static int[][] dp;
              static int count;
              public static void main(String[] args) {
                        int[] w = {1,3,4,5}; // don't need to be in order
                        int[] v = {1,4,5,7};
                        
                        count = 0;
                        System.out.println(ks(4,7,w,v)); // returns 9 -> good!
                        System.out.println("Count is " + count);
                        
                        count = 0;
                        dp = new int[6][8];
                        System.out.println(bottomUpKS(4,7,w,v));
                        System.out.println("Count is " + count);
              }

        // n = item, c = capacity, w = weights, v = values
              public static int ks(int n, int c, int[] w, int[] v) { 
                        count++;
                        if (n == 0 || c == 0) // nothing else that can be added
                            return 0;
                        else if (w[n-1] > c) // item at n-1 can't be added
                                return 0;
                        else {
                                int temp1 = ks(n-1, c, w, v); // not using current item
                                int temp2 = v[n-1] + ks(n-1, c - w[n-1], w, v); // using current item 
                                return Math.max(temp1, temp2); // return the max of both
                        }
              }

        // not quite working - Ken to post
              public static int ksDP(int n, int c, int[] w, int[] v) { 
                        if (n > 0 && dp[n-1][c] > 0)
                                return dp[n-1][c];
                        count++;
                        if (n == 0 || c == 0) // nothing else that can be added
                                return 0;
                        else if (w[n-1] > c) // item at n-1 can't be added
                                return 0;
                        else {
                                int temp1 = ks(n-1, c, w, v); // not using current item
                                int temp2 = v[n-1] + ks(n-1, c - w[n-1], w, v); // using current item 
                                dp[n-1][c] = Math.max(temp1, temp2); // store the max of both
                                return dp[n][c]; 
                        }
              }


              public static int bottomUpKS(int n, int c, int[] w, int[] v) {
                        int[][] dpTable = new int[n+1][c+1];
                        for (int i = 1; i <= n; i++) { // i = items
                                for (int j = 1; j <= c; j++) { // j = capacity
                                        dpTable[i][j] = dpTable[i-1][j];
                                        if (j >= w[i] && dpTable[i-1][j - w[i]] + v[i] > dpTable[i][j]) {
                                                dpTable[i][j] = dpTable[i-1][j - w[i]] + v[i];
                                        }
                                        System.out.println(dpTable[i][j]);
                                }
                        }
                        return dpTable[n][c];
              }
        }