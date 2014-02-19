package org.vaadin.mockapp.samples.crud;

import java.util.Collection;

import org.vaadin.mockapp.samples.AttributeExtension;
import org.vaadin.mockapp.samples.data.Category;
import org.vaadin.mockapp.samples.data.State;
import org.vaadin.teemu.clara.Clara;
import org.vaadin.teemu.clara.binder.annotation.UiDataSource;
import org.vaadin.teemu.clara.binder.annotation.UiField;
import org.vaadin.teemu.clara.binder.annotation.UiHandler;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ProductForm extends CustomComponent {

	@UiField
	NativeSelect state;

	@UiField
	TwinColSelect category;
	@UiField
	TextField price;
	@UiField("stock")
	TextField stockCount;

	// This is needed only for binding to be done properly
	@UiField("name")
	TextField productName;

	private SampleCrudLogic viewLogic;

	public ProductForm() {
		setCompositionRoot(Clara.create("ProductForm.xml", this));
		price.setConverter(new EuroConverter());

		AttributeExtension ae = new AttributeExtension();
		ae.extend(stockCount);
		ae.setAttribute("type", "number");

		for (State s : State.values()) {
			state.addItem(s);
		}

	}

	@UiHandler("save")
	public void saveProduct(ClickEvent event) {
		viewLogic.saveProduct();
	}

	@UiHandler("discard")
	public void discardProduct(ClickEvent event) {
		viewLogic.discardProduct();
	}

	@UiHandler("delete")
	public void deleteProduct(ClickEvent event) {
		viewLogic.deleteProduct();
	}

	public void setCategories(Collection<Category> categories) {
		category.removeAllItems();
		for (Category c : categories) {
			category.addItem(c);
		}
	}

	public void setViewLogic(SampleCrudLogic viewLogic) {
		this.viewLogic = viewLogic;
	}

}
