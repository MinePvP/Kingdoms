package ch.minepvp.spout.kingdoms.listener;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.vanilla.component.entity.Hostile;
import org.spout.vanilla.component.entity.misc.Health;
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

        Entity entity = event.getEntity();

        if ( entity instanceof Hostile) {

            if ( entity.get(Health.class).getLastDamager() instanceof Player) {

                Player killer = (Player)entity.get(Health.class).getLastDamager();

                killer.get(KingdomsComponent.class).getMember().addMonsterKill();

                if ( killer.get(KingdomsComponent.class).getKingdom() != null ) {
                    killer.get(KingdomsComponent.class).getKingdom().addMonsterKill();
                }

            }

        }

    }

}