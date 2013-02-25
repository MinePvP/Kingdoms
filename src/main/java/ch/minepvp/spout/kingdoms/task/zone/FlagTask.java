package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import org.spout.api.geo.World;

public class FlagTask implements Runnable {

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

        int attackers = 0;
        int defenders = 0;

        if ( Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, attacker ) != null ) {
            attackers = Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, attacker ).size();
        }

        if ( Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, defender ) != null ) {
            defenders = Kingdoms.getInstance().getZoneManager().getOnlinePlayersFromKingdomNearZoneFlag( zone, defender ).size();
        }

        if ( attackers == 0 ) {
            return;
        }

        if ( attackers > defenders && lastAttackers > lastDefenders ) {

            // Update Flag
            Kingdoms.getInstance().getZoneManager().updateFlag( world, zone, flagStateOld, -1 );

            flagStateOld--;

        } else if ( attackers < defenders && lastAttackers < lastDefenders ) {

            // Update Flag
            Kingdoms.getInstance().getZoneManager().updateFlag( world, zone, flagStateOld, +1 );

            flagStateOld++;
        }

        lastAttackers = attackers;
        lastDefenders = defenders;

    }
}
