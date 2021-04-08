package io.jmix.tests.screen.individual;

import io.jmix.core.DataManager;
import io.jmix.tests.entity.customer.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Individual;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@UiController("Individual.edit")
@UiDescriptor("individual-edit.xml")
@EditedEntityContainer("individualDc")
public class IndividualEdit extends StandardEditor<Individual> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TextField<String> nameField;
    @Autowired
    private Notifications notifications;
    @Autowired
    private TextField<String> emailField;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        Optional<Customer> customer = dataManager.load(Customer.class)
                .query("select c from Customer c where c.name = :name")
                .parameter("name", nameField.getValue())
                .optional();
        if (customer.isPresent()){
            event.preventCommit();
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Пользователь с таким именем уже зарегистрирован!").show();
        }
        Optional<Customer> customer1 = dataManager.load(Customer.class)
                .query("select c from Customer c where c.email = :email")
                .parameter("email", emailField.getValue())
                .optional();
        if (customer1.isPresent()){
            event.preventCommit();
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Пользователь с таким email уже зарегистрирован!").show();
        }
    }
}