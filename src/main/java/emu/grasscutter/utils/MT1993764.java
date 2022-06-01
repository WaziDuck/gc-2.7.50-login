package emu.grasscutter.utils;

// COPY FROM: https://github.com/Luis99B/MersenneTwisterAlgorithm/blob/main/MersenneTwister.java
// NO LICENSE SO I DO NOT KNOW COPYRIGHT

public class MT1993764 {
    private static final int W = 64;
    private static final int N = 312;
    private static final int M = 156;
    private static final int R = 31;
    private static final long A = 0xB5026F5AA96619E9L;
    private static final int U = 29;
    private static final int S = 17;
    private static final int T = 37;
    private static final long D = 0x5555555555555555L;
    private static final long B = 0x71D67FFFEDA60000L;
    private static final long C = 0xFFF7EEE000000000L;
    private static final long F = 6364136223846793005L;
    private static final long LM = 0x7FFFFFFF;
    private static final long UM = 0xFFFFFFFF80000000L;

    private final long[] MT;
    private int index;

    public void setSeed(long seed) {
        this.MT[0] = seed;
        this.index = N;
        for (int i = 1; i < N; i++) {
            this.MT[i] = (F * (this.MT[i - 1] ^ (this.MT[i - 1] >> (W - 2))) + i);
        }
    }

    public MT1993764() {
        this.MT = new long[N];
        this.setSeed(114514L);
    }

    public MT1993764(long seed) {
        this.MT = new long[N];
        this.setSeed(seed);
    }

    private void twist() {
        for (int i = 0; i < N; i++) {
            long x = (this.MT[i] & UM) + (this.MT[(i + 1) % N] & LM);
            long xA = x >> 1;
            if (x % 2 != 0) {
                xA ^= A;
            }
            this.MT[i] = this.MT[(i + M) % N] ^ xA;
        }
        this.index = 0;
    }

    private long extractNumber() {
        if (this.index >= N) {
            this.twist();
        }
        long y = this.MT[this.index++];
        y ^= (y >> U) & D;
        y ^= (y << S) & B;
        y ^= (y << T) & C;
        y ^= (y >> R);
        return y;
    }

    public double random() {
        return (this.extractNumber() >> 1) / (double) Long.MAX_VALUE;
    }

    public int randInt(int x) {
        if (x <= 0) {
            return 0;
        }
        int bits, val;
        do {
            bits = (int) (this.extractNumber() >> 1);
            val = bits % x;
        } while (bits - val + (x - 1) < 0);
        return val;
    }

    public float randFloat(float x) {
        if (x <= 0.0f) {
            return 0.0f;
        }
        return (float) this.random() * x;
    }

    public double randDouble(double x) {
        if (x <= 0.0d) {
            return 0.0d;
        }
        return this.random() * x;
    }

    public int randRange(int min, int max) {
        if (min >= max) {
            return 0;
        }
        return this.randInt(max - min) + min;
    }

    public long getSeed() {
        return this.MT[0];
    }
}
