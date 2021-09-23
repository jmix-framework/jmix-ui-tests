package io.jmix.tests.screen.localizeddynamic;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.LocalizedDynamic;

@UiController("LocalizedDynamic.browse")
@UiDescriptor("localized-dynamic-browse.xml")
@LookupComponent("localizedDynamicsTable")
public class LocalizedDynamicBrowse extends StandardLookup<LocalizedDynamic> {
}