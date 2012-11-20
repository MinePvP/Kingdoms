package ch.minepvp.spout.kingdoms.manager;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import com.alta189.simplesave.Database;
import org.spout.api.entity.Player;

import java.util.ArrayList;

public class KingdomManager {

    private Kingdoms plugin;
    private Database db;

    private MemberManager memberManager;

    private ArrayList<Kingdom> kingdoms;

    public KingdomManager() {

        plugin = Kingdoms.getInstance();
        db = plugin.getDatabase();

        memberManager = plugin.getMemberManager();

        kingdoms = new ArrayList<Kingdom>();

        load();

    }

    /**
     * Load all Kingdom Entries
     */
    public void load() {

        kingdoms = (ArrayList)db.select(Kingdom.class).execute().find();

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

        kingdoms.add(kingdom);
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

                for ( Member member : kingdom.getInvitetMembers() ) {

                    if ( member.getName().equalsIgnoreCase( player.getName() ) ) {
                        return kingdom;
                    }

                }

            }

        }

        return null;
    }

}
