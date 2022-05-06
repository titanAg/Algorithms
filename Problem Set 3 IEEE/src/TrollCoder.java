// Kyle Orcutt
// Troll Coder

import java.util.*;
import java.lang.*;
import java.io.*;

class TrollCoder {
    public static void main (String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = 0;

        String Q = "Q";
        for (int i = 0; i < a; i++)
            Q += " 0";
        System.out.println(Q);
        int c = in.nextInt();
        int index = -1;
        int[] shifts = new int[a];
        for (int i = 0; i < a; i++) {
            Q = "Q";
            for (int j = 0; j < a; j++) {
                if (i == j) {
                    Q += " 1";
                    index = i;
                } else
                    Q += " 0";
            }
            System.out.println(Q);
            int previous = b;
            b = in.nextInt();
            if (b > c)
                shifts[index] = 1;
        }
        Q = "A";
        for (int i = 0; i < shifts.length; i++)
            Q += shifts[i] == 0 ? " 0" : " 1";
        System.out.println(Q);
    }
}