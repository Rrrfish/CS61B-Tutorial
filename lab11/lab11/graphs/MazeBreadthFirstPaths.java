package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> fringe = new Queue<>();

        fringe.enqueue(s);
        announce();

        while(!fringe.isEmpty())
        {
            int currentVertex = fringe.dequeue();
            marked[currentVertex] = true;
            announce();

            if( currentVertex == t)
            {
                targetFound = true;
                return;
            }
            for(int neighbor : maze.adj(currentVertex))
            {
                if(!marked[neighbor])
                {
                    fringe.enqueue(neighbor);
                    distTo[neighbor] = distTo[currentVertex] + 1;
                    edgeTo[neighbor] = currentVertex;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

