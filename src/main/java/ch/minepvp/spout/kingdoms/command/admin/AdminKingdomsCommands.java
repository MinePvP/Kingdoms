package ch.minepvp.spout.kingdoms.command.admin;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.manager.KingdomManager;
import ch.minepvp.spout.kingdoms.manager.MemberManager;
import org.spout.api.chat.ChatArguments;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.exception.CommandException;
import org.spout.api.lang.Translation;

public class AdminKingdomsCommands {

    private final Kingdoms plugin;
    private KingdomManager kingdomManager;
    private MemberManager memberManager;


    public AdminKingdomsCommands( Kingdoms instance ) {
        plugin = instance;

        kingdomManager = plugin.getKingdomManager();
        memberManager = plugin.getMemberManager();
    }

    @Command(aliases = {"help"}, usage = "", desc = "List all Commands for /kingdom")
    @CommandPermissions("kingdoms.command.admin.kingdom.help")
    public void help(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );
        player.sendMessage( ChatArguments.fromFormatString("{{YELLOW}}Help") );
        player.sendMessage( ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------") );

        if ( player.hasPermission("kingdoms.command.admin.kingdom.addpoints") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 kingdom addpoints  <name> <points>", source, args.getCommand())));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Add Points to a Kingdom", source)) );
        }

        if ( player.hasPermission("kingdoms.command.admin.kingdom.removepoints") ) {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{YELLOW}}/%0 kingdom removepoints <name> <points>", source, args.getCommand())));
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{WHITE}}Remove Points from a Kingdom", source)) );
        }

        player.sendMessage(ChatArguments.fromFormatString("{{BLUE}}-----------------------------------------------------"));

    }

    @Command(aliases = {"addpoints"}, usage = "", desc = "Add Points to a Kingdom")
    @CommandPermissions("kingdoms.command.admin.kingdom.addpoints")
    public void addPoints(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/admin kingdom addpoints <name> <points>", player)) );
            return;
        }

        Kingdom kingdom = kingdomManager.getKingdomByName(args.getString(0));

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Kingdom with this Name!", player)) );
            return;
        }

        kingdom.addPoints( args.getInteger(1) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Added Points!", player)) );
    }

    @Command(aliases = {"removepoints"}, usage = "", desc = "Remove Points from a Kingdom")
    @CommandPermissions("kingdoms.command.admin.kingdom.removedpoints")
    public void remocePoints(CommandContext args, CommandSource source) throws CommandException {

        Player player = plugin.getEngine().getPlayer( source.getName(), true );

        if ( player == null ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("You must be a Player!", source)) );
            return;
        }

        if ( args.length() < 2 ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}/admin kingdom removepoints <name> <points>", player)) );
            return;
        }

        Kingdom kingdom = kingdomManager.getKingdomByName(args.getString(0));

        if ( kingdom == null ) {
            player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{RED}}There is no Kingdom with this Name!", player)) );
            return;
        }

        kingdom.removePoints( args.getInteger(1) );

        player.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}Removed Points!", player)) );
    }

}
