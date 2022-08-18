package one.widebox.showdemo.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.tapestry5.beaneditor.Validate;

import one.widebox.showdemo.entities.enums.Gender;
import one.widebox.showdemo.internal.StringHelper;

@Entity(name = "t_Student")
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String no;
	private String name;
	private Gender gender;
	private Integer age;
	private Date birthdate;
	private String birthPlace;
	private Province province;
	private City city;
	private String filePath;
	private String fileName;

	public Student() {
	}

	public Student(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
	@SequenceGenerator(name = "student_generator", sequenceName = "student_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Validate("required")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Validate("required")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Transient
	public String getBirthdateText() {
		return StringHelper.format(birthdate);
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	@Transient
	public Long getProvinceId() {
		return province == null ? null : province.getId();
	}

	public void setProvinceId(Long id) {
		setProvince(id == null ? null : new Province(id));
	}

	@Transient
	public String getProvinceName() {
		return province == null ? "" : province.getName();
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Transient
	public Long getCityId() {
		return city == null ? null : city.getId();
	}

	public void setCityId(Long id) {
		setCity(id == null ? null : new City(id));
	}

	@Transient
	public String getCityName() {
		return city == null ? "" : city.getName();
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