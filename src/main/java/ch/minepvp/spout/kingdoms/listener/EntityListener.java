package ch.minepvp.spout.kingdoms.listener;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.vanilla.event.entity.VanillaEntityDeathEvent;

public class EntityListener implements Listener {

    private Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;


    public EntityListener() {

        plugin = Kingdoms.getInstance();
        memberManager = plugin.getMemberManager();
        kingdomManager = plugin.getKingdomManager();

    }

    @EventHandler
    public void onEntityDeath( VanillaEntityDeathEvent event ) {


    }

}