package com.rnd.tms.ui.viritin.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.ClientRepository;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.rnd.tms.ui.editor.TimingProfileForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = TimingProfileViritinView.VIEW_NAME)
public class TimingProfileViritinView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "timing_profiles";
	
	@Autowired
	private TimingProfileRepository timingProfileRepository;
	
	@Autowired
	private TimingProfileForm timingProfileForm ;
	
	private MTable<TimingProfile> list = new MTable<>(TimingProfile.class)
            .withProperties("id", "profileName", "client.companyName")
            .withColumnHeaders("ID", "Profile Name", "Client name")
            .setSortableProperties("profileName", "client.companyName")
            .withFullWidth();
	
	private Button addNew = new MButton(FontAwesome.PLUS, this::add);
    private Button edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
    private Button delete = new ConfirmButton(FontAwesome.TRASH_O,
            "Are you sure you want to delete the entry?", this::remove);

    @PostConstruct
    protected void init() {
    	//list.getContainerDataSource().get
        addComponent(new MVerticalLayout(
                        new MHorizontalLayout(addNew, edit, delete),
                        list
                ).expand(list)
        );
        listEntities();
        list.addMValueChangeListener(e -> adjustActionButtonState());
    }

    protected void adjustActionButtonState() {
        boolean hasSelection = list.getValue() != null;
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }

    static final int PAGESIZE = 45;

    private void listEntities() {
        // A dead simple in memory listing would be:
         list.setBeans(timingProfileRepository.findAll());

        // Lazy binding with SortableLazyList: memory and query efficient 
        // connection from Vaadin Table to Spring Repository
        // Note that fetching strategies can be given to MTable constructor as well.
        // Use this approach if you expect you'll have lots of data in your 
        // table.
        
        /*list.setBeans(new SortableLazyList<>(
                // entity fetching strategy
                (firstRow, asc, sortProperty) -> timingProfileRepository.findAllBy(
                        new PageRequest(
                                firstRow / PAGE_SIZE, 
                                PAGE_SIZE,
                                asc ? Sort.Direction.ASC : Sort.Direction.DESC,
                                // fall back to id as "natural order"
                                sortProperty == null ? "id" : sortProperty
                        )
                ),
                // count fetching strategy
                () -> (int) timingProfileRepository.count(),
                PAGE_SIZE
        ));*/
        adjustActionButtonState();

    }

    public void add(ClickEvent clickEvent) {
        edit(new TimingProfile());
    }

    public void edit(ClickEvent e) {
        edit(list.getValue());
    }

    public void remove(ClickEvent e) {
        timingProfileRepository.delete(list.getValue());
        list.setValue(null);
        listEntities();
    }

    protected void edit(final TimingProfile timingProfile) {
        //timingProfileForm = new TimingProfileForm(timingProfile,clientRepository);
    	timingProfileForm.setTimingProfile(timingProfile);
        timingProfileForm.openInModalPopup();
        //addComponent(timingProfileForm);
        //timingProfileForm.setVisible(true);
        timingProfileForm.setSavedHandler(this::saveEntry);
        timingProfileForm.setResetHandler(this::resetEntry);
    }

    public void saveEntry(TimingProfile entry) {
        timingProfileRepository.save(entry);
        listEntities();
        closeWindow();
    }

    public void resetEntry(com.rnd.tms.data.entity.TimingProfile entry) {
        listEntities();
        closeWindow();
    }

    protected void closeWindow() {
        getUI().getWindows().stream().forEach(w -> getUI().removeWindow(w));
    }

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
