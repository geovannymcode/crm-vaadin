package com.baqjug.crm.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.baqjug.crm.backend.Customer;
import com.baqjug.crm.backend.Status;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("customers")
public class CustomerListView extends VerticalLayout {

    private final Grid<Customer> grid = new Grid<>();

    public CustomerListView() {
        setSizeFull();
        configureGrid();
        grid.setItems(getSampleCustomers());
        add(grid);

    }

    private List<Customer> getSampleCustomers() {
        return new ArrayList<>(List.of(
                new Customer("Alicia", "Nieto", "alicia.nieto@meridian.co",
                        Status.CUSTOMER, LocalDate.of(2023, 1, 15)),
                new Customer("Boris", "Martínez", "boris.martinez@bluefern.io",
                        Status.CUSTOMER, LocalDate.of(2022, 11, 3)),
                new Customer("Carolina", "Schmidt", "carolina.schmidt@meridian.co",
                        Status.PROSPECT, null)
        ));
    }


    private void configureGrid() {
       grid.addColumn(Customer::getFirstName).setHeader("Nombre").setSortable(true);
        grid.addColumn(Customer::getLastName).setHeader("Apellido");
        grid.addColumn(Customer::getEmail).setHeader("Correo");
        grid.addColumn(Customer::getStatus).setHeader("Estado");
        grid.addColumn(Customer::getCustomerSince).setHeader("Cliente desde");
        grid.setSizeFull();
    }
}
