package largeStack;

public class Bench {

    public static long benchPush(LongStack st, int nbValues) {
        long d = System.currentTimeMillis();
        for (int i = 1; i <= nbValues; i++) {
            st.push(i);
            assert checkTop(st, i);
        }
        return System.currentTimeMillis() - d;

    }

    private static boolean checkTop(LongStack st, int i) {
        long x = st.peek();
        if (x != i) {
            System.err.println("Expected " + i + " but was " + x);
            return false;
        }
        return true;
    }

    public static long benchPop(LongStack st, int nbValues) {
        long d = System.currentTimeMillis();
        for (int i = nbValues; i > 0; i--) {
            long x = st.pop();
            assert x == i : "Expected " + i + " but was " + x;
        }
        return System.currentTimeMillis() - d;

    }


    public static long benchStack(String id, int size, int nbValues) {
        long d1 = 0, d2 = 0;
        int tries = 30;
        for (int i = 0; i < tries; i++) {
            LongStack st = makeStack(id, size);
            d1 += benchPush(st, nbValues);
            d2 += benchPop(st, nbValues);
            st.free();
        }
        System.err.println(id + ": " + (d1/tries + d2/tries) + " ms (push:" + (d1/tries) + " ms; pop:" + (d2/tries) + "ms)");
        return d1+d2 / tries;
    }


    public static void main(String []args) {
		int nbValues = 100000000;
        int size = 5000;
        benchStack("UnsafeLinkedLongStack", size, nbValues);
	}

    private static LongStack makeStack(String id, int size) {
        if (id.equals("ArrayLongStack")) {
            return new ArrayLongStack(size);
        } else if (id.equals("UnsafeLongStack")) {
            try {
                return new UnsafeLongStack(size);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (id.equals("UnsafeLinkedVariableLongStack")) {
            try {
                return new UnsafeLinkedVariableLongStack(size);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }  else if (id.equals("UnsafeLinkedLongStack")) {
            try {
                return new UnsafeLinkedLongStack(size);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        throw new UnsupportedOperationException();
    }
}