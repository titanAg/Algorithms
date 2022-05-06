import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class PlayerV1 {
    private static Map<Integer, List<Integer>> gateMap = new HashMap<>();
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        Graph graph = new Graph(N,L,E);
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            graph.addEdge(N1, N2);
        }

        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            LinkedList<Integer> list = graph.getAdjList(EI);
            gateMap.put(EI, list);
//            LinkedList<Integer> list = new LinkedList(graph.getAdjList(EI));
            System.err.println("Gate " + (i+1) + " : " + EI);
        }

        System.err.println("Graph:\n" + graph.toString());

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            System.err.println("Skynet Agent: " + SI);

            System.err.println("Graph:\n" + graph.toString());

            boolean commandSent = false;
            // perform BFS
            Queue<Integer> queue = new LinkedList<Integer>();
            ArrayList<Integer> visited = new ArrayList<Integer>();
            queue.add(SI);
            //visited.add(SI);
            while(!queue.isEmpty() && !commandSent) {
                int current = queue.remove();
                System.err.println("At node " + current);
                List<Integer> list = graph.getAdjList(current);
                if (!list.isEmpty()) {
                    for (int i : list) {
                        if (!visited.contains(i) && !commandSent) {
                            System.err.println("i " + i);
                            queue.add(i);
                            visited.add(i);
                            if (gateMap.containsKey(i)) {
                                if (gateMap.get(i).contains(SI)) {
                                    System.out.println(i + " " + SI);
                                    System.err.println("SI Command: " + i + " " + SI);
                                    gateMap.get(i).remove(gateMap.get(i).indexOf(SI));
                                    commandSent = true;
                                    break;
                                }
                                else {
                                    for (int n : gateMap.get(i)) {
                                        System.err.println("checking gate: " + i + " -> " + n);
                                        if (n == current) {
                                            System.out.println(i + " " + n);
                                            System.err.println("N Command: " + i + " " + n);
                                            System.err.println("removing from gate " + gateMap.get(i).get(gateMap.get(i).indexOf(n)));
                                            gateMap.get(i).remove(gateMap.get(i).indexOf(n));
                                            //gateMap.get(n).remove(i);
                                            commandSent = true;
                                            break;
                                        }
                                    }

                                }
                                if (!commandSent) {
                                    System.out.println(i + " " + gateMap.get(i).get(0));
                                    System.err.println("0 Command: " + i + " " + gateMap.get(i).get(0));
                                    gateMap.get(i).remove(0);
                                    commandSent = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

class Graph2 {
    private Map<Integer, LinkedList<Integer>> map = new HashMap<>();
    private int nodeCount;
    private int edgeCount;
    private int exitCount;

    public Graph(int nodeCount, int edgeCount, int exitCount) {
        this.nodeCount = nodeCount;
        this.edgeCount = edgeCount;
        this.exitCount = exitCount;
    }

    public void addNode(Integer s)
    {
        map.put(s, new LinkedList<Integer>());
    }

    public void addEdge(Integer N1, Integer N2) {
        // Add required nodes to map
        if (!map.containsKey(N1))
            addNode(N1);
        if (!map.containsKey(N2))
            addNode(N2);

        // Add connections
        map.get(N1).add(N2);
        map.get(N2).add(N1);
    }

    public LinkedList<Integer> getAdjList(int n) {
        return map.get(n);
    }

    public void severLink(int n, int l) { map.get(n).remove(l); };

    // Prints the adjancency list of each vertex.
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (Integer v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (Integer w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }
}