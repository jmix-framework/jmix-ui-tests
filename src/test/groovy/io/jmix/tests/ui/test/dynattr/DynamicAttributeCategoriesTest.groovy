package io.jmix.tests.ui.test.dynattr

import io.jmix.masquerade.component.Notification
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.dynattr.CategoryEditor
import io.jmix.tests.ui.screen.administration.dynattr.DynamicAttributeBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,dynattr'])
@ContextConfiguration(initializers = TestContextInitializer)
class DynamicAttributeCategoriesTest extends BaseUiTest {

    public static final String CUSTOMER_TYPE = "Customer (Customer)"
    public static final String CUSTOMER = "Customer"
    public static final String CARRIER = "Carrier"
    public static final String CARRIER_TYPE = "Carrier (Carrier)"
    public static final String NEW_CARRIER = "New_Carrier"
    public static final String DISCOUNTS = 'DiscountsForDelete'
    public static final String TRUE = "true"
    public static final String FALSE = "false"

    @Test
    @DisplayName("Creates dynamic attribute category")
    void createDynamicAttributeCategory() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            createCategory()
            $j(CategoryEditor).with {
                saveCategory()
                $j(Notification.class)
                        .shouldBe(VISIBLE)
                setEntityType(CUSTOMER_TYPE)
                setName(CUSTOMER)
                setDefault(true)
                saveCategory()
            }
            checkCategory(CUSTOMER, TRUE)
        }
    }

    @Test
    @DisplayName("Edits dynamic attribute category")
    void editDynamicAttributeCategory() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(CARRIER)
            $j(CategoryEditor).with {
                setName(NEW_CARRIER)
                setDefault(true)
                saveCategory()
            }
            checkCategory(NEW_CARRIER, TRUE)
            applyChanges()

            //check that isDefault value resets in rest categories with the same entity type
            createCategory()
            $j(CategoryEditor).with {
                setEntityType(CARRIER_TYPE)
                setName(CARRIER)
                setDefault(true)
                saveCategory()
            }
            checkCategory(NEW_CARRIER, FALSE)
        }
    }

    @Test
    @DisplayName("Removes dynamic attribute category")
    void removeDynamicAttributeCategory() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            getCategoriesTable()
                    .selectRow(byCells(DISCOUNTS))
                    .shouldBe(VISIBLE)
            removeCategory()
            getCategoriesTable()
                    .getRow(byCells(DISCOUNTS))
                    .shouldNotBe(VISIBLE)
        }
    }
}
