package one.widebox.showdemo.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.base.AdminPage;

public class Home extends AdminPage {

	public static final String USER_ACCOUNT_INFO = "userAccountInfo";
	public static final String CHANGE_PASSWORD = "changePassword";

	@Inject
	private ComponentResources resources;

	@Override
	public void beginRender() {
		super.beginRender();
		if (getActiveCategory() == null) {
			setActiveCategory(USER_ACCOUNT_INFO);
		}
	}

	public Object getActiveBlock() {
		return resources.getBlock(getActiveCategory());
	}
}
