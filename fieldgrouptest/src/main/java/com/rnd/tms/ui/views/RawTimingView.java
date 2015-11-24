package com.rnd.tms.ui.views;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.rnd.tms.data.converter.JodaDateTimeToJavaDate;
import com.rnd.tms.data.converter.JodaDateToStringConverter;
import com.rnd.tms.data.entity.Address;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.RawTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.RawTimingRepository;
import com.rnd.tms.ui.editor.RawTimingEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.CellReference;
import com.vaadin.ui.Grid.CellStyleGenerator;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = RawTimingView.VIEW_NAME)
public class RawTimingView extends VerticalLayout implements View{

	public static final String VIEW_NAME = "raw_timing";

	private final RawTimingRepository repo;

	private final RawTimingEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public RawTimingView(RawTimingRepository repo, RawTimingEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("New", FontAwesome.PLUS);
	}

	@PostConstruct
	void init() {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		addComponent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setColumns("id", "employee.firstName","employee.LastName","timingProfile.profileName","timingProfile.client.companyName",
				"timingProfile.remarks","inDateTime", "outDateTime");
		
		filter.setInputPrompt("Filter by Company name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listRawTimings(e.getText()));

		// Connect selected RawTiming to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editRawTiming((RawTiming) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new RawTiming the new button is clicked
		RawTiming newRawTiming= new RawTiming();
		newRawTiming.setEmployee(new Employee());
		newRawTiming.setTimingProfile(new TimingProfile());
		addNewBtn.addClickListener(e -> editor.editRawTiming(newRawTiming));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listRawTimings(filter.getValue());
		});

		// Initialize listing
		listRawTimings(null);
	}

	// tag::listRawTimings[]
	private void listRawTimings(String text) {
		
		BeanItemContainer container = null;
				
		if (StringUtils.isEmpty(text)) {
			container = new BeanItemContainer(RawTiming.class, repo.findAll());
		}
		container.addNestedContainerProperty("employee.firstName");
		container.addNestedContainerProperty("employee.LastName");
		container.addNestedContainerProperty("timingProfile.profileName");
		container.addNestedContainerProperty("timingProfile.client.companyName");
		container.addNestedContainerProperty("timingProfile.remarks");
		
		grid.setContainerDataSource(container);
		
        grid.getColumn("inDateTime").setConverter(new JodaDateToStringConverter());
        grid.getColumn("outDateTime").setConverter(new JodaDateToStringConverter());
		
		// Create a header row to hold column filters
		/*HeaderRow filterRow = grid.appendHeaderRow();
		for (Object pid: grid.getContainerDataSource()
                .getContainerPropertyIds()) {
			HeaderCell cell = filterRow.getCell(pid);
			
			// Have an input field to use for filter
			TextField filterField = new TextField();
			filterField.setColumns(8);
			
			
			// Update filter When the filter input is changed
			filterField.addTextChangeListener(change -> {
			   // Can't modify filters so need to replace
			   container.removeContainerFilters(pid);
			   
			   // (Re)create the filter if necessary
			   if (! change.getText().isEmpty()){
			      // container.addContainerFilter(new SimpleStringFilter(pid,change.getText(), true, false));
			   }
				});
			cell.setComponent(filterField);
			}*/
		
		//Indexed
	}
	// end::listRawTimings[]

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
