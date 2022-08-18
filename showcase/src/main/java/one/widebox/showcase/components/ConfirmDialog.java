package one.widebox.showcase.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class ConfirmDialog {

	@Parameter(name = "confirmTitle", value = BindingConstants.MESSAGE
			+ ":confirm-title", defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String confirmTitle;

	@Parameter(name = "confirmMessage", value = BindingConstants.MESSAGE
			+ ":confirm-message", defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String confirmMessage;

	@Parameter(name = "cancelBtnLabel", value = BindingConstants.MESSAGE
			+ ":cancel-btn-label", defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String cancelBtnLabel;

	@Parameter(name = "targetId", value = "confirm-model", defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String targetId;

}