package one.widebox.showcase.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import one.widebox.showcase.base.PublicPage;

public class Logout extends PublicPage {

	@Inject
	private Request request;

	public Object onActivate(EventContext eventContext) {
		request.getSession(true).invalidate();
		return Login.class;
	}
}
