package one.widebox.showdemo.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.google.common.base.CaseFormat;

import one.widebox.foggyland.tapestry5.Tab;
import one.widebox.showdemo.pages.CityDetails;
import one.widebox.showdemo.pages.FamilyDetails;
import one.widebox.showdemo.pages.Home;
import one.widebox.showdemo.pages.ProvinceDetails;
import one.widebox.showdemo.pages.Reports;
import one.widebox.showdemo.pages.ResetPassword;
import one.widebox.showdemo.pages.StudentDetails;
import one.widebox.showdemo.pages.StudentFileDetails;
import one.widebox.showdemo.pages.StudentListing;
import one.widebox.showdemo.pages.SysConfig;
import one.widebox.showdemo.pages.UserDetails;
import one.widebox.showdemo.pages.UserListing;

@Import(module = { "bootstrap/collapse", "bootstrap/modal", "bootstrap/dropdown", "bootstrap/popover",
		"bootstrap/tooltip" }, stylesheet = { "bootstrap-nonresponsive.css", "bootstrap-no-border-radius.css",
				"bootstrap-datetimepicker.min.css", "date-picker-no-footer.css",
				"font-awesome-4.6.3/css/font-awesome.min.css", "app.css" })
public class Border extends BaseComponent {

	private static final Tab TAB_HOME = new Tab(Home.class.getName());

	private static final Tab TAB_STUDENT = new Tab(StudentListing.class.getName(), StudentDetails.class.getName(),
			FamilyDetails.class.getName(), StudentFileDetails.class.getName());

	private static final Tab TAB_USER = new Tab(UserListing.class.getName(), UserDetails.class.getName(),
			ResetPassword.class.getName());

	private static final Tab TAB_REPORTS = new Tab(Reports.class.getName());

	private static final Tab TAB_CONFIG = new Tab(SysConfig.class.getName(), ProvinceDetails.class.getName(),
			CityDetails.class.getName());

	@InjectPage
	private Home home;
	@Inject
	private ComponentResources resources;
	@Inject
	private ComponentClassResolver componentClassResolver;
	@Inject
	private JavaScriptSupport javaScriptSupport;
	@Inject
	private Messages messages;

	@Property
	@Inject
	@Symbol(SymbolConstants.APPLICATION_VERSION)
	private String appVersion;

	@Property
	private Tab tab;

	public Tab[] getTabs() {
		return new Tab[] { TAB_HOME, TAB_STUDENT, TAB_USER, TAB_REPORTS, TAB_CONFIG };
	}

	public String getPageNameOfTab() {
		return componentClassResolver.resolvePageClassNameToPageName(tab.getName());
	}

	public String getTabLabel() {
		String labelKey = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tab.getSimpleName()) + "-tab";
		return messages.get(labelKey);
	}

	public String getTabCss() {
		String currentPageName = resources.getPage().getClass().getName();
		return tab.contains(currentPageName) ? "active" : null;
	}

	public String getUserDisplay() {
		return getCurrentUsername();
	}

	public Object onActionFromHome() {
		home.setActiveCategory(Home.USER_ACCOUNT_INFO);
		return Home.class;
	}

	public Object onActionFromChangePassword() {
		home.setActiveCategory(Home.CHANGE_PASSWORD);
		return Home.class;
	}

	@AfterRender
	public void afterRender() {
		javaScriptSupport.require("datepicker");
	}
}
