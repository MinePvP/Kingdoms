package ch.minepvp.spout.kingdoms.command;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.config.KingdomsConfig;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.entity.KingdomLevel;
import ch.minepvp.spout.kingdoms.entity.KingdomRank;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import ch.minepvp.spout.kingdoms.manager.PlotManager;
import ch.minepvp.spout.kingdoms.manager.TaskManager;
import ch.minepvp.spout.kingdoms.task.SpawnTask;
import org.spout.api.chat.ChatArguments;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.lang.Translation;
import org.spout.api.scheduler.TaskPriority;

import java.util.ArrayList;

public class KingdomCommands {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;
    private TaskManager taskManager;


    public KingdomCommands( Kingdoms instance ) {
        plugin = instance;
        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
        taskManager = plugin.getTaskManager();
    }

    @Command(aliases = {"help"}, usage = "", desc = "List all Commands for /kingdom")
    @CommandPermissions("kingdoms.command.kingdom.help")
    public void help(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Help") );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        if ( player.hasPermission("kingdoms.command.kingdom.list") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom list", source)));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}List all Kingdoms", source)) );
        }

        if ( player.hasPermission("kingdoms.command.kingdom.info") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom info <name>", source)));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Info about a Kingdom", source)) );
        }

        if ( player.hasPermission("kingdoms.command.kingdom.create") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.NONE ) ) {
                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom create <name> <tag>", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Create a new Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.members") ) {

            if ( !player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.NONE ) ) {
                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom members", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Info about Kingdom Members", source)) );
            }

        }

        if ( player.hasPermission("kingdoms.command.kingdom.invite") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ||
                 player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.CAPTAIN )   ) {

                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom invite <player>", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Invite a Player to the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.kick") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ||
                    player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.CAPTAIN )   ) {

                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom kick <player>", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Kick a Player to the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.leave") ) {

            if ( !player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.NONE ) ) {
                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom leave", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Leave the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.promote") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ||
                    player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.CAPTAIN )   ) {

                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom promote <player>", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Promote a Player from the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.demote") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ||
                    player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.CAPTAIN )   ) {

                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom demote <player>", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Demote a Player forom the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.setbase") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ) {
                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom setbase", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Base of the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.upgrade") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ) {
                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom upgrade", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Upgrade the Kingdom", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.setspawn") ) {

            if ( player.add(KingdomsComponent.class).getMember().getRank().equals( KingdomRank.LEADER ) ) {
                player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom setspawn", source)));
                player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Set the Kingdom Spawn", source)) );
            }
        }

        if ( player.hasPermission("kingdoms.command.kingdom.spawn") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/kingdom spawn", source)));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Teleport you to the Kingdom Spawn", source)) );
        }

        player.sendMessage(ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------"));

    }

    @Command(aliases = {"list"}, usage = "", desc = "List all Kingdoms")
    @CommandPermissions("kingdoms.command.kingdom.list")
    public void list(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}List all Kingdoms") );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}} TAG - NAME - POINTS - ALL POINT - Players ") );

        // TODO Sort the Kingdoms by Points All / Points

        for ( Kingdom kingdom : kingdomManager.getAllKingdoms()  ) {

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}%0 %1 %2 %3 %4", player, kingdom.getTag(), kingdom.getName(), kingdom.getPoints(), kingdom.getPointsAll(), kingdom.getMembers().size()) ) );

        }

        player.sendMessage(ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------"));

    }

    @Command(aliases = {"info"}, usage = "", desc = "Info about a Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.info")
    public void info(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Kingdom kingdom = null;

        if ( args.length() > 0 ) {
            kingdomManager.getKingdomByName( args.getString(0) );
        } else {
            kingdomManager.getKingdomByPlayer(player);
        }

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There is no Kingdom with this Name!", player) ) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{YELLOW}}Info [%0] %1 ", player, kingdom.getTag(), kingdom.getName())) );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        ArrayList<String> leaders = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.LEADER);
        ArrayList<String> captains = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.CAPTAIN);
        ArrayList<String> members = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.MEMBER);
        ArrayList<String> novices = kingdomManager.getMembersNameFromKingdomByRank(kingdom, KingdomRank.NOVICE);

        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Level : {{GOLD}}%0 {{WHITE}}Points All : {{GOLD}}%1 {{WHITE}}Points : %2", player, kingdom.getLevel().getLevel(), kingdom.getPointsAll(), kingdom.getPoints()) ) );

        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Player Kills : {{GOLD}}%0 {{WHITE}}Player Deaths : %1", player, kingdom.getPlayerKills(), kingdom.getPlayerDeaths()) ) );
        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Monster Kills : {{GOLD}}%0 {{WHITE}}Monster Deaths : %1", player, kingdom.getMonsterKills(), kingdom.getMonsterDeaths()) ) );
        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Block places : {{GOLD}}%0 {{WHITE}}Block breaks : %1", player, kingdom.getBlockPlace(), kingdom.getBlockBreak()) ) );

        if ( leaders != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Leaders : {{GOLD}}%0", player, leaders.toString() ) ) );
        }

        if ( captains != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Captains : {{GOLD}}%0", player, captains.toString() ) ) );
        }

        if ( members != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Members : {{GOLD}}%0", player, members.toString() ) ) );
        }

        if ( novices != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{WHITE}}Novices : {{GOLD}}%0", player, novices.toString()) ) );
        }

        player.sendMessage(ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------"));

    }

    @Command(aliases = {"members"}, usage = "", desc = "Info about Kingdom Members")
    @CommandPermissions("kingdoms.command.kingdom.members")
    public void members(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Members") );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Name - Kills - Deaths") );

        ArrayList<Member> leaders = kingdomManager.getMembersFromKingdomByRank(kingdom, KingdomRank.LEADER);
        ArrayList<Member> captains = kingdomManager.getMembersFromKingdomByRank(kingdom, KingdomRank.CAPTAIN);
        ArrayList<Member> members = kingdomManager.getMembersFromKingdomByRank(kingdom, KingdomRank.MEMBER);
        ArrayList<Member> novices = kingdomManager.getMembersFromKingdomByRank(kingdom, KingdomRank.NOVICE);


        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Leaders") );
        if ( leaders != null ) {

            for ( Member member : leaders ) {

                String color = "WHITE";

                if ( member.isOnline() ) {
                    color = "PINK";
                }

                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{%0}}%1 %2 %3", player, color, member.getName(), member.getPlayerKills(), member.getPlayerDeaths() ) ) );

            }

        }


        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Captains") );
        if ( captains != null ) {

            for ( Member member : captains ) {

                String color = "WHITE";

                if ( member.isOnline() ) {
                    color = "PINK";
                }

                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{%0}}%1 %2 %3", player, color, member.getName(), member.getPlayerKills(), member.getPlayerDeaths() ) ) );

            }

        }

        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Members") );
        if ( members != null ) {

            for ( Member member : members ) {

                String color = "WHITE";

                if ( member.isOnline() ) {
                    color = "PINK";
                }

                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{%0}}%1 %2 %3", player, color, member.getName(), member.getPlayerKills(), member.getPlayerDeaths() ) ) );

            }

        }

        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Novices") );
        if ( novices != null ) {

            for ( Member member : novices ) {

                String color = "WHITE";

                if ( member.isOnline() ) {
                    color = "PINK";
                }

                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{%0}}%1 %2 %3", player, color, member.getName(), member.getPlayerKills(), member.getPlayerDeaths() ) ) );

            }

        }

        player.sendMessage(ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------"));

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
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are already in a Kingdom!", player) ) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}/kingdom create <name> <tag>", player) ) );
            return;
        }

        // Check Name
        kingdom = kingdomManager.getKingdomByName( args.getString(0) );

        if ( kingdom != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There is already a Kingdom with this Name!", player) ) );
            return;
        }

        // Check Tag
        kingdom = kingdomManager.getKingdomByTag(args.getString(1));

        if ( kingdom != null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There is already a Kingdom with this Tag!", player) ) );
            return;
        }

        member.setRank( KingdomRank.LEADER );

        kingdomManager.createKingdom(member, args.getString(0), args.getString(1));
        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}The Kingdom is created! Now you can invite Players with /kingdom invite <player>", player) ) );
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
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false &&
             member.getRank().equals( KingdomRank.CAPTAIN ) == false   ) {

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to be a Captain ore Leader to do this!", player) ) );
            return;
        }

        Player invitePlayer = plugin.getEngine().getPlayer(args.getString(0), true);
        Member inviteMember = memberManager.getMemberByName( args.getString(0) );

        if ( inviteMember == null || invitePlayer == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There is no Player with this Name!", player) ) );
            return;
        }

        kingdom.addInvitedMember(inviteMember);

        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}Player is invited!", player) ) );
        invitePlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You are invited from %0 to join the Kingdom %1", invitePlayer, player.getName(), kingdom.getName()) ) );
        invitePlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{{GOLD}}Type {{DARK_GREEN}}/accept {{GOLD}}ore {{RED}}/reject", invitePlayer) ) );

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
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false &&
                member.getRank().equals( KingdomRank.CAPTAIN ) == false   ) {

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to be a Captain ore Leader to do this!", player) ) );
            return;
        }

        Player kickPlayer = plugin.getEngine().getPlayer(args.getString(0), true);
        Member kickMember = memberManager.getMemberByName( args.getString(0) );

        if ( kickMember == null || kickPlayer == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There are no Player with this Name!", player) ) );
            return;
        }

        if ( member.getKingdom().equalsIgnoreCase( kickMember.getKingdom() ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}The Player is not in your Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( kickMember.getRank() ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You can't kick a Member who has the same Rank!", player) ) );
            return;
        }

        if ( kickMember.getRank().equals( KingdomRank.LEADER ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You can't kick a Leader!", player) ) );
            return;
        }

        kingdom.removeMember(kickMember);

        for ( Member toMember : kingdom.getMembers()  ) {

            Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}%0 was kicked from Kingdom!", toPlayer, kickPlayer.getName())) );
            }

        }

        kickPlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You where kicked from Kingdom by %0!", kickPlayer, player.getName()) ) );
    }


    @Command(aliases = {"leave"}, usage = "", desc = "Leave the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.leave")
    public void leave(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) ) {

            if ( kingdomManager.getMembersFromKingdomByRank( kingdom, KingdomRank.LEADER ).size() == 1 ) {

                if ( kingdom.getMembers().size() > 1 ) {
                    player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to Promote first a Player to a Leader!", player) ) );
                    return;
                }

            }

        }

        member.setRank( KingdomRank.NONE );

        if ( kingdom.getMembers().size() > 1 ) {


            kingdom.removeMember(member);
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You have leave the Kingdom!", player) ) );

            for ( Member toMember : kingdom.getMembers()  ) {

                Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

                if ( toPlayer.isOnline() ) {
                    toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}%0 has leave the Kingdom!", toPlayer, member.getName())) );
                }

            }

        } else {

            kingdomManager.deleteKingdom(kingdom);
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You have leave and deleted the Kingdom!", player) ) );

        }

    }

    @Command(aliases = {"promote"}, usage = "", desc = "Promote a Player from the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.promote")
    public void promote(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false &&
                member.getRank().equals( KingdomRank.CAPTAIN ) == false   ) {

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to be a Captain ore Leader to do this!", player) ) );
            return;
        }

        Player promotePlayer = plugin.getEngine().getPlayer(args.getString(0), true);
        Member promoteMember = memberManager.getMemberByName( args.getString(0) );

        if ( promoteMember == null || promotePlayer == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There are no Player with this Name!", player) ) );
            return;
        }

        if ( member.getKingdom().equalsIgnoreCase( promoteMember.getKingdom() ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}The Player is not in your Kingdom!", player) ) );
            return;
        }

        if ( promoteMember.getRank().equals( KingdomRank.LEADER ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You can't promote a Leader!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.CAPTAIN ) ) {

            if ( promoteMember.getRank().equals( KingdomRank.CAPTAIN ) ){
                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You can't promote a Member who has the same Rank!", player) ) );
                return;
            }

        }

        if ( promoteMember.getRank().equals( KingdomRank.NOVICE ) ) {
            promoteMember.setRank( KingdomRank.MEMBER );
        } else if ( promoteMember.getRank().equals( KingdomRank.MEMBER ) ) {
            promoteMember.setRank( KingdomRank.CAPTAIN );
        } if ( promoteMember.getRank().equals( KingdomRank.CAPTAIN ) ) {
            promoteMember.setRank( KingdomRank.LEADER );
        }


        for ( Member toMember : kingdom.getMembers()  ) {

            Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}%0 was promoted to %1!", toPlayer, promoteMember.getName(), promoteMember.getRank())) );
            }

        }

        promotePlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You where promoted to %0!", promotePlayer, promoteMember.getRank()) ) );
    }

    @Command(aliases = {"demote"}, usage = "", desc = "Demote a Player from the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.demote")
    public void demote(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false &&
                member.getRank().equals( KingdomRank.CAPTAIN ) == false   ) {

            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to be a Captain ore Leader to do this!", player) ) );
            return;
        }

        Player demotePlayer = plugin.getEngine().getPlayer(args.getString(0), true);
        Member demoteMember = memberManager.getMemberByName( args.getString(0) );

        if ( demoteMember == null || demotePlayer == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There are no Player with this Name!", player) ) );
            return;
        }

        if ( member.getKingdom().equalsIgnoreCase( demoteMember.getKingdom() ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}The Player is not in your Kingdom!", player) ) );
            return;
        }

        if ( demoteMember.getRank().equals( KingdomRank.LEADER ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You can't demote a Leader!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.CAPTAIN ) ) {

            if ( demoteMember.getRank().equals( KingdomRank.CAPTAIN ) ){
                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You can't demote a Member who has the same Rank!", player) ) );
                return;
            }

        }

        if ( demoteMember.getRank().equals( KingdomRank.NOVICE ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You can't demote a Novice!", player) ) );
            return;
        }

        if ( demoteMember.getRank().equals( KingdomRank.MEMBER ) ) {
            demoteMember.setRank( KingdomRank.NOVICE );
        } if ( demoteMember.getRank().equals( KingdomRank.CAPTAIN ) ) {
            demoteMember.setRank( KingdomRank.MEMBER );
        }

        for ( Member toMember : kingdom.getMembers()  ) {

            Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}%0 was demoted to %1!", toPlayer, demotePlayer.getName(), demoteMember.getRank())) );
            }

        }

        demotePlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You where demoted to %0!", demotePlayer, demoteMember.getRank()) ) );
    }

    @Command(aliases = {"upgrade"}, usage = "", desc = "Upgrade the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.upgrade")
    public void upgrade(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false    ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to be a Leader to do this!", player) ) );
            return;
        }

        KingdomLevel lvl = null;

        for (KingdomLevel level : KingdomsConfig.LEVELS ) {

            if ( kingdom.getLevel().getLevel() + 1 == level.getLevel() ) {
                lvl = level;
            }

        }

        if ( lvl == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You have already the highest Level!", player) ) );
            return;
        }

        if ( kingdom.getPoints() < lvl.getPoints() ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You have not enough Points!", player) ) );
            return;
        }

        kingdom.removePoints( lvl.getPoints() );
        kingdom.setLevel( lvl.getLevel() );

        for ( Member toMember : kingdom.getMembers()  ) {

            Player toPlayer = plugin.getEngine().getPlayer(toMember.getName(), true);

            if ( toPlayer.isOnline() ) {
                toPlayer.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}The Kingdom has Upgraded to Level %0!", toPlayer, lvl.getLevel() )) );
            }

        }

    }

    @Command(aliases = {"setbase"}, usage = "", desc = "Set the Base for the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.setbase")
    public void setBase(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to be a Leader to do this!", player) ) );
            return;
        }

        if ( kingdom.getMembers().size() < KingdomsConfig.KINGDOMS_BASE_MIN_PLAYERS.getInt() ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You have not enough Members!", player) ) );
            return;
        }

        if ( kingdom.getBaseX() != 0 && kingdom.getBaseY() != 0 && kingdom.getBaseZ() != 0 ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You have already a Base!", player) ) );
            return;
        }

        if ( !player.getWorld().getName().equalsIgnoreCase("world") ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in the Main World!", player) ) );
            return;
        }

        // Check Ranges
        if ( player.getTransform().getPosition().distance( player.getWorld().getSpawnPoint().getPosition() ) > KingdomsConfig.KINGDOMS_DISTANCE_MIN_SPAWN.getInt() ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are to near to the Spawn! %0 of %1 ", player, player.getTransform().getPosition().distance( player.getWorld().getSpawnPoint().getPosition() ), KingdomsConfig.KINGDOMS_DISTANCE_MIN_SPAWN.getInt()) ) );
            return;
        }

        if ( kingdomManager.getAllKingdoms().size() > 0 ) {

            for ( Kingdom nearKingdom : kingdomManager.getAllKingdoms() ) {

                if ( nearKingdom.getBaseX() != 0 && nearKingdom.getBaseY() != 0 && nearKingdom.getBaseZ() != 0 ) {

                    if ( player.getTransform().getPosition().distance( nearKingdom.getBasePoint() ) > KingdomsConfig.KINGDOMS_DISTANCE_MIN_KINGDOMS.getInt() ) {

                        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are to near to the Kingdom %0! %1 of %2 ", player, nearKingdom.getName(), player.getTransform().getPosition().distance( nearKingdom.getBasePoint() ), KingdomsConfig.KINGDOMS_DISTANCE_MIN_KINGDOMS.getInt()) ) );
                        return;

                    }

                }

            }

        }

        // TODO Zone in Range

        kingdom.setBaseX( player.getTransform().getPosition().getBlockX() );
        kingdom.setBaseY( player.getTransform().getPosition().getBlockY() );
        kingdom.setBaseZ( player.getTransform().getPosition().getBlockZ() );

        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}Base set!", player) ) );
    }

    @Command(aliases = {"setspawn"}, usage = "", desc = "Set the Spawn for the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.setspawn")
    public void setSpawn(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( member.getRank().equals( KingdomRank.LEADER ) == false ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to be a Leader to do this!", player) ) );
            return;
        }

        if ( kingdomManager.getKingdomByPoint( player.getTransform().getPosition() ) == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to in the Kingdom!", player) ) );
            return;
        }

        if ( !kingdomManager.getKingdomByPoint( player.getTransform().getPosition() ).getName().equalsIgnoreCase( kingdom.getName() ) ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You need to in your Kingdom!", player) ) );
            return;
        }

        kingdom.setSpawnX( player.getTransform().getPosition().getBlockX() );
        kingdom.setSpawnY(player.getTransform().getPosition().getBlockY());
        kingdom.setSpawnZ(player.getTransform().getPosition().getBlockZ());

        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}Spawn set!", player) ) );
    }

    @Command(aliases = {"spawn"}, usage = "", desc = "Teleport you to the Kingdom")
    @CommandPermissions("kingdoms.command.kingdom.spawn")
    public void spawn(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString( Translation.tr("You must be a Player!", source) ) );
            return;
        }

        Member member = memberManager.getMemberByPlayer(player);
        Kingdom kingdom = kingdomManager.getKingdomByPlayer(player);

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You are not in a Kingdom!", player) ) );
            return;
        }

        if ( kingdom.getSpawnX() == 0 && kingdom.getSpawnY() == 0 && kingdom.getSpawnZ() == 0 ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}Your Kingdom have no Spawn Point!", player) ) );
            return;
        }

        taskManager.createSyncDelayedTask(new SpawnTask(player, kingdom), ((KingdomsConfig.TELEPORT_DELAY.getInt() * 20) * 60), TaskPriority.LOW);
        player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You will be Teleportet in %0! Don't move!", player, ((KingdomsConfig.TELEPORT_DELAY.getInt() * 20) * 60)) ) );
    }

}
