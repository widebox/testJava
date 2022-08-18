package one.widebox.showdemo.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.entities.User;
import one.widebox.showdemo.internal.StringHelper;
import one.widebox.showdemo.services.UserService;

public class UserDetails extends AdminPage {

	@Component
	private Form detailsForm;
	@Component(id = "username")
	private TextField username;
	@Inject
	private Messages messages;
	@Inject
	private ComponentResources resources;
	@Inject
	private UserService userService;
	@Persist
	@Property
	private Long id;
	@Property
	private User row;

	public void onPrepareForSubmit() {
		row = userService.findUser(id);
	}

	public void onValidateFromDetailsForm() {
		row.setUsername(StringHelper.trim(row.getUsername()));
		if (userService.isUsernameRepetead(row)) {
			detailsForm.recordError(username, messages.get("username-repeated"));
		}
	}

	@CommitAfter
	public Object onSuccess() {
		userService.saveOrUpdate(row);
		return UserListing.class;
	}

	public String getDetailsLabel() {
		return messages.get(row.getId() == null ? "add" : "edit");
	}

	@Override
	public Object onActivate(EventContext eventContext) {
		super.onActivate(eventContext);
		id = null;
		if (eventContext.getCount() > 0) {
			id = eventContext.get(Long.class, 0);
		}
		return null;
	}

	public Long onPassivate() {
		return id;
	}

	@CommitAfter
	public Object onActionFromDelete(Long id) {
		userService.deleteUser(id);
		return UserListing.class;
	}

	@Override
	public void beginRender() {
		super.beginRender();
		row = userService.findUser(id);
	}

	public boolean isShowPassword() {
		return row.getId() == null;
	}

}
