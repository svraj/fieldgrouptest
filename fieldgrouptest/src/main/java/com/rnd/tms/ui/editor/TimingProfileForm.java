package com.rnd.tms.ui.editor;

import javax.annotation.PostConstruct;

import com.rnd.tms.data.converter.JodaDurationToStringConverter;
import com.rnd.tms.data.entity.Client;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.ClientRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.fields.TypedSelect;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@SpringComponent
@ViewScope
public class TimingProfileForm extends AbstractForm<TimingProfile> {

    TextField profileName = new MTextField("Profile Name");
    TypedSelect<Client> client = new TypedSelect().withCaption("company name");
    //TextField companyName = new MTextField("Client name");
    TextField remarks = new MTextField("Remarks");
    TextField dailyWorkHours = new MTextField("Daily work hours");
    TextField minBreakDuration = new MTextField("Min Break Duration");
    TextField maxHoursWithoutBreak = new MTextField("Max hours without break");
    TextField minBreakAfterMaxHoursWithoutBreak = new MTextField("Min break after max hours without break");
    // Typically you'd use std CheckBox, using Swithch to demonstrate
    // the awesome extendions by the community: http://vaadin.com/directory
    Switch overtimeAllowed = new Switch("Overtime allowed");
    
    @Autowired
    private ClientRepository clientRepository;
    
    public void setTimingProfile(TimingProfile timingProfile){
    	setEntity(timingProfile);
    };

    public TimingProfileForm(TimingProfile timingProfile) {
        setSizeUndefined();
        setEntity(timingProfile);
        //this.clientRepository=clientRepository;
    }
    
    public TimingProfileForm(){
    	
    }
    public void init(){
    	System.out.println("init");
    }

    @Override
   // @PostConstruct
    protected Component createContent() {
    	if(clientRepository==null){
    		System.out.println("repo null");
    	}
    	client.setOptions(clientRepository.findAll());
    	
    	client.setCaptionGenerator(c->c.getCompanyName());
    	//employee.set("companyName");
    	//employee.setContainerDataSource(clientContainer);
    	//employee.setBuffered(false);
    	//employee.setImmediate(true);
		
    	dailyWorkHours.setConverter(new JodaDurationToStringConverter());
    	minBreakDuration.setConverter(new JodaDurationToStringConverter());
    	maxHoursWithoutBreak.setConverter(new JodaDurationToStringConverter());
    	minBreakAfterMaxHoursWithoutBreak.setConverter(new JodaDurationToStringConverter());
        return new MVerticalLayout(
                new MFormLayout(
                		profileName,
                		client,
                		dailyWorkHours,
                		minBreakDuration,
                		maxHoursWithoutBreak,
                		minBreakAfterMaxHoursWithoutBreak,
                		overtimeAllowed,
                		remarks
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }

}
