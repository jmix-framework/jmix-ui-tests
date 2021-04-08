package io.jmix.tests.screen.company;

import io.jmix.core.DataManager;
import io.jmix.tests.entity.customer.Customer;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import io.jmix.tests.entity.customer.Company;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Company.edit")
@UiDescriptor("company-edit.xml")
@EditedEntityContainer("companyDc")
public class CompanyEdit extends StandardEditor<Company> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TextField<String> nameField;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        try{
            Customer customer = dataManager.load(Customer.class)
                    .query("select c from Customer c where c.name = :name")
                    .parameter("name", nameField.getValue())
                    .one();
            event.preventCommit();
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Пользователь с таким именем уже зарегистрирован!").show();
        } catch (IllegalStateException e){
            notifications.create().withCaption("Данное имя не занято!").show();
        }
    }
}