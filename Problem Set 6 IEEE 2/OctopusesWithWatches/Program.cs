// COSC 340 Lab 4
// Kyle Orcutt

using System;

namespace Watches
{
    using System;
    using System.Collections.Generic;
    using System.IO;

    // Please name your class Main
    class program
    {
        static void Main(String[] args)
        {
            // for testing
            //         File test1 = new File("input1");
            //         Scanner in = new Scanner(test1);
            //  File test2 = new File("input2");
            //  Scanner in = new Scanner(test2);

            // submission
            //Scanner in = new Scanner(System.in);

            String[] nm = Console.ReadLine().Split(" ");
            int n = Convert.ToInt32(nm[0]);
            int m = Convert.ToInt32(nm[1]);

            byte[,] temp = new byte[n,m];

            for (byte i = 0; i < n; i++)
            {
                String[] c = Console.ReadLine().Split(" ");
                for (byte j = 0; j < m; j++)
                {
                    temp[i,j] = (byte)(Convert.ToByte(c[j]) % 3);
                }
            }

            // target times: 3 6 9 12 -> 1-3, 4-6, 7-9, 10-12
            // target time%3 states: 0-2
            byte answer = 0;
            for (short set = 0; set < Math.Pow(3, m); set++)
            {

                // shift temp values in m rows meaning state 0-2
                for (byte i = 0; i < n; i++)
                {
                    short shift = set;
                    for (int j = 0; j < m; j++)
                    {
                        // temp[i][j] = (temp[i][j] + shifts[j]) % 3;
                        temp[i,j] = (byte)((temp[i,j] + shift % 3) % 3);
                        shift /= 3;
                    }
                }

                // Count states that are the same
                // Equivalent states can be unanimously shifted to any value -> target 0
                int count = 0;
                for (byte i = 0; i < n; i++)
                {
                    int c0 = 0, c1 = 0, c2 = 0;
                    for (int j = 0; j < m; j++)
                    {
                        switch (temp[i,j])
                        {
                            case 0:
                                c0++;
                                break;
                            case 1:
                                c1++;
                                break;
                            case 2:
                                c2++;
                                break;
                        }

                    }
                    count += Math.Max(Math.Max(c0, c1), c2);
                }
                // update answer only if a higher state count is found
                answer = (byte)Math.Max(answer, count);
            }
            Console.WriteLine(answer);
        }
    }
}
