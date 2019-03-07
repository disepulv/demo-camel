package com.movix.router.dto;

import java.io.Serializable;

public class MvxBean implements Serializable {
	private static final long serialVersionUID = -1962241705949465182L;
	private Integer id;
	private String name;

	public MvxBean() {
	}

	public MvxBean(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
