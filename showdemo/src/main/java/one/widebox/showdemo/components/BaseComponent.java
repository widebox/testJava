package one.widebox.showdemo.components;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.SessionAttribute;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import one.widebox.foggyland.utils.CalendarHelper;
import one.widebox.showdemo.internal.ApplicationConstants;
import one.widebox.showdemo.services.SelectModelService;

public abstract class BaseComponent {

	@SessionAttribute
	private Long currentUserId;
	@SessionAttribute
	private String currentUsername;
	@Inject
	private Messages messages;
	@Inject
	protected Logger logger;
	@Inject
	private SelectModelService selectModelService;

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

	public String getDateFormat() {
		return ApplicationConstants.DEFAULT_DATE_FORMAT;
	}

	public SelectModel getProvinceModel() {
		return selectModelService.getProvinceModel();
	}

	public SelectModel getCityModel() {
		return selectModelService.getCityModel(null);
	}

	public SelectModel getCityModel(Long provinceId) {
		return selectModelService.getCityModel(provinceId);
	}
}
