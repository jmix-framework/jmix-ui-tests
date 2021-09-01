package io.jmix.tests.ui.screen.search.personProgrammicMapping


import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.FileUploadField
import io.jmix.masquerade.component.TextField

import static io.jmix.masquerade.Conditions.VISIBLE

class PersonProgrammicMappingEditor extends Composite<PersonProgrammicMappingEditor> {
    @Wire
    TextField firstNameField

    @Wire
    TextField lastNameField

    @Wire
    TextField descriptionField

    @Wire
    TextField addressStreetField

    @Wire
    TextField addressCityField

    @Wire
    TextField addressCountryField

    @Wire
    ComboBox gradeField

    @Wire
    ComboBox userField

    @Wire
    FileUploadField pictureField

    @Wire
    Button commitAndCloseBtn

    void saveChanges() {
        commitAndCloseBtn
                .shouldBe(VISIBLE)
                .click()
    }
}
