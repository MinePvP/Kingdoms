package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.database.table.Zone;

public class CooldownTask implements Runnable {

    private Zone zone;

    public CooldownTask( Zone zone ) {
        this.zone = zone;
    }

    @Override
    public void run() {
        zone.setAttackCooldown(false);
    }

}
