package one.widebox.showdemo.base;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.foggyland.tapestry5.services.WebSupport;
import one.widebox.showdemo.components.BaseComponent;

public abstract class PublicPage extends BaseComponent {

	@Inject
	private WebSupport webSupport;
	@Inject
	private Messages messages;
	@Persist
	private String activeCategory;

	public Object onActivate(EventContext eventContext) {
		logger.debug("onActivate(EventContext)");
		return null;
	}

	@BeginRender
	public void beginRender() {
		String url = webSupport.getRequestURL();
		logger.info("beginRender(), url=" + url);
		// appLogger.log("render", url);
	}

	@AfterRender
	public void afterRender() {
		logger.debug("afterRender()");
	}

	public String getHtmlTitle() {
		return messages.get("html-title");
	}

	public String getActiveCategory() {
		return activeCategory;
	}

	public void setActiveCategory(String activeCategory) {
		this.activeCategory = activeCategory;
	}

}
