package largeStack;

public class Bench {

    public static long benchPush(Stack st, int nbValues) {
        long d = System.currentTimeMillis();
        for (int i = 1; i <= nbValues; i++) {
            st.push(i);
            assert st.peek() == i : "Expected " + i + " but was " + st.peek();
        }
        return System.currentTimeMillis() - d;

    }

    public static long benchPush(String id, int nbValues, int times) {
        long d = 0;
        for (int i = 0; i < times; i++) {
            Stack st = makeStack(id);
            d += benchPush(st, nbValues);
            st.free();
        }
        return d;
    }


    public static void main(String []args) {
		int nbValues = 200000000;
        int times = 100;

        String [] ids = new String[]{/*"I2D","U2D",*/"1D"/*, "2D"*/};
        for (String id : ids) {
            long d = benchPush(id, nbValues, times);
            System.err.println(id + ": " + times + "x" + "(" + nbValues + " pushes) in " + d + " ms");
		}
        System.err.println("end");
	}

    private static Stack makeStack(String id) {
        if (id.equals("2D"))
            return new Array2DStack(10000);
        else if (id.equals("1D")) {
            return new ArrayStack(10000);
        } else if (id.equals("I2D")) {
            return new IntBuffer2DStack(10000);
        } else if (id.equals("U2D")) {
            try {
                return new Unsafe2DStack(10000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new UnsupportedOperationException();
    }
}