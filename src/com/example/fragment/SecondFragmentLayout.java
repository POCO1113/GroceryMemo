package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.ListAdapter;
import com.example.item.HouseWorkItem;
import com.example.item.ListItem;
import com.example.shoppingitemlist.MainActivity;
import com.example.shoppingitemlist.R;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "ValidFragment" })
public class SecondFragmentLayout extends ListFragment {

	private ArrayAdapter<ListItem> adapter;
	private ArrayList<ListItem> list = new ArrayList<ListItem>();;
	private DragSortListView mDslv;
	private DragSortController mController;
	private ArrayList<HouseWorkItem> houseWorkItem = new ArrayList<HouseWorkItem>();
	private MainActivity mainActivity;

	@SuppressLint("ValidFragment")
	public SecondFragmentLayout(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				ListItem item = adapter.getItem(from);
				adapter.remove(item);
				adapter.insert(item, to);
			}
		}
	};

	private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			adapter.remove(adapter.getItem(which));
		}
	};

	public DragSortController getController() {
		return mController;
	}

	/**
	 * Called in onCreateView. Override this to provide a custom
	 * DragSortController.
	 */
	public DragSortController buildController(DragSortListView dslv) {
		// defaults are
		// dragStartMode = onDown
		// removeMode = flingRight
		DragSortController controller = new DragSortController(dslv);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setClickRemoveId(R.id.click_remove);
		controller.setRemoveEnabled(true);
		controller.setSortEnabled(true);
		controller.setDragInitMode(DragSortController.ON_DOWN);
		controller.setRemoveMode(DragSortController.FLING_REMOVE);
		

		return controller;
	}

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TEST", "来ている");
		mDslv = (DragSortListView) inflater.inflate(R.layout.fragment_second,
				container, false);
		mController = buildController(mDslv);
		mDslv.setFloatViewManager(mController);
		mDslv.setOnTouchListener(mController);
		mDslv.setDragEnabled(true);

		return mDslv;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView lv = getListView();
		// リストクリック
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int id,
					long arg3) {
				String message = String.format("Clicked item %d", id);
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
						.show();
				mainActivity.printSecondFragmentLayoutDialog(id + 1,
						houseWorkItem, new SecondFragmentLayout(mainActivity),
						list);
			}
		});

		// リスト長押し
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String message = String.format("Long-clicked item %d", arg2);
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

		mDslv = (DragSortListView) getListView();
		mDslv.setDropListener(onDrop);
		mDslv.setRemoveListener(onRemove);

	}

	@Override
	public void onResume() {
		super.onResume();
		setIsCheck(houseWorkItem);
		setListItemAdapter();
	}

	/**
	 * Called from DSLVFragment.onActivityCreated(). Override to set a different
	 * adapter.
	 */
	public void setListItemAdapter() {

		int count = 0;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < houseWorkItem.size(); j++) {
				if (houseWorkItem.get(j).isCheck()) {
					if (i == houseWorkItem.get(j).getSort()) {
						Log.d("TEST", "来ている");
						Log.d("TEST", "i:" + i);
						Log.d("TEST", "j: " + j);
						list.add(new ListItem(houseWorkItem.get(j).getSort(),
								houseWorkItem.get(j).getName()));
						count++;
					}
				}
			}
		}
		int num = count + 1;
		for (int i = 0; i < (15 - count); i++) {
			list.add(new ListItem(num, ""));
			num++;
		}
		Log.d("TEST", "list:" + list.toString());
		adapter = new ListAdapter(getActivity(), list, this, mainActivity);
		setListAdapter(adapter);

	}

	/**
	 * ListViewの入れ直し
	 */
	public void resetListItemAdapter() {
		int count = 0;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < houseWorkItem.size(); j++) {
				if (houseWorkItem.get(j).isCheck()) {
					if (i == houseWorkItem.get(j).getSort()) {
						Log.d("TEST", "来ている");
						Log.d("TEST", "i:" + i);
						Log.d("TEST", "j: " + j);
						list.set(i, new ListItem(
								houseWorkItem.get(j).getSort(), houseWorkItem
										.get(j).getName()));
						count++;
					}
				}
			}
		}
		int num = count + 1;
		for (int i = 0; i < (15 - count); i++) {
			list.add(new ListItem(num, ""));
			num++;
		}
		Log.d("TEST", "list:" + list.toString());
		adapter = new ListAdapter(getActivity(), list, this, mainActivity);
		setListAdapter(adapter);

	}

	public ArrayList<Integer> getItemIds() {
		ArrayList<Integer> itemIds = new ArrayList<Integer>();
		for (int i = 0; i < adapter.getCount(); i++) {
			ListItem groupItem = adapter.getItem(i);
			itemIds.add(groupItem.getItemId());
		}

		return itemIds;
	}

	/**
	 * 削除フラグをセット
	 * 
	 * @param item
	 */
	public void setIsCheck(ArrayList<HouseWorkItem> item) {
		int count = 0;
		for (int i = 0; i < item.size(); i++) {
			if (item.get(i).isCheck()) {
				item.get(i).setSort(count + 1);
				count++;
			}
		}
	}

	/**
	 * ArrayList<HouseWorkItem>インスタンスを取得
	 * 
	 * @return houseWorkItem
	 */
	public ArrayList<HouseWorkItem> getHouseWorkItemList() {
		return houseWorkItem;
	}

	public ArrayList<ListItem> getList() {
		return list;
	}

}
