package com.rnd.tms.ui.editor;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.web.bindery.autobean.shared.AutoBean.PropertyName;
import com.rnd.tms.data.converter.JodaDateTimeToJavaDate;
import com.rnd.tms.data.converter.JodaDateToStringConverter;
import com.rnd.tms.data.converter.JodaDurationToStringConverter;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
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
public class TimingProfileEditor extends GridLayout {

	private final TimingProfileRepository repository;

	/**
	 * The currently edited timingProfile
	 */
	private TimingProfile timingProfile;

	//HorizontalLayout timingProfileLayout = new HorizontalLayout();
	@PropertyId("profileName")
	TextField profileName = new TextField("Profile Name");
	@PropertyId("dailyWorkHours")
	TextField dailyWorkHours = new TextField("Daily Work Hrs");
	@PropertyId("minBreakDuration")
	TextField minBreakDuration = new TextField("Min break Duration");
	@PropertyId("maxHoursWithoutBreak")
	TextField maxHoursWithoutBreak = new TextField("Max work hours without break");
	@PropertyId("minBreakAfterMaxHoursWithoutBreak")
	TextField minBreakAfterMaxHoursWithoutBreak = new TextField("Min Break after Max Hrs without Break");
	
	@PropertyId("overtimeAllowed")
	CheckBox overtimeAllowed = new CheckBox("Overtime allowed");
	@PropertyId("remarks")
	TextField remarks = new TextField("Remarks");
	
	
	HorizontalLayout clientLayout = new HorizontalLayout();
	@PropertyId("client.companyName")
	TextField companyName = new TextField("Client");
	
	HorizontalLayout buttonLayout = new HorizontalLayout();
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	//CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public TimingProfileEditor(TimingProfileRepository repository) {
		super(2,4);
		this.repository = repository;
		setSpacing(true);
		setSizeFull();
		
		addComponents(profileName,dailyWorkHours,minBreakDuration,maxHoursWithoutBreak,minBreakAfterMaxHoursWithoutBreak,overtimeAllowed,remarks);
		
		clientLayout.setSpacing(true);
		clientLayout.addComponents(companyName);
		addComponent(clientLayout);
		
		buttonLayout.setSpacing(true);
		buttonLayout.addComponents(save, cancel, delete);
		addComponent(buttonLayout);
		setComponentAlignment(buttonLayout, Alignment.BOTTOM_LEFT);
		
		
		dailyWorkHours.setConverter(new JodaDurationToStringConverter());
		minBreakDuration.setConverter(new JodaDurationToStringConverter());
		maxHoursWithoutBreak.setConverter(new JodaDurationToStringConverter());
		minBreakAfterMaxHoursWithoutBreak.setConverter(new JodaDurationToStringConverter());
		
		/*inDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		outDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		inDateTime.setResolution(Resolution.MINUTE);
		outDateTime.setResolution(Resolution.MINUTE);
		inDateTime.setConverter(new JodaDateTimeToJavaDate());
		*/
		
		// Configure and style components
		
		//actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
		//addComponent(actions);
		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(timingProfile));
		delete.addClickListener(e -> repository.delete(timingProfile));
		//cancel.addClickListener(e -> editTimingProfile(Ccient));
		cancel.addClickListener(e -> cancelSaveEdit());
		setVisible(false);
	}

	public final void cancelSaveEdit() {
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editTimingProfile(TimingProfile c) {
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			timingProfile = repository.findOne(c.getId());
		}
		else {
			timingProfile = c;
		}
		cancel.setVisible(persisted);

		Iterator<Component> componentIterator = this.getComponentIterator();
		Component component = (Component)componentIterator.next();
		while(componentIterator.hasNext()){
			component = (Component)componentIterator.next();
			if(component instanceof TextField){
				TextField field = (TextField)component;
				field.setNullRepresentation("");
			}
		}
		
		BeanFieldGroup<TimingProfile> timingProfileBinder = new BeanFieldGroup<TimingProfile>(TimingProfile.class);
		timingProfileBinder.bindMemberFields(this);
		timingProfileBinder.setItemDataSource(timingProfile);
		timingProfileBinder.setBuffered(false);
		setVisible(true);
		save.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
