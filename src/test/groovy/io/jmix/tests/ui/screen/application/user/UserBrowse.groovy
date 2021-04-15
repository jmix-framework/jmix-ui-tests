package io.jmix.tests.ui.screen.application.user

import io.jmix.masquerade.Wire
import io.jmix.masquerade.base.Composite
import io.jmix.masquerade.component.Button
import io.jmix.masquerade.component.Table
import io.jmix.tests.ui.screen.system.dialog.ConfirmationDialog

import static io.jmix.masquerade.Selectors.$j
import static io.jmix.masquerade.Selectors.withText

class UserBrowse extends Composite<UserBrowse> {

    @Wire
    Button createBtn
    @Wire
    Button editBtn
    @Wire
    Button removeBtn
    @Wire
    Button refreshBtn
    @Wire
    Table usersTable

    UserEditor createUser() {
        createBtn.click()
        new UserEditor()
    }

    UserEditor editUser(String username) {
        usersTable.getCell(withText(username))
                .click()
        editBtn.click()
        new UserEditor()
    }

    void removeUser(String username) {
        usersTable.getCell(withText(username))
                .click()

        removeBtn.click()

        $j(ConfirmationDialog)
                .yes
                .click()
    }
}
