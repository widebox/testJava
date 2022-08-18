package one.widebox.showdemo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity(name = "t_StudentFile")
public class StudentFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Student student;
	private String remark;
	private String filePath;
	private String fileName;

	public StudentFile() {
	}

	public StudentFile(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Transient
	public Long getStudentId() {
		return student == null ? null : student.getId();
	}

	public void setStudentId(Long id) {
		setStudent(id == null ? null : new Student(id));
	}

	@Column(columnDefinition = "text")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}