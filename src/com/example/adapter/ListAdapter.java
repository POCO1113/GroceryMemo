package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.fragment.SecondFragmentLayout;
import com.example.item.HouseWorkItem;
import com.example.item.ListItem;
import com.example.shoppingitemlist.MainActivity;
import com.example.shoppingitemlist.R;

public class ListAdapter extends ArrayAdapter<ListItem> {
	private LayoutInflater mInflater;
	private SecondFragmentLayout secondFragmentLayout;
	private ListItem item;
	private List<ListItem> list = new ArrayList<ListItem>();
	private ArrayList<HouseWorkItem> houseWorkItems = new ArrayList<HouseWorkItem>();
	int id;
	private static final String IS_CHECK = "check";
	private static final String IS_FAVORITE = "favorite";
	private MainActivity mainActivity;

	public ListAdapter(Context context, List<ListItem> list,
			SecondFragmentLayout secondFragmentLayout, MainActivity mainActivity) {
		super(context, 0, list);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.secondFragmentLayout = secondFragmentLayout;
		this.mainActivity = mainActivity;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (list != null) {
			list = secondFragmentLayout.getList();
		}
		if (houseWorkItems != null) {
			houseWorkItems = secondFragmentLayout.getHouseWorkItemList();
		}

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fragment_second_row,
					parent, false);
			holder = new ViewHolder();
			holder.ivDragHandler = (LinearLayout) convertView
					.findViewById(R.id.drag_handle);
			holder.houseWorkName = (TextView) convertView
					.findViewById(R.id.textView_house_name);
			holder.houseWorkSort = (TextView) convertView
					.findViewById(R.id.textView_house_sort);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		item = getItem(position);
		holder.houseWorkSort.setText("" + item.getItemId());
		holder.houseWorkName.setText(item.getItemName());

		return convertView;
	}

	

	private class ViewHolder {
		LinearLayout ivDragHandler;
		TextView houseWorkName, houseWorkSort;
	}

	/**
	 * 押された位置IDを保持
	 * 
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * 押された位置IDを取得
	 * 
	 * @return id
	 */
	public int getID() {
		return id;
	}

	/**
	 * ソートを入れ直し
	 * 
	 * @param item
	 */
	public void setIsCheck(ArrayList<HouseWorkItem> item, String check) {
		int count = 0;
		for (int i = 0; i < item.size(); i++) {
			if (check == IS_CHECK) {
				if (item.get(i).isCheck()) {
					item.get(i).setSort(count + 1);
					count++;
				}
			} else if (check == IS_FAVORITE) {
				if (item.get(i).isCheck()) {
					item.get(i).setSort(count + 1);
					count++;
				}
			}
		}
	}

	/**
	 * 削除フラグをセット
	 * 
	 * @param item
	 * @param id
	 */
	public void setCheck(ArrayList<HouseWorkItem> item, int id, String check) {
		for (int i = 0; i < item.size(); i++) {
			if (item.get(i).getSort() == id) {
				if (check == "check") {
					item.get(i).setCheck(false);
				} else if (check == "favorite") {
					item.get(i).setFavorite(true);
				}
			}
		}
	}
}
