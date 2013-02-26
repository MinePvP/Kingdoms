package ch.minepvp.spout.kingdoms.task;

public class Task implements Runnable {

    private Integer id;
    private Boolean active = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
