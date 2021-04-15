package io.jmix.tests.screen.individual;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Individual;

@UiController("Individual.edit")
@UiDescriptor("individual-edit.xml")
@EditedEntityContainer("individualDc")
public class IndividualEdit extends StandardEditor<Individual> {
}
