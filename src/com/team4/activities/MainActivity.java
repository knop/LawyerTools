package com.team4.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TCaseEntity;
import com.team4.type.TCompanyEntity;
import com.team4.type.TFinancingEntity;
import com.team4.type.TInfomationEntity;
import com.team4.type.TInfomationsEntity;
import com.team4.type.TInvestmentEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.type.T4List;
import com.team4.utils.util.FuncUtil;
import com.team4.utils.util.T4Log;

/**
*  @Project       : LawyerTools
*  @Program Name  : com.team4.activities.MainActivity.java
*  @Class Name    : MainActivity
*  @Description   : 主页面
*  @Author        : Xiaohui Chen
*  @Creation Date : 2013-3-1 下午1:19:02 
*  @ModificationHistory  
*  Who            When          What 
*  ------------   -----------   ------------------------------------
*  Xiaohui Chen   2013-3-1       Create
*
*/
public class MainActivity extends Activity {
	
	//每次获取记录的条数
	private final static int RECORD_PERPAGE = 10;
	// 单位为dp
	private final static int FOCUS_LINE_WIDTH_DP = 60; 
	
	private final int CONTEXT_MENU_DETAIL = 1;
	private final int CONTEXT_MENU_COMMUNICATION = 2;
	private final int CONTEXT_MENU_MATCH = 3;
	
	//注意:tabIds和tabTypes需要个数匹配
	private static final int tabIds[] = { 
		R.id.tv_tab_company, 
		R.id.tv_tab_investment, 
		R.id.tv_tab_financing, 
		R.id.tv_tab_case };
	private static final String tabTypes[] = { 
		HttpManager.TYPE_COMPANY, 
		HttpManager.TYPE_INVESTMENT,
		HttpManager.TYPE_FINANCING, 
		HttpManager.TYPE_CASE };
	
	private int mCurrentIndex = -1;
	private View mFocusLine;
	private int mTvWidth;
	private int mOffset;
	private Button mBtnRetry;
	private TextView mTvStateText;
	private ProgressBar mPbWaiting;
	private View mLlDataView;
	private View mLlStateView;
	private ListView mLvData;
	private View mFooterView;
	private int mTotalCount;
	private int mEndIndex;
	private T4List<TInfomationEntity> mInfos;
	TaskGetInfomation mTaskGetInfo;
	private boolean mIsLoading;
	private InfomationAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		resetData();
		initTabView();
		initContentView();
		initFocusLine();
		setFocusTab(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		MenuInflater inflater = new MenuInflater(this);  
        inflater.inflate(R.menu.main_menu, menu);  
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override  
    public boolean onOptionsItemSelected(MenuItem item) { 
		switch(item.getItemId()){
		case R.id.refresh:
			getInfomationFirst();
			break;
		case R.id.about:
			showAboutPage();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		TInfomationEntity entity =  (TInfomationEntity)mLvData.getAdapter().getItem(menuInfo.position);
		switch (item.getItemId()) {
		case CONTEXT_MENU_DETAIL:
			showDetail(entity);
			break;
		case CONTEXT_MENU_COMMUNICATION:
			showCommunicationPage(entity);
			break;
		case CONTEXT_MENU_MATCH:
			showMatchPage(entity);
			break;
		default:
			break;
		}
		return true;
	}

	/** 
	*  @Author Xiaohui Chen
	*  @Creation 2013-3-4 上午10:32:32 
	*  @Description 重置ListView数据源相关的字段，只有在切换页面的时候调用
	*
	*/
	private void resetData() {
		if(mInfos == null)
			mInfos = new T4List<TInfomationEntity>();
		mInfos.clear();
		mTotalCount = 0;
		mEndIndex = 0;
	}
	
	/** 
	*  @Author Xiaohui Chen
	*  @Creation 2013-3-4 上午10:33:22 
	*  @Description 初始化Tab页
	*
	*/
	private void initTabView() {
		for (int i = 0; i < tabIds.length; i++) {
			TextView tv = (TextView) findViewById(tabIds[i]);
			tv.setTag(i);
			tv.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					setFocusTab((Integer) view.getTag());
				}
			});
		}
	}

	private void initFocusLine() {
		mFocusLine = findViewById(R.id.v_focus_line);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mTvWidth = dm.widthPixels / tabIds.length;
		mOffset = (mTvWidth - FuncUtil.dp2px(this, FOCUS_LINE_WIDTH_DP)) / 2;
	}

	/** 
	*  @Author Xiaohui Chen
	*  @Creation 2013-3-4 上午10:34:45 
	*  @Description 初始化内容显示区域的UI，主要是设置各个UI显示与否，以及绑定一些listener
	*
	*/
	private void initContentView() {
		mBtnRetry = (Button) findViewById(R.id.btn_retry);		
		mBtnRetry.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				getInfomationFirst();
			}
		});
		mTvStateText = (TextView) findViewById(R.id.tv_state_text);
		mPbWaiting = (ProgressBar) findViewById(R.id.pb_waiting);
		mLlDataView = findViewById(R.id.ll_data_view);
		mLlStateView = findViewById(R.id.ll_state_view);
		mLvData = (ListView) findViewById(R.id.lv_data);
		mFooterView = getLayoutInflater().inflate(R.layout.activity_main_footer, null);
		mLvData.addFooterView(mFooterView);
		if (mAdapter == null) {
			mAdapter = new InfomationAdapter(this);
		}
		mLvData.setAdapter(mAdapter);
		mLvData.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (view.getLastVisiblePosition() >= totalItemCount - 1) {
					loadMoreInfo();
				}				
			}
		});
		mLvData.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				String tabType = tabTypes[mCurrentIndex];
				menu.add(Menu.NONE, CONTEXT_MENU_DETAIL, CONTEXT_MENU_DETAIL, R.string.detail);
				menu.add(Menu.NONE, CONTEXT_MENU_COMMUNICATION, CONTEXT_MENU_COMMUNICATION, R.string.communication);
				if (tabType.equalsIgnoreCase(HttpManager.TYPE_INVESTMENT)
						|| tabType.equalsIgnoreCase(HttpManager.TYPE_FINANCING)) {
					menu.add(Menu.NONE, CONTEXT_MENU_MATCH, CONTEXT_MENU_MATCH, R.string.match);	
				}				
			}
			
		});
		mLvData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View v, int pos,
					long id) {
				showDetail((TInfomationEntity)listView.getAdapter().getItem(pos));
			}
		});
		final EditText et = (EditText)findViewById(R.id.et_search_keyword);
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				String keyword = et.getText().toString();
				((InfomationAdapter)mLvData.getAdapter()).getFilter().filter(keyword);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		resetContentView();
	}

	/** 
	*  @Description    : 重置数据显示区域，当切换标签的时候，需要重新load数据
	*  @Creation Date  : 2013-3-1 下午1:09:32 
	*  @Author         : Xiaohui Chen
	*/
	private void resetContentView() {
		mBtnRetry.setVisibility(View.GONE);		
		mTvStateText.setText(R.string.state_text_waiting);
		mTvStateText.setVisibility(View.VISIBLE);		
		mPbWaiting.setVisibility(View.VISIBLE);		
		mLlDataView.setVisibility(View.GONE);		
		mLlStateView.setVisibility(View.VISIBLE);		
	}
	
	/** 
	*  @Description    : 显示匹配信息页面，只有Financing和Investment两种页面会调用到这个函数
	*  @param entity
	*  @Creation Date  : 2013-3-1 下午1:09:47 
	*  @Author         : Xiaohui Chen
	*/
	private void showMatchPage(TInfomationEntity entity) {
		
	}
	
	/** 
	*  @Description    : 显示联络信息页面
	*  @param entity
	*  @Creation Date  : 2013-3-1 下午1:09:11 
	*  @Author         : Xiaohui Chen
	*/
	private void showCommunicationPage(TInfomationEntity entity) {
		Intent intent = new Intent();
		intent.setClass(this, CommunicationActivity.class);
		intent.putExtra(CommunicationActivity.EXTRA_KEY_ID, entity.getId());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TITLE, entity.getName());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TYPE, tabTypes[mCurrentIndex]);
		startActivity(intent);
	}
	
	/** 
	*  @Description    : 显示详情页面
	*  @param entity
	*  @Creation Date  : 2013-3-1 下午1:10:45 
	*  @Author         : Xiaohui Chen
	*/
	private void showDetail(TInfomationEntity entity) {
		if (entity == null) {
			Toast.makeText(this, "没有详细信息", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent();
		String type = tabTypes[mCurrentIndex];
		if(type.equalsIgnoreCase(HttpManager.TYPE_COMPANY)) {
			intent.setClass(this, CompanyActivity.class);
			intent.putExtra(TCompanyEntity.class.getName(), entity);
		} else if(type.equalsIgnoreCase(HttpManager.TYPE_FINANCING)) {
			intent.setClass(this, FinancingActivity.class);
			intent.putExtra(TFinancingEntity.class.getName(), entity);
		} else if(type.equalsIgnoreCase(HttpManager.TYPE_INVESTMENT)) {
			intent.setClass(this, InvestmentActivity.class);
			intent.putExtra(TInvestmentEntity.class.getName(), entity);
		} else if(type.equalsIgnoreCase(HttpManager.TYPE_CASE)) {
			intent.setClass(this, CaseActivity.class);
			intent.putExtra(TCaseEntity.class.getName(), entity);
		} else {
			T4Log.e(type+"类型不可用");
		}
		startActivity(intent);
	}
	
	/** 
	*  @Author Xiaohui Chen
	*  @Creation 2013-3-4 上午10:36:03 
	*  @param pos
	*  @Description 选中某一个tab的时候，将文字置成高亮，并且滑动Focus line到指定项下方
	*
	*/
	private void setFocusTab(int pos) {
		if (pos > tabIds.length || pos < 0 || pos == mCurrentIndex)
			return; 
		
		//记录上一次focus line所在的位置
		int lastX = mTvWidth * mCurrentIndex + mOffset;
		
		//切换颜色
		TextView tv = null;
		if (mCurrentIndex >= 0) {
			tv = (TextView) findViewById(tabIds[mCurrentIndex]);
			tv.setTextColor(getResources().getColor(R.color.color_zhonghui));
		}
		mCurrentIndex = pos;
		tv = (TextView) findViewById(tabIds[mCurrentIndex]);
		tv.setTextColor(getResources().getColor(R.color.white));
		
		//focus line动画效果
		TranslateAnimation animation = new TranslateAnimation(lastX, mTvWidth
				* mCurrentIndex + mOffset, 0, 0);
		animation.setFillAfter(true);
		mFocusLine.setAnimation(animation);
		
		//获取数据
		getInfomationFirst();
	}
	
	/** 
	*  @Author Xiaohui Chen
	*  @Creation 2013-3-1 下午4:36:17 
	*  @param index
	*  @Description 在该tab页首次获取信息时调用
	*
	*/
	private void getInfomationFirst() {
		//在切换tab的时候，需要清空当前的数据
		resetData();
		
		//执行网络请求获取数据
		getInfomation();
		
		//在第一次执行时，需要初始的loading界面
		resetContentView();
	}
	
	/** 
	*  @Description    : 取消正在进行的请求Task，并开始一个新的Task，同时会设置tab的位置
	*  @param pos
	*  @Creation Date  : 2013-3-1 下午1:11:27 
	*  @Author         : Xiaohui Chen
	*/
	private void getInfomation() {
		if (mTaskGetInfo != null) {
			mTaskGetInfo.cancel(true);
			mTaskGetInfo = null;
		}
		mTaskGetInfo = new TaskGetInfomation(this);
		mIsLoading = true;
		mTaskGetInfo.execute(tabTypes[mCurrentIndex], String.valueOf(mEndIndex / RECORD_PERPAGE + 1));			
	}
	
	/** 
	*  @Description    : 当滑动到listView的最后一条时，会调用这个函数载入更多信息
	*  @Creation Date  : 2013-3-1 下午1:12:40 
	*  @Author         : Xiaohui Chen
	*/
	private void loadMoreInfo() {		
		if (!mIsLoading 
				&& mCurrentIndex >= 0 
				&& mTotalCount != 0 
				&& mTotalCount > mEndIndex) {
			boolean isFiltered = mAdapter.isFiltered();
			if (!isFiltered) {
				T4Log.d("loadMoreInfo");
//				mFooterView.setVisibility(View.VISIBLE);
//				mFooterView = getLayoutInflater().inflate(R.layout.activity_main_footer, null);
				mLvData.addFooterView(mFooterView);
				getInfomation();
			}
		}
	}
	
	/** 
	*  @Description    : 显示“关于”页面
	*  @Creation Date  : 2013-3-1 下午1:13:23 
	*  @Author         : Xiaohui Chen
	*/
	private void showAboutPage() {
		Intent intent = new Intent();
		intent.setClass(this, AboutActivity.class);
		this.startActivity(intent);
	}
	
	/** 
	*  @Description    : Http请求完成后的回调
	*  @param type 请求信息的类型
	*  @param entity 请求返回的数据，如发生异常则为null
	*  @param ex 如请求未成功则需要获取异常信息，否则这个参数为null
	*  @Creation Date  : 2013-3-1 下午1:13:39 
	*  @Author         : Xiaohui Chen
	*/
	@SuppressWarnings("unchecked")
	public void onGetInfosComplete(String type, TInfomationsEntity entity, T4Exception ex) {
		if (ex == null && entity != null) {
			mLlStateView.setVisibility(View.GONE);
			mLlDataView.setVisibility(View.VISIBLE);
			if (mTotalCount == 0) {
				mAdapter.reset();
				mAdapter.getList().addAll((T4List<TInfomationEntity>)entity.getRecords());
				mAdapter.notifyDataSetChanged();
				mLvData.setSelection(0);
			} else {
				mAdapter.getList().addAll(mEndIndex, (T4List<TInfomationEntity>)entity.getRecords());
				mAdapter.notifyDataSetChanged();
			}

			mTotalCount = entity.getTotalCount();
			mEndIndex = entity.getEndIndex();			
		} else {
			String message = "Exception Code: " + ex.getExceptionCode()
					+ "\r\n" + "Message: " + ex.getMessage();
			T4Log.v(message);
			if (mTotalCount == 0) {
				mLlStateView.setVisibility(View.VISIBLE);
				mLlDataView.setVisibility(View.GONE);
				mBtnRetry.setVisibility(View.VISIBLE);
				mTvStateText.setText(R.string.state_text_failed);
				mTvStateText.setVisibility(View.VISIBLE);
				mPbWaiting.setVisibility(View.GONE);
			} else {
				Toast.makeText(this, "加载数据失败，请重试", Toast.LENGTH_LONG).show();
			}
		}
		mIsLoading = false;
//		mFooterView.setVisibility(View.GONE);
		mLvData.removeFooterView(mFooterView);
	}
	
	/**
	*  @Project       : LawyerTools
	*  @Program Name  : com.team4.activities.MainActivity.java
	*  @Class Name    : TaskGetInfomation
	*  @Description   : 获取信息的异步执行类，调用excute时需要传入Type，
	*  @Author        : Xiaohui Chen
	*  @Creation Date : 2013-3-1 下午1:16:01 
	*  @ModificationHistory  
	*  Who            When          What 
	*  ------------   -----------   ------------------------------------
	*  Xiaohui Chen   2013-3-1       Create
	*
	*/
	private static class TaskGetInfomation extends
			AsyncTask<String, Void, TInfomationsEntity> {

		T4Exception mException = null;
		MainActivity mActivity = null;
		String mType;
		String mCurrentPage;

		public TaskGetInfomation(MainActivity activity) {
			mActivity = activity;
		}

		@Override
		public TInfomationsEntity doInBackground(String... params) {
			mType = params[0];
			mCurrentPage = params[1];
			
			TInfomationsEntity entity = null;

			try {
				entity = HttpManager.instance().getInfomation(mActivity, mType, String.valueOf(RECORD_PERPAGE), mCurrentPage);
			} catch (T4Exception ex) {
				mException = ex;
			} 

			return entity;
		}

		@Override
		public void onPostExecute(TInfomationsEntity entity) {
			if (mActivity != null && !isCancelled()) {
				mActivity.onGetInfosComplete(mType, entity, mException);
			}
		}
	}
	
	private static class InfomationAdapter 
		extends BaseAdapter implements Filterable {
		
		private LayoutInflater mInflater;
		T4List<TInfomationEntity> mOriginalList;
		T4List<TInfomationEntity> mFilteredList;
		boolean mIsFiltered;
		
		public InfomationAdapter(Context context) {
			super();
			mInflater = LayoutInflater.from(context);
			mIsFiltered = false;
		}
		
		public void reset() {
			if (mOriginalList != null)
				mOriginalList.clear();
			if (mFilteredList != null)
				mFilteredList.clear();
			mIsFiltered = false;
		}
		
		public T4List<TInfomationEntity> getList() {
			if (mIsFiltered) {
				if (mOriginalList == null)
					mOriginalList = new T4List<TInfomationEntity>(); 
				return mOriginalList;
			} else {
				if (mFilteredList == null)
					mFilteredList = new T4List<TInfomationEntity>(); 
				return mFilteredList;				
			}
		}
		
		public boolean isFiltered() {
			return mIsFiltered;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return getList().size();
		}

		@Override
		public TInfomationEntity getItem(int pos) {
			// TODO Auto-generated method stub
			return getList().get(pos);
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return pos;
		}
		
		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			TInfomationEntity entity = getItem(pos);
			if (convertView == null) {
				view = newItemView(null);
			} else {
				view = convertView;
			}
			
			bindView(view, entity);
			
			return view;
		}
		
		private void bindView(View view, TInfomationEntity entity) {
			if (view == null || entity == null)
				return;
			
			ViewHolder holder = (ViewHolder)view.getTag();
			holder.tvName.setText(entity.getName());
		}
		
		private View newItemView(ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			View view = mInflater.inflate(R.layout.view_infomation_item, parent);
			holder.tvName = (TextView)view.findViewById(R.id.tv_infomation_name);
			view.setTag(holder);
			return view;			
		}
		
		public static class ViewHolder {
			public TextView tvName;
		}

		/** 
		*  @Description    : 用于根据查找的关键字过滤结果
		*  @Method_Name    : getFilter
		*  @return
		*  @Creation Date  : 2013-3-1 下午1:18:02 
		*  @Author         : Xiaohui Chen
		*/
		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				
				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					notifyDataSetChanged();
				}
				
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					
					FilterResults results = new FilterResults();
					
					if (constraint == null || constraint.length() <= 0) {
						mIsFiltered = false;
						results.count = mOriginalList.size();
						results.values = mOriginalList;
					} else {
						mIsFiltered = true;
						if (mFilteredList == null)
							mFilteredList = new T4List<TInfomationEntity>();
						mFilteredList.clear();
						for (int i=0; i<mOriginalList.size(); i++) {
							TInfomationEntity entity = mOriginalList.get(i);
							if(entity.getName().startsWith(constraint.toString())) {
								mFilteredList.add(entity);
							}
						}
						results.count = mFilteredList.size();
						results.values = mFilteredList;
					}
					
					return results;
				}
			};
			return filter;
		}
	}
}
