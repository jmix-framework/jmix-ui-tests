package io.jmix.tests.screen.customer;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Customer;

@UiController("Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}