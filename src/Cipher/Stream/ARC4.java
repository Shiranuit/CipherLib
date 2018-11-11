package Cipher.Stream;

public class ARC4 {
    private int x=0;
    private int y=0;
    private int[] state=new int[256];
    private String key="";
    public ARC4(String key) throws Exception {
        if (key.length() < 1) {
            throw new Exception("Key length must be greater than 0");
        } else {
            this.key=key;
            for (int i=0; i<256; i++) {
                this.state[i]=i;
            }
            for (short i=0; i<256; i++) {
                this.x = (this.key.charAt(i % this.key.length()) + this.state[i] + this.x)&0xFF;
                int temp = this.state[i];
                this.state[i] = this.state[this.x];
                this.state[this.x]=temp;
            }
            this.x=0;
        }
    }

    public String cipher(String input) {
        String output = "";
        for (int i=0; i<input.length(); i++) {
            this.x = (this.x + 1) & 0xFF;
            this.y = (this.state[this.x]+this.y) & 0xFF;
            int temp = this.state[this.x];
            this.state[this.x]=this.state[this.y];
            this.state[this.y]=temp;
            char c = (char)(input.charAt(i)^this.state[(this.state[this.x] + this.state[this.y])&0xFF]);
            output+=String.valueOf(c);
        }
        return output;
    }

    public void reset(){
        this.x=0;
        this.y=0;
        for (int i=0; i<256; i++) {
            this.state[i]=i;
        }
        for (short i=0; i<256; i++) {
            this.x = (this.key.charAt(i % this.key.length()) + this.state[i] + this.x)&0xFF;
            int temp = this.state[i];
            this.state[i] = this.state[this.x];
            this.state[this.x]=temp;
        }
        this.x=0;
    }
}
