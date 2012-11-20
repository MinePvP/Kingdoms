package ch.minepvp.spout.kingdoms.command;

import ch.minepvp.spout.kingdoms.Kingdoms;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.exception.CommandException;
import org.spout.api.lang.Translation;

public class PlotCommands {

    private final Kingdoms plugin;

    public PlotCommands(Kingdoms instance) {
        plugin = instance;
    }

    @Command(aliases = {"help"}, usage = "", desc = "List all Commands for /plot")
    @CommandPermissions("kingdoms.command.plot.help")
    public void help(CommandContext args, CommandSource source) throws CommandException {

        source.sendMessage( ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------") );
        source.sendMessage( ChatArguments.fromFormatString("{YELLOW}Help") );
        source.sendMessage( ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------") );

        if ( source.hasPermission("kingdoms.command.plot.create") ) {
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{WHITE}}/plot create <name>", source)) );
            source.sendMessage( ChatArguments.fromFormatString(Translation.tr("{{GOLD}}-> {{GRAY}}Create a new Plot", source)) );
        }

        source.sendMessage( ChatArguments.fromFormatString("{BLUE}-----------------------------------------------------") );

    }
}
