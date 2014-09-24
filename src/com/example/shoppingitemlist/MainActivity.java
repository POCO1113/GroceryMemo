package com.example.shoppingitemlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.fragment.EditDialogFragment;
import com.example.fragment.SecondFragmentLayout;
import com.example.fragment.TopFragmentLayout;
import com.example.item.HouseWorkItem;
import com.example.item.ListItem;
import com.example.item.ShoppingItem;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private View mLeftDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private BootstrapButton listButton, categoryButton, boughtButton,
			houseWorkButton;
	private ArrayList<ShoppingItem> shoppingItems;
	private ArrayList<HouseWorkItem> houseworkItems;

	private TopFragmentLayout topFragmentLayout;
	private SecondFragmentLayout secondFragmentLayout;

	public static final String TOP_FRAGMENT = "TOP_FRAGMENT";
	public static final String SECOND_FRAGMENT = "SECOND_FRAGMENT";

	private static final String FILE_SHOPPINGLIST = "shopping.txt";
	private static final String FILE_HOUSEWORKLIST = "housework.txt";

	ArrayAdapter<String> adapter;
	List<ListItem> list = new ArrayList<ListItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();

		// ActionBarDrawerToggleインスタンスでドロワーメニューを使えるように設定
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_oepn,
				-R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View view) {
				supportInvalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				supportInvalidateOptionsMenu();
			}
		};

		// ドロワーメニューのイベントハンドラを設定
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		// TopFragmentLayout生成
		TopFragmentLayout topFragmentLayout = new TopFragmentLayout();
		this.topFragmentLayout = topFragmentLayout;
		fragmentTransaction.add(R.id.container, topFragmentLayout, "top");

		if (shoppingItems == null) {
			shoppingItems = topFragmentLayout.getShoppingItemList();
		} else {
			shoppingItems.clear();
		}
		// 買い物リストの書き出し
		printList(TOP_FRAGMENT);

		// SecondFragmentLayout生成
		SecondFragmentLayout secondFragmentLayout = new SecondFragmentLayout(this);
		this.secondFragmentLayout = secondFragmentLayout;

		if (houseworkItems == null) {
			houseworkItems = secondFragmentLayout.getHouseWorkItemList();
		} else {
			houseworkItems.clear();
		}
		// 買い物リストの書き出し
		printList(SECOND_FRAGMENT);
		
		fragmentTransaction.commit();

		changeFragment(SECOND_FRAGMENT);

	}

	@Override
	protected void onResume() {
		super.onResume();
		// menuボタンのクリックイベント
		listButton.setOnClickListener(new ButtonClickListener());
		categoryButton.setOnClickListener(new ButtonClickListener());
		boughtButton.setOnClickListener(new ButtonClickListener());
		houseWorkButton.setOnClickListener(new ButtonClickListener());

	}

	class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mDrawerLayout.closeDrawers();
			if (v.getId() == R.id.button_list) {
				changeFragment(TOP_FRAGMENT);

			} else if (v.getId() == R.id.button_category) {
				changeFragment(SECOND_FRAGMENT);

				// Toast.makeText(MainActivity.this, "button_category push",
				// Toast.LENGTH_SHORT).show();

			} else if (v.getId() == R.id.button_bought) {
				Toast.makeText(MainActivity.this, "button_bought push",
						Toast.LENGTH_SHORT).show();

			} else if (v.getId() == R.id.button_housework) {
				changeFragment(SECOND_FRAGMENT);
				// changeFragment(THIRD_FRAGMENT);
			}

		}
	}

	// その他コールバックメソッドに各種設定
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 各View要素に関する関連付けメソッド
	 * 
	 * @param root
	 */
	public void findView() {
		// DrawerLayoutとViewの関連づけ
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLeftDrawer = findViewById(R.id.left_drawer);
		listButton = (BootstrapButton) mLeftDrawer
				.findViewById(R.id.button_list);
		categoryButton = (BootstrapButton) mLeftDrawer
				.findViewById(R.id.button_category);
		boughtButton = (BootstrapButton) mLeftDrawer
				.findViewById(R.id.button_bought);
		houseWorkButton = (BootstrapButton) mLeftDrawer
				.findViewById(R.id.button_housework);
	}

	/**
	 * 買い物アイテムリストを端末内へ保存
	 * 
	 * @param shoppingItems
	 */
	public void saveShoppingList(ArrayList<ShoppingItem> shoppingItems) {
		String saveStr = "";
		for (int i = 0; i < shoppingItems.size(); i++) {
			saveStr += shoppingItems.get(i).printToString();
		}
		// 呼び出した文字列をファイルに保存する
		try {
			OutputStream out = openFileOutput(FILE_SHOPPINGLIST, MODE_PRIVATE);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,
					"UTF-8"));
			writer.append(saveStr);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 買い物アイテムリストを端末内へ保存
	 * 
	 * @param shoppingItems
	 */
	public void saveHouseWorkList(ArrayList<HouseWorkItem> houseWorkItems) {
		String saveStr = "";
		for (int i = 0; i < houseWorkItems.size(); i++) {
			saveStr += houseWorkItems.get(i).printToString();
		}
		// 呼び出した文字列をファイルに保存する
		try {
			OutputStream out = openFileOutput(FILE_HOUSEWORKLIST, MODE_PRIVATE);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,
					"UTF-8"));
			writer.append(saveStr);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 買い物アイテムの書き出しメソッド
	 * 
	 * @return String
	 */
	public void printList(String mode) {
		String printStr = "";
		try {
			InputStream inputStream = null;
			if (mode == TOP_FRAGMENT) {
				inputStream = openFileInput(FILE_SHOPPINGLIST);
			} else if (mode == SECOND_FRAGMENT) {
				inputStream = openFileInput(FILE_HOUSEWORKLIST);
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			while ((printStr = reader.readLine()) != null) {
				String[] itemArray = printStr.split("/");
				for (int i = 0; i < itemArray.length; i++) {
					String[] item = itemArray[i].split(",");

					if (mode == TOP_FRAGMENT) {
						if (shoppingItems != null) {
							shoppingItems.add(new ShoppingItem(Integer
									.parseInt(item[0]), item[1], Boolean
									.parseBoolean(item[2]), Boolean
									.parseBoolean(item[3]), Integer
									.parseInt(item[4])));
						}

					} else if (mode == SECOND_FRAGMENT) {
						if (houseworkItems != null) {
							houseworkItems.add(new HouseWorkItem(Integer
									.parseInt(item[0]), item[1], Boolean
									.parseBoolean(item[2]), Boolean
									.parseBoolean(item[3]), Integer
									.parseInt(item[4])));
						}
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ダイアログ表示
	 * 
	 * @param id
	 * @param shoppingItems
	 * @param topFragmentLayout
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void printTopFragmentLayoutDialog(int id, ArrayList<ShoppingItem> shoppingItems,
			TopFragmentLayout topFragmentLayout) {
		// フラグメントダイアログを表示する
		DialogFragment editDialogFragment = new EditDialogFragment(id + 1,
				shoppingItems, topFragmentLayout, this);
		editDialogFragment.show(getFragmentManager(), "edit");
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void printSecondFragmentLayoutDialog(int id, ArrayList<HouseWorkItem> houseWorkItems,
			SecondFragmentLayout secondFragmentLayout, List<ListItem> list) {
		// フラグメントダイアログを表示する
		DialogFragment editDialogFragment = new EditDialogFragment(id,
				houseWorkItems, secondFragmentLayout, this,list);
		editDialogFragment.show(getFragmentManager(), "edit");
	}

	/**
	 * Fragmentの切り替えメソッド
	 * 
	 * @param fragmentCode
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void changeFragment(String fragmentCode) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		if (fragmentCode == TOP_FRAGMENT) {
			// TopFragmentLayoutにタグ付け
			fragmentTransaction.show(topFragmentLayout);
			fragmentTransaction.replace(R.id.container, topFragmentLayout,
					"top");
			fragmentTransaction.addToBackStack(null);

		} else if (fragmentCode == SECOND_FRAGMENT) {
			// ソート用リストフラグメントの表示
			fragmentTransaction.hide(topFragmentLayout);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, secondFragmentLayout, "second")
					.commit();
		}
		fragmentTransaction.commit();
	}
}
