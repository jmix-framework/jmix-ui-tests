package io.jmix.tests.ui.screen.administration.businesscalendars.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.DateField
import io.jmix.masquerade.component.DateTimeField
import org.openqa.selenium.support.FindBy

class AdditionalBusinessDayEditor extends Composite<AdditionalBusinessDayEditor> {

    @Wire
    DateField fixedDateField

    @Wire
    DateTimeField startTimeField

    @Wire
    DateTimeField endTimeField

    @FindBy(xpath = '//div[@class=\'popupContent\']//*[text()=\'OK\']')
    Button commitAndCloseBtn

    @FindBy(xpath = '//div[@class=\'popupContent\']//*[text()=\'Close\']')
    Button closeBtn
}
