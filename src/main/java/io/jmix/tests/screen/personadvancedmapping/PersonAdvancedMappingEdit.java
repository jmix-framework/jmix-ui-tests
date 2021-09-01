package io.jmix.tests.screen.personadvancedmapping;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.searchadvanced.PersonAdvancedMapping;

@UiController("PersonAdvancedMapping.edit")
@UiDescriptor("person-advanced-mapping-edit.xml")
@EditedEntityContainer("personAdvancedMappingDc")
public class PersonAdvancedMappingEdit extends StandardEditor<PersonAdvancedMapping> {
}