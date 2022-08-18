package one.widebox.showcase.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showcase.base.PublicPage;

public class NavLink {

	@Parameter(name = "label", required = true, allowNull = false, defaultPrefix = BindingConstants.MESSAGE)
	private String label;

	@Parameter(name = "category", required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String category;

	@Inject
	private ComponentResources resources;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ComponentResources getResources() {
		return resources;
	}

	public void setResources(ComponentResources resources) {
		this.resources = resources;
	}

	public void onActionFromTabLink() {
		PublicPage page = (PublicPage) resources.getPage();
		page.setActiveCategory(category);
	}

	public String getTabStyle() {
		PublicPage page = (PublicPage) resources.getPage();
		return category.equals(page.getActiveCategory()) ? "active" : null;
	}
}