// COSC 320 - Algorithms
// Kyle orcutt
// Coding Game User - titan5i1v3r

import java.util.*;
import java.io.*;
import java.math.*;

class Player {
    private static Map<Integer, List<Integer>> gateMap = new HashMap<>();
    private static Graph graph;
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        graph = new Graph(N,L,E);
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            graph.addEdge(N1, N2);
        }
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            graph.updateNodeAsGate(EI);
            LinkedList<Integer> list = graph.getAdjList(EI);
            gateMap.put(EI, list);
        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
//            System.err.println(graph);

            boolean foundTargets = false;
            for (int gate : gateMap.keySet()) {
                if (gateMap.get(gate).contains(SI)) {
                    sendCommand(gate, SI);
                    foundTargets = true;
                    break;
                }
            }

            // perform BFS
            Queue<Integer> queue = new LinkedList<Integer>();
            ArrayList<Integer> visited = new ArrayList<Integer>();
            queue.add(SI);
            visited.add(SI);
            while(!queue.isEmpty() && !foundTargets) {
                int current = queue.remove();
                System.err.println("At node " + current);
                List<Integer> list = graph.getAdjList(current);
                if (!list.isEmpty()) {
                    for (int i : list) {
                        if (!visited.contains(i)) {
                            System.err.println("i " + i);
                            if (graph.getAdjExitCount(i) > 0) {
                                queue.add(i);
                            }
                            int target = graph.getMostDangerousNode(i);
                            if (gateMap.containsKey(i) && target > -1) {
                                sendCommand(i,target);
                                foundTargets = true;
                                break;
                            }
                            visited.add(i);
                        }
                    }
                }
            }
            if (!foundTargets) {
                for (int k : gateMap.keySet()) {
                    if (gateMap.get(k).size() > 0) {
                        int target = gateMap.get(k).get(0);
                        sendCommand(k,target);
                        break;
                    }
                }
            }
        }
    }

    public static void sendCommand(int n1, int n2) {
        System.out.println(n1 + " " + n2);
        gateMap.get(n1).remove(gateMap.get(n1).indexOf(n2));
        graph.severLink(n2, n1);
    }
}

class Node {
    private int value;
    private boolean isExit;
    public Node(int value) {
        this.value = value;
    }
    public int getValue() { return value; }
    public boolean getIsExit() { return isExit; }

    public void setExit() { this.isExit = true; }
}

class Graph {
    private Map<Integer, LinkedList<Integer>> map = new HashMap<>();
    private Map<Integer, Node> nodes = new HashMap<>();
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
        nodes.put(s, new Node(s));
    }

    public void addEdge(Integer N1, Integer N2) {
        if (!map.containsKey(N1))
            addNode(N1);
        if (!map.containsKey(N2))
            addNode(N2);

        map.get(N1).add(N2);
        map.get(N2).add(N1);
    }

    public LinkedList<Integer> getAdjList(int n) {
        return map.get(n);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Graph: N=" + nodeCount + " L=" + edgeCount + " E=" + exitCount + "");
        for (Integer k : map.keySet()) {
            builder.append(k.toString() + ": ");
            for (Integer v : map.get(k)) {
                builder.append(v.toString() + " ");
            }
            builder.append("\n");
        }
        builder.append("\n");
        for (int n : nodes.keySet()) {
            builder.append("Node: " + nodes.get(n).getValue() + " isExit=" + nodes.get(n).getIsExit() + "\n");
        }
        return (builder.toString());
    }

    public void severLink(int n, int l) { map.get(n).remove(map.get(n).indexOf(l)); };

    public void updateNodeAsGate(int index) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getValue() == index) {
                nodes.get(i).setExit();
                return;
            }
        }
    }

    public int getMostDangerousNode(int key) {
        LinkedList<Integer> list = getAdjList(key);
        int max = 2;
        int target = -1;
        for (int i : list) {
            int count = getAdjExitCount(i);
            if (count >= max) {
                max = count;
                target = i;
            }
        }
        return target;
    }

    public int getAdjExitCount(int key) {
        LinkedList<Integer> list = getAdjList(key);
        int count = 0;
        for (int i : list) {
            if (nodes.get(i).getIsExit()) {
                count++;
            }
        }
        return count;
    }

}