package com.rnd.tms.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.rnd.tms.data.entity.Address;
import com.rnd.tms.data.entity.Client;
import com.rnd.tms.data.repository.ClientRepository;
import com.rnd.tms.ui.editor.ClientEditor;
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

@SpringView(name = ClientView.VIEW_NAME)
public class ClientView extends VerticalLayout implements View{

	public static final String VIEW_NAME = "customer";

	private final ClientRepository repo;

	private final ClientEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public ClientView(ClientRepository repo, ClientEditor editor) {
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
		grid.setColumns("id", "companyName", "contactNumber");
		
		filter.setInputPrompt("Filter by Company name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listClients(e.getText()));

		// Connect selected Client to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editClient((Client) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new Client the new button is clicked
		/*Client newClient= new Client();
		newClient.setAddress(new Address());*/
		addNewBtn.addClickListener(e -> editor.editClient(new Client("")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listClients(filter.getValue());
		});

		// Initialize listing
		listClients(null);
	}

	// tag::listClients[]
	private void listClients(String text) {
		
		BeanItemContainer container = null;
				
		if (StringUtils.isEmpty(text)) {
			container = new BeanItemContainer(Client.class, repo.findAll());
		}
		else {
			container = new BeanItemContainer(Client.class,repo.findByCompanyNameStartsWithIgnoreCase(text));
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
	// end::listClients[]

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
