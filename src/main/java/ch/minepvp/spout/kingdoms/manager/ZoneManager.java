package ch.minepvp.spout.kingdoms.manager;


import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import com.alta189.simplesave.Database;
import org.spout.api.geo.discrete.Point;

import java.util.ArrayList;

public class ZoneManager {

    private Kingdoms plugin;
    private Database db;
    private KingdomManager kingdomManager;

    private ArrayList<Zone> zones;


    public ZoneManager() {

        plugin = Kingdoms.getInstance();
        db = plugin.getDatabase();
        kingdomManager = plugin.getKingdomManager();

        zones = new ArrayList<Zone>();

        load();
    }

    public void load() {

        zones = (ArrayList)db.select(Zone.class).execute().find();

        plugin.getLogger().info("loaded Zones : " + zones.size());
    }

    public void saveAll() {

        if ( zones.size() > 0 ) {

            for ( Zone plot : zones ) {
                db.save(Zone.class, plot);
            }

        }

    }

    public ArrayList<Zone> getAllZones() {
        return zones;
    }

    public void createZone( String name, Point cornerOne, Point cornerTwo ) {

        Zone zone = new Zone();
        zone.setName(name);

        // Corner One
        zone.setCornerOneX( cornerOne.getBlockX() );
        zone.setCornerOneY( cornerOne.getBlockY() );
        zone.setCornerOneZ( cornerOne.getBlockZ() );

        // Corner Two
        zone.setCornerTwoX( cornerTwo.getBlockX() );
        zone.setCornerTwoY( cornerTwo.getBlockY() );
        zone.setCornerTwoZ( cornerTwo.getBlockZ() );

        db.save(Zone.class, zone);
        zones.add(zone);

    }

    public void deleteZone( Zone zone ) {
        zones.remove(zone);
        db.remove(Zone.class, zone);
    }



}
