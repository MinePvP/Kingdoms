package ch.minepvp.spout.kingdoms.command.admin;

import ch.minepvp.spout.kingdoms.Kingdoms;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.NestedCommand;
import org.spout.api.exception.CommandException;

public class AdminCommands {

    private final Kingdoms plugin;

    public AdminCommands( Kingdoms instance) {
        plugin = instance;
    }

    @Command(aliases = {"kingdom", "king"}, usage = "", desc = "Kingdom Commands", min = 1, max = 4)
    @NestedCommand(AdminKingdomsCommands.class)
    public void kingdom(CommandContext args, CommandSource source) throws CommandException {

    }

    @Command(aliases = {"member"}, usage = "", desc = "Kingdom Commands", min = 1, max = 4)
    @NestedCommand(AdminMemberCommans.class)
    public void member(CommandContext args, CommandSource source) throws CommandException {

    }

    @Command(aliases = {"plot"}, usage = "", desc = "Kingdom Commands", min = 1, max = 4)
    @NestedCommand(AdminPlotCommands.class)
    public void plot(CommandContext args, CommandSource source) throws CommandException {

    }

    @Command(aliases = {"zone"}, usage = "", desc = "Kingdom Commands", min = 1, max = 4)
    @NestedCommand(AdminZoneCommands.class)
    public void zone(CommandContext args, CommandSource source) throws CommandException {

    }

}
