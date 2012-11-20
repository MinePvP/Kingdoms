package ch.minepvp.spout.kingdoms.component;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import org.spout.api.component.components.EntityComponent;
import org.spout.api.entity.Player;

public class KingdomsComponent extends EntityComponent {

    public Kingdom getKingdom() {
        return Kingdoms.getInstance().getKingdomManager().getKingdomByPlayer( (Player)getOwner() );
    }

    public Member getMember() {
        return Kingdoms.getInstance().getMemberManager().getMemberByPlayer( (Player)getOwner() );
    }

}
