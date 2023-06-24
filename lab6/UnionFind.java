public class UnionFind
{
    private int[] parents;

    public UnionFind( int n )
    {
        parents = new int[n];

        for(int i = 0 ; i < n ; i++)
        {
            parents[i] = -1;
        }
    }

    public void validate(int v1)
    {
        if(v1 < 0 || v1 > parents.length)
            throw new IllegalArgumentException("not a valid index.");
    }

    public int sizeOf(int v1)
    {
        validate(v1);
        return -find(find(v1));
    }

    public int parent(int v1)
    {
        validate(v1);
        return parents[v1];
    }

    public int find(int v1)
    {
        validate(v1);

        int r = v1;
        while( parent(r) >= 0 )
        {
            r = parent(r);
        }

        int p = v1;
        while( parent(p) >= 0 )
        {
            int k = parent(p);
            parents[p] = r;
            p = k;
        }

        return r;

    }

    public boolean connected(int v1, int v2)
    {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    public void union(int v1, int v2)
    {
        validate(v1);
        validate(v2);
        int p1 = find(v1);
        int p2 = find(v2);

        if(p1 != p2)
        {
            if(sizeOf(p1) >= sizeOf(p2))
            {
                parents[p2] = p1;
            }
            else
            {
                parents[p1] = p2;
            }
        }
    }
}
