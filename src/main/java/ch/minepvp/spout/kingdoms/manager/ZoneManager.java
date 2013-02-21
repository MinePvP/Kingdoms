package ch.minepvp.spout.kingdoms.manager;


import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import com.alta189.simplesave.Database;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.util.OutwardIterator;
import org.spout.vanilla.material.VanillaMaterials;

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

    public Zone getZoneByName( String name ) {

        if ( zones.size() > 0 ) {

            for ( Zone zone : zones ) {

                if ( zone.getName().equalsIgnoreCase( name ) ) {
                    return zone;
                }

            }

        }

        return null;
    }

    public Zone getZoneByPoint( Point point ) {

        if ( zones.size() > 0 ) {

            for ( Zone zone : zones ) {

                if ( zone.contains(point) ) {
                    return zone;
                }

            }

        }

        return null;
    }

    public void createFlag( World world, Zone zone ) {

        // Ground
        OutwardIterator iterator = new OutwardIterator( zone.getFlagX(), zone.getFlagY(), zone.getFlagZ(), 5);

        while ( iterator.hasNext() ) {
            world.setBlockMaterial( iterator.next().getX(), zone.getFlagY() + 1, iterator.next().getZ(), VanillaMaterials.STONE, (short)0, null);
        }

        /*

        // Ground
        world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY() + 1, zone.getFlagZ() + 1, VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY() + 1, zone.getFlagZ(), VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY() + 1, zone.getFlagZ() - 1, VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() + 1, zone.getFlagZ() + 1, VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() + 1, zone.getFlagZ(), VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() + 1, zone.getFlagZ() - 1, VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 1, zone.getFlagZ() + 1, VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 1, zone.getFlagZ() - 1, VanillaMaterials.PLANK, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 1, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);

        // Pole
        for ( int i = 1; i <= 11; i++ ) {
            world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + i, zone.getFlagZ(), VanillaMaterials.FENCE_GATE, (short)0, null);
        }

        // Flag
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +10, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 2, zone.getFlagY() +10, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +9, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 2, zone.getFlagY() +9, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        */

    }

    public void resetFlag( World world, Zone zone ) {

        // TODO

    }

    public void updateFlag( World world, Zone zone, Integer difference ) {

    }


}
