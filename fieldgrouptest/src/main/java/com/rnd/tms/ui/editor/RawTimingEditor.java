package com.rnd.tms.ui.editor;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;
import com.rnd.tms.data.converter.JodaDateTimeToJavaDate;
import com.rnd.tms.data.converter.JodaDateToStringConverter;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.RawTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.RawTimingRepository;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
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
public class RawTimingEditor extends GridLayout {

	private final RawTimingRepository repository;
	@Autowired
	private TimingProfileRepository timingProfileRepository;

	/**
	 * The currently edited rawTiming
	 */
	private RawTiming rawTiming;

	HorizontalLayout rawTimingLayout = new HorizontalLayout();
	DateField inDateTime = new DateField("In Date Time");
	DateField outDateTime = new DateField("Out Date Time");
	
	
	HorizontalLayout timingProfileLayout = new HorizontalLayout();
	@PropertyId("timingProfile.client.companyName")
	TextField companyName = new TextField("Client");
	@PropertyId("timingProfile.profileName")
	ComboBox profileName = new ComboBox("Profile Name");
	@PropertyId("timingProfile.remarks")
	TextField remarks = new TextField("Remarks");
	
	HorizontalLayout employeeLayout = new HorizontalLayout();
	@PropertyId("employee.firstName")
	TextField  firstName= new TextField("Employee First name");
	@PropertyId("employee.lastName")
	TextField  lastName = new TextField("Employee Last name");
	
	

	HorizontalLayout buttonLayout = new HorizontalLayout();
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	//CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public RawTimingEditor(RawTimingRepository repository) {
		super(2,4);
		this.repository = repository;
		setSpacing(true);
		setSizeFull();
		
		//setColumnExpandRatio(1, 0.5f);
		//CcientCode.setSizeFull();
		//addComponent(CcientCode,0,0,1,1);
		
		employeeLayout.setSpacing(true);
		employeeLayout.addComponents(firstName,lastName);
		addComponent(employeeLayout);
		
		timingProfileLayout.setSpacing(true);
		timingProfileLayout.addComponents(profileName,companyName,remarks);
		addComponent(timingProfileLayout);
		
		rawTimingLayout.setSpacing(true);
		rawTimingLayout.addComponents(inDateTime,outDateTime);
		addComponent(rawTimingLayout);
		
		buttonLayout.setSpacing(true);
		
		buttonLayout.addComponents(save, cancel, delete);
		addComponent(buttonLayout);
		setComponentAlignment(buttonLayout, Alignment.BOTTOM_LEFT);
		
		inDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		outDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		inDateTime.setResolution(Resolution.MINUTE);
		outDateTime.setResolution(Resolution.MINUTE);
		inDateTime.setConverter(new JodaDateTimeToJavaDate());
		outDateTime.setConverter(new JodaDateTimeToJavaDate());
		
		
		
		// Configure and style components
		
		//actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
		//addComponent(actions);
		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(rawTiming));
		delete.addClickListener(e -> repository.delete(rawTiming));
		//cancel.addClickListener(e -> editRawTiming(Ccient));
		cancel.addClickListener(e -> cancelSaveEdit());
		setVisible(false);
	}

	public final void cancelSaveEdit() {
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editRawTiming(RawTiming c) {
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			rawTiming = repository.findOne(c.getId());
		}
		else {
			rawTiming = c;
			List<TimingProfile> timingProfiles = timingProfileRepository.findAll();
			BeanItemContainer<TimingProfile> timingProfileContainer = 
					   new BeanItemContainer<>(TimingProfile.class, timingProfiles);
			//timingProfileContainer.
			profileName.setContainerDataSource(timingProfileContainer);
			profileName.setItemCaptionPropertyId("profileName");
			//profileName.set
		}
		cancel.setVisible(persisted);

		// Bind RawTiming properties to similarly named fields
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
		
		
		/*BeanFieldGroup.bindFieldsUnbuffered(rawTiming, this);
		// timingBinder.setItemDataSource(rawTiming);
		 //timingBinder.set
		//beanFieldGroup.buildAndBind(caption, inDateTime, DateField.class);
		BeanFieldGroup.bindFieldsUnbuffered(rawTiming.getEmployee(), this);
		BeanFieldGroup.bindFieldsUnbuffered(rawTiming.getTimingProfile().getClient(), this);*/
		
		//BeanFieldGroup.bindFieldsUnbuffered(rawTiming.getAddress(), this);
		//BeanFieldGroup.this.getFieldFactory().
		//BeanItem<RawTiming> item =  new BeanItem<RawTiming>(Ccient);
		BeanFieldGroup<RawTiming> rawTimingBinder = new BeanFieldGroup<RawTiming>(RawTiming.class);
		rawTimingBinder.bindMemberFields(this);
		rawTimingBinder.setItemDataSource(rawTiming);
		rawTimingBinder.setBuffered(false);
		
		/*BeanFieldGroup<Employee> employeeBinder = new BeanFieldGroup<Employee>(Employee.class);
		employeeBinder.bindMemberFields(this.employeeLayout);
		employeeBinder.setItemDataSource(rawTiming.getEmployee());
		employeeBinder.setBuffered(false);*/
		
		/*BeanFieldGroup<Employee> employeeBinder = new BeanFieldGroup<Employee>(Employee.class);
		employeeBinder.bindMemberFields(this.employeeLayout);
		employeeBinder.setItemDataSource(rawTiming.getEmployee());
		employeeBinder.setBuffered(false);*/
		
	/*	BeanItem<RawTiming> item =  new BeanItem<RawTiming>(rawTiming);
		item.addNestedProperty("timingProfile.profileName");
		item.addNestedProperty("timingProfile.client.companyName");
		item.addNestedProperty("employee.firstName");
		item.addNestedProperty("employee.lastName");
		BeanFieldGroup<RawTiming> rawTimingBinder = new BeanFieldGroup<RawTiming>(RawTiming.class);
		rawTimingBinder.setItemDataSource(item);
		rawTimingBinder.bindMemberFields(this);
		rawTimingBinder.setBuffered(false);*/
		
		/*for(Object propertyId:item.getItemPropertyIds() ){
			addComponent(rawTimingBinder.buildAndBind(propertyId));
		}*/

		setVisible(true);
		
		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		//companyName.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
