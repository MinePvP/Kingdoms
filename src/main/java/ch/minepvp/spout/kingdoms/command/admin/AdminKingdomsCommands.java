package ch.minepvp.spout.kingdoms.command.admin;

import ch.minepvp.spout.kingdoms.Kingdoms;
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

}
