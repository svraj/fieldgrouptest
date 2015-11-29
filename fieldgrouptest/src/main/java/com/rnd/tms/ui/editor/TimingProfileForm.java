package com.rnd.tms.ui.editor;

import com.rnd.tms.data.converter.JodaDurationToStringConverter;
import com.rnd.tms.data.entity.TimingProfile;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

public class TimingProfileForm extends AbstractForm<TimingProfile> {

    TextField profileName = new MTextField("Profile Name");
    TextField companyName = new MTextField("Client name");
    TextField remarks = new MTextField("Remarks");
    TextField dailyWorkHours = new MTextField("Daily work hours");
    TextField minBreakDuration = new MTextField("Min Break Duration");
    TextField maxHoursWithoutBreak = new MTextField("Max hours without break");
    TextField minBreakAfterMaxHoursWithoutBreak = new MTextField("Min break after max hours without break");
    // Typically you'd use std CheckBox, using Swithch to demonstrate
    // the awesome extendions by the community: http://vaadin.com/directory
    Switch overtimeAllowed = new Switch("Overtime allowed");
    

    public TimingProfileForm(TimingProfile timingProfile) {
        setSizeUndefined();
        setEntity(timingProfile);
    }

    @Override
    protected Component createContent() {
    	dailyWorkHours.setConverter(new JodaDurationToStringConverter());
    	minBreakDuration.setConverter(new JodaDurationToStringConverter());
    	maxHoursWithoutBreak.setConverter(new JodaDurationToStringConverter());
    	minBreakAfterMaxHoursWithoutBreak.setConverter(new JodaDurationToStringConverter());
        return new MVerticalLayout(
                new MFormLayout(
                		profileName,
                		companyName,
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
