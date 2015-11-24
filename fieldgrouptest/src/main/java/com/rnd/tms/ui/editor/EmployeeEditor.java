package com.rnd.tms.ui.editor;

import java.util.Iterator;

import javassist.expr.Instanceof;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.repository.EmployeeRepository;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
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
public class EmployeeEditor extends GridLayout {

	private final EmployeeRepository repository;

	/**
	 * The currently edited employee
	 */
	private Employee employee;

	/* Fields to edit properties in Employee entity */
	TextField employeeCode = new TextField("Employee Code");
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	
	
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
	public EmployeeEditor(EmployeeRepository repository) {
		super(2,4);
		this.repository = repository;
		setSpacing(true);
		setSizeFull();
		
		//setColumnExpandRatio(1, 0.5f);
		//employeeCode.setSizeFull();
		//addComponent(employeeCode,0,0,1,1);
		addComponent(employeeCode);
		addComponents(firstName, lastName);
		addComponents(unitNumber,streetNoName, suburb, state,postCode,country);
		addComponent(actions);
		
		// Configure and style components
		
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
		//addComponent(actions);
		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(employee));
		delete.addClickListener(e -> repository.delete(employee));
		//cancel.addClickListener(e -> editEmployee(employee));
		cancel.addClickListener(e -> cancelSaveEdit());
		setVisible(false);
	}

	public final void cancelSaveEdit() {
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editEmployee(Employee c) {
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			employee = repository.findOne(c.getId());
		}
		else {
			employee = c;
		}
		cancel.setVisible(persisted);

		// Bind employee properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		Iterator<Component> componentIterator = this.getComponentIterator();
		Component component = (Component)componentIterator.next();
		while(componentIterator.hasNext()){
			component = (Component)componentIterator.next();
			if(component instanceof TextField){
				TextField field = (TextField)component;
				field.setNullRepresentation("");
			}
		}
		BeanFieldGroup.bindFieldsUnbuffered(employee, this);
		BeanFieldGroup.bindFieldsUnbuffered(employee.getAddress(), this);
		//BeanFieldGroup.this.getFieldFactory().
		/*BeanItem<Employee> item =  new BeanItem<Employee>(employee);
		item.addNestedProperty("address.streetNoName");
		item.addNestedProperty("address.suburb");
		item.addNestedProperty("address.state");
		item.addNestedProperty("address.postCode");
		item.addNestedProperty("address.country");
		
		BeanFieldGroup<Employee> beanFieldGroup = new BeanFieldGroup<Employee>(Employee.class);
		
		
		for(Object propertyId:item.getItemPropertyIds() ){
			addComponent(beanFieldGroup.buildAndBind(propertyId));
		}*/

		setVisible(true);
		
		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		firstName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
