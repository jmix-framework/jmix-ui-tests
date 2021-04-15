package io.jmix.tests.screen.spaceport;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spaceport.SpacePort;

@UiController("SpacePort.browse")
@UiDescriptor("space-port-browse.xml")
@LookupComponent("spacePortsTable")
public class SpacePortBrowse extends StandardLookup<SpacePort> {
}
