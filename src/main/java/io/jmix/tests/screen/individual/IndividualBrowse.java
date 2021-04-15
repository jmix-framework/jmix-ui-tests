package io.jmix.tests.screen.individual;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Individual;

@UiController("Individual.browse")
@UiDescriptor("individual-browse.xml")
@LookupComponent("individualsTable")
public class IndividualBrowse extends StandardLookup<Individual> {
}
