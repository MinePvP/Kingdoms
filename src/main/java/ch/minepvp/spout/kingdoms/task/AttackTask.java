package ch.minepvp.spout.kingdoms.task;

import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Zone;

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



        isAttacked = true;

    }

    private void stopAttack() {

        // Set Zone Task inactive



        zone.setAttacker(null);
        zone.setDefender(null);

        zone.setAttack(false);

    }

}
