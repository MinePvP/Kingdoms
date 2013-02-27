package ch.minepvp.spout.kingdoms.command.admin;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import ch.minepvp.spout.kingdoms.manager.ZoneManager;

public class AdminZoneCommands {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;
    private ZoneManager zoneManager;


    public AdminZoneCommands( Kingdoms instance ) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
        zoneManager = plugin.getZoneManager();
    }

}
