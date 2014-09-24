package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.item.HouseWorkItem;
import com.example.item.ListItem;
import com.example.item.ShoppingItem;
import com.example.shoppingitemlist.MainActivity;
import com.example.shoppingitemlist.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

@SuppressLint({ "NewApi", "ValidFragment" })
public class EditDialogFragment extends DialogFragment {
	private BootstrapEditText editText;
	private int num;
	private ArrayList<ShoppingItem> shoppingItems;
	private ArrayList<HouseWorkItem> houseWorkItems;
	private List<ListItem> list;
	private TopFragmentLayout topFragmentLayout;
	private SecondFragmentLayout secondFragmentLayout;
	private MainActivity mainActivity;
	int position = 0;
	Dialog dialog = null;

	public EditDialogFragment(int id, ArrayList<ShoppingItem> shoppingItems,
			TopFragmentLayout topFragmentLayout, MainActivity mainActivity) {
		this.num = id;
		this.shoppingItems = shoppingItems;
		this.topFragmentLayout = topFragmentLayout;
		this.mainActivity = mainActivity;
	}

	public EditDialogFragment(int id, ArrayList<HouseWorkItem> houseWorkItems,
			SecondFragmentLayout secondFragmentLayout, MainActivity mainActivity, List<ListItem> list) {
		this.num = id;
		this.houseWorkItems = houseWorkItems;
		this.secondFragmentLayout = secondFragmentLayout;
		this.mainActivity = mainActivity;
		this.list = list;
	}
	

	/**
	 * ダイアログコンテナを生成する。
	 */
	@Override
	public Dialog onCreateDialog(Bundle bundle) {
		Dialog dialog = super.onCreateDialog(bundle);

		// タイトル
		if(shoppingItems != null) {
			dialog.setTitle("編 集");
		} else {
			dialog.setTitle("リストを追加");
		}
		
		// ダイアログ外タップで消えないように設定
		dialog.setCanceledOnTouchOutside(false);
		setDialog(dialog);

		return dialog;
	}

	/**
	 * UIを生成する。
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup c, Bundle b) {
		View content = inflater.inflate(R.layout.dialog, null);
		editText = (BootstrapEditText) content
				.findViewById(R.id.editText_dialog_edit_text);

		if (shoppingItems != null) {
			for (int i = 0; i < shoppingItems.size(); i++) {
				if (shoppingItems.get(i).getSort() == num) {
					position = i;
				}
			}
			editText.setText(shoppingItems.get(getPosition()).getName());

			BootstrapButton okButton = (BootstrapButton) content
					.findViewById(R.id.button_dialog_ok);
			okButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String editName = editText.getText().toString();
					shoppingItems.get(getPosition()).setName(editName);
					mainActivity.saveShoppingList(shoppingItems);
					topFragmentLayout.setListAdapter();
					dialog.dismiss();
				}
			});

		}
		
		if (houseWorkItems != null) {
			for (int i = 0; i < houseWorkItems.size(); i++) {
				if (houseWorkItems.get(i).getSort() == num) {
					position = i;
				}
			}
			editText.setText(houseWorkItems.get(getPosition()).getName());

			BootstrapButton okButton = (BootstrapButton) content
					.findViewById(R.id.button_dialog_ok);
			okButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					position = houseWorkItems.size();
					String insertName = editText.getText().toString();
					houseWorkItems.add(new HouseWorkItem(position, insertName, true, false, num));
					mainActivity.saveHouseWorkList(houseWorkItems);
					list.set(position, new ListItem(num, houseWorkItems.get(num-1).getName()));
					
					//このタイミングでリストを入れ直しする。
					
					mainActivity.changeFragment(MainActivity.SECOND_FRAGMENT);
					
					dialog.dismiss();
				}
			});

		}

		return content;
	}

	public int getPosition() {
		return position;
	}

	public void setDialog(Dialog d) {
		dialog = d;
	}

}
