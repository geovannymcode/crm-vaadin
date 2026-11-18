package com.baqjug.crm.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baqjug.crm.backend.Customer;
import com.baqjug.crm.backend.Status;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.signals.Signal;
import com.vaadin.flow.signals.local.ValueSignal;

@Route("customers")
public class CustomerListView extends VerticalLayout {

    private final Grid<Customer> grid = new Grid<>();
    private final List<Customer> customers = getSampleCustomers();
    private final ValueSignal<String> searchValue = new ValueSignal<>("");
    private final ValueSignal<Customer> editedCustomer = new ValueSignal<>(null);
    private final Binder<Customer> binder = new Binder<>();

    public CustomerListView() {
        setSizeFull();
        var search = createSearch();
        configureGrid();
        var form = createForm();
        var content = new HorizontalLayout(grid, form);
        content.setSizeFull();
        add(search, content);

    }

    private List<Customer> getSampleCustomers() {
        return new ArrayList<>(List.of(
                new Customer("Alicia", "Nieto", "alicia.nieto@meridian.co",
                        Status.CUSTOMER, LocalDate.of(2023, 1, 15)),
                new Customer("Boris", "Martínez", "boris.martinez@bluefern.io",
                        Status.CUSTOMER, LocalDate.of(2022, 11, 3)),
                new Customer("Carolina", "Schmidt", "carolina.schmidt@meridian.co",
                        Status.PROSPECT, null)));
    }

    private void configureGrid() {
        grid.addColumn(Customer::getFirstName).setHeader("Nombre").setSortable(true);
        grid.addColumn(Customer::getLastName).setHeader("Apellido");
        grid.addColumn(Customer::getEmail).setHeader("Correo");
        grid.addColumn(Customer::getStatus).setHeader("Estado");
        grid.addColumn(Customer::getCustomerSince).setHeader("Cliente desde");
        grid.asSingleSelect().bindValue(editedCustomer, editedCustomer::set);
        grid.setSizeFull();
    }

    private TextField createSearch() {
        var search = new TextField();
        search.setPlaceholder("Buscar");
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.bindValue(searchValue, searchValue::set);
        Signal.effect(search, () -> updateCustomerList(searchValue.get()));
        return search;
    }

    private void updateCustomerList(String query) {
        var filtered = customers;

        if (!query.isEmpty()) {
            var lower = query.toLowerCase();
            filtered = customers.stream()
                    .filter(c -> c.getFirstName().toLowerCase().contains(lower)
                            || c.getLastName().toLowerCase().contains(lower)
                            || c.getEmail().toLowerCase().contains(lower)
                            || c.getStatus().name().toLowerCase().contains(lower))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        grid.setItems(filtered);
    }

    private FormLayout createForm() {
        var firstName = new TextField("Nombre");
        var lastName = new TextField("Apellido");
        var email = new EmailField("Correo");
        var status = new ComboBox<Status>("Estado");
        binder.forField(firstName)
                .asRequired("El nombre es obligatorio")
                .bind(Customer::getFirstName, Customer::setFirstName);

        binder.forField(lastName)
                .asRequired("El apellido es obligatorio")
                .bind(Customer::getLastName, Customer::setLastName);

        binder.forField(email)
                .asRequired("El correo es obligatorio")
                .bind(Customer::getEmail, Customer::setEmail);

        binder.forField(status)
                .asRequired("Selecciona un estado")
                .bind(Customer::getStatus, Customer::setStatus);

        status.setItems(Status.values());
        var customerSince = new DatePicker("Cliente desde");

        binder.bind(customerSince, Customer::getCustomerSince, Customer::setCustomerSince);

        var form = new FormLayout(firstName, lastName, email, status, customerSince);
        Signal.effect(form, () -> binder.setBean(editedCustomer.get()));
        form.setWidth("300px");
        form.bindVisible(editedCustomer.map(customer -> customer != null));
        return form;
    }
}
