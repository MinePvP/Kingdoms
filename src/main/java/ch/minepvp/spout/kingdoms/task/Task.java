package ch.minepvp.spout.kingdoms.task;

public class Task implements Runnable {

    private int pid;
    private boolean inactive = false;

    @Override
    public void run() {
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

}
