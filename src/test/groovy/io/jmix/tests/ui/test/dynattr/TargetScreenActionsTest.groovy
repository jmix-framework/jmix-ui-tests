package io.jmix.tests.ui.test.dynattr

import com.codeborne.selenide.Selenide
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor
import io.jmix.tests.ui.screen.administration.dynattr.CategoryEditor
import io.jmix.tests.ui.screen.administration.dynattr.DynamicAttributeBrowse
import io.jmix.tests.ui.screen.application.gas.GasBrowse
import io.jmix.tests.ui.screen.application.gas.GasEditor
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Selenide.$

import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*
import static io.jmix.tests.ui.test.dynattr.CategoryAttributeActionsTest.GAS
import static io.jmix.tests.ui.test.dynattr.CategoryAttributeActionsTest.THIRD

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,dynattr'])
@ContextConfiguration(initializers = TestContextInitializer)
class TargetScreenActionsTest extends BaseUiTest {

    public static final String GAS_BROWSER = "Gas browser (Gas.browse)"
    public static final String GAS_EDITOR = "Gas editor (Gas.edit)"
    public static final String COMPONENT = 'component'
    public static final String GAS_FORM_LAST = 'gasFormLast'
    public static final String GAS_FORM = 'gasForm'
    public static final String GAS_EDITOR_DEFAULT_FORM = 'form'
    public static final String THIRD_ATTR = '+GasThird'
    public static final String INTEGER = "Integer"

    @Test
    @DisplayName("Creates category attribute")
    void editCategoryAttribute() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                editAttribute(THIRD)
                $j(CategoryAttributeEditor).with {
                    openVisibilityTab()
                    removeTargetScreen
                            .shouldBe(VISIBLE, DISABLED)
                    //create target screen
                    addTargetScreen()
                    setScreen(0, GAS_BROWSER)
                    addTargetScreen()
                    setScreen(1, GAS_EDITOR)

                    //remove target screen
                    targetScreensTable.selectRow(byIndex(0))
                    removeTargetScreen.shouldBe(ENABLED)
                            .click()
                    $j(ConfirmationDialog).confirmChanges()
                    targetScreensTable.getRow(byCells(GAS_BROWSER))
                            .shouldNotBe(VISIBLE)

                    //edit component of target screen
                    targetScreensTable.selectRow(byIndex(0))
                            .$(byJTestId(COMPONENT))
                            .setValue(GAS_FORM)
                    saveChanges()
                }
                checkCategoryAttribute(THIRD, INTEGER)
                saveCategory()
            }
            applyChanges()
            getCategoriesTable()
                    .selectRow(byCells(GAS))
            checkAttributeTable(THIRD)
        }
        $j(MainScreen).openGasBrowse()
        Selenide.sleep(5000)
        $j(GasBrowse).create()
        $j(GasEditor).with {
            shouldBe(VISIBLE)
            $(byChain(byJTestId(GAS_FORM), byJTestId(THIRD_ATTR)))
                    .shouldBe(VISIBLE)
        }

        //edit target screen - edit component id
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                editAttribute(THIRD)
                $j(CategoryAttributeEditor).with {
                    openVisibilityTab()
                    targetScreensTable.selectRow(byIndex(0))
                            .$(byJTestId(COMPONENT))
                            .setValue(GAS_FORM_LAST)

                    saveChanges()
                }
                checkCategoryAttribute(THIRD, INTEGER)
                saveCategory()
            }
            applyChanges()
            getCategoriesTable()
                    .selectRow(byCells(GAS))
            checkAttributeTable(THIRD)
        }
        $j(MainScreen).openGasBrowse()
        Selenide.sleep(5000)
        $j(GasBrowse).create()
        $j(GasEditor).with {
            $(byChain(byJTestId(GAS_FORM_LAST), byJTestId(THIRD_ATTR)))
                    .shouldBe(VISIBLE)
        }
    }
}
