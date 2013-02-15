package ch.minepvp.spout.kingdoms.task;

import ch.minepvp.spout.kingdoms.Kingdoms;

public class SaveTask implements Runnable {

    public void run() {

        Kingdoms.getInstance().getMemberManager().saveAll();
        Kingdoms.getInstance().getKingdomManager().saveAll();
        Kingdoms.getInstance().getPlotManager().saveAll();
        Kingdoms.getInstance().getZoneManager().saveAll();

    }

}
