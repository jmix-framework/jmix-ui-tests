package io.jmix.tests.ui.screen.administration.businesscalendars.browse;

import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait;

class BusinessCalendarsBrowse extends Composite<BusinessCalendarsBrowse> implements TableActionsTrait {

    @Wire
    Button createBtn

    @Wire
    Button editBtn

    @Wire
    Button removeBtn
}

