package ch.minepvp.spout.kingdoms.component;

import ch.minepvp.spout.kingdoms.Kingdoms;
import ch.minepvp.spout.kingdoms.database.table.Kingdom;
import ch.minepvp.spout.kingdoms.database.table.Member;
import org.spout.api.chat.ChatArguments;
import org.spout.api.component.impl.SceneComponent;
import org.spout.api.component.type.EntityComponent;
import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Transform;
import org.spout.api.lang.Translation;
import org.spout.vanilla.plugin.component.living.neutral.Human;

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

        if ( scene.getPosition().getBlockX() == scene.getTransform().getPosition().getBlockX() &&
             scene.getPosition().getBlockY() == scene.getTransform().getPosition().getBlockY() &&
             scene.getPosition().getBlockZ() == scene.getTransform().getPosition().getBlockZ() ) {

            return;
        }

        Kingdom lastKingdom = Kingdoms.getInstance().getKingdomManager().getKingdomByPoint( scene.getPosition() );
        Kingdom nowKingdom = Kingdoms.getInstance().getKingdomManager().getKingdomByPoint( player.add(Human.class).getLivePosition() );

        if ( lastKingdom == null ) {

            if ( nowKingdom != null ) {

                player.sendMessage(ChatArguments.fromFormatString( Translation.tr("{{GOLD}}You are now in Kingdom %0", player, nowKingdom.getName()) ));

            }

        } else {

            if ( nowKingdom == null ) {

               player.sendMessage(ChatArguments.fromFormatString( Translation.tr("{{RED}}You leave the Kingdom %0", player, lastKingdom.getName()) ));

            }

        }


    }

}
