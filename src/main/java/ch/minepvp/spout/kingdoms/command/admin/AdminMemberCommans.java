package ch.minepvp.spout.kingdoms.command.admin;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;

public class AdminMemberCommans {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;


    public AdminMemberCommans( Kingdoms instance ) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
    }

}
