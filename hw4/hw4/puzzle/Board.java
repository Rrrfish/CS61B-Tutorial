package temp;

import edu.princeton.cs.algs4.Queue;
import hw4.puzzle.WorldState;

import java.util.HashSet;

public class Board implements WorldState {
    private int[][] board;
    private int N;
    private HashSet<Board> mark = new HashSet<>();


    public Board(int[][] tiles)
    {
        N = tiles.length;
        board = new int[N][N];

        for(int i = 0 ; i < N ; i++)
        {
            for(int j = 0 ; j < N ; j++)
            {
                board[i][j] = tiles[i][j];
            }
        }
        mark.add(this);
    }

    public int tileAt(int i, int j)
    {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    public int size()
    {
        return N;
    }

    public Iterable<WorldState> neighbors()
    {
        int blankX = 0, blankY = 0;

        //find the blank tile
        for(int i = 0 ; i < N ; i ++)
        {
            for(int j = 0 ; j < N ; j++)
            {
                if(board[i][j] == 0)
                {
                    blankX = i;
                    blankY = j;
                    break;
                }
            }
        }

//        ArrayList<WorldState> neighbs = new ArrayList<>();

        Queue<WorldState> neighbs = new Queue<WorldState>();

        int[][] fakeBoard = new int[N][N];

        for(int i = 0 ; i < N ; i++)
        {
            for(int j = 0 ; j < N ; j++)
            {
                fakeBoard[i][j] = board[i][j];
            }
        }

        //find neighbors of the blank tile and change it to the next state
        int[] Xx = new int[]{blankX-1, blankX+1, blankX, blankX};
        int[] Yy = new int[]{blankY, blankY, blankY-1, blankY+1};

        for(int i = 0 ; i < 4 ; i++)
        {
            int x = Xx[i], y = Yy[i];
            if(x >= 0 && x < N && y >=0 && y < N)
            {
                fakeBoard[blankX][blankY] = fakeBoard[x][y];
                fakeBoard[x][y] = 0;
                Board neighbor = new Board(fakeBoard);
//                neighbs.add(neighbor);
                if(mark.contains(neighbor))
                {
                    fakeBoard[x][y] = fakeBoard[blankX][blankY];
                    fakeBoard[blankX][blankY] = 0;
                    continue;
                }

                neighbs.enqueue(neighbor);
                mark.add(neighbor);
                fakeBoard[x][y] = fakeBoard[blankX][blankY];
                fakeBoard[blankX][blankY] = 0;
            }
        }

        return neighbs;
    }

    public int hamming()
    {
        int count = 1;
        int differences = 0;
        for(int i = 0 ; i < N ; i++)
        {
            for(int j = 0 ; j < N ; j ++)
            {
                if(board[i][j] != count ++)
                {
                    differences ++;
                }
            }
        }

        return differences;
    }

    public int manhattan()
    {
        int differences = 0;
        for(int i = 0 ; i < N; i++)
        {
            for(int j = 0 ; j < N ; j ++)
            {
                if(board[i][j] == 0)
                {
                    continue;
                }

                differences += Math.abs((board[i][j]-1)/N - i) + Math.abs((board[i][j]-1)%N - j);

            }
        }

        return differences;
    }

    public int estimatedDistanceToGoal()
    {
        return manhattan();
    }

    public boolean equals(Object y)
    {
        if(! ( y instanceof Board ))
        {
            return false;
        }

        if( this == y )
        {
            return true;
        }

        Board other = (Board) y;

        if(other.size() != size()) return false;

        for(int i = 0;  i < N; i++)
        {
            for(int j = 0 ; j < N ; j++)
            {
                if(board[i][j] != other.board[i][j])
                {
                    return false;
                }
            }
        }

        return true;
    }

    /** Returns the string representation of the board.
     * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
