package ch.minepvp.spout.kingdoms.command;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.component.SelectionComponent;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.entity.KingdomChannel;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import org.spout.api.chat.ChatArguments;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.NestedCommand;
import org.spout.api.component.impl.SceneComponent;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.lang.Translation;

public class KingdomsCommand {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;

    public KingdomsCommand( Kingdoms instance) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
    }

    @Command(aliases = {"kingdom", "king"}, usage = "", desc = "Kingdom Commands", min = 1, max = 4)
    @NestedCommand(KingdomCommands.class)
    public void kingdom(CommandContext args, CommandSource source) throws CommandException {

    }

    @Command(aliases = {"zone"}, usage = "", desc = "Zone Commands", min = 1, max = 4)
    @NestedCommand(ZoneCommands.class)
    public void zone(CommandContext args, CommandSource source) throws CommandException {

    }

    @Command(aliases = {"plot"}, usage = "", desc = "Plot Commands", min = 1, max = 2)
    @NestedCommand(PlotCommands.class)
    public void plot(CommandContext args, CommandSource source) throws CommandException {

    }

    @Command(aliases = {"channel", "ch"}, usage = "", desc = "Chat Command", min = 0, max = 0)
    public void chat(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        Member member = player.get(KingdomsComponent.class).getMember();
        Kingdom kingdom = player.get(KingdomsComponent.class).getKingdom();

        // Switch the ChatChannel to Chat
        if ( member.getChannel().equals( KingdomChannel.LOCAL ) ) {

            if ( kingdom != null ) {
                member.setChannel( KingdomChannel.KINGDOM );
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}You are now in Kingdom Chat!", player)) );
            } else {
                member.setChannel( KingdomChannel.GLOBAL );
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}You are now in Global Chat!", player)) );
            }

        } else if ( member.getChannel().equals( KingdomChannel.KINGDOM ) ) {

            if ( player.hasPermission("kingdoms.chat.global") ) {
                member.setChannel( KingdomChannel.GLOBAL );
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}You are now in Global Chat!", player)) );
            } else {
                member.setChannel( KingdomChannel.LOCAL );
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}You are now in Local Chat!", player)) );
            }

        } else if ( member.getChannel().equals( KingdomChannel.GLOBAL ) ) {

            if ( player.hasPermission("kingdoms.chat.staff") || player.isObserver() ) {
                member.setChannel( KingdomChannel.STAFF );
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}You are now in Staff Chat!", player)) );
            } else {
                member.setChannel( KingdomChannel.LOCAL );
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}You are now in Local Chat!", player)) );
            }

        } else if ( member.getChannel().equals( KingdomChannel.STAFF ) ) {

            member.setChannel( KingdomChannel.LOCAL );
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}You are now in Local Chat!", player)) );

        }

    }

    @Command(aliases = {"accept"}, usage = "", desc = "Accept Command", min = 0, max = 0)
    public void accept(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByInvitedPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You are not Invited in any Kingdom!", player)) );
            return;
        }

        for ( Member toMember : kingdom.getMembers()  ) {

            Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}%0 has joined the Kingdom!", toPlayer, player.getName())) );
            }

        }

        member.setRank( KingdomRank.NOVICE );
        member.setKingdom( kingdom.getId() );

        kingdom.removeInvitedMember(member);
        kingdom.addMember(member);

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}You have joined the Kingdom!", player)) );

    }

    @Command(aliases = {"reject"}, usage = "", desc = "Reject Command", min = 0, max = 0)
    public void reject(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByInvitedPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}You are not Invited in any Kingdom!", player)) );
            return;
        }

        for ( Member toMember : kingdom.getMembers()  ) {

            Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}%0 has rejected the Kingdom invite!", toPlayer, player.getName())) );
            }

        }

        kingdom.removeInvitedMember(member);

    }

    @Command(aliases = {"setpos"}, usage = "", desc = "Set Postitons", min = 0, max = 0)
    public void setpos(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() == 1 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/setpos <1|2>", player)) );
            return;
        }

        SceneComponent scene = player.getScene();

        if ( args.get(1).equals("1") ) {
            player.add(SelectionComponent.class).getSelection().setPoint1( player.getScene().getPosition() );
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("Point 1 : %0, X : %1  Y : %2 Z : %3",
                    player, scene.getWorld(), scene.getPosition().getBlockX(), scene.getPosition().getBlockY(), scene.getPosition().getBlockZ() )));
        } else {
            player.add(SelectionComponent.class).getSelection().setPoint2( player.getScene().getPosition() );
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("Point 2 : %0, X : %1  Y : %2 Z : %3",
                    player, scene.getWorld(), scene.getPosition().getBlockX(), scene.getPosition().getBlockY(), scene.getPosition().getBlockZ() )));
        }

    }

}
