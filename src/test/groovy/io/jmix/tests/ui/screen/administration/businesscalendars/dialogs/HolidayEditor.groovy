package io.jmix.tests.ui.screen.administration.businesscalendars.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.TextField

class HolidayEditor extends Composite<HolidayEditor> {

    @Wire(path = 'holidayTypeComboBox')
    ComboBox holidayType

    @Wire(path = 'dayOfWeekCombobox')
    ComboBox dayOfWeek

    @Wire
    TextField descriptionField
}
