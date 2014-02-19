package org.vaadin.mockapp.samples.crud;

import org.vaadin.mockapp.samples.ResetButtonForTextField;
import org.vaadin.mockapp.samples.backend.DataService;
import org.vaadin.teemu.clara.Clara;
import org.vaadin.teemu.clara.binder.annotation.UiField;
import org.vaadin.teemu.clara.binder.annotation.UiHandler;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class SampleCrudView extends CustomComponent implements View {

	public static final String VIEW_NAME = "Editor";
	@UiField
	ProductTable table;
	@UiField
	ProductForm form;

	@UiField("root") VerticalLayout layout;

	@UiField
	private TextField filter;
	private SampleCrudLogic viewLogic = new SampleCrudLogic(this);
	Button newProduct = new Button("New product");

	CheckBox formAsPopup = new CheckBox("Form as popup");
	Window formWindow = new Window("Edit form");

	public SampleCrudView() {
		setCompositionRoot(Clara.create("SampleCrudView.xml", this));
		form.setViewLogic(viewLogic);
		form.setCategories(DataService.get().getAllCategories());

		ResetButtonForTextField.extend(filter);

		formWindow.setWidth("400px");
		formWindow.setModal(true);
		formWindow.addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				viewLogic.discardProduct();
			}
		});
		viewLogic.init();
	}

	@UiHandler("new")
	public void newProduct(ClickEvent e) {
		viewLogic.newProduct();
	}

	@UiHandler("formAsPopup")
	public void formAsPopupValueChange(ValueChangeEvent event) {
		if (formAsPopup.getValue()) {
			layout.removeComponent(form);
			formWindow.setContent(form);
		} else {
			formWindow.setContent(null);
			layout.addComponent(form);
		}
	}

	@UiHandler("filter")
	public void filterChange(TextChangeEvent event) {
		table.setFilter(event.getText());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		viewLogic.enter(event.getParameters());
	}

	public void showError(String msg) {
		Notification.show(msg, Type.ERROR_MESSAGE);
	}

	public void showSaveNotification(String msg) {
		Notification.show(msg, Type.TRAY_NOTIFICATION);
	}

}
