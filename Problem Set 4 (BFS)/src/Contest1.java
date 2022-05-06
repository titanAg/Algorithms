import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Contest1 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] students = new int[n];
        for (int i = 0; i < n; i++) {
            students[i] = input.nextInt();
        }
        Arrays.sort(students);

        int last = students.length - 1;
        int minMax = students[0] + students[last];
        for (int i = 1, k = last-1; i < students.length; i++, k--) {
            if (minMax > students[i] + students[k])
                minMax = students[i] + students[k];
        }
        System.out.println(minMax);
    }
}