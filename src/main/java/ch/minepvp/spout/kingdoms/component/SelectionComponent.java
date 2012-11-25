package ch.minepvp.spout.kingdoms.component;

import ch.minepvp.spout.kingdoms.protection.Selection;
import org.spout.api.component.components.EntityComponent;

public class SelectionComponent extends EntityComponent {

    private Selection selection;

    @Override
    public void onAttached() {
        selection = new Selection();
    }

    public Selection getSelection() {
        return selection;
    }

}
