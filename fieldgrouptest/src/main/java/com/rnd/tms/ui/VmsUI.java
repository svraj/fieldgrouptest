package com.rnd.tms.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.rnd.tms.ui.views.ClientView;
import com.rnd.tms.ui.views.EmployeeView;
import com.rnd.tms.ui.views.ProcessedTimingView;
import com.rnd.tms.ui.views.TimingProfileView;
import com.rnd.tms.ui.views.ViewScopedView;
import com.rnd.tms.ui.viritin.views.ClientViritinView;
import com.rnd.tms.ui.viritin.views.EmployeeViritinView;
import com.rnd.tms.ui.viritin.views.ProcessedTimingViritinView;
import com.rnd.tms.ui.viritin.views.TimingProfileViritinView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme("valo")
@SpringUI
@Viewport("user-scalable=no,initial-scale=1.0")
public class VmsUI extends UI {

    // we can use either constructor autowiring or field autowiring
    @Autowired
    private SpringViewProvider viewProvider;
    
    
   private final VerticalLayout root = new VerticalLayout(); 
   private final Panel viewContainer = new Panel();

    @Override
    protected void init(VaadinRequest request) {
    	//Responsive.makeResponsive(this);
    	 //setContent(new Label("Hello! I'm the root UI!"));
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        createMenu();
       createContentBody();
        setNavigator();
    }
    
    private void setNavigator() {
    	  Navigator navigator = new Navigator(this, viewContainer);
          navigator.addProvider(viewProvider);
	}

	private void createContentBody() {
         viewContainer.setSizeFull();
         root.addComponent(viewContainer);
         root.setExpandRatio(viewContainer, 1.0f);
       
	}

	private void createMenu(){
    	final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("Zones", ViewScopedView.VIEW_NAME));
        //navigationBar.addComponent(createNavigationButton("Employee ",EmployeeView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Employees Viritin",EmployeeViritinView.VIEW_NAME));
        //navigationBar.addComponent(createNavigationButton("Client ",ClientView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Clients ",ClientViritinView.VIEW_NAME));
        //navigationBar.addComponent(createNavigationButton("Timing Profile",TimingProfileView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Timing Profiles",TimingProfileViritinView.VIEW_NAME));
        //navigationBar.addComponent(createNavigationButton("Processed Timing",ProcessedTimingView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Processed Timings",ProcessedTimingViritinView.VIEW_NAME));
        root.addComponent(navigationBar);
    }
    
    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        // If you didn't choose Java 8 when creating the project, convert this to an anonymous listener class
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }
}