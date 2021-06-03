package io.jmix.tests.ui.screen.application.individual

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog

import static io.jmix.masquerade.Conditions.ENABLED
import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells

class IndividualBrowse extends Composite<IndividualBrowse> {
    @Wire(path = 'createBtn')
    Button create

    @Wire
    Table individualsTable

    @Wire
    Button lookupSelectAction

    @Wire(path = "editBtn")
    Button edit

    @Wire(path = "removeBtn")
    Button remove

    void editIndividual(String name) {
        individualsTable.shouldBe(VISIBLE)
                .selectRow(byCells(name))
                .shouldBe(VISIBLE)
        edit.shouldBe(ENABLED)
                .click()
    }

    void removeIndividual(String name) {
        individualsTable.shouldBe(VISIBLE)
                .selectRow(byCells(name))
                .shouldBe(VISIBLE)
        remove.shouldBe(ENABLED)
                .click()
        $j(ConfirmationDialog).confirmChanges()
        individualsTable.shouldBe(VISIBLE)
                .getRow(byCells(name))
                .shouldNotBe(VISIBLE)
    }

    void checkEntitySaved(String firstName, String lastName) {
        individualsTable.shouldBe(VISIBLE)
                .getRow(byCells(firstName, lastName))
                .shouldBe(VISIBLE)
    }
}
