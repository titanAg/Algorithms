// COSC 320 Lab 4
// Kyle Orcuut

using System;
using System.Collections.Generic;

namespace L4_csa_CollectingGold_CSharp
{
    class Solution
    {
        static int n;
        static int m;
        static void Main(String[] args)
        {
            // file input for testing
            //String[] lines = System.IO.File.ReadAllLines(@"../../../input1"); // csa test case
            String[] lines = System.IO.File.ReadAllLines(@"../../../input2"); // Simple multiple path case
            //String[] lines = System.IO.File.ReadAllLines(@"../../../input3"); // Simple multiple path case - mirror swap
            String[] inLine1 = lines[0].Split(" ");

            // console input for submission
            //String[] inLine1 = Console.ReadLine().Split();

            n = Convert.ToInt32(inLine1[0]);
            m = Convert.ToInt32(inLine1[1]);

            Dictionary<long, int> indexMap = new Dictionary<long, int>();
            Dictionary<int, long> keyMap = new Dictionary<int, long>();

            long[,] graph = new long[n, n];
            long min = long.MaxValue, max = 0;

            for (int i = 0; i < n; i++)
            {
                // file input for testing
                long id = Convert.ToInt64(lines[i + 1]);

                // console input for submission
                //long id = Convert.ToInt64(Console.ReadLine());


                indexMap.Add(id, i);
                keyMap.Add(i, id);

                if (id < min)
                    min = id;
                if (id > max)
                    max = id;
            }

            for (long i = 0; i < m; i++)
            {
                // file input for testing
                String[] inLine = lines[n+1+i].Split(" ");

                // console input for submission
                //String[] inLine = Console.ReadLine().Split(" ");

                long n1 = indexMap[Convert.ToInt64(inLine[0])];
                long n2 = indexMap[Convert.ToInt64(inLine[1])];
                graph[n1, n2] = Convert.ToInt64(inLine[2]);
                graph[n2, n1] = Convert.ToInt64(inLine[2]);
                //Console.WriteLine("Prime for: " + inLine[2] + " = " + GetNodeGold(Convert.ToInt32(inLine[2])));
            }

            long[,] path = dijkstra(graph, indexMap[min], keyMap);

            Console.WriteLine(path[indexMap[max],1]);

            //Console.WriteLine();
            //for (long i = 0; i < n; i++)
            //{
            //    for (long j = 0; j < n; j++)
            //    {
            //        Console.Write(graph[i, j] + " ");
            //    }
            //    Console.WriteLine();
            //}
            //long sum = val1 + val2;
            //Console.WriteLine(sum);
        }

        public static long GetNodeGold(long n)
        {
            if (n <= 2)
                return 1;
            long gold = 2;
            long count = 1;
            for (long i = 3; i < n; i++)
            {
                if (IsPrime(i))
                {
                    gold *= i;
                    count++;
                }
                if (gold * i + 1 > n)
                    return count;
            }
            return -1;
        }

        public static bool IsPrime(long n)
        {
            if (n == 1)
                return false;
            for (long i = 2; i < n; i++)
                if (n % i == 0)
                    return false;
            return true;
        }

        //static void printSolution(long[,] dist, long n)
        //{
        //    Console.Write("Vertex     Distance "
        //                  + "from Source   Gold\n");
        //    for (long i = 0; i < n; i++)
        //        Console.Write(i + " \t\t " + dist[i,0] +"   " +dist[i,1] +"\n");
        //}


        // Code recycled from: 
        // https://www.geeksforgeeks.org/csharp-program-for-dijkstras-shortest-path-algorithm-greedy-algo-7/
        static long minDistance(long[,] dist,
                bool[] sptSet)
        {
            // Initialize min value 
            long min = long.MaxValue, min_index = -1;

            for (long v = 0; v < n; v++)
                if (sptSet[v] == false && dist[v,0] <= min)
                {
                    min = dist[v,0];
                    min_index = v;
                }

            return min_index;
        }

        // Code recycled from: 
        // https://www.geeksforgeeks.org/csharp-program-for-dijkstras-shortest-path-algorithm-greedy-algo-7/
        static long[,] dijkstra(long[,] graph, int src, Dictionary<int, long> keyMap)
        {
            long[,] dist = new long[n,2]; // The output array. dist[i] 
                                     // will hold the shortest 
                                     // distance from src to i 

            bool[] sptSet = new bool[n];

            for (int i = 0; i < n; i++)
            {
                dist[i, 0] = long.MaxValue;
                sptSet[i] = false;
            }

            dist[src, 0] = 0;
            dist[src, 1] = GetNodeGold(keyMap[src]);

            for (int count = 0; count < n - 1; count++)
            {
                long u = minDistance(dist, sptSet);

                sptSet[u] = true;

                for (int v = 0; v < n; v++)
                    if (!sptSet[v] && graph[u, v] != 0 &&
                         dist[u, 0] != long.MaxValue && dist[u, 0] + graph[u, v] < dist[v, 0])
                    {
                        dist[v, 0] = dist[u, 0] + graph[u, v];
                        dist[v, 1] = dist[u, 1] + GetNodeGold(keyMap[v]);
                    }
            }
            return dist;
        }
    }
}
