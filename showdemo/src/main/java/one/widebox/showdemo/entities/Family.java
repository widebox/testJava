package one.widebox.showdemo.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.tapestry5.beaneditor.Validate;

import one.widebox.showdemo.entities.enums.Gender;

@Entity(name = "t_Family")
public class Family implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Student student;
	private String name;
	private Gender gender;
	private String tel;

	public Family() {
	}

	public Family(Long id) {
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

	@Validate("required")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Validate("required")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}