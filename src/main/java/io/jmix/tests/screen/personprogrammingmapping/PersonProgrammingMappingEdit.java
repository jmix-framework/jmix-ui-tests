package io.jmix.tests.screen.personprogrammingmapping;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.searchProgremmic.PersonProgrammingMapping;

@UiController("PersonProgrammingMapping.edit")
@UiDescriptor("person-programming-mapping-edit.xml")
@EditedEntityContainer("personProgrammingMappingDc")
public class PersonProgrammingMappingEdit extends StandardEditor<PersonProgrammingMapping> {

}