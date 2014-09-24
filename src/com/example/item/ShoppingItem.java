package com.example.item;

public class ShoppingItem {
	private int id;
	private String name;
	private boolean check;
	private boolean isFavorite;
	private int sort;
	private int lastID;

	public ShoppingItem() {
		this.id = 0;
		this.name = "";
		this.check = true;
		this.isFavorite = false;
		this.sort = 0;

	}

	public ShoppingItem(int id, String name, boolean check, boolean isFavorite,int sort) {
		this.id = id;
		this.name = name;
		this.check = check;
		this.isFavorite = isFavorite;
		this.sort = lastID + 1;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the check
	 */
	public boolean isCheck() {
		return check;
	}

	/**
	 * @return the isFavorite
	 */
	public boolean isFavorite() {
		return isFavorite;
	}

	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param check
	 *            the check to set
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * @param check
	 *            the check to set
	 */
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * @return the id
	 */
	public int getNewSort() {
		return id;

	}

	public String printToString() {
		return id + "," + name + "," + check + "," + isFavorite + "," + sort
				+ "/";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShoppingItem [id=" + id + ", name=" + name + ", check=" + check
				+ ", isFavorite=" + isFavorite + ", sort=" + sort + "]";
	}

}
