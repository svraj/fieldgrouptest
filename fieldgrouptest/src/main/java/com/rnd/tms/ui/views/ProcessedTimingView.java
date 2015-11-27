package com.rnd.tms.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.rnd.tms.data.converter.JodaDateToStringConverter;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.ProcessedTimingRepository;
import com.rnd.tms.ui.editor.ProcessedTimingEditor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = ProcessedTimingView.VIEW_NAME)
public class ProcessedTimingView extends HorizontalSplitPanel implements View{

	public static final String VIEW_NAME = "timings";

	private final ProcessedTimingRepository repo;

	private final ProcessedTimingEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public ProcessedTimingView(ProcessedTimingRepository repo, ProcessedTimingEditor editor) {
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
		VerticalLayout leftLayout = new VerticalLayout(actions, grid);
		VerticalLayout rightLayout = new VerticalLayout(editor);
		setFirstComponent(leftLayout);
		setSecondComponent(editor);

		// Configure layouts and components
		actions.setSpacing(true);
		leftLayout.setMargin(true);
		leftLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setColumns("id", "employee.firstName","employee.LastName","timingProfile.profileName",
				"inDateTime", "outDateTime");
		grid.addStyleName(ValoTheme.TABLE_BORDERLESS);
		filter.setInputPrompt("Filter by Company name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listProcessedTimings(e.getText()));

		// Connect selected ProcessedTiming to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editProcessedTiming((ProcessedTiming) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new ProcessedTiming the new button is clicked
		ProcessedTiming newProcessedTiming= new ProcessedTiming();
		newProcessedTiming.setEmployee(new Employee());
		newProcessedTiming.setTimingProfile(new TimingProfile());
		addNewBtn.addClickListener(e -> editor.editProcessedTiming(newProcessedTiming));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listProcessedTimings(filter.getValue());
		});

		// Initialize listing
		listProcessedTimings(null);
	}

	// tag::listProcessedTimings[]
	private void listProcessedTimings(String text) {
		
		BeanItemContainer container = null;
				
		if (StringUtils.isEmpty(text)) {
			container = new BeanItemContainer(ProcessedTiming.class, repo.findAll());
		}
		container.addNestedContainerProperty("employee.firstName");
		container.addNestedContainerProperty("employee.LastName");
		container.addNestedContainerProperty("timingProfile.profileName");
		//container.addNestedContainerProperty("timingProfile.client.companyName");
		//container.addNestedContainerProperty("timingProfile.remarks");
		
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
	// end::listProcessedTimings[]

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
