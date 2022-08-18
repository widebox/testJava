package one.widebox.showcase.base;

import org.apache.tapestry5.EventContext;

import one.widebox.foggyland.tapestry5.RedirectException;
import one.widebox.showcase.pages.Login;

public abstract class AdminPage extends PublicPage {

	@Override
	public Object onActivate(EventContext eventContext) {
		super.onActivate(eventContext);
		if (getCurrentUserId() == null) {
			throw new RedirectException(Login.class);
		}
		return null;
	}

}
