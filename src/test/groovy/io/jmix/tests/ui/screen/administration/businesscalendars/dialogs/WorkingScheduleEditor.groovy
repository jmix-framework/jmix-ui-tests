package io.jmix.tests.ui.screen.administration.businesscalendars.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.DateTimeField
import org.openqa.selenium.support.FindBy

class WorkingScheduleEditor extends Composite<WorkingScheduleEditor> {

    @Wire
    DateTimeField startTimeField

    @Wire
    DateTimeField endTimeField

    @Wire
    Button addAnotherIntervalLinkButton

    @FindBy(xpath = '//*[@class=\'v-slot v-slot-link v-align-center v-align-middle\']')
    Button deleteIcon

    @FindBy(xpath = '//*[@class=\'v-horizontallayout v-layout v-horizontal v-widget v-has-height\']//*[span=\'OK\']')
    Button commitAndCloseBtn

    @FindBy(xpath = '//*[@class=\'v-horizontallayout v-layout v-horizontal v-widget v-has-height\']//*[span=\'Close\']')
    Button closeBtn
}
