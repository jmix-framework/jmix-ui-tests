package io.jmix.tests.ui.test.dynattr

import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.PostgreSQLContextInitializer
import io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor
import io.jmix.tests.ui.screen.administration.dynattr.CategoryEditor
import io.jmix.tests.ui.screen.application.gas.GasEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.containOptions
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells
import static io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor.USER
import static io.jmix.tests.ui.test.dynattr.CategoryAttributeActionsTest.GAS
import static io.jmix.tests.ui.test.dynattr.TargetScreenActionsTest.GAS_FORM

@ExtendWith([
        SpringBootExtension,
        ChromeExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,dynattr'])
@ContextConfiguration(initializers = PostgreSQLContextInitializer)
class DynamicAttributeCalculatedValuesTest extends BaseUiTest {

    public static final String CALCULATED_ATTRIBUTE_NAME = "Calculated"
    public static final String JPQL_CONDITION = "{E}.enabled = 'false'"
    public static final String JPQL_CONDITION_EDITED = "{E}.enabled = 'true'"
    public static final String LOOKUP_ATTR_USER = "Lookup Attr [LookupAttr]"
    public static final String ADMIN = "[admin]"

    @Test
    @DisplayName("Checks calculated values for entity attribute")
    void checkCalculatedValues() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        createAttribute()
                        $j(CategoryAttributeEditor).with {
                            shouldBe(VISIBLE)
                            setName(CALCULATED_ATTRIBUTE_NAME)
                            setType(ENTITY_TYPE)
                            lookupField
                                    .shouldBe(VISIBLE)
                                    .setChecked(true)
                            entityClassField
                                    .setValue("User (User)")
                            setWhereClauseForCalculatedJpql(JPQL_CONDITION)
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute(CALCULATED_ATTRIBUTE_NAME, USER)
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable()
                            .selectRow(byCells(GAS))
                    checkAttributeTable(CALCULATED_ATTRIBUTE_NAME)
                }
        checkCalculatedValuesEntity(LOOKUP_ATTR_USER, ADMIN)
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(CALCULATED_ATTRIBUTE_NAME)
                        $j(CategoryAttributeEditor).with {
                            setWhereClauseForCalculatedJpql(JPQL_CONDITION_EDITED)
                            saveChanges()
                        }
                        checkCategoryAttribute(CALCULATED_ATTRIBUTE_NAME, USER)
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable()
                            .selectRow(byCells(GAS))
                    checkAttributeTable(CALCULATED_ATTRIBUTE_NAME)
                }
        checkCalculatedValuesEntity(ADMIN, LOOKUP_ATTR_USER)
    }

    private void checkCalculatedValuesEntity(String visibleValue, String nonVisibleValue) {
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            shouldBe(VISIBLE)
            calculatedAttribute
                    .shouldBe(VISIBLE)
                    .openOptionsPopup()
                    .shouldHave(containOptions(visibleValue))
                    .shouldNotHave(containOptions(nonVisibleValue))
            close()
        }
    }
}
