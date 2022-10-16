package io.jmix.tests.ui.screen.administration.businesscalendars.editor

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.TabSheet
import io.jmix.masquerade.component.TextField

class BusinessCalendarEditor extends Composite<BusinessCalendarEditor> {

    @Wire
    TextField nameField

    @Wire
    TextField codeField

    @Wire
    TextField sourceField

    @Wire
    TabSheet workingScheduleTabId

    @Wire
    TabSheet additionalBusinessDaysTabId

    @Wire
    Button create

    @Wire
    Button edit

    @Wire
    Button remove

    @Wire(path = 'commitAndCloseBtn')
    Button ok

    @Wire(path = 'closeBtn')
    Button close
}
