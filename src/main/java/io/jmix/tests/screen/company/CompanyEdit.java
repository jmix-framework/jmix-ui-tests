package io.jmix.tests.screen.company;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Company;

@UiController("Company.edit")
@UiDescriptor("company-edit.xml")
@EditedEntityContainer("companyDc")
public class CompanyEdit extends StandardEditor<Company> {
}
