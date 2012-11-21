package ch.minepvp.spout.kingdoms.component;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import org.spout.api.chat.ChatArguments;
import org.spout.api.component.components.EntityComponent;
import org.spout.api.component.components.TransformComponent;
import org.spout.api.entity.Player;
import org.spout.api.lang.Translation;

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
        TransformComponent transform = getOwner().getTransform();

        if ( transform.getPosition().getBlockX() == transform.getTransformLive().getPosition().getBlockX() &&
             transform.getPosition().getBlockY() == transform.getTransformLive().getPosition().getBlockY() &&
             transform.getPosition().getBlockZ() == transform.getTransformLive().getPosition().getBlockZ() ) {

            return;
        }

        Kingdom lastKingdom = Kingdoms.getInstance().getKingdomManager().getKingdomByPoint( transform.getPosition() );
        Kingdom nowKingdom = Kingdoms.getInstance().getKingdomManager().getKingdomByPoint( transform.getTransformLive().getPosition() );

        if ( lastKingdom == null ) {

            if ( nowKingdom != null ) {

                player.sendMessage(ChatArguments.fromFormatString( Translation.tr("You are now in Kingdom %0", player, nowKingdom.getName()) ));

            }

        } else {

            if ( nowKingdom == null ) {

               player.sendMessage(ChatArguments.fromFormatString( Translation.tr("You leave the Kingdom %0", player, lastKingdom.getName()) ));

            }

        }


    }

}
