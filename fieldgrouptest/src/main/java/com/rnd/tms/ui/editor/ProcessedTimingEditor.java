package com.rnd.tms.ui.editor;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.rnd.tms.data.converter.JodaDateTimeToJavaDate;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.EmployeeRepository;
import com.rnd.tms.data.repository.ProcessedTimingRepository;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.rnd.tms.ui.TmsUiUtils;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form
 * in multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for
 * all your forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@ViewScope
public class ProcessedTimingEditor extends GridLayout {

	private final ProcessedTimingRepository repository;
	@Autowired
	private TimingProfileRepository timingProfileRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * The currently edited rawTiming
	 */
	private ProcessedTiming rawTiming;

	HorizontalLayout rawTimingLayout = new HorizontalLayout();
	DateField inDateTime = new DateField("In Date Time");
	DateField outDateTime = new DateField("Out Date Time");
	
	//TextField 
	
	

	HorizontalLayout timingProfileLayout = new HorizontalLayout();

	@PropertyId("timingProfile.client.companyName")
	TextField companyName = new TextField("Client");

	// @PropertyId("timingProfile")
	ComboBox timingProfile = new ComboBox("Profile Name");

	@PropertyId("timingProfile.remarks")
	TextField remarks = new TextField("Remarks");

	HorizontalLayout employeeLayout = new HorizontalLayout();
	// @PropertyId("employee.firstName")
	ComboBox employee = new ComboBox("Employee");
	// @PropertyId("employee.lastName")
	// TextField lastName = new TextField("Employee Last name");

	HorizontalLayout buttonLayout = new HorizontalLayout();
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	Button process = new Button("Process", FontAwesome.SAVE);

	// CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public ProcessedTimingEditor(ProcessedTimingRepository repository) {
		//super(2,8);
		this.repository = repository;
		setSpacing(true);
		setSizeFull();

		employeeLayout.setSpacing(true);
		employeeLayout.addComponents(employee);
		addComponent(employeeLayout);

		timingProfileLayout.setSpacing(true);
		companyName.setReadOnly(true);
		remarks.setReadOnly(true);
		timingProfileLayout.addComponents(timingProfile, companyName, remarks);
		addComponent(timingProfileLayout);

		rawTimingLayout.setSpacing(true);
		rawTimingLayout.addComponents(inDateTime, outDateTime);
		addComponent(rawTimingLayout);

		buttonLayout.setSpacing(true);

		buttonLayout.addComponents(save, cancel, delete,process);
		addComponent(buttonLayout);
		setComponentAlignment(buttonLayout, Alignment.BOTTOM_LEFT);

		inDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		outDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		inDateTime.setResolution(Resolution.MINUTE);
		outDateTime.setResolution(Resolution.MINUTE);
		inDateTime.setConverter(new JodaDateTimeToJavaDate());
		outDateTime.setConverter(new JodaDateTimeToJavaDate());

		// Configure and style components

		// actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		setVisible(false);
	}

	@PostConstruct
	private void initComponents() {
		prepareTimingProfileCombo();
		prepareEmployeeCombo();
		//styleComponents();
		registerListeners();
		TmsUiUtils.setTextFieldNullRepresentation(this);
		remarks.setEnabled(false);
	}

	private void registerListeners() {
		save.addClickListener(e -> repository.save(rawTiming));
		delete.addClickListener(e -> repository.delete(rawTiming));
		cancel.addClickListener(e -> cancelSaveEdit());
	}

	private void styleComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		process.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
	}

	public final void cancelSaveEdit() {
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editProcessedTiming(ProcessedTiming c) {
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			rawTiming = repository.findOne(c.getId());
		} else {
			rawTiming = c;
			initComponents();
		}
		cancel.setVisible(persisted);

		BeanFieldGroup<ProcessedTiming> rawTimingBinder = new BeanFieldGroup<ProcessedTiming>(
				ProcessedTiming.class);
		rawTimingBinder.bindMemberFields(this);
		rawTimingBinder.setItemDataSource(rawTiming);
		rawTimingBinder.setBuffered(false);
		remarks.setReadOnly(true);
		companyName.setReadOnly(true);
		setVisible(true);
		save.focus();
	}

	private void prepareTimingProfileCombo() {
		List<TimingProfile> timingProfiles = timingProfileRepository.findAll();
		BeanItemContainer<TimingProfile> timingProfileContainer = new BeanItemContainer<TimingProfile>(
				TimingProfile.class, timingProfiles);
		// timingProfile.setItemCaptionMode(ItemCaptionMode.PROPERTY);

		timingProfile.setItemCaptionPropertyId("profileName");
		timingProfile.setContainerDataSource(timingProfileContainer);
		timingProfile.setBuffered(false);
		timingProfile.setImmediate(true);
	}

	private void prepareEmployeeCombo() {
		List<Employee> employees = employeeRepository.findAll();
		BeanItemContainer<Employee> employeeContainer = new BeanItemContainer<Employee>(
				Employee.class, employees);
		// employee.setItemCaptionMode(ItemCaptionMode.ID);
		employee.setItemCaptionPropertyId("firstName");
		employee.setContainerDataSource(employeeContainer);
		employee.setImmediate(true);
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}
