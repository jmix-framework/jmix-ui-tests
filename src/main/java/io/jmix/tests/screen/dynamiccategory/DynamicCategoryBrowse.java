package io.jmix.tests.screen.dynamiccategory;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.DynamicCategory;

@UiController("DynamicCategory.browse")
@UiDescriptor("dynamic-category-browse.xml")
@LookupComponent("dynamicCategoriesTable")
public class DynamicCategoryBrowse extends StandardLookup<DynamicCategory> {
}