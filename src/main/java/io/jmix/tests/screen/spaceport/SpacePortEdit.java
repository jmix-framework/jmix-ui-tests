package io.jmix.tests.screen.spaceport;

import io.jmix.tests.entity.spacebody.Moon;
import io.jmix.tests.entity.spacebody.Planet;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spaceport.SpacePort;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("SpacePort.edit")
@UiDescriptor("space-port-edit.xml")
@EditedEntityContainer("spacePortDc")
public class SpacePortEdit extends StandardEditor<SpacePort> {
    @Autowired
    private Notifications notifications;

    @Autowired
    private EntityPicker<Planet> planetField;

    @Autowired
    private EntityPicker<Moon> moonField;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (!moonField.isEmpty() && !planetField.isEmpty()) {
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Место расположения порта может быть лишь одно").show();
            event.preventCommit();
        }
        if (moonField.isEmpty() && planetField.isEmpty()) {
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Не выбрано место расположения порта").show();
            event.preventCommit();
        }
    }
}