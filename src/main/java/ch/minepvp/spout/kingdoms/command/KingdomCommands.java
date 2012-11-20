package ch.minepvp.spout.kingdoms.command;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.lang.Translation;

import java.util.ArrayList;

public class KingdomCommands {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;

    public KingdomCommands( Kingdoms instance ) {
        plugin = instance;
        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
    }

    @Command(aliases = {"help"}, usage = "", desc = "List all Commands for /kingdom")
    @CommandPermissions("kingdoms.command.kingdom.help")
    public void help(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{YELLOW}Help") );
        player.sendMessage( ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------") );

        if ( player.hasPermission("kingdoms.command.kingdom.list") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{WHITE}}/kingdoms list", source)));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{GRAY}}List all Kingdoms", source)) );
        }

        if ( player.hasPermission("kingdoms.command.kingdom.create") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{WHITE}}/kingdoms create <name> <tag>", source)));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{GRAY}}Create a new Kingdom", source)) );
        }

        if ( player.hasPermission("kingdoms.command.kingdom.invite") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ||
                 player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.CAPTAIN )   ) {

                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{WHITE}}/kingdoms invite <player>", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{GRAY}}Invite a Player to the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.kick") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ||
                    player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.CAPTAIN )   ) {

                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{WHITE}}/kingdoms kick <player>", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{GRAY}}Kick a Player to the Kingdom", source)) );
            }
        }

        player.sendMessage(ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------"));

    }

    @Command(aliases = {"list"}, usage = "", desc = "List all Kingdoms")
    @CommandPermissions("kingdoms.command.kingdom.list")
    public void list(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{YELLOW}List all Kingdoms") );
        player.sendMessage( ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------") );


        for ( Kingdom kingdom : kingdomManager.getAllKingdoms()  ) {

            ArrayList<String> leaders = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.LEADER);
            ArrayList<String> captains = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.CAPTAIN);
            ArrayList<String> members = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.MEMBER);
            ArrayList<String> novices = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.NOVICE);

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Name : {{GOLD}}%0 {{WHITE}}Tag : %1", player, kingdom.getName(), kingdom.getTag()) ) );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Level : {{GOLD}}%0 {{WHITE}}Points All : {{GOLD}}%1 {{WHITE}}Points : %2", player, kingdom.getLevel(), kingdom.getPointsAll(), kingdom.getPoints()) ) );

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Player Kills : {{GOLD}}%0 {{WHITE}}Player Deaths : %1", player, kingdom.getPlayerKills(), kingdom.getPlayerDeaths()) ) );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Monster Kills : {{GOLD}}%0 {{WHITE}}Monster Deaths : %1", player, kingdom.getMonsterKills(), kingdom.getMonsterDeaths()) ) );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Block places : {{GOLD}}%0 {{WHITE}}Block breaks : %1", player, kingdom.getBlockPlace(), kingdom.getBlockBreak()) ) );

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Leaders : {{GOLD}}%0", player, leaders.toString() ) ) );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Captains : {{GOLD}}%0", player, captains.toString() ) ) );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Members : {{GOLD}}%0", player, members.toString() ) ) );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Novices : {{GOLD}}%0", player, novices.toString()) ) );

            player.sendMessage(ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------"));
        }

    }

    @Command(aliases = {"create"}, usage = "", desc = "Create a Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.create")
    public void create(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom!= null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You are already in a Kingdom!", player) ) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("/kingdom create <name> <tag>", player) ) );
            return;
        }

        // Check Name
        kingdom = kingdomManager.getKingdomByName( args.getString(0) );

        if ( kingdom != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("There is already a Kingdom with this Name!", player) ) );
            return;
        }

        // Check Tag
        kingdom = kingdomManager.getKingdomByTag(args.getString(1));

        if ( kingdom != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("There is already a Kingdom with this Tag!", player) ) );
            return;
        }

        member.setRank( KingdomRank.LEADER );

        kingdomManager.createKingdom(member, args.getString(0), args.getString(1));
    }

    @Command(aliases = {"invite"}, usage = "", desc = "Invite a Player to the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.invite")
    public void invite(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false &&
             member.getRank().equals( KingdomRank.CAPTAIN ) == false   ) {

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("You need to be a Captain ore Leader to do this!", player) ) );
            return;
        }

        Player invitePlayer = plugin.getEngine().getPlayer(args.getString(0), true);
        Member inviteMember = memberManager.getMemberByName( args.getString(0) );

        if ( inviteMember == null || invitePlayer == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("There are no Player with this Name!", player) ) );
            return;
        }

        kingdom.addInvitetMember(inviteMember);

        invitePlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("You are invited from %0 to join the Kingdom %1", invitePlayer, player.getName(), kingdom.getName()) ) );
    }


    @Command(aliases = {"kick"}, usage = "", desc = "Kick a Player to the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.kick")
    public void kick(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false &&
                member.getRank().equals( KingdomRank.CAPTAIN ) == false   ) {

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("You need to be a Captain ore Leader to do this!", player) ) );
            return;
        }

        Player kickPlayer = plugin.getEngine().getPlayer(args.getString(0), true);
        Member kickMember = memberManager.getMemberByName( args.getString(0) );

        if ( kickMember == null || kickPlayer == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("There are no Player with this Name!", player) ) );
            return;
        }

        if ( member.getRank().equals( kickMember.getRank() ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("You can't kick a Member who has the same Rank!", player) ) );
            return;
        }

        if ( kickMember.getRank().equals( KingdomRank.LEADER ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("You can't kick a Leader!", player) ) );
            return;
        }

        kingdom.removeMember(kickMember);

        for ( Member toMember : kingdom.getMembers()  ) {

            Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("%0 was kicked from Kingdom!", toPlayer, kickPlayer.getName())) );
            }

        }

        kickPlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("You where kicked from Kingdom by %0!", kickPlayer, player.getName()) ) );
    }



}
