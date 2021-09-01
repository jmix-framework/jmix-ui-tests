package io.jmix.tests.screen.personadvancedmapping;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.searchadvanced.PersonAdvancedMapping;

@UiController("PersonAdvancedMapping.browse")
@UiDescriptor("person-advanced-mapping-browse.xml")
@LookupComponent("personAdvancedMappingsTable")
public class PersonAdvancedMappingBrowse extends StandardLookup<PersonAdvancedMapping> {
}