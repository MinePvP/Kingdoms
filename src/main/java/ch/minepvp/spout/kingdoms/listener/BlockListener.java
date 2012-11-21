package ch.minepvp.spout.kingdoms.listener;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import com.alta189.simplesave.Database;
import org.spout.api.entity.Player;
import org.spout.api.event.Cause;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.vanilla.event.cause.PlayerBreakCause;
import org.spout.vanilla.event.cause.PlayerPlacementCause;

public class BlockListener implements Listener {

    private Kingdoms plugin;
    private Database db;


    public BlockListener() {

        plugin = Kingdoms.getInstance();
        db = plugin.getDatabase();

    }

    @EventHandler (order = Order.EARLIEST)
    public void onBlockChangeEvent( BlockChangeEvent event ) {

        Player player;

        // Stats
        if ( event.getCause() instanceof PlayerBreakCause ) {

            player = ((PlayerBreakCause) event.getCause()).getSource();




        } else if ( event.getCause() instanceof PlayerPlacementCause ) {

            player = ((PlayerPlacementCause) event.getCause()).getSource();



        }

    }

    @EventHandler (order = Order.MONITOR)
    public void onBlockChangeEventStats( BlockChangeEvent event ) {

        if ( event.isCancelled() ) {
            return;
        }

        Player player;

        // Stats
        if ( event.getCause() instanceof PlayerBreakCause ) {

            player = ((PlayerBreakCause) event.getCause()).getSource();
            player.add(KingdomsComponent.class).getMember().addBlockBreak();

            if ( player.add(KingdomsComponent.class).getKingdom() != null ) {
                player.add(KingdomsComponent.class).getKingdom().addBlockBreak();
            }

        } else if ( event.getCause() instanceof PlayerPlacementCause ) {

            player = ((PlayerPlacementCause) event.getCause()).getSource();
            player.add(KingdomsComponent.class).getMember().addBlockPlace();

            if ( player.add(KingdomsComponent.class).getKingdom() != null ) {
                player.add(KingdomsComponent.class).getKingdom().addBlockPlace();
            }

        }

    }

}
