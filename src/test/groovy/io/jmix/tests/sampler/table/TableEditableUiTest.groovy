package io.jmix.tests.sampler.table

import io.jmix.masquerade.component.CheckBox
import io.jmix.masquerade.component.ComboBox
import io.jmix.masquerade.component.Table
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.sampler.BaseSamplerUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import static io.jmix.masquerade.Conditions.EDITABLE
import static io.jmix.masquerade.Selectors.*

@ExtendWith(ChromeExtension)
class TableEditableUiTest extends BaseSamplerUiTest {

    public static final String GRADE_VALUE = "Premium"
    public static final String AGE_VALUE = '33'

    @Test
    @DisplayName("Check edit row of editable table")
    void checkTableEditRowsValue() {
        openSample('table-editable')
        def row = $j(Table.class, 'customerTable')
                .getRow(byCells('Potter'))
        row.$(byJTestId('age'))
                .setValue(AGE_VALUE)
        //TODO https://github.com/Haulmont/jmix-masquerade/issues/6
        $j(CheckBox.class, 'active')
                .shouldBe(EDITABLE)
                .setChecked(false)
                .setChecked(false)
        $j(ComboBox.class, 'grade')
                .shouldBe(EDITABLE)
                .openOptionsPopup()
                .select(GRADE_VALUE)
    }
}
