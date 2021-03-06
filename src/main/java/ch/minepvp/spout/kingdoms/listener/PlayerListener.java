package ch.minepvp.spout.kingdoms.listener;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.component.SelectionComponent;
import ch.minepvp.spout.kingdoms.config.KingdomsConfig;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Plot;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import ch.minepvp.spout.kingdoms.entity.KingdomChannel;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import ch.minepvp.spout.kingdoms.manager.PlotManager;
import ch.minepvp.spout.kingdoms.manager.ZoneManager;
import org.spout.api.chat.ChatArguments;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerChatEvent;
import org.spout.api.event.player.PlayerInteractEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.event.player.PlayerLeaveEvent;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.inventory.Inventory;
import org.spout.api.lang.Translation;
import org.spout.vanilla.component.entity.living.Hostile;
import org.spout.vanilla.component.entity.living.Human;
import org.spout.vanilla.component.entity.misc.Health;
import org.spout.vanilla.event.player.PlayerBucketEvent;
import org.spout.vanilla.event.player.PlayerDeathEvent;
import org.spout.vanilla.event.player.PlayerRespawnEvent;
import org.spout.vanilla.inventory.entity.ArmorInventory;
import org.spout.vanilla.inventory.entity.QuickbarInventory;
import org.spout.vanilla.material.VanillaMaterials;

import java.sql.Date;

public class PlayerListener implements Listener {

    private Kingdoms plugin;
    private MemberManager memberManager;
    private KingdomManager kingdomManager;
    private PlotManager plotManager;
    private ZoneManager zoneManager;


    public PlayerListener() {

        plugin = Kingdoms.getInstance();
        memberManager = plugin.getMemberManager();
        kingdomManager = plugin.getKingdomManager();
        plotManager = plugin.getPlotManager();
        zoneManager = plugin.getZoneManager();

    }

    @EventHandler(order = Order.MONITOR)
    public void onPlayerJoin( PlayerJoinEvent event ) {

        Player player = event.getPlayer();
        Member member = memberManager.getMemberByPlayer(player);

        if ( member == null ) {
            memberManager.createMember(player);
        }

        player.add(KingdomsComponent.class);
        player.get(KingdomsComponent.class).getMember().setOnline(true);
        player.get(KingdomsComponent.class).getMember().setLastLogin( new Date(System.currentTimeMillis() ) );

        player.add(SelectionComponent.class);

        memberManager.save(player);
    }

    @EventHandler (order = Order.MONITOR)
    public void onPlayerQuitEvent( PlayerLeaveEvent event ) {

        if ( event.getPlayer().get(KingdomsComponent.class) == null ) {
            return;
        }

        event.getPlayer().get(KingdomsComponent.class).getMember().setOnline(false);
        event.getPlayer().get(KingdomsComponent.class).getMember().setLastLogout( new Date(System.currentTimeMillis()) );
        memberManager.save(event.getPlayer());
    }

    @EventHandler (order = Order.MONITOR)
    public void onPLayerChatEvent( PlayerChatEvent event) {

        Player player = event.getPlayer();
        Member member = player.get(KingdomsComponent.class).getMember();
        Kingdom kingdom = player.get(KingdomsComponent.class).getKingdom();

        String playerPrefix = "";

        if ( !player.getData("prefix").getString().isEmpty() ) {
            playerPrefix = player.getData("prefix").getString();
        }

        String kingdomPrefix = "";

        if ( kingdom != null ) {
            kingdomPrefix = " {{BLUE}}[{{WHITE}}" + kingdom.getTag() + "{{BLUE}}]";
        }

        if ( member.getChannel().equals( KingdomChannel.LOCAL ) ) {

            for ( Player toPlayer : plugin.getServer().getOnlinePlayers() ) {

                if ( player.getScene().getPosition().getDistance( toPlayer.getScene().getTransform().getPosition() ) <= KingdomsConfig.CHAT_LOCAL_MAX_DISTANCE.getInt()) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString("{{BLUE}}[" + KingdomsConfig.CHAT_LOCAL_PREFIX.getString() + "{{BLUE}}]" + kingdomPrefix + "{{WHITE}}" + playerPrefix + ".{{WHITE}}" + player.getName() + " {{GOLD}}: {{YELLOW}}" + event.getMessage().getPlainString()) );
                }

            }

        } else if ( member.getChannel().equals( KingdomChannel.KINGDOM ) ) {

            for ( Member toMember : kingdom.getOnlineMembers() ) {

                Player toPlayer = plugin.getServer().getPlayer(toMember.getName(), true);

                toPlayer.sendMessage( ChatArguments.fromFormatString("{{BLUE}}[" + KingdomsConfig.CHAT_KINGDOM_PREFIX.getString() + "{{BLUE}}]" + kingdomPrefix + "{{WHITE}}" + playerPrefix + ".{{WHITE}}" + player.getName() + " {{GOLD}}: {{GOLD}}" + event.getMessage().getPlainString()) );

            }


        } else if ( member.getChannel().equals( KingdomChannel.GLOBAL ) ) {

            for ( Player toPlayer : plugin.getServer().getOnlinePlayers() ) {

               toPlayer.sendMessage( ChatArguments.fromFormatString("{{BLUE}}[" + KingdomsConfig.CHAT_GLOBAL_PREFIX.getString() + "{{BLUE}}]" + kingdomPrefix + "{{WHITE}}" + playerPrefix + ".{{WHITE}}" + player.getName() + " {{GOLD}}: {{DARK_GREEN}}" + event.getMessage().getPlainString()) );

            }

        } else if ( member.getChannel().equals( KingdomChannel.STAFF ) ) {

            for ( Player toPlayer : plugin.getServer().getOnlinePlayers() ) {

                if ( toPlayer.hasPermission("kingdoms.chat.staff") ) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString("{{BLUE}}[" + KingdomsConfig.CHAT_STAFF_PREFIX.getString() + "{{BLUE}}]" + kingdomPrefix + "{{WHITE}}" + playerPrefix + ".{{WHITE}}" + player.getName() + " {{GOLD}}: {{RED}}" + event.getMessage().getPlainString()) );
                }

            }

        }

        event.setCancelled(true);

    }

    @EventHandler (order = Order.EARLIEST)
    public void onPlayerInteractEventProtection( PlayerInteractEvent event) {

        if ( event.isAir() || event.isCancelled() ) {
            return;
        }

        Player player = event.getPlayer();
        Block block = player.getWorld().getBlock( event.getInteractedPoint() );

        if ( block.isMaterial( VanillaMaterials.BED,
                               VanillaMaterials.BOOK,
                               VanillaMaterials.BREWING_STAND,
                               VanillaMaterials.CHEST,
                               VanillaMaterials.DISPENSER,
                               VanillaMaterials.DROPPER,
                               VanillaMaterials.ENCHANTMENT_TABLE,
                               VanillaMaterials.FURNACE,
                               VanillaMaterials.FURNACE_BURNING,
                               VanillaMaterials.HOPPER,
                               VanillaMaterials.JUKEBOX,
                               VanillaMaterials.LEVER,
                               VanillaMaterials.REDSTONE_REPEATER,
                               VanillaMaterials.STONE_BUTTON,
                               VanillaMaterials.TRAPPED_CHEST_BLOCK,
                               VanillaMaterials.WOOD_BUTTON,
                               VanillaMaterials.WOODEN_DOOR ) ) {

            Kingdom kingdom = kingdomManager.getKingdomByPoint( event.getInteractedPoint() );
            Plot plot = plotManager.getPlotByPoint( event.getInteractedPoint() );
            Zone zone = zoneManager.getZoneByPoint( event.getInteractedPoint() );

            if ( kingdom == null && plot == null && zone == null ) {
                return;
            }

            if ( kingdom != null ) {

                if ( plot != null ) {

                    // Plot
                    if ( !plot.getOwner().equalsIgnoreCase( player.getName() ) ) {
                        event.setCancelled(true);
                        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You cant do this!", player) ) );
                        return;
                    }

                } else {

                    // Kingdom
                    if ( kingdom.getId() != player.get(KingdomsComponent.class).getKingdom().getId() ) {
                        event.setCancelled(true);
                        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You cant do this!", player) ) );
                        return;
                    }

                    if ( player.get(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.NOVICE ) ) {
                        event.setCancelled(true);
                        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You cant do this!", player) ) );
                        return;
                    }

                }

            }

            if ( zone!= null ) {

                if ( zone.getKingdom() != null ) {

                    if ( !zone.getKingdom().equalsIgnoreCase( player.get(KingdomsComponent.class).getKingdom().getName() ) ) {
                        event.setCancelled(true);
                        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You cant do this!", player) ) );
                        return;
                    }

                }

            }

        }

    }

    @EventHandler (order = Order.MONITOR)
    public void PlayerRespawnEventZone( PlayerRespawnEvent event ) {

        Player player = event.getPlayer();

        if ( player.get(KingdomsComponent.class).getKingdom() == null ) {
            return;
        }

        Zone zone = zoneManager.getZoneByPoint( player.getScene().getPosition() );

        if ( zone == null ) {
            return;
        }

        if ( !zone.isAttack() ) {
            return;
        }

        if ( zone.getAttacker().getName().equalsIgnoreCase( player.get(KingdomsComponent.class).getKingdom().getName() ) ) {

            if ( zone.getDeathCounterAttacker() == zone.getLifePoolAttackers() ) {
                return;
            }

            zone.addDeathCounterAttacker();

            event.setPoint( zone.getSpawnAttackers() );

        } else if ( zone.getDefender().getName().equalsIgnoreCase( player.get(KingdomsComponent.class).getKingdom().getName() ) ) {

            if ( zone.getDeathCounterDefenders() == zone.getLifePoolDefenders() ) {
                return;
            }

            zone.addDeathCounterDefenders();

            event.setPoint( zone.getSpawnDefenders() );

        }

    }


    @EventHandler (order = Order.MONITOR)
    public void onPlayerInteractEventSelection( PlayerInteractEvent event) {


        if ( event.isAir() || event.isCancelled() ) {
            return;
        }

        Player player = event.getPlayer();

        if ( player.hasPermission("kingdoms.zones.selection") ) {

            if ( event.getHeldItem() != null ) {

                if ( event.getHeldItem().isMaterial(VanillaMaterials.WOODEN_AXE) ) {

                    switch ( event.getAction() ) {
                        case LEFT_CLICK:
                            player.get(SelectionComponent.class).getSelection().setPoint1( event.getInteractedPoint() );
                            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("Point 1 : %0, X : %1  Y : %2 Z : %3", player,
                                    event.getInteractedPoint().getWorld().getName(), event.getInteractedPoint().getX(), event.getInteractedPoint().getY(), event.getInteractedPoint().getZ())) );
                        case RIGHT_CLICK:
                            player.get(SelectionComponent.class).getSelection().setPoint2(event.getInteractedPoint() );
                            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("Point 2 : %0, X : %1  Y : %2 Z : %3", player,
                                    event.getInteractedPoint().getWorld().getName(), event.getInteractedPoint().getX(), event.getInteractedPoint().getY(), event.getInteractedPoint().getZ())) );
                    }

                }

            }

        }

    }

    @EventHandler (order = Order.MONITOR)
    public void onBlockChangeEventSelection( PlayerBucketEvent event ) {

        Player player = event.getPlayer();

        if ( player.hasPermission("kingdoms.zones.selection") ) {

        }

    }


    @EventHandler (order = Order.MONITOR)
    public void onPlayerDeathEventStats( PlayerDeathEvent event ) {

        Player player = event.getPlayer();

        if ( player.get(Health.class).getLastDamager() instanceof Player) {

            player.get(KingdomsComponent.class).getMember().addPlayerDeath();

            if ( player.get(KingdomsComponent.class).getKingdom() != null ) {
                player.get(KingdomsComponent.class).getKingdom().addPlayerDeath();
            }

            Player killer = (Player)player.get(Health.class).getLastDamager();

            killer.get(KingdomsComponent.class).getMember().addPlayerKill();

            if ( killer.get(KingdomsComponent.class).getKingdom() != null ) {
                killer.get(KingdomsComponent.class).getKingdom().addPlayerKill();
            }

        } else if ( player.get(Health.class).getLastDamager() instanceof Hostile) {

            player.get(KingdomsComponent.class).getMember().addMonsterDeath();

            if ( player.get(KingdomsComponent.class).getKingdom() != null ) {
                player.get(KingdomsComponent.class).getKingdom().addMonsterDeath();
            }

        }

    }

    @EventHandler (order = Order.MONITOR)
    public void onBlockChangeEventStats( PlayerBucketEvent event ) {

        Player player = event.getPlayer();

        Kingdom kingdom = kingdomManager.getKingdomByPoint( event.getBlockClicked().getPosition() );
        Plot plot = plotManager.getPlotByPoint( event.getBlockClicked().getPosition() );
        Zone zone = zoneManager.getZoneByPoint( event.getBlockClicked().getPosition() );

        if ( kingdom == null && plot == null && zone == null ) {
            return;
        }

        if ( kingdom != null ) {

            if ( plot != null ) {

                // Plot
                if ( !plot.getOwner().equalsIgnoreCase( player.getName() ) ) {
                    event.setCancelled(true);
                    player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You can't do this!", player) ) );
                    return;
                }

            } else {

                // Kingdom
                if ( kingdom.getId() != player.get(KingdomsComponent.class).getKingdom().getId() ) {
                    event.setCancelled(true);
                    player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You can't do this!", player) ) );
                    return;
                }

                if ( player.get(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.NOVICE ) ) {
                    event.setCancelled(true);
                    player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You can't do this!", player) ) );
                    return;
                }

            }

        }

        if ( zone!= null ) {

            if ( zone.getKingdom() != null ) {

                if ( !zone.getKingdom().equalsIgnoreCase( player.get(KingdomsComponent.class).getKingdom().getName() ) ) {
                    event.setCancelled(true);
                    player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You can't do this!", player) ) );
                    return;
                }

            }

        }





    }

}
