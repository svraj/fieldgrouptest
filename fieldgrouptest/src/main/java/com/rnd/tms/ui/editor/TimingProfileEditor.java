package com.rnd.tms.ui.editor;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form in
 * multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class TimingProfileEditor extends GridLayout {

	private final TimingProfileRepository repository;
	private TimingProfile timingProfile;

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);
	
	@Autowired
	public TimingProfileEditor(TimingProfileRepository repository) {
		super(2,4);
		this.repository = repository;
		setSpacing(true);
		setSizeFull();
		constructMainEditLayout();
		addComponent(actions);
	}
	
	private void registerComponentListeners() {
		save.addClickListener(e -> saveTimingProfile(timingProfile));
		delete.addClickListener(e -> repository.delete(timingProfile));
		//cancel.addClickListener(e -> editTimingProfile(timingProfile));
		cancel.addClickListener(e -> cancelSaveEdit());
	}

	private void saveTimingProfile(TimingProfile timingProfile) {
		System.out.println("Going to save Profile-"+timingProfile);
		System.out.println(timingProfile.getProfileName());
		repository.save(timingProfile);
	}

	private void styleComponents() {
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		delete.setStyleName(ValoTheme.BUTTON_DANGER);
	}

	private void constructMainEditLayout() {
		System.out.println("***constructMainEditLayout***");
			bind(false);
			styleComponents();
			
			registerComponentListeners();
			handleTextFieldNulls();
			save.focus();
	}

	private void bind(boolean showBaseEntityFields){
		System.out.println("@@@Going to Bind");
		if(timingProfile!=null){
			System.out.println("Profile Name is - "+timingProfile.getProfileName());
			BeanItem<TimingProfile> item =  new BeanItem<TimingProfile>(timingProfile);
			item.addNestedProperty("client.companyName");
			BeanFieldGroup beanFieldGroup = new BeanFieldGroup<TimingProfile>(TimingProfile.class);
			for(Object propertyId:item.getItemPropertyIds() ){
					//System.out.println(propertyId);
					if(showBaseEntityFields){
						addComponent(beanFieldGroup.buildAndBind(propertyId));
					}else{
						if(!(propertyId.equals("recordCreatedBy") || propertyId.equals("recordCreatedDate") ||
								propertyId.equals("recordModifiedBy") || propertyId.equals("recordModifiedDate") ||
								propertyId.equals("recordCurrentStatus"))){
							addComponent(beanFieldGroup.buildAndBind(propertyId));
						}
					}
			}
		}
	}

	public final void editTimingProfile(TimingProfile c) {
		
		final boolean persisted = c.getId() != null;
		System.out.println("@#@editTimingProfile"+c.getId()+persisted);
		if (persisted) {
			// Find fresh entity for editing
			timingProfile = repository.findOne(c.getId());
		}
		else {
			timingProfile = c;
		}
		cancel.setVisible(persisted);
		constructMainEditLayout();
	}

	private void handleTextFieldNulls() {
		Iterator<Component> componentIterator = iterator();
		//Component component = (Component)componentIterator.next();
		while(componentIterator.hasNext()){
			Component component = (Component)componentIterator.next();
			if(component instanceof TextField){
				TextField field = (TextField)component;
				field.setNullRepresentation("");
			}
		}
	}
	
	public void customRemoveComponent(Component componentToBeRemoved){
		Iterator<Component> componentIterator = iterator();
		Component component = (Component)componentIterator.next();
		while(componentIterator.hasNext()){
			component = (Component)componentIterator.next();
			if(component.equals(componentToBeRemoved)){
				removeComponent(component);
			}
		}
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
	
	public final void cancelSaveEdit() {
		setVisible(false);
	}

	public interface ChangeHandler {
		void onChange();
	}

}
