package concurrency.java.optimize.tasks;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public enum JobTypeConstants {
    DEMO_ONE("demo_one","test one"),
    DEMO_TWO("demo_one","test one");

    private String dec;
    private String chinaDec;

    JobTypeConstants(String dec, String chinaDec) {
        this.dec = dec;
        this.chinaDec = chinaDec;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getChinaDec() {
        return chinaDec;
    }

    public void setChinaDec(String chinaDec) {
        this.chinaDec = chinaDec;
    }
}