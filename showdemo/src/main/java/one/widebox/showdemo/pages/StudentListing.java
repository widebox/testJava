package one.widebox.showdemo.pages;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import one.widebox.foggyland.utils.CalendarHelper;
import one.widebox.showdemo.base.AdminPage;
import one.widebox.showdemo.components.MyGrid;
import one.widebox.showdemo.entities.Student;
import one.widebox.showdemo.entities.examples.StudentExample;
import one.widebox.showdemo.internal.StringHelper;
import one.widebox.showdemo.services.StudentService;

public class StudentListing extends AdminPage {

	@Component
	private MyGrid grid;
	@Inject
	private StudentService studentService;
	@Inject
	private Session session;
	@Property
	private List<Student> rows;
	@Property
	private Student row;
	@Persist
	@Property
	private StudentExample example;
	@Persist
	@Property
	private Date beginBirthdate;
	@Persist
	@Property
	private Date endBirthdate;
	@Persist
	@Property
	private Long provinceId;
	@Persist
	@Property
	private Long cityId;
	@Persist
	@Property
	private String cityName;

	public void onSelectedFromSearch() {
		grid.setCurrentPage(1);
	}

	public void onSelectedFromClear() {
		grid.setCurrentPage(1);
		example = new StudentExample();
		beginBirthdate = null;
		endBirthdate = null;
		provinceId = null;
		cityId = null;
		cityName = null;
	}

	@Override
	public void beginRender() {
		super.beginRender();
		if (example == null) {
			example = new StudentExample();
		}
		rows = listRow();
		// rows = studentService.listStudent(example, provinceId, cityId);
		if (rows.size() > 0 && grid.getSortModel().getSortConstraints().size() == 0) {
			grid.getSortModel().updateSort("no");
		}
	}

	@SuppressWarnings("unchecked")
	private List<Student> listRow() {
		Criteria crit = session.createCriteria(Student.class).createAlias("city", "city", JoinType.LEFT_OUTER_JOIN)
				.add(Example.create(example).enableLike(MatchMode.ANYWHERE).ignoreCase());
		crit.addOrder(Order.asc("no"));
		if (provinceId != null) {
			crit.add(Restrictions.eq("province.id", provinceId));
		}
		if (StringHelper.isNotBlank(cityName)) {
			crit.add(Restrictions.ilike("city.name", cityName, MatchMode.ANYWHERE));
		}
		if (beginBirthdate != null) {
			crit.add(Restrictions.ge("birthdate", beginBirthdate));
		}
		if (endBirthdate != null) {
			crit.add(Restrictions.lt("birthdate", CalendarHelper.increaseDays(endBirthdate, 1)));
		}
		return crit.list();
	}
}
