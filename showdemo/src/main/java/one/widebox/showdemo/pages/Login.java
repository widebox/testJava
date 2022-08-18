package one.widebox.showdemo.pages;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.foggyland.tapestry5.RedirectException;
import one.widebox.foggyland.tapestry5.services.WebSupport;
import one.widebox.showdemo.base.PublicPage;
import one.widebox.showdemo.entities.User;
import one.widebox.showdemo.entities.enums.UserStatus;
import one.widebox.showdemo.services.PasswordService;
import one.widebox.showdemo.services.UserService;

@Import(stylesheet = { "bootstrap-no-border-radius.css", "date-picker-no-footer.css", "app.css" })
public class Login extends PublicPage {

	@SessionAttribute
	private Long currentUserId;
	@SessionAttribute
	private String currentUsername;
	@InjectComponent
	private Form loginForm;
	@Inject
	private UserService userService;
	@Inject
	private PasswordService passwordService;
	@Inject
	private Messages messages;
	@Inject
	private WebSupport webSupport;

	@Property
	private String username;
	@Property
	private String password;

	public void onValidateFromLoginForm() {
		if (loginForm.getHasErrors()) {
			return;
		}
		User user = userService.findUserByUsername(username);
		if (user == null) {
			loginForm.recordError(messages.get("not-match"));
			return;
		}
		String digest = passwordService.digest(password);
		if (!digest.equals(user.getDigest())) {
			loginForm.recordError(messages.get("not-match"));
			return;
		}
		if (UserStatus.DISABLED.equals(user.getStatus())) {
			loginForm.recordError(messages.get("account-disabled"));
			return;
		}

		// change sessionId after successfully signed in.
		webSupport.invalidateSession();
		currentUserId = user.getId();
		currentUsername = user.getUsername();
	}

	public Object onSuccess() {
		logger.info("onSuccess(), Login OK!");
		return Home.class;
	}

	@Override
	public void beginRender() {
		super.beginRender();
		logger.info("beginRender(), currentUserId=" + currentUserId);
		if (currentUserId != null) {
			logger.info("beginRender(), already signed in.");
			throw new RedirectException(Home.class);
		}
	}

}
