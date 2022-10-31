package io.jmix.tests.ui.test.dynattr

import com.codeborne.selenide.Selenide
import io.jmix.masquerade.component.*
import io.jmix.tests.JmixUiTestsApplication
import io.jmix.tests.extension.ChromeExtension
import io.jmix.tests.ui.extension.PostgreSQLExtension
import io.jmix.tests.ui.extension.SpringBootExtension
import io.jmix.tests.ui.initializer.TestContextInitializer
import io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor
import io.jmix.tests.ui.screen.administration.dynattr.CategoryEditor
import io.jmix.tests.ui.screen.application.gas.GasBrowse
import io.jmix.tests.ui.screen.application.gas.GasEditor
import io.jmix.tests.ui.screen.application.user.UserBrowse
import io.jmix.tests.ui.screen.system.main.MainScreen
import io.jmix.tests.ui.test.BaseUiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import static com.codeborne.selenide.Condition.*
import static com.codeborne.selenide.Selenide.$
import static io.jmix.masquerade.Conditions.*
import static io.jmix.masquerade.Selectors.*
import static io.jmix.tests.ui.screen.administration.dynattr.CategoryAttributeEditor.*
import static io.jmix.tests.ui.test.dynattr.CategoryAttributeActionsTest.GAS
import static io.jmix.tests.ui.test.dynattr.TargetScreenActionsTest.GAS_EDITOR_DEFAULT_FORM
import static io.jmix.tests.ui.test.dynattr.TargetScreenActionsTest.GAS_FORM
import static java.lang.Boolean.TRUE

@ExtendWith([
        SpringBootExtension,
        ChromeExtension,
        PostgreSQLExtension
])
@SpringBootTest(classes = JmixUiTestsApplication,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ['jmix.liquibase.contexts=base,dynattr'])
@ContextConfiguration(initializers = TestContextInitializer)
class DynamicAttributeWithDifferentTypesTest extends BaseUiTest {

    public static final String ADD_BUTTON = 'add'
    public static final String VALUES_SELECT = 'values_select'
    public static final String COMMIT_BTN = 'commitBtn'
    public static final String WIDTH_VALUE_PX = '500px'
    public static final String TWENTY = "20"
    public static final String FIVE = "5"

    @Test
    @DisplayName("Checks boolean type attribute")
    void checkBooleanAttribute() {
        loginAsAdmin()
        //create boolean attribute
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        createAttribute()
                        $j(CategoryAttributeEditor).with {
                            setName(BOOLEAN)
                            setRequired(true)
                            checkCode(GAS, BOOLEAN)
                            setType(BOOLEAN)
                            setValidationScript(BOOLEAN_VALIDATE_SCRIPT)
                            setDefaultBooleanValue("True")
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute(BOOLEAN, TRUE.toString())
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable()
                            .selectRow(byCells(GAS))
                    checkAttributeTable(BOOLEAN)
                }
        $j(MainScreen).openGasBrowse()
                .create()
        $j(GasEditor).with {
            booleanAttribute.shouldBe(VISIBLE)
            nameField.shouldBe(ENABLED)
                    .setValue(BOOLEAN_VALUE)
            saveChanges()
            $j(Notification.class)
                    .shouldBe(VISIBLE)
            closeWithoutSaving()
        }
        $j(MainScreen)
                .openDynamicAttributeBrowse().with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                editAttribute(BOOLEAN)
                $j(CategoryAttributeEditor).with {
                    setDefaultBooleanValue(FALSE)
                    setRequired(false)
                    saveChanges()
                    checkCategoryAttribute(BOOLEAN, FALSE)
                    saveCategory()
                }
                applyChanges()
            }
        }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            nameField.shouldBe(ENABLED)
                    .setValue(BOOLEAN_VALUE)
            booleanAttribute
                    .shouldNotBe(checked)
            closeWithoutSaving()
        }
    }

    @Test
    @DisplayName("Checks date type attribute")
    void checkDateAttribute() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        refreshAndDelay(400)
                        createAttribute()
                        $j(CategoryAttributeEditor).with {
                            shouldBe(VISIBLE)
                            setName(DATE)
                            setRequired(true)
                            checkCode(GAS, DATE)
                            setType(DATE)
                            [isCollectionField, defaultDateField, widthField, defaultDateIsCurrent]
                                    .each {
                                        it.shouldBe(VISIBLE, ENABLED)
                                    }
                            setDateIsCurrent(true)
                            defaultDateField.shouldNotBe(VISIBLE)
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute(DATE, TRUE.toString())
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(DATE)
                }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    checkDynamicAttributeVisibility(DATE)
                    create()
                }
        $j(GasEditor).with {
            nameField.shouldBe(VISIBLE, ENABLED)
                    .setValue('Date')
            dateAttr
                    .shouldBe(VISIBLE)
                    .shouldNotHave(dateValue(""))
            closeWithoutSaving()
        }
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(DATE)
                        $j(CategoryAttributeEditor).with {
                            setDateIsCurrent(false)
                            defaultDateField
                                    .shouldBe(VISIBLE)
                            isCollectionField.setChecked(true)
                            saveChanges()
                            saveCategory()
                        }
                        applyChanges()
                    }
                }
        $j(MainScreen).openGasBrowse()
        $j(GasBrowse).create()
        $j(GasEditor).with {
            nameField.shouldBe(VISIBLE, ENABLED)
                    .setValue('date')
            dateAttrCollection.shouldBe(VISIBLE)
            saveChanges()
            $j(Notification.class)
                    .shouldBe(VISIBLE)
            $(byChain(byJTestId('+GasDate'), byJTestId(VALUES_SELECT)))
                    .shouldBe(VISIBLE)
                    .click()
            $j(DialogWindow, 'dialog_selectValueDialog').with {
                shouldBe(VISIBLE)
                [getDate(1), getDate(2)]
                        .each {
                            $j(DateField, 'listValueField')
                                    .shouldBe(VISIBLE)
                                    .setDateValue(it as String)
                            $j(Button, ADD_BUTTON)
                                    .shouldBe(VISIBLE)
                                    .click()
                        }
                $j(Button, COMMIT_BTN)
                        .shouldBe(VISIBLE)
                        .click()
            }
            dateAttrCollection
                    .shouldBe(VISIBLE)
                    .shouldHave(valueContains(getDate(1) as String + " 00:00, " + getDate(2) as String + " 00:00"))
            closeWithoutSaving()
        }
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(DATE)
                        $j(CategoryAttributeEditor).with {
                            setDefaultDateField(DEFAULT_DATE_VALUE)
                            defaultDateField.shouldBe(VISIBLE)
                            isCollectionField.setChecked(false)
                            saveChanges()
                            saveCategory()
                        }
                        applyChanges()
                    }
                }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            dateAttr.shouldBe(VISIBLE)
                    .shouldHave(dateValue(DEFAULT_DATE_VALUE))
        }
    }

    @Test
    @DisplayName("Checks date without time type attribute")
    void checkDateWithoutTimeAttribute() {
        loginAsAdmin()
        $j(MainScreen)
                .openDynamicAttributeBrowse().with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                createAttribute()
                $j(CategoryAttributeEditor).with {
                    shouldBe(VISIBLE)
                    setName(DATE_WITHOUT_TIME)
                    checkCode(GAS, DATE_WITHOUT_TIME)
                    setType(DATE_NO_TIME_TYPE)
                    [isCollectionField, defaultDateWithoutTimeField, widthField, defaultDateIsCurrent]
                            .each {
                                it.shouldBe(VISIBLE, ENABLED)
                            }
                    setRequired(true)
                    setIsCollection(true)
                    addScreensOnVisibility(GAS_FORM)
                    saveChanges()
                }
                checkCategoryAttribute(DATE_WITHOUT_TIME, TRUE.toString())
                saveCategory()
            }
            applyChanges()
            getCategoriesTable().getRow(byCells(GAS))
            checkAttributeTable(DATE_WITHOUT_TIME)
        }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    checkDynamicAttributeVisibility(DATE_WITHOUT_TIME)
                    create()
                }
        $j(GasEditor).with {
            nameField.shouldBe(VISIBLE, ENABLED)
                    .setValue('Date without time')
            dateWithoutTimeCollection
                    .shouldBe(VISIBLE)
            saveChanges()
            $j(Notification.class)
                    .shouldBe(VISIBLE)
            $(byChain(byJTestId('+GasDateNoTime'), byJTestId('timepart')))
                    .shouldNotBe(VISIBLE)
            $(byChain(byJTestId('+GasDateNoTime'), byJTestId(VALUES_SELECT)))
                    .shouldBe(VISIBLE)
                    .click()
            $j('dialog_selectValueDialog').with {
                shouldBe(VISIBLE)
                [getDate(1), getDate(2)]
                        .each {
                            $j(DateField, 'listValueField')
                                    .shouldBe(VISIBLE)
                                    .setDateValue(it as String)
                            $j(Button, ADD_BUTTON)
                                    .shouldBe(VISIBLE)
                                    .click()
                        }
                $j(Button, COMMIT_BTN)
                        .shouldBe(VISIBLE)
                        .click()
            }
            dateWithoutTimeCollection
                    .shouldHave(valueContains(getDate(1) as String + ", " + getDate(2) as String))
            closeWithoutSaving()
        }
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(DATE_WITHOUT_TIME)
                        $j(CategoryAttributeEditor).with {
                            setDateIsCurrent(true)
                            defaultDateWithoutTimeField
                                    .shouldNotBe(VISIBLE)
                            setRequired(false)
                            isCollectionField.setChecked(false)
                            saveChanges()
                            saveCategory()
                        }
                        applyChanges()
                    }
                    $j(MainScreen)
                            .openGasBrowse()
                            .create()
                    $j(GasEditor).with {
                        nameField.shouldBe(ENABLED)
                                .setValue('date no time')
                        $j(DateField, '+GasDateNoTime').shouldBe(VISIBLE)
                                .shouldHave(dateValue(getDate(0)))
                        closeWithoutSaving()
                    }
                    $j(MainScreen).openDynamicAttributeBrowse()
                            .with {
                                editCategory(GAS)
                                $j(CategoryEditor).with {
                                    editAttribute(DATE_WITHOUT_TIME)
                                    $j(CategoryAttributeEditor).with {
                                        setDateIsCurrent(false)
                                        setDefaultDateWithoutTimeField(DEFAULT_DATE_VALUE)
                                        saveChanges()
                                        saveCategory()
                                    }
                                    applyChanges()
                                }
                            }
                    $j(MainScreen)
                            .openGasBrowse()
                            .create()
                    $j(GasEditor).with {
                        $j(DateField, '+GasDateNoTime').shouldBe(VISIBLE)
                                .shouldHave(dateValue(DEFAULT_DATE_VALUE))
                    }
                }
    }

    @Test
    @DisplayName("Checks double type attribute")
    void checkDoubleAttribute() {
        loginAsAdmin()
        //create double attribute
        $j(MainScreen)
                .openDynamicAttributeBrowse().with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                createAttribute()
                $j(CategoryAttributeEditor).with {
                    setName(DOUBLE)
                    setRequired(true)
                    checkCode(GAS, DOUBLE)
                    setType(DOUBLE)
                    minDoubleField
                            .shouldBe(VISIBLE)
                            .setValue('12.34')
                    maxDoubleField
                            .shouldBe(VISIBLE)
                            .setValue('100.50')
                    addScreensOnVisibility(GAS_FORM)
                    saveChanges()
                }
                checkCategoryAttribute(DOUBLE, 'GasDouble')
                saveCategory()
            }
            applyChanges()
            getCategoriesTable().selectRow(byCells(GAS))
            checkAttributeTable(DOUBLE)
        }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    checkDynamicAttributeVisibility(DOUBLE)
                    create()
                }
        $j(GasEditor).with {
            shouldBe(VISIBLE)
            doubleAttribute
                    .shouldBe(VISIBLE)
            setName("double")
            checkAttributeValidation()
            doubleAttribute.setValue('11')
            checkAttributeValidation()
            doubleAttribute.setValue('101')
            checkAttributeValidation()
            closeWithoutSaving()
        }
        //edit double attribute - check is collection, default value
        $j(MainScreen)
                .openDynamicAttributeBrowse().with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                editAttribute(DOUBLE)
                $j(CategoryAttributeEditor).with {
                    setRequired(false)
                    ["Some string", '10.3', '101.5']
                            .each {
                                setIncorrectDefaultDouble(it)
                            }
                    setDefaultDouble(DEFAULT_DOUBLE as String)
                    saveChanges()
                }
                checkCategoryAttribute(DOUBLE, DEFAULT_DOUBLE as String)
                saveCategory()
            }
            applyChanges()
            getCategoriesTable().selectRow(byCells(GAS))
            checkAttributeTable(DOUBLE)
        }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            doubleAttribute
                    .shouldBe(VISIBLE)
                    .shouldHave(value(DEFAULT_DOUBLE))
            close()
        }
        $j(MainScreen)
                .openDynamicAttributeBrowse().with {
            editCategory(GAS)
            $j(CategoryEditor).with {
                editAttribute(DOUBLE)
                $j(CategoryAttributeEditor).with {
                    setRequired(false)
                    setIsCollection(true)
                    minDoubleField
                            .delegate
                            .clear()
                    maxDoubleField
                            .delegate
                            .clear()
                    defaultDoubleField
                            .delegate
                            .clear()
                    saveChanges()
                }
                checkCategoryAttribute(DOUBLE, "GasDouble")
                saveCategory()
            }
            applyChanges()
        }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            doubleAttributeCollection
                    .shouldBe(VISIBLE)
            $(byChain(byJTestId('+GasDouble'), byJTestId(VALUES_SELECT)))
                    .shouldBe(VISIBLE)
                    .click()
            $j('dialog_selectValueDialog')
                    .with {
                        shouldBe(VISIBLE)
                        ['13.5', '88.8']
                                .each {
                                    $j('listValueField').setValue(it)
                                    $j(Button, ADD_BUTTON)
                                            .shouldBe(VISIBLE)
                                            .click()
                                }
                        $j(Button, COMMIT_BTN)
                                .shouldBe(VISIBLE)
                                .click()
                    }
            doubleAttributeCollection.
                    shouldHave(valueContains("13.5, 88.8"))
            closeWithoutSaving()
        }
    }

    @Test
    @DisplayName("Checks entity type attribute")
    void checkEntityAttribute() {
        loginAsAdmin()

        //create entity attribute with is collection = true, required = true
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        createAttribute()
                        $j(CategoryAttributeEditor).with {
                            setName(USER)
                            setRequired(true)
                            checkCode(GAS, USER)
                            setType(ENTITY_TYPE)
                            [isCollectionField, lookupField, widthField, entityClassField, defaultEntityIdField, screenField]
                                    .each {
                                        it.shouldBe(VISIBLE, ENABLED)
                                    }
                            checkDynamicAttributeValidation()
                            entityClassField
                                    .setValue("User (User)")
                            checkDynamicAttributeValidation()
                            screenField
                                    .setValue("Users (User.browse)")
                            isCollectionField
                                    .setChecked(true)
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute((GAS + USER), TRUE.toString())
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(USER)
                }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    checkDynamicAttributeVisibility(USER)
                    create()
                }
        $j(GasEditor).with {
            userCollection
                    .shouldBe(VISIBLE)
            setName("entity")
            checkAttributeValidation()
            $(byChain(byJTestId('+GasUser'), byJTestId(VALUES_SELECT)))
                    .shouldBe(VISIBLE)
                    .click()
            $j('dialog_selectValueDialog')
                    .with {
                        shouldBe(VISIBLE)
                        $j('entity_lookup')
                                .shouldBe(VISIBLE)
                                .click()
                        $j(UserBrowse).with {
                            shouldBe(VISIBLE)
                            usersTable
                                    .shouldBe(VISIBLE)
                                    .selectRow(byCells(ADMIN))
                            select()
                        }
                        $j(Button, COMMIT_BTN)
                                .shouldBe(VISIBLE)
                                .click()
                    }
            userCollection.
                    shouldHave(valueContains(ADMIN_VALUE))
            closeWithoutSaving()
        }
        //edit  entity attribute with is collection = false, required = false, set default value
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(GAS + USER)
                        $j(CategoryAttributeEditor).with {
                            setRequired(false)
                            isCollectionField
                                    .shouldBe(VISIBLE)
                                    .setChecked(false)
                            $(byChain(byJTestId('defaultEntityIdField'), byJTestId('lookup')))
                                    .shouldBe(VISIBLE)
                                    .click()
                            $j(UserBrowse).with {
                                shouldBe(VISIBLE)
                                usersTable
                                        .shouldBe(VISIBLE)
                                        .selectRow(byCells(ADMIN))
                                select()
                            }
                            defaultEntityIdField
                                    .shouldHave(valueContains(ADMIN_VALUE))
                            saveChanges()
                        }
                        checkCategoryAttribute(GAS + USER, ADMIN_VALUE)
                        saveCategory()
                    }
                    applyChanges()
                }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            userCollection
                    .shouldBe(VISIBLE)
                    .shouldHave(valueContains(ADMIN_VALUE))
        }
    }

    @Test
    @DisplayName("Checks enum type attribute")
    void checkEnumAttribute() {
        loginAsAdmin()
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        createAttribute().with {
                            setName(ENUMER)
                            setRequired(true)
                            checkCode(GAS, ENUMER)
                            setType(ENUMERATION_TYPE)
                            [isCollectionField, widthField, defaultStringValue]
                                    .each {
                                        it.shouldBe(VISIBLE, ENABLED)
                                    }
                            enumerationField
                                    .shouldBe(VISIBLE)
                            checkDynamicAttributeValidation()
                            setIsCollection(true)
                            editEnum().with {
                                [RED, GREEN, BLUE]
                                        .each {
                                            addEnumValue(it)
                                        }
                                dataGrid
                                        .selectRow(byCells(GREEN))
                                        .$(byJTestId('remove_item_Green'))
                                        .click()
                                dataGrid
                                        .getRow(byCells(GREEN))
                                        .shouldNotBe(VISIBLE)
                                saveChanges()
                                enumerationField.shouldBe(value(LIST_OF_ENUM_VALUES))
                            }
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute(ENUMER, ENUMERATION_TYPE)
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(ENUMER)
                }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    checkDynamicAttributeVisibility(ENUMER)
                    create()
                }
        $j(GasEditor).with {
            enumerCollection
                    .shouldBe(VISIBLE)
            setName("Enumeration")
            checkAttributeValidation()
            $(byChain(byJTestId('+GasEnumer'), byJTestId(VALUES_SELECT)))
                    .shouldBe(VISIBLE)
                    .click()
            $j('dialog_selectValueDialog')
                    .with {
                        shouldBe(VISIBLE)
                        $j(ComboBox.class, 'listValueField')
                                .shouldBe(VISIBLE)
                                .openOptionsPopup()
                                .select(RED)
                        $j(ComboBox.class, 'listValueField')
                                .shouldBe(VISIBLE)
                                .openOptionsPopup()
                                .select(BLUE)
                        $j(Button, COMMIT_BTN)
                                .shouldBe(VISIBLE)
                                .click()
                    }
            enumerCollection
                    .shouldHave(valueContains(RED + ", " + BLUE))
        }
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(ENUMER)
                        $j(CategoryAttributeEditor).with {
                            setRequired(false)
                            isCollectionField.setChecked(false)
                            defaultStringValue.setValue(GREEN)
                            checkDynamicAttributeValidation()
                            defaultStringValue
                                    .delegate
                                    .clear()
                            defaultStringValue.setValue(RED)

                            saveChanges()
                            checkCategoryAttribute(ENUMER, ENUMERATION_TYPE)
                            saveCategory()
                        }
                        applyChanges()
                    }
                }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            enumerCollection
                    .shouldBe(VISIBLE)
                    .shouldHave(valueContains(RED))
        }
    }

    @Test
    @DisplayName("Checks Fixed-point number type attribute")
    void checkFixedPointNumberAttribute() {
        loginAsAdmin()

        //create Fixed-point number attribute with is collection = true, required = true
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        createAttribute()
                        $j(CategoryAttributeEditor).with {
                            setName(FIXED_POINT)
                            setRequired(true)
                            checkCode(GAS, FIXED_POINT)
                            setType(FIXED_POINT_NUMBER)
                            [isCollectionField, lookupField, widthField, defaultValue, numberFormatPattern, minValue, maxValue]
                                    .each {
                                        it.shouldBe(VISIBLE, ENABLED)
                                    }
                            numberFormatPattern
                                    .setValue(NUMBER_FORMAT)
                            defaultValue
                                    .setValue(DEFAULT_FIXED_POINT_VALUE)
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute(FIXED_POINT, DEFAULT_FIXED_POINT_VALUE)
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(FIXED_POINT)
                }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    //    checkDynamicAttributeVisibility(FIXED_POINT)
                    create()
                }
        $j(GasEditor).with {
            fixedPoint
                    .shouldBe(VISIBLE)
                    .shouldHave(value(FORMATTED_VALUE))
            close()
        }
        //edit fixed-point number attribute with is collection = true, required = false, remove default value, format
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(FIXED_POINT)
                        $j(CategoryAttributeEditor).with {
                            setIsCollection(true)
                            clearFieldValue(defaultValue)
                            clearFieldValue(numberFormatPattern)
                            saveChanges()
                        }
                        checkCategoryAttribute(FIXED_POINT, TRUE.toString())
                        saveCategory()
                    }
                    applyChanges()
                }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            fixedPointCollection
                    .shouldBe(VISIBLE)
            setName("FixedPoint")
            checkAttributeValidation()
            $(byChain(byJTestId('+GasFixedPoint'), byJTestId(VALUES_SELECT)))
                    .shouldBe(VISIBLE)
                    .click()
            $j('dialog_selectValueDialog')
                    .with {
                        shouldBe(VISIBLE)
                        ['55555', '666666']
                                .each {
                                    $j('listValueField').setValue(it)
                                    $j(Button, ADD_BUTTON)
                                            .shouldBe(VISIBLE)
                                            .click()
                                }
                        $j(Button, COMMIT_BTN)
                                .shouldBe(VISIBLE)
                                .click()
                    }
            fixedPointCollection.
                    shouldHave(valueContains("55,555, 666,666"))
            closeWithoutSaving()
        }
        //edit fixed-point number attribute with is collection = false, check min, max value
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(FIXED_POINT)
                        $j(CategoryAttributeEditor).with {
                            setRequired(false)
                            isCollectionField
                                    .shouldBe(VISIBLE)
                                    .setChecked(false)
                            minValue
                                    .shouldBe(VISIBLE)
                                    .setValue('100')
                            maxValue
                                    .shouldBe(VISIBLE)
                                    .setValue('1000')
                            ["Some value", '99', '1001']
                                    .each {
                                        defaultValue
                                                .shouldBe(VISIBLE)
                                                .setValue(it)
                                        saveChanges()
                                        $j(Notification.class)
                                                .shouldBe(VISIBLE)
                                        clearFieldValue(defaultValue)
                                    }
                            saveChanges()
                        }
                        checkCategoryAttribute(FIXED_POINT, FIXED_POINT_NUMBER)
                        saveCategory()
                    }
                    applyChanges()
                }

        $j(MainScreen)
                .openGasBrowse()
                .create()

        $j(GasEditor).with {
            ["Some value", '99', '1001']
                    .each {
                        fixedPoint
                                .shouldBe(VISIBLE)
                                .setValue(it)
                        saveChanges()
                        $j(Notification.class)
                                .shouldBe(VISIBLE)
                    }
        }
    }

    @Test
    @DisplayName("Checks Integer type attribute")
    void checkIntegerAttribute() {
        loginAsAdmin()

        //create integer attribute with width = 500px, min, max values are set
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        refreshAndDelay(400)
                        createAttribute()
                        $j(CategoryAttributeEditor).with {
                            setName(INTEGER)
                            setRequired(true)
                            checkCode(GAS, INTEGER)
                            setType(INTEGER)
                            [isCollectionField, lookupField, widthField, defaultIntField, minIntValue, maxIntValue]
                                    .each {
                                        it.shouldBe(VISIBLE)
                                    }
                            widthField.setValue("500px")
                            minIntValue.setValue("10")
                            maxIntValue.setValue(TWENTY)
                            ["Some value", '9', '21']
                                    .each {
                                        defaultIntField
                                                .setValue(it)
                                        saveChanges()
                                        $j(Notification.class)
                                                .shouldBe(VISIBLE)
                                        clearFieldValue(defaultIntField)
                                    }
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute((GAS + INTEGER), INTEGER_CODE)
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(INTEGER_CODE)
                }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    checkDynamicAttributeVisibility(INTEGER)
                    create()
                }
        $j(GasEditor).with {
            setName("integer")
            checkIntegerWidth(WIDTH_VALUE_PX)
            checkAttributeValidation()
            ['test', '9', '21'].each {

                integerField
                        .setValue(it)
                checkAttributeValidation()
            }
            closeWithoutSaving()
        }
        //edit integer attribute with width = 50%
        String percentageWidth = "50%"

        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(INTEGER_CODE)
                        $j(CategoryAttributeEditor).with {
                            widthField
                                    .shouldBe(VISIBLE)
                                    .delegate
                                    .clear()
                            widthField
                                    .setValue(percentageWidth)
                                    .shouldHave(value(percentageWidth))
                            defaultIntField
                                    .shouldBe(VISIBLE)
                                    .setValue(DEFAULT_INT_VALUE)
                            openVisibilityTab()
                            removeScreensOnVisibility(0) // should remove editor with gasForm
                            addGasEditorOnVisibility(1, GAS_EDITOR_DEFAULT_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute((GAS + INTEGER), DEFAULT_INT_VALUE)
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(INTEGER)
                }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            integerField
                    .shouldHave(value(DEFAULT_INT_VALUE))
//                    .shouldHave(cssValue("width", percentageWidth)) // shows actual value in px instead of percentage
        }

        //edit integer attribute with isCollection = true
        //TODO
        //https://github.com/Haulmont/jmix-dynattr/issues/95
        /* $j(MainScreen).openDynamicAttributeBrowse()
                 .with {
                     editCategory(GAS)
                     $j(CategoryEditor).with {
                         editAttribute(INTEGER)
                         $j(CategoryAttributeEditor).with {
                             setIsCollection(true)
                             saveChanges()
                         }
                         checkCategoryAttribute(INTEGER, DEFAUIL_INT_VALUE)
                         saveCategory()
                     }
                     applyChanges()
                     getCategoriesTable()
                             .selectRow(byCells(GAS))
                     checkAttributeTable(INTEGER)
                 }
         $j(MainScreen)
                 .openGasBrowse()

                 .create()
         $j(GasEditor).with {
             integerCollection
                     .shouldBe(VISIBLE)
             $(byChain(byJTestId('+GasInteger'), byJTestId('values_select')))
                     .shouldBe(VISIBLE)
                     .click()
             $j('dialog_selectValueDialog')
                     .with {
                         shouldBe(VISIBLE)
                         ['13', '18']
                                 .each {
                                     $j('listValueField').setValue(it)
                                     $j(Button, 'add')
                                             .shouldBe(VISIBLE)
                                             .click()
                                 }
                         $j(Button, 'commitBtn')
                                 .shouldBe(VISIBLE)
                                 .click()
                     }
             integerCollection.
                     shouldHave(valueContains("13, 18"))
             closeWithoutSaving()
         }*/
    }

    @Test
    @DisplayName("Checks String type attribute")
    void checkStringAttribute() {
        loginAsAdmin()

        //create string attribute with row count, default value are set
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        refreshAndDelay(400)
                        createAttribute()
                        $j(CategoryAttributeEditor).with {
                            setName(STRING)

                            checkCode(GAS, STRING)
                            setType(STRING)
                            [isCollectionField, lookupField, widthField, rowsCount, defaultStringValue]
                                    .each {
                                        it.shouldBe(VISIBLE)
                                    }
                            rowsCount.setValue(FIVE)
                            defaultStringValue.setValue(DEFAULT_STRING_VALUE)
                            addScreensOnVisibility(GAS_FORM)
                            saveChanges()
                        }
                        checkCategoryAttribute((GAS + STRING), DEFAULT_STRING_VALUE)
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(STRING)
                }
        $j(MainScreen)
                .openGasBrowse()
                .with {
                    checkDynamicAttributeVisibility(STRING)
                    create()
                }
        $j(GasEditor).with {
            stringField
                    .shouldBe(VISIBLE)
                    .shouldHave(value(DEFAULT_STRING_VALUE))
                    .shouldHave(attribute("rows", FIVE))

            close()
        }
        //edit string attribute, "isCollection", "required" set to true
        $j(MainScreen).openDynamicAttributeBrowse()
                .with {
                    editCategory(GAS)
                    $j(CategoryEditor).with {
                        editAttribute(GAS + STRING)
                        $j(CategoryAttributeEditor).with {
                            defaultStringValue
                                    .shouldBe(VISIBLE)
                                    .delegate
                                    .clear()
                            setIsCollection(true)
                            setRequired(true)
                            saveChanges()
                        }
                        checkCategoryAttribute((GAS + STRING), TRUE.toString())
                        saveCategory()
                    }
                    applyChanges()
                    getCategoriesTable().selectRow(byCells(GAS))
                    checkAttributeTable(STRING)
                }
        $j(MainScreen)
                .openGasBrowse()
                .create()
        $j(GasEditor).with {
            setName("string")
            checkAttributeValidation()
            stringCollectionTypeField
                    .shouldBe(VISIBLE)
            $(byChain(byJTestId('+GasString'), byJTestId(VALUES_SELECT)))
                    .shouldBe(VISIBLE)
                    .click()
            $j('dialog_selectValueDialog')
                    .with {
                        shouldBe(VISIBLE)
                         ['first', 'second']
                                 .each {
                                     $j('listValueField').setValue(it)
                                     $j(Button, 'add')
                                             .shouldBe(VISIBLE)
                                             .click()
                                 }
                        $j(Button, COMMIT_BTN)
                                .shouldBe(VISIBLE)
                                .click()
                    }
              stringCollectionTypeField.
                      shouldHave(valueContains("first, second"))
            closeWithoutSaving()
        }
    }

    protected void refreshAndDelay(int milliseconds) {
        Selenide.refresh()
        Selenide.sleep(milliseconds)
    }
}
