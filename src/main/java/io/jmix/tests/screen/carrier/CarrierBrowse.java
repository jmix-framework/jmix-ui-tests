package io.jmix.tests.screen.carrier;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spaceport.Carrier;

@UiController("Carrier.browse")
@UiDescriptor("carrier-browse.xml")
@LookupComponent("carriersTable")
public class CarrierBrowse extends StandardLookup<Carrier> {
}