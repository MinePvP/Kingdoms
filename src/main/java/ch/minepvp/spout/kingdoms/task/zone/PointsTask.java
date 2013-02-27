package ch.minepvp.spout.kingdoms.task.zone;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import ch.minepvp.spout.kingdoms.task.Task;

import java.util.ArrayList;

public class PointsTask extends Task {

    @Override
    public void run() {

        ArrayList<Zone> zones = Kingdoms.getInstance().getZoneManager().getAllZones();

        if ( zones != null ) {

            for ( Zone zone : zones ) {

                if ( zone.isActive() ) {

                    Kingdom kingdom = Kingdoms.getInstance().getKingdomManager().getKingdomByName( zone.getKingdom() );

                    if ( kingdom != null ) {

                        kingdom.addPoints( zone.getPoints() );

                    }

                }

            }

        }

    }

}
