package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private double Mu;
    private double sigma;
    private int[] NumSOfOpenSites;
    private double squareDeviationOfSamples = 0;
    private int SumNumOfOpenSites = 0;
    private int T = 0;

    public PercolationStats(int N, int T, PercolationFactory pf)
    {
        if( N <= 0 || T <= 0 ) throw new IllegalArgumentException();

        NumSOfOpenSites = new int[T];

        for(int i = 0 ; i < T ; i++)
        {
            int NumOfOpenSites = 0;
            Percolation sample = pf.make(N);
            while(!sample.percolates())
            {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                sample.open(row, col);

                NumOfOpenSites ++;
                SumNumOfOpenSites ++;
            }
            NumSOfOpenSites[i] = NumOfOpenSites;
            this.T ++;
        }

    }

    public double mean()
    {
        Mu = SumNumOfOpenSites*1.0 / T;

        return Mu;
    }

    public double stddev()
    {
        for(int i = 0 ; i < T ; i++)
        {
            squareDeviationOfSamples += (NumSOfOpenSites[i] - Mu)*(NumSOfOpenSites[i] - Mu);
        }

        sigma = Math.sqrt(squareDeviationOfSamples / (T-1));

        return sigma;
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
