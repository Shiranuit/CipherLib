import Cipher.Stream.ARC4;

public class Main {
    public static void main(String[] args) throws Exception{
        ARC4 rc4 = new ARC4("abc");
        String a = "bonjour";
        String b = rc4.cipher(a);
        System.out.println(a);
        System.out.println(b);
        rc4.reset();
        System.out.println(rc4.cipher(b));

    }
}
