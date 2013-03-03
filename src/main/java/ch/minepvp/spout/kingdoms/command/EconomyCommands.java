package ch.minepvp.spout.kingdoms.command;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.component.KingdomsComponent;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.manager.*;
import org.spout.api.chat.ChatArguments;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.lang.Translation;

public class EconomyCommands {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;


    public EconomyCommands( Kingdoms instance ) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
    }

    @Command(aliases = {"help"}, usage = "", desc = "List all Commands for /economy /money")
    @CommandPermissions("kingdoms.command.economy.help")
    public void help(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Help") );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        if ( player.hasPermission("kingdoms.command.economy.info") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 info <name>", source, args.getCommand())));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Info about a your Money", source)) );
        }

        if ( player.hasPermission("kingdoms.command.economy.pay") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 pay <name|kingdom> <amount>", source, args.getCommand())));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Pay a Player or Kingdom Credits", source)) );
        }

        if ( player.hasPermission("kingdoms.command.economy.top") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 top>", source, args.getCommand())));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}List the 10 richest Player on the Server", source)) );
        }

    }

    @Command(aliases = {"info"}, usage = "", desc = "Show your Money")
    @CommandPermissions("kingdoms.command.economy.info")
    public void info(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("Credits : %0$", player, player.get(KingdomsComponent.class).getMember().getMoney() ) ) );
    }

    @Command(aliases = {"pay"}, usage = "", desc = "Pay a Player Money")
    @CommandPermissions("kingdoms.command.economy.pay")
    public void pay(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}/money pay <name> <amount>", player) ) );
            return;
        }

        Member toMember = memberManager.getMemberByName( args.getString(0) );
        Kingdom toKingdom = null;
        Integer money = args.getInteger(1);

        if ( toMember == null ) {

            toKingdom = kingdomManager.getKingdomByName( args.getString(0) );

            if ( toKingdom == null ) {
                player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}There is no Player or Kingdom with this Name!", player) ) );
                return;
            }

        }

        if ( player.get(KingdomsComponent.class).getMember().getMoney() < money ) {
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{RED}}You have not enough Credits!", player) ) );
            return;
        }

        player.get(KingdomsComponent.class).getMember().removeMoney( money );

        if ( toMember != null ) {

            toMember.addMoney( money );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You have send %0 Credits to %1!", player, money, toMember.getName() ) ) );

            if ( toMember.isOnline() ) {
                Player toPlayer = plugin.getEngine().getPlayer( toMember.getName(), true );

                toPlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}The Player %0 has send you %1 Credits!", player, player.getName(), money ) ) );
            }

        } else {

            toKingdom.addMoney( money );
            player.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You have send %0 Credits to %1!", player, money, toKingdom.getName() ) ) );

            for ( Member member : toKingdom.getMembers() ) {

                if ( member.isOnline() ) {

                    Player toPlayer = plugin.getEngine().getPlayer(member.getName(), true);

                    toPlayer.sendMessage( ChatArguments.fromFormatString( Translation.tr("{{GOLD}}The Player %0 has send to your Kingdom %1 Credits!", player, player.getName(), money) ) );

                }

            }

        }

    }

}
