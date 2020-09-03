
package os_fifo;


public class Job {
    private int JID, ECU;

    public Job(int JID, int ECU) {
        this.JID = JID;
        this.ECU = ECU;
    }

    public int getJID() {
        return JID;
    }

    public void setJID(int JID) {
        this.JID = JID;
    }

    public int getECU() {
        return ECU;
    }

    public void setECU(int ECU) {
        this.ECU = ECU;
    }
    
    
    public String toString()
    {
        return JID + "\t" + ECU;
    }
}
