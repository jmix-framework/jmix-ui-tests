package io.jmix.tests.screen.waybill;

import io.jmix.ui.screen.*;
import io.jmix.tests.entity.waybill.WayBill;
import io.jmix.core.DataManager;
import io.jmix.tests.entity.customer.Company;
import io.jmix.tests.entity.customer.Customer;
import io.jmix.tests.entity.customer.Individual;
import io.jmix.tests.entity.spacebody.Moon;
import io.jmix.tests.entity.spacebody.Planet;
import io.jmix.tests.entity.spaceport.Carrier;
import io.jmix.tests.entity.spaceport.SpacePort;
import io.jmix.tests.entity.waybill.WayBillItem;
import io.jmix.tests.service.ChargeCountWaybillItemService;
import io.jmix.ui.Notifications;
import io.jmix.ui.RemoveOperation;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.InstanceContainer;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("WayBill.edit")
@UiDescriptor("way-bill-edit.xml")
@EditedEntityContainer("wayBillDc")
public class WayBillEdit extends StandardEditor<WayBill> {
    @Autowired
    private EntityPicker<Customer> shipperField;
    @Autowired
    private CheckBox checkBoxCompanyShipper;

    @Autowired
    private CheckBox checkBoxMoonDeparture;
    @Autowired
    private CheckBox checkBoxMoonDestination;
    @Autowired
    private EntityPicker<Moon> moonDeparturePicker;
    @Autowired
    private EntityPicker<Planet> planetDeparturePicker;
    @Autowired
    private EntityPicker<Planet> planetDestinationPicker;
    @Autowired
    private EntityPicker<Moon> moonDestinationPicker;
    @Autowired
    private EntityPicker<SpacePort> departurePortField;
    @Autowired
    private EntityPicker<SpacePort> destinationPortField;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;
    @Autowired
    private InstanceContainer<WayBill> wayBillDc;
    @Autowired
    private CollectionPropertyContainer<WayBillItem> itemsDc;
    @Autowired
    private TextField<Double> totalChargeField;
    @Autowired
    private TextField<Double> totalWeightField;
    @Autowired
    private ChargeCountWaybillItemService chargeCountWaybillItemService;
    @Autowired
    private Logger log;
    @Autowired
    private EntityPicker<Customer> consigneeField;
    @Autowired
    private EntityPicker<Carrier> carrierField;
    @Autowired
    private Table<WayBillItem> itemsTable;


    // динамическое изменение поля totalWeight после изменений в таблице WayBillItems
    // после добавления новой записи
    @Install(to = "itemsTable.create", subject = "afterCloseHandler")
    private void itemsTableCreateAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        try {
            if (itemsTable.getItems() != null) {
                recountTotalValues();
            } else {
                notifications.create(Notifications.NotificationType.WARNING).withCaption("Список пуст!").show();
            }
        } catch (NullPointerException e) {
            notifications.create(Notifications.NotificationType.WARNING).withCaption("Список пуст!").show();
        }

    }

    // после изменения записи
    @Install(to = "itemsTable.edit", subject = "afterCloseHandler")
    private void itemsTableEditAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        recountTotalValues();
    }

    // после удаления записи
    @Install(to = "itemsTable.remove", subject = "afterActionPerformedHandler")
    private void itemsTableRemoveAfterActionPerformedHandler(RemoveOperation.AfterActionPerformedEvent<WayBillItem> afterActionPerformedEvent) {
        try {
            if (itemsTable.getItems() != null) {
                recountTotalValues();
            } else {
                notifications.create(Notifications.NotificationType.WARNING).withCaption("Список пуст!").show();
            }

            List<WayBillItem> list = itemsDc.getItems();
            List<WayBillItem> mutableList = new ArrayList<>(list);

            for (int i = 0; i < list.size(); i++) {
                mutableList.get(i).setNumber(i + 1);
            }
            log.info(mutableList.toString());

            itemsDc.setItems(mutableList);

        } catch (NullPointerException e) {
            notifications.create(Notifications.NotificationType.WARNING).withCaption("Список пуст!").show();
        }
    }

    private void recountTotalValues() {
        double totalWeight = chargeCountWaybillItemService.getTotalWeight(getEditedEntity());
        double totalCharge = chargeCountWaybillItemService.getTotalCharge(getEditedEntity());

        totalWeightField.setValue(totalWeight);
        totalChargeField.setValue(totalCharge);
    }

    // установка значения порта по умолчанию     ////////////////////////////////////////////////////////////////////////////////////////////

    // установка значения порта по умолчанию у планеты-отправителя груза
    @Subscribe("planetDeparturePicker")
    public void onPlanetDeparturePickerValueChange(HasValue.ValueChangeEvent<Planet> event) {
        findDefaultPlanetPort(event, departurePortField);
    }

    // установка значения порта по умолчанию у спутника-отправителя груза
    @Subscribe("moonDeparturePicker")
    public void onMoonDeparturePickerValueChange(HasValue.ValueChangeEvent<Moon> event) {
        findDefaultMoonPort(event, departurePortField);
    }

    // установка значения порта по умолчанию у планеты-получателя груза
    @Subscribe("planetDestinationPicker")
    public void onPlanetDestinationPickerValueChange(HasValue.ValueChangeEvent<Planet> event) {
        findDefaultPlanetPort(event, destinationPortField);
    }

    // установка значения порта по умолчанию у спутника-получателя груза
    @Subscribe("moonDestinationPicker")
    public void onMoonDestinationPickerValueChange(HasValue.ValueChangeEvent<Moon> event) {
        findDefaultMoonPort(event, destinationPortField);
    }

    private void findDefaultPlanetPort(HasValue.ValueChangeEvent<Planet> event, EntityPicker<SpacePort> portPickerField) {
        try {
            Planet planet = event.getValue();
            SpacePort spacePort = dataManager.loadValue("select e from st_SpacePort e where e.isDefault = true and e.planet = :planet", SpacePort.class)
                    .parameter("planet", planet)
                    .one();
            portPickerField.setValue(spacePort);
        } catch (IllegalStateException e) {
            notifications.create(Notifications.NotificationType.WARNING).withCaption("У данной планеты не задан порт по умолчанию!").show();
        }
    }

    private void findDefaultMoonPort(HasValue.ValueChangeEvent<Moon> event, EntityPicker<SpacePort> portPickerField) {
        try {
            Moon moon = event.getValue();
            SpacePort spacePort = dataManager.loadValue("select e from st_SpacePort e where e.isDefault = true and e.moon = :moon", SpacePort.class)
                    .parameter("moon", moon)
                    .one();
            portPickerField.setValue(spacePort);
        } catch (IllegalStateException e) {
            notifications.create(Notifications.NotificationType.WARNING).withCaption("У данного спутника не задан порт по умолчанию!").show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //проверка наличия перевозчика для выбранного порта получения
    @Subscribe("destinationPortField")
    public void onDestinationPortFieldValueChange(HasValue.ValueChangeEvent<SpacePort> event) {
        checkUniquePorts(destinationPortField, departurePortField, event);
        checkExistingCarriers();
    }

    //проверка наличия перевозчика для выбранного порта отправления
    @Subscribe("departurePortField")
    public void onDeparturePortFieldValueChange(HasValue.ValueChangeEvent<SpacePort> event) {
        checkUniquePorts(departurePortField, destinationPortField, event);
        checkExistingCarriers();
    }

    private void checkUniquePorts(EntityPicker<SpacePort> thisPort, EntityPicker<SpacePort> thatPort, HasValue.ValueChangeEvent<SpacePort> event) {
        if (!thatPort.isEmpty() && !thisPort.isEmpty() && thatPort.getValue().equals(event.getValue())) {
            notifications.create(Notifications.NotificationType.ERROR).withCaption("Доставка в тот же порт невозможна!").show();
            thisPort.clear();
        }
    }

    private void checkExistingCarriers() {
        if (!departurePortField.isEmpty() && !destinationPortField.isEmpty()) {
            SpacePort spacePort1 = departurePortField.getValue();
            SpacePort spacePort2 = destinationPortField.getValue();
            List<Carrier> carrierList = dataManager.load(Carrier.class).query("select e from st_Carrier e").fetchPlan("new-carrier-view").list();

            log.info(carrierList.toString());
           /* List<Carrier> filteredList = carrierList.stream().filter(e -> (e.getPorts().contains(spacePort1) && e.getPorts().contains(spacePort2))).collect(Collectors.toList());

            carrierField.setOptionsList(filteredList);
            if (filteredList.isEmpty()) {
                notifications.create(Notifications.NotificationType.WARNING).withCaption("Ни один перевозчик не обслуживает оба порта!").show();
            }
            */
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    @Subscribe("companyShipperLookup")
    public void onCompanyShipperLookupValueChange(HasValue.ValueChangeEvent<Customer> event) {
        Customer company = event.getValue();
        log.info(company.toString());

        shipperField.setValue(company);
    }

    @Subscribe("individualShipperLookup")
    public void onIndividualShipperLookupValueChange(HasValue.ValueChangeEvent<Customer> event) {
        Customer individual = event.getValue();
        log.info(individual.toString());

        shipperField.setValue(individual);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    @Inject
    private CheckBox checkBoxCompanyConsignee;
    @Inject
    private EntityPicker<Individual> individualConsigneePicker;
    @Inject
    private EntityPicker<Company> companyConsigneePicker;

    @Subscribe("checkBoxCompanyConsignee")
    public void onCheckBoxCompanyConsigneeValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (checkBoxCompanyConsignee.isChecked()) {
            companyConsigneePicker.setVisible(true);
            individualConsigneePicker.setVisible(false);
        }
        if (!checkBoxCompanyConsignee.isChecked()) {
            companyConsigneePicker.setVisible(false);
            individualConsigneePicker.setVisible(true);
        }
    }

    @Subscribe("individualConsigneePicker")
    public void onIndividualConsigneePickerValueChange(HasValue.ValueChangeEvent<Individual> event) {
        Customer customer = event.getValue();
        if (customer != null && customer.equals(shipperField.getValue())) {
            log.info(customer.toString());

            notifications.create(Notifications.NotificationType.ERROR).withCaption("Получатель и отправитель не могут быть одним и тем же лицом!").show();
            individualConsigneePicker.clear();
        } else {
            consigneeField.setValue(customer);
        }
    }

    @Subscribe("companyConsigneePicker")
    public void onCompanyConsigneePickerValueChange(HasValue.ValueChangeEvent<Company> event) {
        Customer customer = event.getValue();
        if (customer != null && customer.equals(shipperField.getValue())) {
            log.info(customer.toString());

            notifications.create(Notifications.NotificationType.ERROR).withCaption("Получатель и отправитель не могут быть одним и тем же лицом!").show();
            companyConsigneePicker.clear();
        } else {
            consigneeField.setValue(customer);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @Subscribe("checkBoxMoonDeparture")
    public void onCheckBoxMoonDepartureValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (checkBoxMoonDeparture.isChecked()) {
            planetDeparturePicker.setVisible(false);
            moonDeparturePicker.setVisible(true);
        }
        if (!checkBoxMoonDeparture.isChecked()) {
            planetDeparturePicker.setVisible(true);
            moonDeparturePicker.setVisible(false);
        }
    }


    @Subscribe("checkBoxMoonDestination")
    public void onCheckBoxMoonDestinationValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (checkBoxMoonDestination.isChecked()) {
            planetDestinationPicker.setVisible(false);
            moonDestinationPicker.setVisible(true);
        }
        if (!checkBoxMoonDestination.isChecked()) {
            planetDestinationPicker.setVisible(true);
            moonDestinationPicker.setVisible(false);
        }
    }

    //передвижение элемента WayBillItem вверх
    @Subscribe("buttonUp")
    public void onButtonUpClick(Button.ClickEvent event) {
        if (itemsTable.getSingleSelected() != null) {
            List<WayBillItem> list = itemsDc.getItems();
            List<WayBillItem> mutableList = new ArrayList<>(list);
            WayBillItem selected = itemsTable.getSingleSelected();
            int index = mutableList.indexOf(selected);
            if (index > 0) {
                WayBillItem prev = mutableList.get(index - 1);
                mutableList.set(index - 1, selected);
                mutableList.set(index, prev);
                selected.setNumber(index);
                prev.setNumber(index + 1);
            }
            itemsDc.setItems(mutableList);
        }
    }

    //передвижение элемента WayBillItem вниз
    @Subscribe("buttonDown")
    public void onButtonDownClick(Button.ClickEvent event) {
        if (itemsTable.getSingleSelected() != null) {
            List<WayBillItem> list = itemsDc.getItems();
            List<WayBillItem> mutableList = new ArrayList<>(list);
            WayBillItem selected = itemsTable.getSingleSelected();
            int index = mutableList.indexOf(selected);
            if (index < mutableList.size() - 1) {
                WayBillItem next = mutableList.get(index + 1);
                mutableList.set(index + 1, selected);
                mutableList.set(index, next);
                selected.setNumber(index + 2);
                next.setNumber(index + 1);
            }
            itemsDc.setItems(mutableList);
        }
    }
}
