package io.jmix.tests.ui.test.dynattr

import io.jmix.masquerade.component.Notification
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor
import io.jmix.tests.ui.screen.administration.dynattr.CategoryEditor
import io.jmix.tests.ui.screen.administration.dynattr.DynamicAttributeBrowse
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Condition.text
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,dynattr'])
@ContextConfiguration(initializers = TestContextInitializer)
class CategoryAttributeActionsTest extends BaseUiTest {

    public static final String GAS = 'Gas'
    public static final String FIRST = "First"
    public static final String SECOND = "Second"
    public static final String DEFAULT_VALUE_INT = "123"
    public static final String THIRD = "Third"

    @Test
    @DisplayName("Creates category attribute")
    void createCategoryAttribute() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                createAttribute()
                $j(CategoryAttributeEditor).with {
                    saveChanges()
                    $j(Notification.class)
                            .shouldBe(VISIBLE)
                    setName(FIRST)
                    setRequired(true)
                    checkCode(GAS, FIRST)
                    setType(STRING)
                    [lookupField, isCollectionField, widthField, rowsCount, defaultStringValue]
                            .each {
                                it.shouldBe(VISIBLE, EDITABLE)
                            }
                    saveChanges()
                }
                checkCategoryAttribute(FIRST, GAS + FIRST)
                saveCategory()
            }
            getCategoriesTable()
                    .selectRow(byCells(GAS))
            checkAttributeTable(FIRST)
        }
    }

    @Test
    @DisplayName("Edits category attribute")
    void editCategoryAttribute() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                editAttribute(THIRD)
                $j(CategoryAttributeEditor).with {
                    defaultIntField
                            .shouldBe(VISIBLE, EDITABLE)
                            .setValue(DEFAULT_VALUE_INT)
                    saveChanges()
                }
                categoryAttrsTable
                        .shouldBe(VISIBLE)
                        .getRow(byCells(THIRD))
                        .shouldBe(VISIBLE)
                        .shouldHave(text(DEFAULT_VALUE_INT))
                saveCategory()
            }
            getCategoriesTable()
                    .selectRow(byCells(GAS))
            checkAttributeTable(THIRD)
        }
    }

    @Test
    @DisplayName("Removes category attribute")
    void removeCategoryAttribute() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                categoryAttrsTable
                        .shouldBe(VISIBLE)
                        .selectRow(byCells(SECOND))
                removeBtn.shouldBe(ENABLED)
                        .click()
                $j(ConfirmationDialog).confirmChanges()
                categoryAttrsTable
                        .shouldBe(VISIBLE)
                        .getRow(byCells(SECOND))
                        .shouldNotBe(VISIBLE)
                saveCategory()
            }
            getCategoriesTable()
                    .selectRow(byCells(GAS))
            attributesTable
                    .shouldBe(VISIBLE)
                    .getRow(byCells(SECOND))
                    .shouldNotBe(VISIBLE)
        }
    }
}
