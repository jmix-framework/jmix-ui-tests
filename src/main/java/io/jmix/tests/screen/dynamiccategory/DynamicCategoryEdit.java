package io.jmix.tests.screen.dynamiccategory;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.DynamicCategory;

@UiController("DynamicCategory.edit")
@UiDescriptor("dynamic-category-edit.xml")
@EditedEntityContainer("dynamicCategoryDc")
public class DynamicCategoryEdit extends StandardEditor<DynamicCategory> {
}