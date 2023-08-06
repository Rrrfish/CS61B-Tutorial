package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        /**根据hw的提示，会往整数溢出想，但结果不仅仅是由这个因素影响的。
         * 1. List中只有后四个数字是有效的（换言之，只有后四位影响hashCode）
         * 2. 但是对于这个havaNiceHashCodeSpread()函数，因为函数内模的是10，
         *    所以只有最后一个数字会影响最后结果是true还是false，
         * 即使后四位有三位是均匀的，去掉下面的注释会发现测试还是会失败
         */

        for (int i = 0; i < 1000; i++) {
            ArrayList<Integer> a1 = new ArrayList<>();

            int len = StdRandom.uniform(5, 10);

            for(int j = 0 ; j < len-4 ; j++)
            {
                a1.add(StdRandom.uniform(0,256));
            }
            a1.add(1);
            a1.add(2);
            a1.add(3);
/*            a1.add(StdRandom.uniform(0, 255));
            a1.add(StdRandom.uniform(0, 255));
            a1.add(StdRandom.uniform(0, 255));*/
            a1.add(4);

            deadlyList.add(new ComplexOomage(a1));
        }
        // Your code here.

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
