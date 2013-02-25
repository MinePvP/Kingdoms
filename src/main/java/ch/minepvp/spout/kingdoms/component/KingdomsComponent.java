package ch.minepvp.spout.kingdoms.component;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import ch.minepvp.spout.kingdoms.database.table.Zone;
import org.spout.api.chat.ChatArguments;
import org.spout.api.component.impl.SceneComponent;
import org.spout.api.component.type.EntityComponent;
import org.spout.api.entity.Player;
import org.spout.api.lang.Translation;
import org.spout.vanilla.component.entity.living.neutral.Human;

public class KingdomsComponent extends EntityComponent {

    public Kingdom getKingdom() {
        return Kingdoms.getInstance().getKingdomManager().getKingdomByPlayer( (Player)getOwner() );
    }

    public Member getMember() {
        return Kingdoms.getInstance().getMemberManager().getMemberByPlayer( (Player)getOwner() );
    }

    @Override
    public void onTick( float dt ) {

        Player player = (Player)getOwner();
        SceneComponent scene = getOwner().getScene();

        if ( player.get(Human.class).getLivePosition() == null ) {
            return;
        }

        if ( scene.getPosition().getBlockX() == player.get(Human.class).getLivePosition().getBlockX() &&
             scene.getPosition().getBlockY() == player.get(Human.class).getLivePosition().getBlockY() &&
             scene.getPosition().getBlockZ() == player.get(Human.class).getLivePosition().getBlockZ() ) {

            return;
        }

        // Kingdom
        Kingdom lastKingdom = Kingdoms.getInstance().getKingdomManager().getKingdomByPoint( scene.getPosition() );
        Kingdom nowKingdom = Kingdoms.getInstance().getKingdomManager().getKingdomByPoint( player.get(Human.class).getLivePosition() );

        if ( lastKingdom == null ) {

            if ( nowKingdom != null ) {

                player.sendMessage(ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You are now in Kingdom %0", player, nowKingdom.getName()) ));

            }

        } else {

            if ( nowKingdom == null ) {

               player.sendMessage(ChatArguments.fromFormatString( Translation.tr("{{RED}}You leave the Kingdom %0", player, lastKingdom.getName()) ));

            }

        }

        // Zone
        Zone lastZone = Kingdoms.getInstance().getZoneManager().getZoneByPoint( scene.getPosition() );
        Zone nowZone = Kingdoms.getInstance().getZoneManager().getZoneByPoint( player.get(Human.class).getLivePosition() );

        if ( lastZone == null ) {

            if ( nowZone != null ) {

                player.sendMessage(ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You are now in Zone %0", player, nowZone.getName()) ));

            }

        } else {

            if ( nowZone == null ) {

                player.sendMessage(ChatArguments.fromFormatString( Translation.tr("{{RED}}You leave the Zone %0", player, lastZone.getName()) ));

            }

        }

    }

}
