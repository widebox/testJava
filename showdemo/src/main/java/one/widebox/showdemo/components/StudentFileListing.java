package one.widebox.showdemo.components;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.foggyland.tapestry5.OctetStreamResponse;
import one.widebox.foggyland.tapestry5.services.oss.OssService;
import one.widebox.showdemo.entities.StudentFile;
import one.widebox.showdemo.services.StudentService;

public class StudentFileListing extends BaseComponent {

	@Parameter(name = "studentId", required = true, allowNull = false, defaultPrefix = BindingConstants.PROP)
	@Property
	private Long studentId;

	@Component
	private MyGrid grid;
	@Inject
	private StudentService studentService;
	@Inject
	private OssService ossService;
	@Property
	private List<StudentFile> rows;
	@Property
	private StudentFile row;

	@CommitAfter
	public void onActionFromDelete(Long id) {
		StudentFile studentFile = studentService.findStudentFile(studentId, id);
		if (studentFile.getId() != null) {
			studentService.deleteStudentFile(id);
		}
	}

	public StreamResponse onActionFromDownload(Long id) {
		StudentFile studentFile = studentService.findStudentFile(studentId, id);
		return new OctetStreamResponse(ossService.loadAsStream(studentFile.getFilePath()), studentFile.getFileName());
	}

	@BeginRender
	public void beginRender() {
		rows = studentService.listStudentFileByStudentId(studentId);
		if (rows.size() > 0 && grid.getSortModel().getSortConstraints().size() == 0) {
			grid.getSortModel().updateSort("fileName");
		}
	}

}
