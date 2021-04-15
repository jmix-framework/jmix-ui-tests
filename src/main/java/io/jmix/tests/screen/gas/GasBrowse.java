package io.jmix.tests.screen.gas;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.atmosphere.Gas;

@UiController("Gas.browse")
@UiDescriptor("gas-browse.xml")
@LookupComponent("gasesTable")
public class GasBrowse extends StandardLookup<Gas> {
}
