package one.widebox.showdemo.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.tapestry5.beaneditor.Validate;

import one.widebox.foggyland.tapestry5.AbstractOptionModel;

@Entity(name = "t_Province")
public class Province extends AbstractOptionModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer seq;
	private String name;

	public Province() {
	}

	public Province(Long id) {
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

	@Validate("required")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Validate("required")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getLabel() {
		return name;
	}

	@Transient
	public Object getValue() {
		return id;
	}

}