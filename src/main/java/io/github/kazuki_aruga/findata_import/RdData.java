package io.github.kazuki_aruga.findata_import;

import java.io.Serializable;

public class RdData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String compCode;

	private int year;

	private int rd;

	private int ni;

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public int getNi() {
		return ni;
	}

	public void setNi(int ni) {
		this.ni = ni;
	}

}
