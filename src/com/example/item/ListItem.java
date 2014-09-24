package com.example.item;

public class ListItem {

	private int itemId;
	private String itemName;
	private HouseWorkItem houseWorkItem;

	public ListItem(HouseWorkItem houseWorkItem) {
		this.houseWorkItem = houseWorkItem;
		this.itemId = houseWorkItem.getSort();
		this.itemName = houseWorkItem.getName();
	}

	public ListItem(int itemId, String itemName) {
		this.itemId = itemId;
		this.itemName = itemName;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @return the houseWorkItem
	 */
	public HouseWorkItem getHouseWorkItem() {
		return houseWorkItem;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @param houseWorkItem the houseWorkItem to set
	 */
	public void setHouseWorkItem(HouseWorkItem houseWorkItem) {
		this.houseWorkItem = houseWorkItem;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ListItem [itemId=" + itemId + ", itemName=" + itemName + "]";
	}
	
	

	
}
