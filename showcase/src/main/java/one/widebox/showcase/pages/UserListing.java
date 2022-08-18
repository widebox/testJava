package one.widebox.showcase.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showcase.base.AdminPage;
import one.widebox.showcase.entities.User;
import one.widebox.showcase.services.UserService;

public class UserListing extends AdminPage {

	@Inject
	private UserService userService;
	@Property
	private List<User> rows;
	@Property
	private User row;

	@Override
	public void beginRender() {
		rows = userService.listUser();
	}

	public int getRowCount() {
		return rows.size();
	}

}
