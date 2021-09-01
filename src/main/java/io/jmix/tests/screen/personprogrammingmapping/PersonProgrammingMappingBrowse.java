package io.jmix.tests.screen.personprogrammingmapping;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.searchProgremmic.PersonProgrammingMapping;

@UiController("PersonProgrammingMapping.browse")
@UiDescriptor("person-programming-mapping-browse.xml")
@LookupComponent("personProgrammingMappingsTable")
public class PersonProgrammingMappingBrowse extends StandardLookup<PersonProgrammingMapping> {
}