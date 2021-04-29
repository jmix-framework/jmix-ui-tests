package io.jmix.tests.screen.atmosphere;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.atmosphere.Atmosphere;

@UiController("Atmosphere.browse")
@UiDescriptor("atmosphere-browse.xml")
@LookupComponent("atmospheresTable")
public class AtmosphereBrowse extends StandardLookup<Atmosphere> {
}