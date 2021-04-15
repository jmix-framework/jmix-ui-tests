package io.jmix.tests.screen.spaceport;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spaceport.SpacePort;

@UiController("SpacePort.edit")
@UiDescriptor("space-port-edit.xml")
@EditedEntityContainer("spacePortDc")
public class SpacePortEdit extends StandardEditor<SpacePort> {
}
