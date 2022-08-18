package one.widebox.showdemo.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.entities.User;
import one.widebox.showdemo.internal.ApplicationConstants;
import one.widebox.showdemo.services.UserService;

public class ResetPassword extends AdminPage {

	@Inject
	private UserService userService;
	@Persist
	@Property
	private Long id;
	@Property
	private User user;
	@Property
	private String newPassword;

	@CommitAfter
	public Object onSuccess() {
		userService.resetPassword(id, newPassword);
		return UserListing.class;
	}

	@Override
	public Object onActivate(EventContext eventContext) {
		super.onActivate(eventContext);
		if (eventContext.getCount() != 1) {
			return ApplicationConstants.HTTP_ERROR_404;
		}
		id = eventContext.get(Long.class, 0);
		return null;
	}

	public Long onPassivate() {
		return id;
	}

	public Object onActionFromCancel() {
		return UserListing.class;
	}

	@Override
	public void beginRender() {
		super.beginRender();
		user = userService.findUser(id);
	}
}
