package com.rnd.tms.ui.editor;

import com.rnd.tms.data.entity.Employee;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

public class EmployeeForm extends AbstractForm<Employee> {

    TextField employeeCode = new MTextField("Name");
    TextField firstName = new MTextField("First name");
    TextField lastName = new MTextField("Last name");
    TextField email = new MTextField("Email");
    TextField phoneNumber = new MTextField("Phone number");
    DateField dateOfBirth = new DateField("Birthday");
    // Typically you'd use std CheckBox, using Swithch to demonstrate
    // the awesome extendions by the community: http://vaadin.com/directory
    Switch foreigner = new Switch("Foreigner");
    

    public EmployeeForm(Employee employee) {
        setSizeUndefined();
        setEntity(employee);
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                		employeeCode,
                		firstName,
                		lastName,
                        email,
                        phoneNumber,
                        dateOfBirth,
                        foreigner
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }

}
