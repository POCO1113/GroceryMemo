package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.adapter.CustomTopAdapter;
import com.example.item.ShoppingItem;
import com.example.shoppingitemlist.MainActivity;
import com.example.shoppingitemlist.R;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class TopFragmentLayout extends Fragment {

	private ListView shoppingListView;
	private BootstrapButton addButton;
	private BootstrapEditText itemEditText;
	private View root;
	private ArrayList<ShoppingItem> shoppingItem = new ArrayList<ShoppingItem>();
	int count = 0;
	int num = 0;
	private static TopFragmentLayout topFragmentLayout;
	private CustomTopAdapter cta;

	public static TopFragmentLayout newInstance() {
		topFragmentLayout = new TopFragmentLayout();
		return topFragmentLayout;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_top, container, false);
		findView(root);
		addButton.setOnClickListener(new ButtonClickListener());

		setIsCheck(shoppingItem);
		setListAdapter();

		return root;
	}

	class ButtonClickListener implements OnClickListener,
			android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {

			// 追加ボタンを押したとき
			if (v.getId() == R.id.button_add) {
				if (!itemEditText.getText().toString().equals("")) {
					String name = itemEditText.getText().toString();
					if (shoppingItem != null)
						num = shoppingItem.size() + 1;
					shoppingItem.add(new ShoppingItem(num, name, true, false,
							count));
					setIsCheck(shoppingItem);
					num = shoppingItem.size();
					itemEditText.setText("");
					Toast.makeText(root.getContext(), name + "を追加しました",
							Toast.LENGTH_SHORT).show();
					setListAdapter();
				} else {
					Toast.makeText(root.getContext(), "文字を入力してください。",
							Toast.LENGTH_SHORT).show();
				}
			}
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// Log.d("TEST", "DialogInterface dialogに来てるよ");
			// //今特に使用してない。
		}
	}

	/**
	 * 各View要素に関する関連付けメソッド
	 * 
	 * @param root
	 */
	public void findView(View root) {
		topFragmentLayout = (TopFragmentLayout) getFragmentManager()
				.findFragmentByTag("top");
		addButton = (BootstrapButton) root.findViewById(R.id.button_add);
		shoppingListView = (ListView) root
				.findViewById(R.id.listView_shoopingList);
		itemEditText = (BootstrapEditText) root
				.findViewById(R.id.editText_item);
	}

	/**
	 * ListViewに表示するためにアダプターにセット
	 */
	public void setListAdapter() {
		// データを格納するためのArrayListを宣言
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < shoppingItem.size(); i++) {
			if (shoppingItem.get(i).isCheck()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				if (shoppingItem.get(i).isCheck()) {
					map.put("name_code", shoppingItem.get(i).getName());
					data.add(map);
				}

			}
		}

		// 作成したdataとカスタマイズしたレイアウトrow.xmlを 紐付けたCustomAdapterを作成する
		cta = new CustomTopAdapter(root.getContext(), data,
				R.layout.fragment_top_row, new String[] { "name_code" },
				new int[] { R.id.textView_name }, shoppingItem,
				topFragmentLayout, (MainActivity) getActivity());
		shoppingListView.setAdapter(cta);
		// Log.d("TEST", shoppingItem.toString());

	}

	/**
	 * 削除フラグをセット
	 * 
	 * @param item
	 */
	public void setIsCheck(ArrayList<ShoppingItem> item) {
		int count = 0;
		for (int i = 0; i < item.size(); i++) {
			if (item.get(i).isCheck()) {
				item.get(i).setSort(count + 1);
				count++;
			}
		}
	}

	/**
	 * ArrayList<ShoppingItem>インスタンスを取得
	 * 
	 * @return shoppingItem
	 */
	public ArrayList<ShoppingItem> getShoppingItemList() {
		return shoppingItem;
	}

}
