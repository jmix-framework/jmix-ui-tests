package io.jmix.tests.screen.atmosphere;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.atmosphere.Atmosphere;

@UiController("Atmosphere.edit")
@UiDescriptor("atmosphere-edit.xml")
@EditedEntityContainer("atmosphereDc")
public class AtmosphereEdit extends StandardEditor<Atmosphere> {
}