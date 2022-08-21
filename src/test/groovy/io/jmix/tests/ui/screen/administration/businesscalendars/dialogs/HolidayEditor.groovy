package io.jmix.tests.ui.screen.administration.businesscalendars.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class HolidayEditor extends Composite<HolidayEditor> {

    @Wire(path = 'holidayTypeComboBox')
    ComboBox holidayType

    @Wire
    TextField descriptionField

    @Wire(path = 'commitAndCloseBtn')
    Button ok

    @Wire(path = 'closeBtn')
    Button close
}
