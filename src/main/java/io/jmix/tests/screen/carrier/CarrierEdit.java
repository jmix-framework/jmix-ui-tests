package io.jmix.tests.screen.carrier;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spaceport.Carrier;

@UiController("Carrier.edit")
@UiDescriptor("carrier-edit.xml")
@EditedEntityContainer("carrierDc")
public class CarrierEdit extends StandardEditor<Carrier> {
}