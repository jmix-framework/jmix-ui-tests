package io.jmix.tests.screen.company;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Company;

@UiController("Company.browse")
@UiDescriptor("company-browse.xml")
@LookupComponent("companiesTable")
public class CompanyBrowse extends StandardLookup<Company> {
}