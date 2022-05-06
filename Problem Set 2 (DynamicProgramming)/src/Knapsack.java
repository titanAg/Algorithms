import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Knapsack {
    static int[][] dp;
    static HashSet<Integer> indexes = new HashSet<>();
    public static void main(String[] args) throws FileNotFoundException {
        //Scanner in = new Scanner(System.in);
        Scanner in = new Scanner(new File("knapsack1.txt"));
        int c = in.nextInt();
        int n = in.nextInt();
        dp = new int[n+1][c+1];
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(in.nextInt(), in.nextInt());
        }
        fillKnapsack(c, items, n);
    }

    // n = number of items, c = capacity
    static void fillKnapsack(int c,  Item[] items, int n) {
        int dp[][] = new int[n+1][c+1];

        // Build table dp[][] in bottom-up manner
        for (int item = 0; item <= n; item++) {
            for (int weight = 0; weight <= c; weight++) {
                // if item/possible weight is 0
                if (item == 0 || weight == 0)
                    dp[item][weight] = 0;

                else if (items[item-1].weight <= weight) {
                    dp[item][weight] = Math.max(dp[item-1][weight], items[item-1].value + dp[item-1][weight - items[item-1].weight]);
                }
                else
                    dp[item][weight] = dp[item-1][weight]; // row above previous value
                System.out.println("DP for item " + item + " weight " + weight + " " + dp[item][weight]);
            }
        }
        int maxVal = dp[n][c];
        printKnapsack(c, items, n, dp, maxVal);
    }

    static void printKnapsack (int c, Item[] items, int n, int dp[][], int maxVal) {
        int weight = c;
        ArrayList<Integer> objects = new ArrayList<>();
        for (int item = n; item > 0 && maxVal > 0; item--) {

            if (maxVal == dp[item-1][weight])
                continue;
            else {
                objects.add(item-1);
                maxVal -= items[item-1].value;
                weight -= items[item-1].weight;
            }
        }
        Collections.sort(objects);
        for (int x : objects)
            System.out.print(x + " ");
    }
}

class Item {
    int weight;
    int value;
    public Item(int w, int v) {
        weight = w;
        value = v;
    }
    @Override
    public String toString() {
        return "Item: weight=" + weight + " value=" + value;
    }
}
