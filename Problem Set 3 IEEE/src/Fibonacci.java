// Kyle Orcutt
// Fibonacci

import java.util.*;
        import java.lang.*;
        import java.io.*;

class Fibonacci {
    public static void main (String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            int m = in.nextInt();
            System.out.println(fib(m+1)%10);
        }
    }
    public static long fib(int n) {
        n = n % 60;
        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5)/2;
        return (long)((Math.pow(phi, n) - Math.pow(-phi, -n)) / (2 * phi - 1));
    }
}
