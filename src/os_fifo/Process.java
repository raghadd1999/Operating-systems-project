package os_fifo;

public class Process {

    private int pid;
    private int cut = 0;
    private int ecu;

    public static enum STATE {
        NEW, READY, WAITING, TERMINATED, RUNNING
    }

    public Process(Job job) {
        pid = job.getJID();
        ecu = job.getECU();
    }

    public void increateCUT() {
        cut++;
    }

    public int getEcu() {
        return ecu;
    }

    public void setEcu(int ecu) {
        this.ecu = ecu;
    }

    private STATE state = STATE.NEW;

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCut() {
        return cut;
    }

    public void setCut(int cut) {
        this.cut = cut;
    }

    @Override
    public String toString() {
        return "Process{" + "pid=" + pid + ", cut=" + cut + ", ecu=" + ecu + ", state=" + state + '}';
    }

}
