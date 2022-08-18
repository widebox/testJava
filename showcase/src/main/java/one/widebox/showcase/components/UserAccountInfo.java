package one.widebox.showcase.components;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showcase.entities.User;
import one.widebox.showcase.services.UserService;

public class UserAccountInfo extends BaseComponent {

	@Inject
	private UserService userService;
	@Inject
	private Messages messages;
	@Property
	private User user;

	@BeginRender
	public void beginRender() {
		user = userService.findUser(getCurrentUserId());
	}

	public String getStatusText() {
		return user.getStatus() == null ? "" : messages.get("UserStatus." + user.getStatus());
	}

}
