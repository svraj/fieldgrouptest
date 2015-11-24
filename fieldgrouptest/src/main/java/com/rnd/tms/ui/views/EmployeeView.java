package com.rnd.tms.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.rnd.tms.data.entity.Address;
import com.rnd.tms.data.entity.Employee;
import com.rnd.tms.data.repository.EmployeeRepository;
import com.rnd.tms.ui.editor.EmployeeEditor;
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
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = EmployeeView.VIEW_NAME)
public class EmployeeView extends VerticalLayout implements View{

	public static final String VIEW_NAME = "employee";

	private final EmployeeRepository repo;

	private final EmployeeEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public EmployeeView(EmployeeRepository repo, EmployeeEditor editor) {
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
		grid.setColumns("id", "firstName", "lastName","employeeCode");
		
		filter.setInputPrompt("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listEmployees(e.getText()));

		// Connect selected Employee to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editEmployee((Employee) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new Employee the new button is clicked
		/*Employee newEmployee= new Employee();
		newEmployee.setAddress(new Address());*/
		addNewBtn.addClickListener(e -> editor.editEmployee(new Employee("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listEmployees(filter.getValue());
		});

		// Initialize listing
		listEmployees(null);
	}

	// tag::listEmployees[]
	private void listEmployees(String text) {
		
		BeanItemContainer container = null;
				
		if (StringUtils.isEmpty(text)) {
			container = new BeanItemContainer(Employee.class, repo.findAll());
		}
		else {
			container = new BeanItemContainer(Employee.class,repo.findByLastNameStartsWithIgnoreCase(text));
		}
		grid.setContainerDataSource(container);
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
	// end::listEmployees[]

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
