package one.widebox.showcase.components;

import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import one.widebox.foggyland.utils.CalendarHelper;

public abstract class BaseComponent {

	@SessionAttribute
	private Long currentUserId;
	@SessionAttribute
	private String currentUsername;
	@Inject
	private Messages messages;
	@Inject
	protected Logger logger;

	public Long getCurrentUserId() {
		return currentUserId;
	}

	public String getCurrentUsername() {
		return currentUsername;
	}
	
	public String getCopyrightText() {
		Integer year = CalendarHelper.yearOfToday();
		return messages.format("copyright-text", year);
	}

}
