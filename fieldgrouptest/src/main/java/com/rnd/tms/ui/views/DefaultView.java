package com.rnd.tms.ui.views;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";
    
    ComboBox profileName = new ComboBox("Profile Name");
    
    @Autowired
	private TimingProfileRepository timingProfileRepository;

    @PostConstruct
    void init() {
        addComponent(new Label("This is the default view"));
        List<TimingProfile> timingProfiles = timingProfileRepository.findAll();
		BeanItemContainer<TimingProfile> timingProfileContainer = 
				   new BeanItemContainer<>(TimingProfile.class, timingProfiles);
		
        profileName.setImmediate(true);
		profileName.setContainerDataSource(timingProfileContainer);
		
		profileName.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		profileName.setItemCaptionPropertyId("profileName");
		
		profileName.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				TimingProfile timingProfile = (TimingProfile)event.getProperty().getValue();
				System.out.println("@@@"+timingProfile.getProfileName());
				
			}
		});
		
		addComponent(profileName);
		//profileName.set
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }
}