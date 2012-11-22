package ch.minepvp.spout.kingdoms.manager;


import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.task.StopInactiveTasksTask;
import ch.minepvp.spout.kingdoms.task.Task;
import org.spout.api.scheduler.TaskPriority;

import java.util.ArrayList;

public class TaskManager {

    private Kingdoms plugin;

    private ArrayList<Task> allTasks;
    private ArrayList<Task> inactiveTasks;

    public TaskManager() {

        plugin = Kingdoms.getInstance();

        allTasks = new ArrayList<Task>();
        inactiveTasks = new ArrayList<Task>();

        createSyncRepeatingTask( new StopInactiveTasksTask(), 1200L, 1200L, TaskPriority.MEDIUM );
    }

    public void createAsyncDelayedTask( Task task, long delay, TaskPriority priority ) {

        Integer pid = plugin.getEngine().getScheduler().scheduleAsyncDelayedTask(plugin, task, delay, priority);

        task.setPid(pid);
        this.allTasks.add(task);

    }

    public void createSyncDelayedTask( Task task, long delay, TaskPriority priority ) {

        Integer pid = plugin.getEngine().getScheduler().scheduleSyncDelayedTask(plugin, task, delay, priority);

        task.setPid(pid);
        this.allTasks.add(task);

    }

    public void createSyncRepeatingTask( Task task, long delay, long repeating, TaskPriority priority ) {

        Integer pid = plugin.getEngine().getScheduler().scheduleSyncRepeatingTask(plugin, task, delay, repeating, priority);

        task.setPid(pid);
        this.allTasks.add(task);

    }

    public void setInactive( Task task ) {

        if ( task != null ) {
            this.inactiveTasks.add(task);
        }

    }

    public ArrayList<Task> getInactiveTasks() {
        return this.inactiveTasks;
    }

    public void stopInactiveTasks() {

        if ( inactiveTasks.size() > 0 ) {

            for ( Task task : this.inactiveTasks ) {
                plugin.getEngine().getScheduler().cancelTask( task.getPid() );
            }

        }

    }

    public void stopAllTasks() {

        if ( allTasks.size() > 0 ) {

            for ( Task task : this.allTasks ) {
                plugin.getEngine().getScheduler().cancelTask( task.getPid() );
            }

        }

    }



}
