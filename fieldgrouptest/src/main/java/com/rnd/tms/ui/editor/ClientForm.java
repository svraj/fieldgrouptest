package com.rnd.tms.ui.editor;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.rnd.tms.data.entity.Client;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public class ClientForm extends AbstractForm<Client>{
	
	TextField companyName = new MTextField("Company name");
	TextField contactNumber = new MTextField("Contact number");
	
	public ClientForm(Client client){
		setEntity(client);
		setSizeUndefined();
	}

	@Override
	protected Component createContent() {
		return new MVerticalLayout(
				new MFormLayout(companyName,contactNumber)
				.withWidth(""),
				getToolbar()
				).withWidth("");
	}

}
