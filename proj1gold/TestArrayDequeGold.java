import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold
{
    @Test
    public void testArrayDeque()
    {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StringBuilder sb = new StringBuilder();
        int len = 0;

        for(int i = 0 ; i < 10 ; i++)
        {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.25) {
                sad.addLast(i);
                ads.addLast(i);
                //System.out.println(sad.get(0) + " " + ads.get(0));
                len++;
                sb.append("addLast(" + i + ")\n");
                assertEquals(sb.toString(), sad.get(0), ads.get(0));
                //assertEquals(sb.toString(), sad.get(0), (Integer)2);
            } else if(numberBetweenZeroAndOne < 0.5) {
                sad.addFirst(i);
                ads.addFirst(i);
                len++;
                sb.append("addFirst(" + i + ")\n");
                assertEquals(sb.toString(), sad.get(len-1), ads.get(len-1));
            } else if(numberBetweenZeroAndOne < 0.75) {
                if (ads.isEmpty()) {
                    sb.append("isEmpty()\n");
                    assertTrue(sb.toString(), sad.isEmpty());
                    continue;
                }
                Integer i1 = sad.removeFirst();
                Integer i2 = ads.removeFirst();
                len--;
                sb.append("removeFirst()\n");
                assertEquals(sb.toString(), i1, i2);
            }else {
                if (ads.isEmpty()) {
                    sb.append("isEmpty()\n");
                    assertTrue(sb.toString(), sad.isEmpty());
                    continue;
                }
                Integer i1 = sad.removeLast();
                Integer i2 = ads.removeLast();
                len--;
                sb.append("removeLast()\n");
                assertEquals(sb.toString(), i1, i2);
            }


            /*for(int k = 0 ; k < d1.size() ; k++)
            {
                assertEquals(sb.toString(), d1.get(k), d2.get(k));
            }*/
        }
    }


}
