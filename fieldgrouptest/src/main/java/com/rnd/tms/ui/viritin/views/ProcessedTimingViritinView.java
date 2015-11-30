package com.rnd.tms.ui.viritin.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.rnd.tms.business.dto.TimingsProcessDTO;
import com.rnd.tms.business.enums.TimingRecordProcessStatus;
import com.rnd.tms.data.converter.JodaDateToStringConverter;
import com.rnd.tms.data.converter.JodaDurationToStringConverter;
import com.rnd.tms.data.entity.ProcessedTiming;
import com.rnd.tms.data.repository.ProcessedTimingRepository;
import com.rnd.tms.services.TimingsProcessingService;
import com.rnd.tms.ui.editor.ProcessedTimingForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = ProcessedTimingViritinView.VIEW_NAME)
public class ProcessedTimingViritinView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "processed_timings";
	
	@Autowired
	private ProcessedTimingRepository timingProfileRepository;
	
	@Autowired
	private ProcessedTimingForm timingProfileForm ;
	
	@Autowired
	private TimingsProcessingService timingsProcessingService;
	
	static final int PAGE_SIZE = 10;
	
	private MTable<ProcessedTiming> list = new MTable<>(ProcessedTiming.class)
            .withProperties("id", "timingProfile.profileName", "employee.firstName","inDateTime","outDateTime","mainBreakDuration","totalWorkingHours","dayBalance")
            .withColumnHeaders("ID", "Profile Name", "Employee name","In","Out","Main break","Total work Hrs","Balance")
            .setSortableProperties("id", "timingProfile.profileName", "employee.firstName","inDateTime","outDateTime","mainBreakDuration","totalWorkingHours","dayBalance")
            .withFullWidth();
            
	
	private Button addNew = new MButton(FontAwesome.PLUS, this::add);
    private Button edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
    private Button delete = new ConfirmButton(FontAwesome.TRASH_O,
            "Are you sure you want to delete the entry?", this::remove);
    private Button process = new MButton(FontAwesome.CLOCK_O, this::process);

    @PostConstruct
    protected void init() {
    	//list.getContainerDataSource().get
    	list.setConverter("inDateTime", new JodaDateToStringConverter());
    	list.setConverter("outDateTime", new JodaDateToStringConverter());
    	list.setConverter("mainBreakDuration", new JodaDurationToStringConverter());
    	list.setConverter("totalWorkingHours", new JodaDurationToStringConverter());
    	list.setConverter("dayBalance", new JodaDurationToStringConverter());
    	
    	list.setPageLength(PAGE_SIZE);
        addComponent(new MVerticalLayout(
                        new MHorizontalLayout(addNew, edit, delete,process),
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
        edit(new ProcessedTiming());
    }

    public void edit(ClickEvent e) {
        edit(list.getValue());
    }
    
    public void process(ClickEvent e){
    	ProcessedTiming processedTiming = list.getValue();
    	System.out.println("Going to process -"+processedTiming.getId());
    	TimingsProcessDTO timingsProcessDTO = process(processedTiming);
    	if(TimingRecordProcessStatus.FAILED == timingsProcessDTO.getProcessStatus()){
    		Notification.show("Failed!!!");
    	}
    	
    	
    }
    
    protected TimingsProcessDTO process(final ProcessedTiming processedTiming){
    	TimingsProcessDTO timingsProcessDTO = null;
    	try{
    		 timingsProcessDTO = timingsProcessingService.processProcessedTiming(processedTiming);
    	}catch(Exception e){
    		timingsProcessDTO = new TimingsProcessDTO();
    		timingsProcessDTO.setProcessStatus(TimingRecordProcessStatus.FAILED);
    	}
		return timingsProcessDTO;
    }

    public void remove(ClickEvent e) {
        timingProfileRepository.delete(list.getValue());
        list.setValue(null);
        listEntities();
    }

    protected void edit(final ProcessedTiming timingProfile) {
        //timingProfileForm = new ProcessedTimingForm(timingProfile,clientRepository);
    	timingProfileForm.setProcessedTiming(timingProfile);
        timingProfileForm.openInModalPopup();
        //addComponent(timingProfileForm);
        //timingProfileForm.setVisible(true);
        timingProfileForm.setSavedHandler(this::saveEntry);
        timingProfileForm.setResetHandler(this::resetEntry);
    }

    public void saveEntry(ProcessedTiming entry) {
        timingProfileRepository.save(entry);
        listEntities();
        closeWindow();
    }

    public void resetEntry(com.rnd.tms.data.entity.ProcessedTiming entry) {
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
