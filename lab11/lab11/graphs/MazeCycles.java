package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

import java.util.Random;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] cameFrom;


    public MazeCycles(Maze m) {
        super(m);

        cameFrom = new int[maze.V()];
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        Stack<Integer> fringe = new Stack<Integer>();

        Random rand = new Random();
        int startX = rand.nextInt(maze.N());
        int startY = rand.nextInt(maze.N());
        int s = maze.xyTo1D(startX, startY);
        fringe.push(s);

        while(!fringe.isEmpty())
        {
            int current = fringe.pop();
            marked[current] = true;
            for( int neighbor : maze.adj(current))
            {
                if( !marked[neighbor] )
                {
                    announce();
                    fringe.push(neighbor);
                    cameFrom[neighbor] = current;
                    distTo[neighbor] = distTo[current] + 1;
                    System.out.println(1);
                }
                else if( neighbor != cameFrom[current] )
                {
                    cameFrom[neighbor] = current;
                    drawCycle(neighbor);
                    distTo[neighbor] = distTo[current] + 1;
                    announce();
                    return;
                }

            }
        }

    }

    // Helper methods go here
    private void drawCycle(int v)
    {
        int cur = cameFrom[v];
        edgeTo[v] = cur;

        while(cur != v)
        {
            edgeTo[cur] = cameFrom[cur];
            cur = edgeTo[cur];
        }
    }
}

