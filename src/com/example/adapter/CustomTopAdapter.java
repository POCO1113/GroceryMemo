package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.fragment.TopFragmentLayout;
import com.example.item.ShoppingItem;
import com.example.shoppingitemlist.MainActivity;
import com.example.shoppingitemlist.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CustomTopAdapter extends SimpleAdapter {
	private LayoutInflater mLayoutInflater;
	private Context context;
	private int id;
	private ArrayList<ShoppingItem> shoppingItems;
	int count = 0;
	private static TopFragmentLayout topFragmentLayout;
	private static final String IS_CHECK = "check";
	private static final String IS_FAVORITE = "favorite";
	private MainActivity mainActivity;
	private ListView customListView;
	private TextView name;
	private BootstrapButton editButton, deleteButton;

	// コンストラクター
	public CustomTopAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to,
			ArrayList<ShoppingItem> shoppingItems,
			TopFragmentLayout topFragmentLayout, MainActivity mainActivity) {
		super(context, data, resource, from, to);
		this.context = context;
		this.shoppingItems = shoppingItems;
		this.topFragmentLayout = topFragmentLayout;
		this.mainActivity = mainActivity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mLayoutInflater = LayoutInflater.from(context);

		// レイアウトにrow.xmlを紐付ける
		convertView = mLayoutInflater.inflate(R.layout.fragment_top_row, parent, false);
		customListView = (ListView) parent;

		// 該当する位置のデータを取得する
		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) customListView.getItemAtPosition(position);

		//Viewの関連づけ
		findView(convertView);
		// コード各種をViewにセット
		name.setText((String) data.get("name_code"));
		name.setTag(position);
		editButton.setTag(position);
		deleteButton.setTag(position);
		setID(position);
		//買い物リストを保存
		mainActivity.saveShoppingList(shoppingItems);

		// ボタンクリックリスナーをセット
		editButton.setOnClickListener(new ButtonClickListener());
		deleteButton.setOnClickListener(new ButtonClickListener());

		return convertView;
	}

	class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = 0;
			try {
				id = (Integer) v.getTag();
			} catch (Exception e) {
				e.getStackTrace();
			}

			// 押された位置をセットする
			setID((Integer) v.getTag());

			if (v.getId() == R.id.button_edit) {
//				Log.d("TEST", "編集ボタンの" + id + "番目が押された！");
				mainActivity.printTopFragmentLayoutDialog(id, shoppingItems, topFragmentLayout);

			} else if (v.getId() == R.id.button_delete) {
//				Log.d("TEST", "削除ボタンの" + id + "番目が押された！");
				setCheck(shoppingItems, id + 1, IS_CHECK);
				setIsCheck(shoppingItems, IS_CHECK);
				mainActivity.saveShoppingList(shoppingItems);
				topFragmentLayout.setListAdapter();
			}
		}
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
	public void setIsCheck(ArrayList<ShoppingItem> item, String check) {
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
	public void setCheck(ArrayList<ShoppingItem> item, int id, String check) {
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

	/**
	 * 各View要素に関する関連付けメソッド
	 * 
	 * @param root
	 */
	public void findView(View view) {
		name = (TextView) view.findViewById(R.id.textView_name);
		editButton = (BootstrapButton) view.findViewById(R.id.button_edit);
		deleteButton = (BootstrapButton) view.findViewById(R.id.button_delete);
	}

}