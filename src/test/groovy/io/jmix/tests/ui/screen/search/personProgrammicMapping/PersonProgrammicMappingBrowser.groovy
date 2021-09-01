package io.jmix.tests.ui.screen.search.personProgrammicMapping

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table
import io.jmix.tests.ui.screen.search.personAdvancedMapping.PersonAdvancedMappingEditor

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.byCells

class PersonProgrammicMappingBrowser extends Composite<PersonProgrammicMappingBrowser> {

    @Wire
    Table personProgrammingMappingsTable

    @Wire
    Button createBtn

    @Wire
    Button editBtn

    PersonAdvancedMappingEditor createPerson() {
        createBtn
                .shouldBe(VISIBLE)
                .click()
        return new PersonAdvancedMappingEditor()
    }

    PersonAdvancedMappingEditor editPerson(String name) {
        personProgrammingMappingsTable
                .shouldBe(VISIBLE)
                .selectRow(byCells(name))
        editBtn
                .shouldBe(VISIBLE)
                .click()
        return new PersonAdvancedMappingEditor()
    }
}
