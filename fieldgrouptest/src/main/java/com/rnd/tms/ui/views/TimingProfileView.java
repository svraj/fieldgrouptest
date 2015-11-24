package com.rnd.tms.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.rnd.tms.data.entity.Address;
import com.rnd.tms.data.entity.Client;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.rnd.tms.ui.editor.TimingProfileEditor;
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

@SpringView(name = TimingProfileView.VIEW_NAME)
public class TimingProfileView extends VerticalLayout implements View{

	public static final String VIEW_NAME = "timing_profile";

	private final TimingProfileRepository repo;

	private final TimingProfileEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public TimingProfileView(TimingProfileRepository repo, TimingProfileEditor editor) {
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
		//grid.set
		//grid.get
		grid.setColumns("id","profileName","dailyWorkHours","maxHoursWithoutBreak","minBreakDuration","client.companyName");
		
		filter.setInputPrompt("Filter by Profile name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listTimingProfiles(e.getText()));

		// Connect selected TimingProfile to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editTimingProfile((TimingProfile) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new TimingProfile the new button is clicked
		TimingProfile newTimingProfile= new TimingProfile();
		//newTimingProfile.setClient(new Client());
		addNewBtn.addClickListener(e -> editor.editTimingProfile(newTimingProfile));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listTimingProfiles(filter.getValue());
		});

		// Initialize listing
		listTimingProfiles(null);
	}

	// tag::listTimingProfiles[]
	private void listTimingProfiles(String text) {
		BeanItemContainer container = null;
		if (StringUtils.isEmpty(text)) {
			container = new BeanItemContainer(TimingProfile.class, repo.findAll());
		}
		/*else {
			container = new BeanItemContainer(TimingProfile.class,repo.findByCompanyNameStartsWithIgnoreCase(text));
		}*/
		container.addNestedContainerProperty("client.companyName");
		//container.addNestedContainerProperty("timingProfile.profileName");
		grid.setContainerDataSource(container);
	}
	// end::listTimingProfiles[]

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
