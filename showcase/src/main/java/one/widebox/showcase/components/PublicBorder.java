package one.widebox.showcase.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

@Import(stylesheet = { "bootstrap-no-border-radius.css", "font-awesome-4.6.3/css/font-awesome.min.css", "app.css" })
public class PublicBorder extends BaseComponent {

	@Inject
	private ComponentResources resources;
	@Inject
	private Messages messages;

	public String getHtmlTitle() {
		String key = "html." + resources.getPage().getClass().getSimpleName();
		return messages.contains(key) ? messages.get(key) : messages.get("html-title");
	}

}
