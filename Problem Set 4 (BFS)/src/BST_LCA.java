import java.util.*;
import java.io.*;

class Node {
    Node left;
    Node right;
    int data;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

public class BST_LCA {

    // Help from https://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/
// Still couldn't get the one case working
    public static void printPath(ArrayList<Integer> path) {
        for (int i = 0; i < path.size(); i++)
            System.out.print(path.get(i) + " ");
        System.out.println();
    }

    public static Node lca(Node root, int v1, int v2) {
        if (root == null)
            return null;

        // LCA left
        if (root.data > v1 && root.data > v2)
            return lca(root.left, v1, v2);

        // LCA right
        if (root.data < v1 && root.data < v2)
            return lca(root.right, v1, v2);

        return root;
    }

    public static boolean hasPath(Node root, int v, ArrayList<Integer> path) {
        if (root == null || root.data == v)
            return true;

        path.add(root.data);
        if (root.left != null && hasPath(root.left, v, path))
            return true;

        if (root.right != null && hasPath(root.right, v, path))
            return true;

        path.remove(path.size()-1);
        return false;
    }

    public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        int v1 = scan.nextInt();
        int v2 = scan.nextInt();
        scan.close();
        Node ans = lca(root,v1,v2);
        System.out.println(ans.data);
    }
}

