package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Zone;

public class FlagTask implements Runnable {

    private Zone zone;
    private Kingdom attacker;
    private Kingdom defender;

    public FlagTask( Zone zone, Kingdom attacker, Kingdom defender ) {

        this.zone = zone;
        this.attacker = attacker;
        this.defender = defender;

    }


    @Override
    public void run() {








    }
}
