import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class PaintMe {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Integer[]> appValues = new ArrayList<Integer[]>();
        while (input.hasNext()) {
            String[] temp = input.nextLine().split(" ");
            Integer[] vals = new Integer[temp.length];
            for (int i = 0; i < temp.length; i++){
                vals[i] = Integer.parseInt(temp[i]);
            }
            if (vals[0] > 0)
                appValues.add(vals);
        }
        for (int i = 0; i < appValues.size(); i++) {
            Integer[] vals = appValues.get(i);
            double paintRequired = ((vals[1] * vals[3] * 2) + (vals[2] * vals[3] * 2) + (vals[1] * vals[2]));
            for (int k = i+1; k <= i+vals[5]; k++) {
                paintRequired -=  appValues.get(k)[0] * appValues.get(k)[1];
            }
            int answer = (int)Math.ceil((paintRequired * vals[0] / vals[4]));
            System.out.println(answer);
            i+= vals[5];
        }
    }
}