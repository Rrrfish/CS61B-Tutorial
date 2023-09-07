package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.HashSet;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private class vertaxComparator implements Comparator<vertax>
    {

        @Override
        public int compare(vertax o1, vertax o2) {
            return o1.priority - o2.priority;
        }
    }

    private class vertax
    {
        public int idx;
        public int priority;

        public vertax(int idx, int costSoFar)
        {
            this.idx = idx;
            this.priority = costSoFar + h(idx);
        }
    }


    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        MinPQ<vertax> fringe = new MinPQ<>(new vertaxComparator());
        int[] costSoFar = new int[maze.V()];
        int[] cameFrom = new int[maze.V()];
        costSoFar[s] = 0;

        vertax cur = new vertax(s, costSoFar[s]);
        fringe.insert(cur);

        while(!fringe.isEmpty())
        {
            cur = fringe.delMin();
            marked[cur.idx] = true;
            announce();

            if(cur.idx == t)
            {
                targetFound = true;
                return;
            }

            for( int next : maze.adj(cur.idx))
            {
                if(!marked[next])
                {
                    vertax neighbor = new vertax(next, costSoFar[cur.idx]);
                    fringe.insert(neighbor);
                    edgeTo[next] = cur.idx;
                    distTo[next] = distTo[cur.idx] + 1;
                    cameFrom[next] = cur.idx;
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

