package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] openSiteFractions;
    private int T = 0;

    public PercolationStats(int N, int T, PercolationFactory pf)
    {
        if( N <= 0 || T <= 0 ) throw new IllegalArgumentException();

        openSiteFractions = new double[T];

        for(int i = 0 ; i < T ; i++)
        {
            int NumOfOpenSites = 0;
            Percolation sample = pf.make(N);
            while(!sample.percolates())
            {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                sample.open(row, col);

            }
            openSiteFractions[i] = (sample.numberOfOpenSites() * 1.0) / (N * N);
            this.T ++;
        }

    }

    public double mean()
    {
        return StdStats.mean(openSiteFractions);
    }

    public double stddev()
    {
        return StdStats.stddev(openSiteFractions);
    }

    public double confidenceLow()
    {
        return mean() - (1.96 * stddev())/Math.sqrt(T);
    }

    public double confidenceHigh()
    {
        return mean() + (1.96 * stddev())/Math.sqrt(T);
    }
}
