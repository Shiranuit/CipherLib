package Cipher.Stream;

import Cipher.Util.Serialise;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class XCipher {

    private byte[] seed;
    private SecureRandom rnd;
    private long offset = 0;

    public XCipher() {
        this.rnd = new SecureRandom();
        SecureRandom gen = new SecureRandom();
        int size = gen.nextInt(256);
        this.seed = gen.generateSeed(size);
        this.rnd.setSeed(this.seed);
    }

    public XCipher(int size) {
        this.rnd = new SecureRandom();
        SecureRandom gen = new SecureRandom();
        this.seed = gen.generateSeed(size);
        this.rnd.setSeed(this.seed);
    }

    public XCipher(byte[] seed) {
        this.seed = seed;
        this.rnd = new SecureRandom();
        this.rnd.setSeed(seed);
    }

    public XCipher(byte[] seed, long offset) {
        this.seed = seed;
        this.rnd = new SecureRandom();

        this.rnd.setSeed(seed);
        this.offset = offset;
        for (int i=0; i<offset; i++) {
            rnd.nextInt(255);
        }
    }

    public void setOffset(long offset) {
        this.reset();
        for (long i=0; i<offset; i++) {
            this.rnd.nextInt(255);
        }
        this.offset = offset;
    }

    public void setSeed(byte[] seed) {
        rnd.setSeed(seed);
    }

    public void setSeed(String seed) {
        setSeed(seed, false);
    }

    public void setSeed(String seed,boolean resetOffset) {
        Serialise.Reader reader = new Serialise.Reader(seed);
        int lenght = reader.readInt();
        long offset = reader.readLong();
        byte[] data = new byte[lenght];
        for (int i=lenght-1; i>-1; i--) {
            data[i]=reader.readByte();
        }
        this.seed = data;
        if (resetOffset) {
            setOffset(offset);
        } else {
            this.reset();
        }
    }

    public String toString() {
        String txt = "";
        for (int i=0; i<this.seed.length; i++){
            txt += this.seed[i]+"|";
        }
        return txt;
    }

    public byte[] getSeedBytes() {
        return this.seed;
    }

    public String getSeed() {
        Serialise.Writer writer = new Serialise.Writer();

        for (int i=0; i<this.seed.length; i++) {
            writer.writeByte(this.seed[i]);
        }
        writer.writeLong(this.offset);
        writer.writeInt(this.seed.length);
        return writer.flush();
    }

    public void reset() {
        this.rnd = new SecureRandom();
        this.rnd.setSeed(this.seed);
        this.offset=0;
    }

    public String cipher(String text) {
        String result = "";
        for (int i=0; i<text.length(); i++) {
            char c = text.charAt(i);
            int ascii = (int)c;
            int v = rnd.nextInt(255);
            int f = ascii ^ v;
            result += (char)f;
            this.offset++;
        }
        return result;
    }

    public long getOffset() {
        return this.offset;
    }

}
