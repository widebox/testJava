package one.widebox.showdemo.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;

import one.widebox.foggyland.tapestry5.OctetStreamResponse;
import one.widebox.foggyland.tapestry5.services.WebSupport;
import one.widebox.foggyland.utils.PhotoResizeHelper;
import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.entities.Student;
import one.widebox.showdemo.services.StudentService;

public class UploadPhoto extends AdminPage {

	@Component
	private Form detailsForm;
	@Inject
	private Messages messages;
	@Inject
	private StudentService studentService;
	@Inject
	private Request request;
	@Inject
	private ComponentResources resources;
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	@Inject
	private WebSupport webSupport;
	@Inject
	private AlertManager alertManager;
	@Persist
	@Property
	private Long studentId;
	@Persist
	@Property
	private Student row;
	@Property
	private UploadedFile file;

	public Object onSuccess() {
		if (doUpload()) {
			return webSupport.createPageRenderLink(StudentDetails.class, studentId);
		}
		return this;
	}

	@CommitAfter
	private Boolean doUpload() {
		row = studentService.findStudent(studentId);
		if (row.getId() == null) {
			return false;
		}
		if (file == null || file.getSize() <= 0) {
			alertManager.error(messages.get("file-required"));
			return false;
		} else {
			byte[] resized = null;
			try {
				// 限制長寬不超過600px已經足夠在電腦顯示器和考勤機上清晰顯示。
				resized = PhotoResizeHelper.resize(file.getStream(), 600, 600);
			} catch (Exception e) {
				alertManager.error(messages.get("not-image-file"));
				return false;
			}
			studentService.savePhoto(studentId, resized, file.getFileName());
			return true;
		}
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
		row = studentService.findStudent(studentId);
	}

}
