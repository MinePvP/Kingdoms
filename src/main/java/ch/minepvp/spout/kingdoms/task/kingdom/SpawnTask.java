package ch.minepvp.spout.kingdoms.task.kingdom;

import ch.minepvp.spout.kingdoms.task.Task;
import org.spout.api.chat.ChatArguments;
import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;
import org.spout.api.lang.Translation;

public class SpawnTask extends Task {

    private Player player;
    private Point point;

    private int lastX;
    private int lastY;
    private int lastZ;

    public SpawnTask( Player player, Point point ) {
        this.player = player;
        this.point = point;

        lastX = player.getScene().getPosition().getBlockX();
        lastY = player.getScene().getPosition().getBlockY();
        lastZ = player.getScene().getPosition().getBlockZ();
    }

    @Override
    public void run() {

        if ( player.getScene().getPosition().getBlockX() == lastX &&
             player.getScene().getPosition().getBlockY() == lastY &&
             player.getScene().getPosition().getBlockZ() == lastZ ) {

            player.teleport( point );

        } else {
            player.sendMessage(ChatArguments.fromFormatString(Translation.tr("{{RED}}Teleportation aborted! You have moved!", player)));
        }

        setActive(false);

    }

}
