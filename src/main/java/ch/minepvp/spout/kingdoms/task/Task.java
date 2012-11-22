package ch.minepvp.spout.kingdoms.task;

public class Task implements Runnable {

    private Integer pid;

    @Override
    public void run() {
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

}
