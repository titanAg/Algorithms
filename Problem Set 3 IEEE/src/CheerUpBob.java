// Kyle Orcutt
// Let's Cheer Up Bob

import java.util.*;
import java.lang.*;
import java.io.*;

class CheerUpBob {
    public static void main (String[] args) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        int[] rows = new int[9];
        int[] cols = new int[9];

        for (int i = 0; i < 8; i++) {
            rows[i] = in.nextInt()-1;
            cols[i] = in.nextInt()-1;
        }

        Queue<State> q = new LinkedList<State>();
        State start = new State();
        q.add(start);

        while (!q.isEmpty()) {
            State current = q.remove();
            if (current.hasWon() == 1) {
                for (Integer[] m : current.getMyMoves()) {
                    System.out.println(m[0]+1 + " " + (m[1]+1));
                }
                break;
            }
            else if (current.hasWon() == 0) {
                int lastMove = current.getMoveCount();
                if (current.isBobsTurn()) {
                    for (int i = lastMove; i < 9; i++) {
                        int row = rows[i];
                        int col = cols[i];
                        if (current.getBoard()[row][col] == '.') {
                            current.getBoard()[row][col] = 'x';
                            current.setMoves(i+1);
                            current.setBobsTurn(false);
                            q.add(current);
                            break;
                        }
                    }
                }
                else {
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (current.getBoard()[i][j] == '.') {
                                State next = new State();
                                next.setMyMoves(current.getMyMoves());
                                next.setBoard(current.getBoard());
                                next.getBoard()[i][j] = 'o';
                                next.setBobsTurn(true);
                                next.addMove(new Integer[] {i,j});
                                q.add(next);
                            }
                        }
                    }
                }
            }

        }

    }


}

class State {
    char[][] board = new char[3][3];
    int moveCount;
    boolean bobsTurn;
    ArrayList<Integer[]> myMoves = new ArrayList<>();

    public State() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '.';
            }
        }
        moveCount = 0;
        bobsTurn = true;
    }

    public boolean isBobsTurn() { return bobsTurn; }
    public void setBobsTurn(boolean isTurn) { bobsTurn = isTurn; }

    public int getMoveCount() { return moveCount; }
    public void setMoves(int count) { moveCount = count; }

    public char[][] getBoard() { return board; }
    public void setBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public ArrayList<Integer[]> getMyMoves() { return new ArrayList<>(myMoves); }
    public void setMyMoves(ArrayList<Integer[]> moves) {
        for (Integer[] m : moves) {
            this.myMoves.add(m);
        }
    }
    public void addMove(Integer[] m) { myMoves.add(m); }

    // -1 me 0 nobody 1 bob
    public int hasWon() {
        for (int i = 0; i < 3; i++) {
            //check rows
            int xCount = 0;
            int oCount = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'x') xCount++;
                if (board[i][j] == 'o') oCount++;
            }
            if (xCount == 3) return 1;
            if (oCount == 3) return -1;

            // check cols
            xCount = 0;
            oCount = 0;
            for (int j = 0; j < 3; j++) {
                if (board[j][i] == 'x') xCount++;
                if (board[j][i] == 'o') oCount++;
            }
            if (xCount == 3) return 1;
            if (oCount == 3) return -1;
        }

        //check 1st diagonal
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == 'x') xCount++;
            if (board[i][i] == 'o') oCount++;
        }
        if (xCount == 3) return 1;
        if (oCount == 3) return -1;

        //check 2nd diagonal
        xCount = 0;
        oCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][2-i] == 'x') xCount++;
            if (board[i][2-i] == 'o') oCount++;
        }
        if (xCount == 3) return 1;
        if (oCount == 3) return -1;

        return 0;
    }
}