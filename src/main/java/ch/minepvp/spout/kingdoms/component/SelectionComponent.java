package ch.minepvp.spout.kingdoms.component;

import ch.minepvp.spout.kingdoms.protection.Selection;
import org.spout.api.component.components.EntityComponent;
import org.spout.api.entity.Player;

public class SelectionComponent extends EntityComponent {

    private Selection selection;

    @Override
    public void onAttached() {
        selection = new Selection( (Player)getOwner() );
    }

    public Selection getSelection() {
        return selection;
    }

}
