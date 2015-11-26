package com.rnd.tms.ui.editor;

import java.util.Iterator;




import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.rnd.tms.data.entity.Client;
import com.rnd.tms.data.repository.ClientRepository;
import com.rnd.tms.ui.TmsUiUtils;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form in
 * multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@ViewScope
public class ClientEditor extends GridLayout {

	private final ClientRepository repository;

	/**
	 * The currently edited client
	 */
	private Client client;

	/* Fields to edit properties in Client entity */
	TextField companyName = new TextField("Company Name");
	TextField contactNumber = new TextField("Contact Number");
	
	/* Fields to edit properties in Address entity */
	TextField unitNumber = new TextField("Unit No");
	TextField streetNoName = new TextField("Street No/Name");
	TextField suburb = new TextField("Suburb");
	TextField state = new TextField("State");
	TextField postCode = new TextField("Postcode");
	TextField country = new TextField("Country");
	

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public ClientEditor(ClientRepository repository) {
		super(2,4);
		this.repository = repository;
		setSpacing(true);
		setSizeFull();

		addComponents(companyName, contactNumber);
		addComponents(unitNumber,streetNoName, suburb, state,postCode,country);
		addComponent(actions);
		
		setComponentAlignment(actions, Alignment.BOTTOM_LEFT);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		
		setVisible(false);
	}
	
	@PostConstruct
	private void initComponents(){
		styleComponents();
		registerListeners();
		TmsUiUtils.setTextFieldNullRepresentation(this);
	}
	
	private void registerListeners() {
		save.addClickListener(e -> repository.save(client));
		delete.addClickListener(e -> repository.delete(client));
		cancel.addClickListener(e -> cancelSaveEdit());
	}


	private void styleComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
	}

	public final void cancelSaveEdit() {
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editClient(Client c) {
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			client = repository.findOne(c.getId());
		}
		else {
			client = c;
		}
		cancel.setVisible(persisted);

		BeanFieldGroup.bindFieldsUnbuffered(client, this);
		if(client.getAddress()!=null){
			BeanFieldGroup.bindFieldsUnbuffered(client.getAddress(), this);
		}

		setVisible(true);
		
		save.focus();
		companyName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
