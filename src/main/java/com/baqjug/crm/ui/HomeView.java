package com.baqjug.crm.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeView extends VerticalLayout {

    public HomeView() {
        var name = new TextField("Nombre");
        var birthDate = new DatePicker("Fecha de nacimiento");
        var save = new Button("Guardar");
        save.addClickListener(e -> Notification.show("Guardado"));

        var discard = new Button("Descartar");
        discard.addClickListener(e -> Notification.show("Descartado"));
        var buttons = new HorizontalLayout(save, discard);
        add(name, birthDate, buttons);
    }
}
