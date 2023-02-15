package io.jmix.tests.ui.test.dynattr

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor
import io.jmix.tests.ui.screen.administration.dynattr.CategoryEditor
import io.jmix.tests.ui.screen.administration.dynattr.DynamicAttributeBrowse
import io.jmix.tests.ui.screen.application.discounts.DiscountBrowser
import io.jmix.tests.ui.screen.application.discounts.DiscountEditor
import io.jmix.tests.ui.screen.application.localizedDynamic.LocalizedDynamicBrowse
import io.jmix.tests.ui.screen.application.localizedDynamic.LocalizedDynamicEditor
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static io.jmix.masquerade.Conditions.VISIBLE
import static io.jmix.masquerade.Conditions.value
import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.byCells
import static io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor.GREEN

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['main.liquibase.contexts=base,dynattr'])
@ContextConfiguration(initializers = TestContextInitializer)
class LocalizationDynamicAttributesTest extends BaseUiTest {

    public static final String LOCALIZED_TYPE = "Localized dynamic (LocalizedDynamic)"
    public static final String LOCALIZED = "Localized"
    public static final String LOCALIZED_RU = "Localized_Ru"
    public static final String EN_LOCALE = "English|en"
    public static final String RU_LOCALE = "Russian|ru"
    public static final String DISCOUNTS = 'DiscountsLocale'
    public static final String LOCALIZATION_EN = 'Localization_En'
    public static final String LOCALIZATION_RU = 'Localization_Ru'
    public static final String DESCRIPTION_EN = 'Description_En'
    public static final String DESCRIPTION_RU = 'Description_Ru'
    public static final String DISCOUNT_EDITOR = 'Discounts editor (Discounts.edit)'
    public static final String DISCOUNT_BROWSER = 'Discounts browser (Discounts.browse)'
    public static final String LOCAL_ENUM = 'LocalizedEnum'
    public static final String GREEN_RU = "green_ru"
    public static final String RED_RU = "red_ru"

    @Test
    @DisplayName("Checks category localization")
    void checkCategoryLocalization() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            createCategory()
            $j(CategoryEditor).with {
                setName(LOCALIZED)
                setDefault(true)
                setEntityType(LOCALIZED_TYPE)
                openLocalizationTab()
                localizedNameField
                        .shouldBe(VISIBLE)
                        .shouldHave(value(LOCALIZED))
                setLocalizedValue(EN_LOCALE, LOCALIZED)
                setLocalizedValue(RU_LOCALE, LOCALIZED_RU)
                saveCategory()
            }
            applyChanges()
        }
        $j(MainScreen)
                .openLocalizedCategoryBrowse()
        Selenide.sleep(5000)
        $j(LocalizedDynamicBrowse)
                .create()
        $j(LocalizedDynamicEditor).with {
            categoryField
                    .shouldBe(VISIBLE)
                    .shouldHave(value(LOCALIZED))
        }
        logout()
        loginAsAdminRus()
        $j(MainScreen)
                .openLocalizedCategoryBrowse()
        Selenide.sleep(5000)
        $j(LocalizedDynamicBrowse)
                .create()
        $j(LocalizedDynamicEditor).with {
            categoryField
                    .shouldBe(VISIBLE)
                    .shouldHave(value(LOCALIZED_RU))
        }
    }

    @Test
    @DisplayName("Checks attribute localization")
    void checkAttributeLocalization() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(DISCOUNTS)
            $j(CategoryEditor).with {
                createAttribute()
                $j(CategoryAttributeEditor).with {
                    setName(LOCALIZATION_EN)
                    setType(STRING)
                    setDescription(DESCRIPTION_EN)
                    openLocalizationTab()
                    localizedDescriptionField
                            .shouldBe(VISIBLE)
                            .shouldHave(value(DESCRIPTION_EN))
                    localizedNameField
                            .shouldBe(VISIBLE)
                            .shouldHave(value(LOCALIZATION_EN))
                    setLocalizedValue(EN_LOCALE, LOCALIZATION_EN, DESCRIPTION_EN)
                    setLocalizedValue(RU_LOCALE, LOCALIZATION_RU, DESCRIPTION_RU)
                    addScreensOnVisibility(null, DISCOUNT_EDITOR, DISCOUNT_BROWSER)
                    saveChanges()
                }
                saveCategory()
            }
            applyChanges()
        }
        $j(MainScreen)
                .openDiscounts()
        Selenide.sleep(5000)
        $j(DiscountBrowser)
                .checkDynamicAttributeVisibility(LOCALIZATION_EN)
        logout()
        loginAsAdminRus()
        $j(MainScreen).openDiscounts()
        Selenide.sleep(5000)
        $j(DiscountBrowser)
                .checkDynamicAttributeVisibility(LOCALIZATION_RU)
    }

    @Disabled("https://github.com/Haulmont/jmix-dynattr/issues/96")
    @Test
    @DisplayName("Checks enum localization")
    void checkEnumLocalization() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
        $j(DynamicAttributeBrowse).with {
            editCategory(DISCOUNTS)
            $j(CategoryEditor).with {
                createAttribute()
                $j(CategoryAttributeEditor).with {
                    setName(LOCAL_ENUM)
                    setType(ENUMERATION_TYPE)
                    editEnum().with {
                        [
                                [RED, RED_RU],
                                [GREEN, GREEN_RU]
                        ]
                                .each {
                                    addEnumValue(it[0])
                                    dataGrid
                                            .selectRow(byCells(it[0]))
                                    setLocalizedValue(EN_LOCALE, it[0])
                                    setLocalizedValue(RU_LOCALE, it[1])
                                }
                        addScreensOnVisibility(null, DISCOUNT_EDITOR, DISCOUNT_BROWSER)
                        saveChanges()
                    }
                    saveCategory()
                }
                applyChanges()
            }
            $j(MainScreen)
                    .openDiscounts()
                    .create()
            $j(DiscountEditor).with {
                enumer
                        .shouldBe(VISIBLE)
                        .openOptionsPopup()
                        .select(GREEN)
                        .shouldHave(Condition.value(GREEN))

            }
            logout()
            loginAsAdminRus()
            $j(MainScreen)
                    .openDiscounts()
                    .create()
            $j(DiscountEditor).with {
                enumer
                        .shouldBe(VISIBLE)
                        .openOptionsPopup()
                        .select(GREEN_RU)
                        .shouldHave(Condition.value(GREEN_RU))
            }
        }
    }
}
