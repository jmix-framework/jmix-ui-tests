package io.jmix.tests.screen.moon;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.spacebody.Moon;

@UiController("Moon.browse")
@UiDescriptor("moon-browse.xml")
@LookupComponent("moonsTable")
public class MoonBrowse extends StandardLookup<Moon> {
}
