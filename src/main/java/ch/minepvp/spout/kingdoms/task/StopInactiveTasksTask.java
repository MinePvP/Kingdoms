package ch.minepvp.spout.kingdoms.task;

import ch.minepvp.spout.kingdoms.Kingdoms;

public class StopInactiveTasksTask implements Runnable {

    @Override
    public void run() {
        Kingdoms.getInstance().getTaskManager().stopInactiveTasks();
    }

}
