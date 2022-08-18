package one.widebox.showdemo.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.entities.Province;
import one.widebox.showdemo.internal.StringHelper;
import one.widebox.showdemo.services.AppService;

public class ProvinceDetails extends AdminPage {

	@Component
	private Form detailsForm;
	@Inject
	private Messages messages;
	@Inject
	private AppService appService;
	@Persist
	@Property
	private Long id;
	@Property
	private Province row;

	public void onPrepareForSubmit() {
		row = appService.findProvince(id);
	}

	public void onValidateFromDetailsForm() {
		row.setName(StringHelper.trim(row.getName()));
		if (appService.isProvinceSeqRepetead(row)) {
			detailsForm.recordError(messages.get("seq-repeated"));
		}
		if (appService.isProvinceNameRepetead(row)) {
			detailsForm.recordError(messages.get("name-repeated"));
		}
	}

	@CommitAfter
	public Object onSuccess() {
		appService.saveOrUpdate(row);
		return SysConfig.class;
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
		appService.deleteProvince(id);
		return SysConfig.class;
	}

	@Override
	public void beginRender() {
		super.beginRender();
		row = appService.findProvince(id);
	}

}
