package io.jmix.tests.screen.planet;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spacebody.Planet;

@UiController("Planet.browse")
@UiDescriptor("planet-browse.xml")
@LookupComponent("planetsTable")
public class PlanetBrowse extends StandardLookup<Planet> {
}