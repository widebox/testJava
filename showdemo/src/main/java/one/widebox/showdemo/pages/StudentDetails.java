package one.widebox.showdemo.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import one.widebox.foggyland.tapestry5.OctetStreamResponse;
import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.entities.Student;
import one.widebox.showdemo.internal.StringHelper;
import one.widebox.showdemo.services.StudentService;

public class StudentDetails extends AdminPage {

	@Component
	private Form detailsForm;
	@Inject
	private Messages messages;
	@Inject
	private StudentService studentService;
	@InjectComponent
	private Zone cityModelZone;
	@InjectComponent
	private Zone ageZone;
	@Inject
	private Request request;
	@Inject
	private ComponentResources resources;
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	@Persist
	@Property
	private Long id;
	@Persist
	@Property
	private Student row;

	public void onPrepareForSubmit() {
		row = studentService.findStudent(id);
	}

	public void onValidateFromDetailsForm() {
		row.setNo(StringHelper.trim(row.getNo()));
		if (studentService.isNoRepetead(row)) {
			detailsForm.recordError(messages.get("no-repeated"));
		}
	}

	@CommitAfter
	public Object onSuccess() {
		studentService.saveOrUpdate(row);
		return StudentListing.class;
	}

	public String getDetailsLabel() {
		return messages.get(row.getId() == null ? "add" : "edit");
	}

	public Link getPhoto() {
		Link returnLink = resources.createEventLink("photo", new Object[] { row.getId() });
		return returnLink;
	}

	public StreamResponse onPhoto(Long studentId) {
		byte[] binary = studentService.findStudentPhotoBinary(studentId);
		return new OctetStreamResponse(binary, "studnet_photo.jpg");
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
		studentService.deleteStudent(id);
		return StudentListing.class;
	}

	@Override
	public void beginRender() {
		super.beginRender();
		row = studentService.findStudent(id);
	}

	public SelectModel getCityModel() {
		return getCityModel(row.getProvinceId());
	}

	public void onValueChangedFromProvince(Long provinceId) {
		if (provinceId != null) {
			row.setProvinceId(provinceId);
		}
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(cityModelZone);
		}
	}

	public void onBirthdateChanged(@RequestParameter(value = "param", allowBlank = true) String birthdate) {
		// TODO
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(ageZone);
		}
	}
}
