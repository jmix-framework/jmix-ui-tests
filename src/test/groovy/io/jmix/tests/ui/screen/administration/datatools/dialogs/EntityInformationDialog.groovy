package io.jmix.tests.ui.screen.administration.datatools.dialogs

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table
import io.jmix.masquerade.component.TextArea

import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byText

class EntityInformationDialog extends Composite<EntityInformationDialog> {
    @Wire
    Button insertBtn

    @Wire
    Button updateBtn

    @Wire
    Button selectBtn

    @Wire
    Button copyBtn

    @Wire
    Table infoTable

    void checkTableAndEntityName(String entityName) {
        infoTable.shouldBe(VISIBLE)
                .getRow(byText(entityName))
                .shouldBe(VISIBLE)
    }

    static void checkBtnCaptionAndClick(Button button, String btnCaption) {
        button.shouldBe(VISIBLE)
                .shouldHave(caption(btnCaption))
                .click()
    }

    static void checkScriptArea(String jTestId, String value) {
        $j(TextArea, jTestId).shouldBe(VISIBLE)
                .shouldHave(valueContains(value))
    }
}
