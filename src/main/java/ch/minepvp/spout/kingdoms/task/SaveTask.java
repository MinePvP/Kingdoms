package ch.minepvp.spout.kingdoms.task;

import ch.minepvp.spout.kingdoms.Kingdoms;
import org.spout.api.geo.cuboid.Region;
import org.spout.api.scheduler.Task;

public class SaveTask implements Runnable {

    @Override
    public void run() {

        Kingdoms.getInstance().getMemberManager().saveAll();
        Kingdoms.getInstance().getKingdomManager().saveAll();
        Kingdoms.getInstance().getPlotManager().saveAll();

    }

}
