package one.widebox.showdemo.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.base.AdminPage;

public class SysConfig extends AdminPage {

	public static final String PROVINCE_PAGE = "province";

	@Inject
	private ComponentResources resources;

	@Override
	public void beginRender() {
		super.beginRender();
		if (getActiveCategory() == null) {
			setActiveCategory(PROVINCE_PAGE);
		}
	}

	public Object getActiveBlock() {
		return resources.getBlock(getActiveCategory());
	}

}
