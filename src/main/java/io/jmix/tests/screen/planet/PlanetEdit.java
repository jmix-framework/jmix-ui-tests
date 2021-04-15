package io.jmix.tests.screen.planet;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spacebody.Planet;

@UiController("Planet.edit")
@UiDescriptor("planet-edit.xml")
@EditedEntityContainer("planetDc")
public class PlanetEdit extends StandardEditor<Planet> {
}
