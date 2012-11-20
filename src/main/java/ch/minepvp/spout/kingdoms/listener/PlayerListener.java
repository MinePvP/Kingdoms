package ch.minepvp.spout.kingdoms.listener;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.component.SelectionComponent;
import ch.minepvp.spout.kingdoms.config.KingdomsConfig;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.entity.KingdomChannel;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.channel.ChatChannel;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.player.PlayerChatEvent;
import org.spout.api.event.player.PlayerInteractEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.event.player.PlayerLeaveEvent;
import org.spout.api.lang.Translation;
import org.spout.vanilla.component.living.passive.Human;
import org.spout.vanilla.material.VanillaMaterials;

public class PlayerListener implements Listener {

    private Kingdoms plugin;
    private MemberManager memberManager;


    public PlayerListener() {

        plugin = Kingdoms.getInstance();
        memberManager = plugin.getMemberManager();

    }

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {

        Player player = event.getPlayer();
        Member member = memberManager.getMemberByPlayer(player);

        if ( member == null ) {
            memberManager.createMember(player);
        }

    }

    @EventHandler
    public void onPlayerQuitEvent( PlayerLeaveEvent event ) {

        memberManager.save(event.getPlayer());

    }

    @EventHandler
    public void onPLayerChatEvent( PlayerChatEvent event) {

        Player player = event.getPlayer();
        Member member = player.add(KingdomsComponent.class).getMember();
        Kingdom kingdom = player.add(KingdomsComponent.class).getKingdom();

        if ( member.getChannel().equals( KingdomChannel.LOCAL ) ) {

            for ( Player toPlayer : plugin.getServer().getOnlinePlayers() ) {

                if ( player.getTransform().getPosition().getDistance( toPlayer.getTransform().getPosition() ) <= KingdomsConfig.KINGDOMS_CHAT_LOCAL_MAX_DISTANCE.getInt()) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString("{{BLUE}}[{{YELLOW}}LOCAL{{BLUE}}] {{WHITE}}" + player.getName() + " {{GOLD}}: {{GOLD}}" + event.getMessage().getPlainString()) );
                }

            }

        } else if ( member.getChannel().equals( KingdomChannel.KINGDOM ) ) {



        } else if ( member.getChannel().equals( KingdomChannel.GLOBAL ) ) {

            for ( Player toPlayer : plugin.getServer().getOnlinePlayers() ) {

               toPlayer.sendMessage( ChatArguments.fromFormatString("{{BLUE}}[{{DARK_GREEN}}GLOBAL{{BLUE}}] {{WHITE}}" + player.getName() + " {{GOLD}}: {{WHITE}}" + event.getMessage().getPlainString()) );

            }

        } else if ( member.getChannel().equals( KingdomChannel.STAFF ) ) {

            for ( Player toPlayer : plugin.getServer().getOnlinePlayers() ) {

                if ( toPlayer.hasPermission("kingdoms.chat.staff") ) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString("{{BLUE}}[{{GOLD}}STAFF{{BLUE}}] {{WHITE}}" + player.getName() + " {{GOLD}}: {{RED}}" + event.getMessage().getPlainString()) );
                }

            }

        }

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteractEvent( PlayerInteractEvent event) {

        Player player = event.getPlayer();

        /*  TODO add it again
        if ( player.hasPermission("kingdoms.zones.selection") ) {

            if ( event.getHeldItem().isMaterial(VanillaMaterials.WOODEN_AXE) ) {

                switch (event.getAction()) {
                    case LEFT_CLICK:
                        player.add(SelectionComponent.class).getSelection().setPoint1( event.getInteractedPoint() );
                        player.sendMessage(ChatArguments.fromFormatString(Translation.tr("Point 1 selected!", player)) );
                    case RIGHT_CLICK:
                        player.add(SelectionComponent.class).getSelection().setPoint2(event.getInteractedPoint());
                        player.sendMessage(ChatArguments.fromFormatString(Translation.tr("Point 2 selected!", player) ) );
                }

            }

        }
        */

    }


}
