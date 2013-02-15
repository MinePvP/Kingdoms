package ch.minepvp.spout.kingdoms.manager;


import ch.minepvp.spout.kingdoms.Kingdoms;
import org.spout.api.scheduler.Task;
import org.spout.api.scheduler.TaskPriority;

import java.util.ArrayList;

public class TaskManager {

    private Kingdoms plugin;

    private ArrayList<Task> tasks;

    public TaskManager() {

        plugin = Kingdoms.getInstance();

        tasks = new ArrayList<Task>();

        //createSyncRepeatingTask( new StopInactiveTasksTask(), 1200L, 1200L, TaskPriority.MEDIUM );
    }

    public void createAsyncDelayedTask( Runnable runnable, long delay, TaskPriority priority ) {

        Task task = plugin.getEngine().getScheduler().scheduleAsyncDelayedTask(plugin, runnable, delay, priority);

        this.tasks.add(task);

    }

    public void createSyncDelayedTask( Runnable runnable, long delay, TaskPriority priority ) {

        Task task = plugin.getEngine().getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay, priority);

    }

    public void createSyncRepeatingTask( Runnable runnable, long delay, long repeating, TaskPriority priority ) {

        Task task = plugin.getEngine().getScheduler().scheduleSyncRepeatingTask(plugin, runnable, delay, repeating, priority);


    }

    public void stopInactiveTasks() {

        if ( tasks.size() > 0 ) {

            for ( Task task : this.tasks ) {

                if ( !task.isAlive() && !task.isExecuting() ) {
                    plugin.getEngine().getScheduler().cancelTasks( task );
                }

            }

        }

    }

    public void stopAllTasks() {

        if ( tasks.size() > 0 ) {
            plugin.getEngine().getScheduler().cancelAllTasks();
        }

    }

}
