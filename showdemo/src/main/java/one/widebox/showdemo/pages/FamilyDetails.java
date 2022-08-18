package one.widebox.showdemo.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.foggyland.tapestry5.RedirectException;
import one.widebox.foggyland.tapestry5.services.WebSupport;
import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.entities.Family;
import one.widebox.showdemo.entities.Student;
import one.widebox.showdemo.services.StudentService;

public class FamilyDetails extends AdminPage {

	@Component
	private Form detailsForm;
	@Inject
	private Messages messages;
	@Inject
	private StudentService studentService;
	@Inject
	private WebSupport webSupport;
	@Persist
	@Property
	private Long studentId;
	@Persist
	@Property
	private Long id;
	@Property
	private Student student;
	@Property
	private Family row;

	public void onPrepareForSubmit() {
		row = studentService.findFamily(studentId, id);
	}

	public void onValidateFromDetailsForm() {
	}

	@CommitAfter
	public Object onSuccess() {
		studentService.saveOrUpdate(row, studentId);
		return webSupport.createPageRenderLink(StudentDetails.class, studentId);
	}

	public String getDetailsLabel() {
		return messages.get(row.getId() == null ? "add" : "edit");
	}

	public String getStudentDetails() {
		return messages.get("student-details") + " " + student.getName();
	}

	@CommitAfter
	public Object onActionFromDelete(Long id) {
		row = studentService.findFamily(studentId, id);
		if (row.getId() != null) {
			studentService.deleteFamily(id);
		}
		return webSupport.createPageRenderLink(StudentDetails.class, studentId);
	}

	@Override
	public Object onActivate(EventContext eventContext) {
		super.onActivate(eventContext);
		studentId = null;
		id = null;
		if (eventContext.getCount() > 0) {
			studentId = eventContext.get(Long.class, 0);
		}
		if (eventContext.getCount() > 1) {
			id = eventContext.get(Long.class, 1);
		}
		return null;
	}

	public Long[] onPassivate() {
		return new Long[] { studentId, id };
	}

	@Override
	public void beginRender() {
		super.beginRender();
		student = studentService.findStudent(studentId);
		if (student.getId() == null) {
			throw new RedirectException(Home.class);
		}
		row = studentService.findFamily(studentId, id);
	}

}
