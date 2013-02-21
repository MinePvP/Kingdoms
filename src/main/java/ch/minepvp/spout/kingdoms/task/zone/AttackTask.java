package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import org.spout.api.scheduler.TaskPriority;

public class AttackTask implements Runnable {

    private Zone zone;
    private Kingdom attacker;
    private Kingdom defender;

    private boolean isAttacked = false;

    public AttackTask( Zone zone, Kingdom attacker, Kingdom defender ) {

        this.zone = zone;
        this.attacker = attacker;
        this.defender = defender;


    }


    @Override
    public void run() {

        if ( isAttacked ) {
            stopAttack();
        } else {
            startAttack();
        }


    }

    private void startAttack() {

        zone.setAttacker(attacker);
        zone.setDefender(defender);

        zone.setAttack(true);

        // Start Zone Task

        Runnable task = new FlagTask(zone, attacker, defender);
        Kingdoms.getInstance().getTaskManager().createSyncRepeatingTask(task, 0L, 40L, TaskPriority.HIGH);

        isAttacked = true;

    }

    private void stopAttack() {

        // Start Cooldown Task
        zone.setAttackCooldown(true);

        Long delay = (zone.getCooldown() * 20L) * 60L;

        Runnable task = new CooldownTask(zone);
        Kingdoms.getInstance().getTaskManager().createAsyncDelayedTask(task, delay, TaskPriority.LOW);

        zone.setAttacker(null);
        zone.setDefender(null);

        zone.setAttack(false);

    }

}
