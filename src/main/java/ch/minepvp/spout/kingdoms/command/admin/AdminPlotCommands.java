package ch.minepvp.spout.kingdoms.command.admin;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import ch.minepvp.spout.kingdoms.manager.PlotManager;

public class AdminPlotCommands {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;
    private PlotManager plotManager;


    public AdminPlotCommands( Kingdoms instance ) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
        plotManager = plugin.getPlotManager();
    }

}
