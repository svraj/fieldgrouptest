package com.rnd.tms.ui.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.fields.ElementCollectionField;
import org.vaadin.viritin.fields.EnumSelect;
import org.vaadin.viritin.fields.MDateField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.fields.TypedSelect;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.rnd.tms.data.converter.JodaDateTimeToJavaDate;
import com.rnd.tms.data.converter.JodaDateToStringTimeConverter;
import com.rnd.tms.data.converter.JodaDurationToStringConverter;
import com.rnd.tms.data.entity.BreakDetail;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.entity.BreakDetail.BreakType;
import com.rnd.tms.data.repository.EmployeeRepository;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

@SpringComponent
@ViewScope
public class ProcessedTimingForm extends AbstractForm<ProcessedTiming> {

	TypedSelect<Employee> employee = new TypedSelect()
			.withCaption("Employee name");
	TypedSelect<TimingProfile> timingProfile = new TypedSelect()
			.withCaption("Timing Profile");
	// TextField companyName = new MTextField("Client name");

	DateField inDateTime = new MDateField("In Date time");
	DateField outDateTime = new MDateField("Out Date time");

	TextField mainBreakDuration = new MTextField("Main Break");
	TextField otherBreaksDuration = new MTextField("Other Breaks");
	TextField totalBreakDuration = new MTextField("Total Breaks");
	TextField totalWorkingHours = new MTextField("Total Hours");

	TextField dayBalance = new MTextField("Day Balance");
	TextField totalOT = new MTextField("Total OT");

	ElementCollectionField<BreakDetail> breakDetails = new ElementCollectionField<>(
			BreakDetail.class, BreakRowEditorModel.class).expand("breakStart");

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TimingProfileRepository timingProfileRepository;

	public void setProcessedTiming(ProcessedTiming processedTiming) {
		setEntity(processedTiming);
	};

	public ProcessedTimingForm(ProcessedTiming processedTiming) {
		setSizeUndefined();
		setEntity(processedTiming);
	}

	public ProcessedTimingForm() {

	}

	private static void setComponentCommonWidth(String width,
			Component... components) {
		for (Component component : components) {
			component.setWidth(width);
		}
	}

	private static void setComponentCommonConverter(Converter converter,
			Component... components) {
		for (Component component : components) {
			TextField field = (TextField) component;
			field.setConverter(converter);
		}
	}

	public void init() {
		System.out.println("init");
	}

	public static final class BreakRowEditorModel {

		EnumSelect<BreakType> breakType = new EnumSelect<BreakType>();
		TextField breakStart = new MTextField();
		TextField breakEnd = new MTextField();
		MTextField remarks = new MTextField();
		MTextField actual = new MTextField();
		MTextField effective = new MTextField();

		public BreakRowEditorModel() {
			super();
			setComponentCommonWidth("4em", breakStart, breakEnd, actual,
					effective);
			setComponentCommonConverter(new JodaDateToStringTimeConverter(),breakStart, breakEnd);
			setComponentCommonConverter(new JodaDurationToStringConverter(),actual, effective);
		}
	}

	@Override
	protected Component createContent() {
		inDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		outDateTime.setDateFormat("E MM/dd/yyyy HH:mm");
		inDateTime.setResolution(Resolution.MINUTE);
		outDateTime.setResolution(Resolution.MINUTE);
		inDateTime.setConverter(new JodaDateTimeToJavaDate());
		outDateTime.setConverter(new JodaDateTimeToJavaDate());

		setComponentCommonWidth("4em", mainBreakDuration, otherBreaksDuration,totalBreakDuration);
		setComponentCommonWidth("4em", totalWorkingHours, dayBalance, totalOT);

		employee.setSizeUndefined();
		employee.setOptions(employeeRepository.findAll());
		// String employeeName =
		employee.setCaptionGenerator(c -> c.getFirstName());

		timingProfile.setSizeUndefined();
		timingProfile.setOptions(timingProfileRepository.findAll());
		timingProfile.setCaptionGenerator(c -> c.getProfileName());

		// breakDetails.get

		setComponentCommonConverter(new JodaDurationToStringConverter(),mainBreakDuration,otherBreaksDuration,totalBreakDuration);
		setComponentCommonConverter(new JodaDurationToStringConverter(),totalWorkingHours,dayBalance,totalOT);
		
		GridLayout timingResultBreak = new GridLayout(6, 1);
		timingResultBreak.setSpacing(true);
		timingResultBreak.addComponents(mainBreakDuration, otherBreaksDuration,totalBreakDuration);
		timingResultBreak.addComponents(totalWorkingHours, dayBalance);

		timingResultBreak.addComponent(totalOT);
		// timingResultBreak.setSizeUndefined();
		timingResultBreak.setWidth(100, Unit.PERCENTAGE);
		timingResultBreak.setSizeFull();

		/*
		 * if(getEntity().getTimingProfile()!=null){
		 * if(getEntity().getTimingProfile().getOvertimeAllowed()){
		 * timingResultBreak.addComponent(totalOT); } }
		 */

		return new MVerticalLayout(new MFormLayout(
				// profileName,
				new MHorizontalLayout(employee, timingProfile),
				new MHorizontalLayout(inDateTime, outDateTime), breakDetails,
				timingResultBreak
		// dailyWorkHours,
		// minBreakDuration,
		// maxHoursWithoutBreak,
		// minBreakAfterMaxHoursWithoutBreak,
		// overtimeAllowed,
		// remarks
				).withWidth(""), getToolbar()).withWidth("");
	}

}
