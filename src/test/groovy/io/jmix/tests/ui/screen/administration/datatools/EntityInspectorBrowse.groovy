package io.jmix.tests.ui.screen.administration.datatools

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.ComboBox
import io.jmix.tests.ui.screen.administration.datatools.traits.TableActionsTrait
import io.jmix.masquerade.component.Table

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.byText

class EntityInspectorBrowse extends Composite<EntityInspectorBrowse> implements TableActionsTrait {
    @Wire
    ComboBox entitiesLookup

    @Wire
    ComboBox showMode

    @Wire(path = 'IndividualTable_composition')
    Table individualTable

    @Wire
    Button create

    @Wire
    Button edit

    @Wire
    Button remove

    @Wire
    Button restore

    @Wire
    Button wipeOut

    @Wire
    Button excelExport

    void findEntityByFilter(String filterValue, String finalValue) {
        entitiesLookup.shouldBe(VISIBLE)
                .setFilter(filterValue)
                .getOptionsPopup()
                .select(byText(finalValue))
                .shouldHave(value(finalValue))
    }

    void selectShowMode(String mode) {
        showMode.shouldBe(VISIBLE)
                .openOptionsPopup()
                .select(byText(mode))
                .shouldHave(value(mode))
    }
}
