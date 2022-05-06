// Kyle Orcutt
// Vangelis Batbear Bubble Challenge

import java.util.*;
import java.lang.*;

class Batbear {
    public static void main (String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int graphCount = in.nextInt();
        for (int i = 0; i < graphCount; i++) {
            boolean hasLoop = false;
            int vertexCount = in.nextInt();
            int edgeCount = in.nextInt();
            Graph g = new Graph(vertexCount, edgeCount);
            for (int k = 0; k < edgeCount; k++) {
                int n1 = in.nextInt();
                int n2 = in.nextInt();
                // test for loop
                if (n1 == n2) {
                    hasLoop = true;
                    break;
                }
                g.addEdge(n1,n2);
            }
            g.updateComponents();
            System.out.println(g);
            System.out.println(hasLoop ? "1" : g.hasLoop() ? "1" : "0");
            //System.out.println(hasLoop ? 1 : edgeCount > vertexCount-1 ? 1 : 0);
        }
    }
}

class Graph {
    private int vertexCount, edgeCount;
    private ArrayList<Map<Integer, LinkedList<Integer>>> components = new ArrayList<>();
    private Map<Integer, LinkedList<Integer>> map = new HashMap<>();
    boolean hasLoop = false;
    public Graph(int vertexCount, int edgeCount) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
    }

    private void addComponent(int[] nodes) {
        Map<Integer, LinkedList<Integer>> m = new HashMap<>();
        for (int i = 0; i < nodes.length; i++) {
            m.put(nodes[i],new LinkedList<Integer>());
        }
        components.add(m);
    }

    public void updateComponents() {
        boolean containsKeys = false;
        ArrayList<Integer> sortedKeys = new ArrayList<>();
        sortedKeys.addAll(map.keySet());
        Collections.sort(sortedKeys);
        for (Integer k : sortedKeys) {
            containsKeys = false;
            int n1 = k;
            for (Integer n2 : map.get(k)) {
                for (Map<Integer, LinkedList<Integer>> m : components) {
                    if (m.containsKey(n1) || m.containsKey(n2)) {
                        containsKeys = true;
                        if (!m.containsKey(n1))
                            m.put(n1,new LinkedList<>());
                        if (!m.containsKey(n2))
                            m.put(n2,new LinkedList<>());
                        if (!m.get(n1).contains(n2)) {
                            m.get(n1).add(n2);
                        }
                        if (!m.get(n2).contains(n1)) {
                            m.get(n2).add(n1);
                        }
                    }
                }
                if (!containsKeys) {
                    addComponent(new int[] {n1, n2});
                    components.get(components.size()-1).get(n1).add(n2);
                    components.get(components.size()-1).get(n2).add(n1);
                }
            }
        }
    }

    public void addEdge(int n1, int n2) {
        if (!map.containsKey(n1))
            map.put(n1, new LinkedList<> ());
        if (!map.containsKey(n2))
            map.put(n2, new LinkedList<> ());
        map.get(n1).add(n2);
        map.get(n2).add(n1);
    }

    public boolean hasLoop() {
        for (Map<Integer, LinkedList<Integer>> m : components) {
            int nodeCount = m.size();
            int edgeCount = 0;
            Set<Set<Integer>> s1 = new HashSet<>();
            for (Integer k : m.keySet()) {
                for (Integer n : m.get(k)) {
                    Set<Integer> ts = new HashSet<>();
                    ts.add(k);
                    ts.add(n);
                    if (!s1.contains(ts)) {
                        edgeCount++;
                        s1.add(ts);
                    }
                }
            }
            //System.out.println("nodeCount: " + nodeCount + " edgeCount: " + edgeCount);
            if (edgeCount >= nodeCount)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph: \n");
        for (Integer k : map.keySet()) {
            sb.append("  " + k + " -> ");
            for (Integer n : map.get(k)) {
                sb.append(n + ",");
            }
            sb.append("\n");
        }
        for (Map<Integer, LinkedList<Integer>> m : components) {
            sb.append("Component: \n");
            for (Integer k : m.keySet()) {
                sb.append("  Edge: " + k + " -> ");
                for (Integer n : m.get(k)) {
                    sb.append(n + ", ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}