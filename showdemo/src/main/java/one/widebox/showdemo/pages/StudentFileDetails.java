package one.widebox.showdemo.pages;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import one.widebox.foggyland.tapestry5.RedirectException;
import one.widebox.foggyland.tapestry5.services.WebSupport;
import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.entities.Student;
import one.widebox.showdemo.entities.StudentFile;
import one.widebox.showdemo.services.StudentService;

public class StudentFileDetails extends AdminPage {

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
	@Property
	private Student student;
	@Property
	private StudentFile row;
	@Property
	private UploadedFile file;

	public void onPrepareForSubmit() {
		row = new StudentFile();
	}

	public void onValidateFromDetailsForm() {
		if (file == null) {
			detailsForm.recordError(messages.get("file-required"));
			// } else {
			// String uploadFileType = StringHelper.getUploadFileType(file.getFileName());
			// boolean isExist = ApplicationConstants.FILE_TYPES.contains(uploadFileType);
			// if (!isExist) {
			// detailsForm.recordError(messages.get("file-type-invalid"));
			// } else if (file.getSize() > ApplicationConstants.FILE_SIZE_LIMIT) {
			// detailsForm.recordError(messages.get("file-size-exceed-limit"));
			// }
		}
	}

	@CommitAfter
	public Object onSuccess() {
		studentService.saveOrUpdate(row, studentId, file);
		return webSupport.createPageRenderLink(StudentDetails.class, studentId);
	}

	public String getStudentDetails() {
		return messages.get("student-details") + " " + student.getName();
	}

	@Override
	public Object onActivate(EventContext eventContext) {
		super.onActivate(eventContext);
		studentId = null;
		if (eventContext.getCount() > 0) {
			studentId = eventContext.get(Long.class, 0);
		}
		return null;
	}

	public Long onPassivate() {
		return studentId;
	}

	@Override
	public void beginRender() {
		super.beginRender();
		student = studentService.findStudent(studentId);
		if (student.getId() == null) {
			throw new RedirectException(Home.class);
		}
		row = new StudentFile();
	}

}
