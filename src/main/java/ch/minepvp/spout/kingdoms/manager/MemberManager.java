package ch.minepvp.spout.kingdoms.manager;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Member;
import com.alta189.simplesave.Database;
import org.spout.api.entity.Player;

import java.sql.Date;
import java.util.ArrayList;

public class MemberManager {

    private Kingdoms plugin;
    private Database db;

    private ArrayList<Member> members;

    public MemberManager() {

        plugin = Kingdoms.getInstance();
        db = plugin.getDatabase();

        members = new ArrayList<Member>();

        load();
    }

    /**
     * Load all Member Entries
     */
    private void load() {

        members = (ArrayList)db.select(Member.class).execute().find();

        plugin.getLogger().info("loaded Members : " + members.size());

    }

    /**
     * Save a Member Entry over Player
     *
     * @param player
     */
    public void save( Player player) {

        if ( members.size() > 0 ) {

            for ( Member member : members ) {

                if ( member.getName().equalsIgnoreCase( player.getName() ) ) {
                    db.save(Member.class, member);
                }

            }

        }

    }

    /**
     * Save all Member Entries
     */
    public void saveAll() {

        if ( members.size() > 0 ) {

            for ( Member member : members ) {
                db.save(Member.class, member);
            }

        }

    }

    /**
     * Load a Member Entry over the Player
     *
     * @param player
     * @return
     */
    public Member getMemberByPlayer( Player player ) {

        if ( members.size() > 0 ) {

            for ( Member member : members ) {

                if ( member.getName().equalsIgnoreCase( player.getName() ) ) {
                    return member;
                }

            }

        }

        return null;
    }

    public Member getMemberByName( String name ) {

        if ( members.size() > 0 ) {

            for ( Member member : members ) {

                if ( member.getName().equalsIgnoreCase( name ) ) {
                    return member;
                }

            }

        }

        return null;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Create a new Member Entry
     *
     * @param player
     * @return
     */
    public Member createMember( Player player ) {

        Member member = new Member();
        member.setName( player.getName() );
        member.setFirstLogin( new Date( System.currentTimeMillis() ) );

        members.add(member);
        save(player);

        return member;
    }

}
