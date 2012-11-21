package ch.minepvp.spout.kingdoms.manager;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Plot;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import com.alta189.simplesave.Database;
import org.spout.api.entity.Player;
import org.spout.api.geo.Protection;
import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.plugin.services.ProtectionService;

import java.util.ArrayList;
import java.util.Collection;

public class KingdomManager {

    private Kingdoms plugin;
    private Database db;

    private MemberManager memberManager;
    private PlotManager plotManager;

    private ArrayList<Kingdom> kingdoms;

    public KingdomManager() {

        plugin = Kingdoms.getInstance();
        db = plugin.getDatabase();

        memberManager = plugin.getMemberManager();
        plotManager = plugin.getPlotManager();

        kingdoms = new ArrayList<Kingdom>();

        load();

    }

    /**
     * Load all Kingdom Entries
     */
    public void load() {

        kingdoms = (ArrayList)db.select(Kingdom.class).execute().find();

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {

                for ( Member member : memberManager.getMembers() ) {

                    if ( kingdom.getName().equalsIgnoreCase( member.getKingdom() ) ) {

                        kingdom.addMember(member);

                    }

                }

            }

        }

        plugin.getLogger().info("loaded Kingdoms : " + kingdoms.size());

    }

    /**
     * Save all Kingdom Entries
     */
    public void saveAll() {

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {
                db.save(Kingdom.class, kingdom);
            }

        }

    }

    /**
     * Create a Kingdom
     *
     * @param member
     * @param name
     * @param tag
     */
    public void createKingdom( Member member, String name, String tag ) {

        Kingdom kingdom = new Kingdom();
        kingdom.setName( name );
        kingdom.setTag( tag );
        kingdom.addMember( member );
        kingdom.setLevel(1);

        db.save(Kingdom.class, kingdom);
        kingdoms.add(kingdom);

        member.setKingdom( kingdom.getId() );
    }

    /**
     * Delete the Kingdom with all his stuff
     *
     * @param kingdom
     */
    public void deleteKingdom( Kingdom kingdom ) {

        // Reset Zones
        // TODO

        // Delete Plots
        if ( plotManager.getPlotsByKingdom(kingdom).size() > 0 ) {

            for ( Plot plot : plotManager.getPlotsByKingdom(kingdom) ) {
                plotManager.deletePlot(plot);
            }

        }

        // Delete Kingdom
        kingdoms.remove(kingdom);
        db.remove(Kingdom.class, kingdom);

    }

    /**
     *
     *
     * @param player
     * @return
     */
    public Kingdom getKingdomByPlayer( Player player) {

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {

                for ( Member member : kingdom.getMembers() ) {

                    if ( member.getName().equalsIgnoreCase( player.getName() ) ) {

                        return kingdom;

                    }

                }

            }

        }

        return null;
    }

    public Kingdom getKingdomByName( String name) {

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {

                if ( kingdom.getName().equalsIgnoreCase( name ) ) {
                    return kingdom;
                }

            }

        }

        return null;
    }

    public Kingdom getKingdomByTag( String tag) {

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {

                if ( kingdom.getTag().equalsIgnoreCase( tag ) ) {
                    return kingdom;
                }

            }

        }

        return null;
    }

    /**
     *
     *
     * @return
     */
    public ArrayList<Kingdom> getAllKingdoms() {
        return kingdoms;
    }

    public ArrayList<Member> getMembersFromKingdomByRank( Kingdom kingdom, KingdomRank rank) {

        ArrayList<Member> members = new ArrayList<Member>();

        for ( Member member : kingdom.getMembers() ) {

            if ( member.getRank().equals( rank ) ) {
                members.add(member);
            }

        }

        if ( members.size() > 0 ) {
            return members;
        }

        return null;
    }

    public ArrayList<String> getMembersNameFromKingdomByRank( Kingdom kingdom, KingdomRank rank) {

        ArrayList<String> members = new ArrayList<String>();

        for ( Member member : kingdom.getMembers() ) {

            if ( member.getRank().equals( rank ) ) {
                members.add(member.getName());
            }

        }

        if ( members.size() > 0 ) {
            return members;
        }

        return null;
    }

    public Kingdom getKingdomByInvitedPlayer( Player player ) {

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {

                for ( Member member : kingdom.getInvitedMembers() ) {

                    if ( member.getName().equalsIgnoreCase( player.getName() ) ) {
                        return kingdom;
                    }

                }

            }

        }

        return null;
    }

    public Kingdom getKingdomByPoint( Point point ) {

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {

                if ( kingdom.contains(point) ) {
                     return kingdom;
                }

            }

        }

        return null;
    }

    /*
    @Override
    public Protection getProtection(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Protection> getAllProtections(World world) {

        if ( kingdoms.size() > 0 ) {
            return (Collection)kingdoms;
        }

        return null;
    }

    @Override
    public Collection<Protection> getAllProtections(Point point) {

        Collection<Protection> protections = new ArrayList<Protection>();

        if ( kingdoms.size() > 0 ) {

            for ( Kingdom kingdom : kingdoms ) {

                if ( kingdom.contains(point) ) {
                    protections.add(kingdom);
                }

            }

        }

        if ( protections.size() > 0 ) {
            return protections;
        }

        return null;
    }

    @Override
    public Collection<Protection> getAllProtections() {

        if ( kingdoms.size() > 0 ) {
            return (Collection)kingdoms;
        }

        return null;
    }
    */

}
