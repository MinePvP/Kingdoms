package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import ch.minepvp.spout.kingdoms.task.Task;
import org.spout.api.geo.World;

public class FlagTask extends Task {

    private Zone zone;
    private Kingdom attacker;
    private Kingdom defender;

    private Integer lastAttackers = 0;
    private Integer lastDefenders = 0;

    private Integer flagStateOld = 10;

    private World world;

    public FlagTask( Zone zone, Kingdom attacker, Kingdom defender ) {

        this.zone = zone;
        this.attacker = attacker;
        this.defender = defender;

        world = Kingdoms.getInstance().getEngine().getWorld("world");

    }


    @Override
    public void run() {

        if ( !zone.isAttack() ) {

            if ( isActive() ) {
                setActive(false);
            }

            return;
        }

        int attackers = 0;
        int defenders = 0;

        if ( Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, attacker ) != null ) {
            attackers = Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, attacker ).size();
        }

        if ( Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, defender ) != null ) {
            defenders = Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, defender ).size();
        }

        Kingdoms.getInstance().getLogger().info("Attackers : " + attackers + " Defenders :" + defenders);
        Kingdoms.getInstance().getLogger().info("LastAttackers : " + lastAttackers + " LastDefenders :" + lastDefenders);

        if ( attackers > defenders && lastAttackers > lastDefenders ) {


            Kingdoms.getInstance().getLogger().info("Flag for Attackers");


            if ( flagStateOld > 0 ) {

                // Update Flag
                Kingdoms.getInstance().getZoneManager().updateFlag( world, zone, flagStateOld, -1 );

                flagStateOld--;

            }


        } else if ( attackers < defenders && lastAttackers < lastDefenders ) {


            Kingdoms.getInstance().getLogger().info("Flag for Defenders");


            if ( flagStateOld < 10 ) {

                // Update Flag
                Kingdoms.getInstance().getZoneManager().updateFlag( world, zone, flagStateOld, +1 );

                flagStateOld++;

            }

        }

        lastAttackers = attackers;
        lastDefenders = defenders;

    }

    public boolean wasAttackSuccessfully() {

        if ( flagStateOld == 0 ) {
            return true;
        }

        return false;
    }
}
