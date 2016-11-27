/**
 * 
 */
package io.github.kazuki_aruga.findata_import;

import java.io.Serializable;

/**
 * @author kazuki
 *
 */
public class FinData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String compCode;

	private int year;

	private int sales;

	private int assets;

	private int debt;

	private int ebitda;

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

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public int getAssets() {
		return assets;
	}

	public void setAssets(int assets) {
		this.assets = assets;
	}

	public int getDebt() {
		return debt;
	}

	public void setDebt(int debt) {
		this.debt = debt;
	}

	public int getEbitda() {
		return ebitda;
	}

	public void setEbitda(int ebitda) {
		this.ebitda = ebitda;
	}

}
