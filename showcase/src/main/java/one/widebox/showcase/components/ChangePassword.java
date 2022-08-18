package one.widebox.showcase.components;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showcase.entities.User;
import one.widebox.showcase.pages.Home;
import one.widebox.showcase.services.PasswordService;
import one.widebox.showcase.services.UserService;

public class ChangePassword extends BaseComponent {

	@InjectPage
	private Home myAccount;
	@Inject
	private UserService userService;
	@Inject
	private PasswordService passwordService;
	@Inject
	private Messages messages;
	@Inject
	private AlertManager alertManager;
	@Property
	private String currentPassword;
	@Property
	private String newPassword;
	@Property
	private String confirmNewPassword;
	@Component
	private Form form;

	public void onValidateFromForm() {
		User user = userService.findUser(getCurrentUserId());
		if (user.getId() == null) {
			form.recordError(messages.get("user-not-exist"));
			return;
		}
		if (StringUtils.isEmpty(currentPassword)) {
			form.recordError(messages.get("null-current-password"));
			return;
		}
		if (!passwordService.digest(currentPassword).equals(user.getDigest())) {
			form.recordError(messages.get("wrong-current-password"));
			return;
		}
		if (StringUtils.isEmpty(newPassword)) {
			form.recordError(messages.get("null-new-password"));
			return;
		}
		if (!newPassword.equals(confirmNewPassword)) {
			form.recordError(messages.get("new-confirm-not-equal"));
			return;
		}
	}

	@CommitAfter
	public void onSuccess() {
		userService.resetPassword(getCurrentUserId(), newPassword);
		myAccount.setActiveCategory(Home.USER_ACCOUNT_INFO);
		alertManager.info(messages.get("change-password-successful"));
	}

}
