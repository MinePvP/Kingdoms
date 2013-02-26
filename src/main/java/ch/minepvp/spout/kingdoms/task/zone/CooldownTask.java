package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.database.table.Zone;
import ch.minepvp.spout.kingdoms.task.Task;

public class CooldownTask extends Task {

    private Zone zone;

    public CooldownTask( Zone zone ) {
        this.zone = zone;
    }

    @Override
    public void run() {
        zone.setAttackCooldown(false);
        setActive(false);
    }

}
