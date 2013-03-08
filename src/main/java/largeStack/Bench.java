package largeStack;

public class Bench {

    public static long benchPush(Stack st, int nbValues) {
        long d = System.currentTimeMillis();
        for (int i = 1; i <= nbValues; i++) {
            st.push(i);
            assert checkTop(st, i);
        }
        return System.currentTimeMillis() - d;

    }

    private static boolean checkTop(Stack st, int i) {
        int x = st.peek();
        if (x != i) {
            System.err.println("Expected " + i + " but was " + x);
            return false;
        }
        return true;
    }

    public static long benchPop(Stack st, int nbValues) {
        long d = System.currentTimeMillis();
        for (int i = nbValues; i > 0; i--) {
            int x = st.pop();
            assert x == i : "Expected " + i + " but was " + x;
        }
        return System.currentTimeMillis() - d;

    }


    public static long benchStack(String id, int nbValues, int times) {
        long d1 = 0, d2 = 0;
        for (int i = 0; i < times; i++) {
            Stack st = makeStack(id);
            d1 += benchPush(st, nbValues);
            //d2 += benchPop(st, nbValues);
            st.free();
        }
        System.err.println(id + ": " + times + "x" + "(" + nbValues + ") push: " + d1 + " ms; pop:" + d2 + "ms");
        return d1+d2;
    }


    public static void main(String []args) {
		int nbValues = 200000000;
        int times = 30;

        String [] ids = new String[]{/*"I2D","U2D","UL2D", "2D"*/"1D"};
        for (String id : ids) {
            benchStack(id, nbValues, times);
		}
	}

    private static Stack makeStack(String id) {
        if (id.equals("2D"))
            return new Array2DStack(20000);
        else if (id.equals("1D")) {
            return new ArrayStack(20000);
        } else if (id.equals("I2D")) {
            return new IntBuffer2DStack(10000);
        } else if (id.equals("U2D")) {
            try {
                return new Unsafe2DStack(20000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (id.equals("UL2D")) {
            try {
                return new UnsafeLong2DStack(20000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new UnsupportedOperationException();
    }
}