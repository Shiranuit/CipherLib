package Cipher.Util;

import java.math.BigInteger;

public class Serialise {

    public static class Reader {
        private BigInteger data;
        private int offset=0;
        public Reader(String serialised) {
            this.data=new BigInteger(serialised,16);
        }
        public Reader(String serialised, int offset) {
            this.data=new BigInteger(serialised,16);
            this.offset=offset;
        }

        public Reader(BigInteger serialised) {
            this.data=serialised;
        }
        public Reader(BigInteger serialised, int offset) {
            this.data=serialised;
            this.offset=offset;
        }

        public BigInteger read(int bits) {
            BigInteger val = this.data.shiftRight(this.offset).and(BigInteger.valueOf(2).pow(bits).subtract(BigInteger.ONE));
            this.offset+=bits;
            return val;
        }

        public byte readByte() {
            return (byte)(read(8).intValue()-127);
        }
        public short readShort() {
            return read(16).shortValue();
        }
        public int readInt() {
            return read(32).intValue();
        }
        public long readLong() {
            return read(64).longValue();
        }
    }

    public static class Writer {
        private BigInteger data=BigInteger.ZERO;
        private int offset=0;
        public void write(BigInteger val, int bits) {
            this.offset += bits;
            this.data = this.data.shiftLeft(bits).or(val);
        }
        public String flush() {
            String txt = this.data.toString(16);
            this.data=BigInteger.ZERO;
            return txt;
        }
        public BigInteger bInt() {
            return this.data;
        }

        public int getOffset(){
            return this.offset;
        }

        public void writeByte(byte val) {
            write(BigInteger.valueOf(val+127), 8);
        }

        public void writeShort(short val) {
            write(BigInteger.valueOf(val), 16);
        }

        public void writeInt(int val) {
            write(BigInteger.valueOf(val), 32);
        }

        public void writeLong(long val) {
            write(BigInteger.valueOf(val), 64);
        }
    }
}
