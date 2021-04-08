package io.jmix.tests.screen.customer;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Customer;

@UiController("Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}