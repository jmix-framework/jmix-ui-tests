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
import io.jmix.tests.ui.screen.application.dymanicCategories.DynamicCategoryBrowse
import io.jmix.tests.ui.screen.application.dymanicCategories.DynamicCategoryEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Condition.empty

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,dynattr'])
@ContextConfiguration(initializers = TestContextInitializer)
class CategorizedEntityTest extends BaseUiTest {

    public static final String DYNAMIC_CATEGORY_ENTITY = "Dynamic category (DynamicCategory)"
    public static final String DYNAMIC_CATEGORY = "Dynamic category"
    public static final String CATEGORIZED = 'Categorized'

    @Test
    @DisplayName("Checks category display for categorized entity ")
    void checkCategoryDisplay() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            createCategory()
            $j(CategoryEditor).with {
                entityType.shouldBe(VISIBLE)
                        .setFilter(DYNAMIC_CATEGORY_ENTITY)
                        .getOptionsPopup()
                        .select(DYNAMIC_CATEGORY_ENTITY)
                        .shouldHave(value(DYNAMIC_CATEGORY_ENTITY))
                setName(DYNAMIC_CATEGORY)
                createAttribute()
                $j(CategoryAttributeEditor).with {
                    setName(CATEGORIZED)
                    setType(STRING)
                    saveChanges()
                }
                saveCategory()
            }
            applyChanges()
        }
        $j(MainScreen)
                .openDynamicCategoryBrowse()
        Selenide.sleep(5000)
        $j(DynamicCategoryBrowse)
                .create()
        $j(DynamicCategoryEditor).with {
            shouldBe(VISIBLE)
            category
                    .shouldBe(VISIBLE)
                    .shouldBe(empty)
                    .openOptionsPopup()
                    .select(DYNAMIC_CATEGORY)
            categorized
                    .shouldBe(VISIBLE)
        }

        //Edit category - set default = true
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(DYNAMIC_CATEGORY)
            $j(CategoryEditor).with {
                isDefault
                        .shouldBe(VISIBLE)
                        .setChecked(true)
                saveCategory()
            }
            applyChanges()
        }
        $j(MainScreen)
                .openDynamicCategoryBrowse()
        Selenide.sleep(5000)
        $j(DynamicCategoryBrowse)
                .create()
        $j(DynamicCategoryEditor).with {
            category
                    .shouldBe(VISIBLE)
                    .shouldHave(value(DYNAMIC_CATEGORY))
            categorized
                    .shouldBe(VISIBLE)
        }
    }
}
