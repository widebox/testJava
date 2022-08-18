package one.widebox.showdemo.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import one.widebox.foggyland.tapestry5.hibernate.services.Dao;
import one.widebox.foggyland.tapestry5.services.BinarySupport;
import one.widebox.foggyland.tapestry5.services.oss.OssService;
import one.widebox.showdemo.entities.Family;
import one.widebox.showdemo.entities.Student;
import one.widebox.showdemo.entities.StudentFile;
import one.widebox.showdemo.entities.examples.StudentExample;
import one.widebox.showdemo.internal.StringHelper;

public class StudentServiceImpl implements StudentService {

	@Inject
	private Dao dao;
	@Inject
	private OssService ossService;
	@Inject
	private BinarySupport binarySupport;

	@Override
	public void saveOrUpdate(Student studnet) {
		dao.saveOrUpdate(studnet);
	}

	@Override
	public void saveOrUpdate(Family family, Long studentId) {
		if (family.getId() == null) {
			family.setStudent(findStudent(studentId));
		}
		dao.saveOrUpdate(family);
	}

	@Override
	public void saveOrUpdate(StudentFile studentFile, Long studentId, UploadedFile file) {
		studentFile.setStudentId(studentId);
		studentFile.setFileName(file.getFileName());
		studentFile.setFilePath(ossService.write(file.getStream(), file.getFileName()));
		dao.saveOrUpdate(studentFile);
	}

	@Override
	public void savePhoto(Long studentId, byte[] data, String fileName) {
		Student student = findStudent(studentId);
		if (student.getId() != null) {
			student.setFileName(fileName);
			student.setFilePath(ossService.write(data, fileName));
			dao.saveOrUpdate(student);
		}
	}

	@Override
	public Student findStudent(Long id) {
		return dao.findById(Student.class, id);
	}

	@Override
	public Family findFamily(Long id) {
		return dao.findById(Family.class, id);
	}

	@Override
	public Family findFamily(Long studentId, Long id) {
		return dao.findOne(Family.class,
				Arrays.asList(Restrictions.eq("student.id", studentId), Restrictions.idEq(id)));
	}

	@Override
	public StudentFile findStudentFile(Long studentId, Long id) {
		return dao.findOne(StudentFile.class,
				Arrays.asList(Restrictions.eq("student.id", studentId), Restrictions.idEq(id)));
	}

	@Override
	public byte[] findStudentPhotoBinary(Long studentId) {
		Student student = findStudent(studentId);
		return StringHelper.isBlank(student.getFilePath()) ? loadDefaultPhoto()
				: ossService.loadAsByteArray(student.getFilePath());
	}

	private byte[] loadDefaultPhoto() {
		InputStream is = getClass().getClassLoader()
				.getResourceAsStream("one/widebox/showdemo/services/resources/default_photo.jpg");
		return binarySupport.readBinary(is);
	}

	@Override
	public List<Student> listStudent(StudentExample example, Long provinceId, Long cityId) {
		List<Criterion> crits = new ArrayList<Criterion>();
		if (provinceId != null) {
			crits.add(Restrictions.eq("province.id", provinceId));
		}
		if (cityId != null) {
			crits.add(Restrictions.eq("city.id", cityId));
		}
		return dao.list(Student.class, example, crits, Arrays.asList(Order.asc("id")));
	}

	@Override
	public List<Family> listFamilyByStudentId(Long studentId) {
		return dao.list(Family.class, Arrays.asList(Restrictions.eq("student.id", studentId)));
	}

	@Override
	public List<StudentFile> listStudentFileByStudentId(Long studentId) {
		return dao.list(StudentFile.class, Arrays.asList(Restrictions.eq("student.id", studentId)));
	}

	@Override
	public void deleteStudent(Long id) {
		deleteAllFamilyByStudentId(id);
		dao.delete(Student.class, id);
	}

	private void deleteAllFamilyByStudentId(Long studentId) {
		List<Family> list = listFamilyByStudentId(studentId);
		for (Family student : list) {
			dao.delete(student);
		}
	}

	@Override
	public void deleteFamily(Long id) {
		dao.delete(Family.class, id);
	}

	@Override
	public void deleteStudentFile(Long id) {
		dao.delete(StudentFile.class, id);
	}

	@Override
	public boolean isNoRepetead(Student studnet) {
		return dao.isPropertyValueDuplicated(Student.class, studnet.getId(), "no", studnet.getNo());
	}
}
