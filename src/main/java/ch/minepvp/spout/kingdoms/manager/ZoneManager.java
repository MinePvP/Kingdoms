package ch.minepvp.spout.kingdoms.manager;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import com.alta189.simplesave.Database;
import org.spout.api.entity.Player;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;
import org.spout.api.util.FlatIterator;
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

    public ArrayList<Player> getOnlinePlayersInZone( Zone zone ) {

        ArrayList<Player> players = new ArrayList<Player>();

        if ( plugin.getServer().getOnlinePlayers() != null ) {

            for ( Player player : plugin.getServer().getOnlinePlayers() ) {

                if ( zone.contains( player.getScene().getPosition() ) ) {
                    players.add(player);
                }

            }

        }

        if ( players.size() > 0 ) {
            return players;
        }

        return null;
    }

    public ArrayList<Player> getOnlinePlayersFromKingdomNearZoneFlag( Zone zone, Kingdom kingdom ) {

        ArrayList<Player> players = new ArrayList<Player>();

        if ( plugin.getServer().getOnlinePlayers() != null ) {

            for ( Player player : plugin.getServer().getOnlinePlayers() ) {

                if ( player.get(KingdomsComponent.class).getKingdom() != null ) {

                    if ( player.get(KingdomsComponent.class).getKingdom().equals( kingdom ) ) {

                        if ( zone.isNearFlag( player.getScene().getPosition() ) ) {
                            players.add(player);
                        }

                    }

                }

            }

        }

        if ( players.size() > 0 ) {
            return players;
        }

        return null;
    }

    public void createFlag( World world, Zone zone ) {

        // Ground
        createGround(world, zone.getFlagX() - 4, zone.getFlagX() + 4, zone.getFlagY(), zone.getFlagY(), zone.getFlagZ() - 4, zone.getFlagZ() + 4, VanillaMaterials.STONE);
        createGround(world, zone.getFlagX() - 2, zone.getFlagX() + 2, zone.getFlagY() + 1, zone.getFlagY() + 1, zone.getFlagZ() - 2, zone.getFlagZ() + 2, VanillaMaterials.SLAB);
        createGround(world, zone.getFlagX() - 1, zone.getFlagX() + 1, zone.getFlagY() + 1, zone.getFlagY() + 1, zone.getFlagZ() - 1, zone.getFlagZ() + 1, VanillaMaterials.STONE);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() + 1, zone.getFlagZ(), VanillaMaterials.GLOWSTONE_BLOCK, (short)0, null);

        // Pole
        for ( int i = 1; i <= 12; i++ ) {
            world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY() + 1 + i, zone.getFlagZ(), VanillaMaterials.WOODEN_FENCE, (short)0, null);
        }

        // Flag
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() +13, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() +12, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +13, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +12, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);

    }

    public void resetFlag( World world, Zone zone ) {

        // Ground
        createGround(world, zone.getFlagX() - 4, zone.getFlagX() + 4, zone.getFlagY(), zone.getFlagY(), zone.getFlagZ() - 4, zone.getFlagZ() + 4, VanillaMaterials.AIR);
        createGround(world, zone.getFlagX() - 2, zone.getFlagX() + 2, zone.getFlagY() + 1, zone.getFlagY() + 1, zone.getFlagZ() - 2, zone.getFlagZ() + 2, VanillaMaterials.AIR);
        createGround(world, zone.getFlagX() - 1, zone.getFlagX() + 1, zone.getFlagY() + 1, zone.getFlagY() + 1, zone.getFlagZ() - 1, zone.getFlagZ() + 1, VanillaMaterials.AIR);

        // Pole
        for ( int i = 1; i <= 12; i++ ) {
            world.setBlockMaterial( zone.getFlagX() -1, zone.getFlagY() + 1 + i, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
        }

        // Flag
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() +13, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), zone.getFlagY() +12, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +13, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, zone.getFlagY() +12, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);

    }

    public void updateFlag( World world, Zone zone, Integer old, Integer difference ) {

        world.setBlockMaterial( zone.getFlagX(), 2 + old + difference, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), 1 + old + difference, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, 2 + old + difference, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, 1 + old + difference, zone.getFlagZ(), VanillaMaterials.AIR, (short)0, null);

        world.setBlockMaterial( zone.getFlagX(), old + difference, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX(), old + difference, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, old + difference, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);
        world.setBlockMaterial( zone.getFlagX() + 1, old + difference, zone.getFlagZ(), VanillaMaterials.WOOL, (short)0, null);

    }

    private void createGround( World world, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, BlockMaterial material ) {

        for ( int x = xMin; x <=  xMax; x++ ) {

            for ( int z = zMin; z <=  zMax; z++ ) {

                for ( int y = yMin; y <=  yMax; y++ ) {

                    world.setBlockMaterial( x, y, z, material, (short)0, null);

                }

            }

        }

    }


}
