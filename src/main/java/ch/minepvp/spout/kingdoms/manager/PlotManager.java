package ch.minepvp.spout.kingdoms.manager;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Plot;
import com.alta189.simplesave.Database;
import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;

import java.util.ArrayList;

public class PlotManager {

    private Kingdoms plugin;
    private Database db;

    private MemberManager memberManager;
    private KingdomManager kingdomManager;

    private ArrayList<Plot> plots;

    public PlotManager() {

        plugin = Kingdoms.getInstance();
        db = plugin.getDatabase();

        memberManager = plugin.getMemberManager();
        kingdomManager = plugin.getKingdomManager();

        plots = new ArrayList<Plot>();

        load();

    }

    public void load() {

        plots = (ArrayList)db.select(Plot.class).execute().find();

        plugin.getLogger().info("loaded Plots : " + plots.size());

    }

    public void saveAll() {

        if ( plots.size() > 0 ) {

            for ( Plot plot : plots ) {
                db.save(Plot.class, plot);
            }

        }

    }

    public void createPlot( Kingdom kingdom, Point cornerOne, Point cornerTwo) {

        Plot plot = new Plot();
        plot.setKingdom( kingdom.getId() );

        // Corner One
        plot.setCornerOneX( cornerOne.getBlockX() );
        plot.setCornerOneY( cornerOne.getBlockY() );
        plot.setCornerOneZ( cornerOne.getBlockZ() );

        // Corner Two
        plot.setCornerTwoX(cornerTwo.getBlockX());
        plot.setCornerTwoY(cornerTwo.getBlockY());
        plot.setCornerTwoZ(cornerTwo.getBlockZ());

        db.save(Plot.class, plot);

        plots.add(plot);
    }

    public void deletePlot( Plot plot ) {
        plots.remove(plot);
        db.remove(Plot.class, plot);
    }

    public ArrayList<Plot> getPlotsByKingdom( Kingdom kingdom ) {

        ArrayList<Plot> plots = new ArrayList<Plot>();

        for ( Plot plot : this.plots ) {

            if ( plot.getKingdom().equalsIgnoreCase( kingdom.getName() ) ) {
                plots.add(plot);
            }

        }

        if ( plots.size() > 0 ) {
            return plots;
        }

        return null;
    }

    public Plot getPlotFromKingdomByPoint( Kingdom kingdom, Point point ) {

        if ( getPlotsByKingdom(kingdom).size() > 0 ) {

            for ( Plot plot : getPlotsByKingdom(kingdom) ) {

                if ( plot.getCornerTwoX() > point.getBlockX() && plot.getCornerOneX() < point.getBlockX()) {

                    if ( plot.getCornerTwoZ() > point.getBlockZ() && plot.getCornerTwoZ() < point.getBlockZ() ) {
                        return plot;
                    }

                }

            }

        }

        return null;
    }

    public Plot getPlotByPoint( Point point ) {

        if ( plots.size() > 0 ) {

            for ( Plot plot : plots ) {

                if ( plot.getCornerTwoX() > point.getBlockX() && plot.getCornerOneX() < point.getBlockX()) {

                    if ( plot.getCornerTwoZ() > point.getBlockZ() && plot.getCornerTwoZ() < point.getBlockZ() ) {
                        return plot;
                    }

                }

            }

        }

        return null;
    }

}
