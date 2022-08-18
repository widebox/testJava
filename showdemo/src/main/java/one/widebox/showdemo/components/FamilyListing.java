package one.widebox.showdemo.components;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import one.widebox.showdemo.entities.Family;
import one.widebox.showdemo.services.StudentService;

public class FamilyListing extends BaseComponent {

	@Parameter(name = "studentId", required = true, allowNull = false, defaultPrefix = BindingConstants.PROP)
	@Property
	private Long studentId;

	@Component
	private MyGrid grid;
	@Inject
	private StudentService studentService;
	@Property
	private List<Family> rows;
	@Property
	private Family row;

	@BeginRender
	public void beginRender() {
		rows = studentService.listFamilyByStudentId(studentId);
		if (rows.size() > 0 && grid.getSortModel().getSortConstraints().size() == 0) {
			grid.getSortModel().updateSort("name");
		}
	}

}
