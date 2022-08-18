package one.widebox.showdemo.services;

import java.util.List;

import org.apache.tapestry5.upload.services.UploadedFile;

import one.widebox.showdemo.entities.Family;
import one.widebox.showdemo.entities.Student;
import one.widebox.showdemo.entities.StudentFile;
import one.widebox.showdemo.entities.examples.StudentExample;

public interface StudentService {

	void saveOrUpdate(Student studnet);

	void saveOrUpdate(Family family, Long studentId);

	void saveOrUpdate(StudentFile studentFile, Long studentId, UploadedFile file);

	void savePhoto(Long studentId, byte[] data, String fileName);

	Student findStudent(Long id);

	Family findFamily(Long id);

	Family findFamily(Long studentId, Long id);

	StudentFile findStudentFile(Long studentId, Long id);

	byte[] findStudentPhotoBinary(Long studentId);

	List<Student> listStudent(StudentExample example, Long provinceId, Long cityId);

	List<Family> listFamilyByStudentId(Long studentId);

	List<StudentFile> listStudentFileByStudentId(Long studentId);

	void deleteStudent(Long id);

	void deleteFamily(Long id);

	void deleteStudentFile(Long id);

	boolean isNoRepetead(Student studnet);

}
