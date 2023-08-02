package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {
    private WeightedQuickUnionUF sites;
    private boolean[] site;
    private int N;
    private int numberOfOpenSites = 0;
    //private ArrayList<Integer> OpenSitesFirstLayer;
    private int virtualTopSite;
    private ArrayList<Integer> OpenSitesLastLayer;

    public Percolation( int N )
    {
        virtualTopSite = N*N;
        sites = new WeightedQuickUnionUF(N*N + 1);
        this.N = N;
        site = new boolean[N*N];
        //OpenSitesFirstLayer = new ArrayList<>();
        OpenSitesLastLayer  = new ArrayList<>();
        for(int i = 0 ; i < N*N ; i++)
        {
            site[i] = false;
        }
    }


    public void open( int row, int col )
    {
        if( row < 0 || row > N-1 || col < 0 || col > N-1)
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int idx = xyTo1D(row, col);
        //if(idx < N) OpenSitesFirstLayer.add(idx);
        if(site[idx] == true) return;
        if(row == 0)
        {
            sites.union(virtualTopSite, idx);
        }
        if(row == N-1) OpenSitesLastLayer.add(idx);

        site[idx] = true;
        connectAdjacent(row, col);
        numberOfOpenSites ++;
    }

    private int xyTo1D(int row, int col)
    {
        return (row)*N + col;
    }

    private void connectAdjacent(int row, int col)
    {
        if(row > 0 && site[xyTo1D(row-1, col)])
        {
            sites.union(xyTo1D(row-1, col), xyTo1D(row, col));
        }
        if(row < N-1 && site[xyTo1D(row+1, col)])
        {
            sites.union(xyTo1D(row+1, col), xyTo1D(row, col));
        }
        if(col > 0 && site[xyTo1D(row, col-1)])
        {
            sites.union(xyTo1D(row, col-1), xyTo1D(row, col));
        }
        if(col < N-1 && site[xyTo1D(row, col+1)])
        {
            sites.union(xyTo1D(row, col+1), xyTo1D(row, col));
        }
    }

    public boolean isOpen(int row, int col)
    {
        return site[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col)
    {
        /*if(!OpenSitesFirstLayer.isEmpty())
        {
            int idx = xyTo1D(row, col);
            for(int i : OpenSitesFirstLayer)
            {
                if(sites.connected(i, idx))
                {
                    return true;
                }
            }
        }*/

        return sites.connected(virtualTopSite, xyTo1D(row, col));
    }

    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    public boolean percolates()
    {
        /*for(int i : OpenSitesFirstLayer)
        {
            for(int j : OpenSitesLastLayer)
            {
                if( sites.connected(i, j))
                    return true;
            }
        }*/

        for(int i : OpenSitesLastLayer)
        {
            if( sites.connected( virtualTopSite, i))
            {
                return true;
            }
        }

        return false;
    }
    public static void main(String[] args) {}
}

