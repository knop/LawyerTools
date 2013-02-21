package com.team4.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TCompaniesEntity;
import com.team4.type.TCompanyEntity;
import com.team4.type.TInfomationEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;
import com.team4.utils.util.FuncUtil;
import com.team4.utils.util.T4Log;

public class MainActivity extends Activity {
	
	private static final String RECORD_PERPAGE = "100";
	private static final String PAGE_NUMBER = "1";
	private static final int FOCUS_LINE_WIDTH_DP = 60; // 单位为dp
	
	//注意:tabId和tabType需要个数匹配
	private static final int tabId[] = { R.id.tv_tab_company, R.id.tv_tab_financing, 
		R.id.tv_tab_investment, R.id.tv_tab_case };
	private static final String tabType[] = { HttpManager.TYPE_COMPANY, 
		HttpManager.TYPE_FINANCING, HttpManager.TYPE_INVESTMENT, HttpManager.TYPE_CASE };
	
	private int mCurrentPos = -1;
	private View mFocusLine;
	private int mTvWidth;
	private int mOffset;
	private Button mBtnRetry;
	private TextView mTvStateText;
	private ProgressBar mPbWaiting;
	private View mLlDataView;
	private View mLlStateView;
	private ListView mLvData;
	private TCompaniesEntity mCompaniesEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTabView();
		initContentView();
		initFocusLine();
		setFocusTab(0);
	}

	private void initTabView() {
		for (int i = 0; i < tabId.length; i++) {
			TextView tv = (TextView) findViewById(tabId[i]);
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
		mTvWidth = dm.widthPixels / tabId.length;
		mOffset = (mTvWidth - FuncUtil.dp2px(this, FOCUS_LINE_WIDTH_DP)) / 2;
	}

	private void initContentView() {
		mBtnRetry = (Button) findViewById(R.id.btn_retry);		
		mBtnRetry.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		mTvStateText = (TextView) findViewById(R.id.tv_state_text);
		mPbWaiting = (ProgressBar) findViewById(R.id.pb_waiting);
		mLlDataView = findViewById(R.id.ll_data_view);
		mLlStateView = findViewById(R.id.ll_state_view);
		mLvData = (ListView) findViewById(R.id.lv_data);
		mLvData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				showDetail((TInfomationEntity)listView.getAdapter().getItem(pos));
			}
		});
		resetContentView();
	}

	//重置数据显示区域，当切换标签的时候，需要重新load数据
	private void resetContentView() {
		mBtnRetry.setVisibility(View.GONE);		
		mTvStateText.setText("正在获取数据");
		mTvStateText.setVisibility(View.VISIBLE);		
		mPbWaiting.setVisibility(View.VISIBLE);		
		mLlDataView.setVisibility(View.GONE);		
		mLlStateView.setVisibility(View.VISIBLE);		
	}
	
	private void showDetail(TInfomationEntity entity) {
		if (entity == null) {
			Toast.makeText(this, "没有详细信息", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent();
		String type = tabType[mCurrentPos];
		if(type.equalsIgnoreCase(HttpManager.TYPE_COMPANY)) {
			intent.setClass(this, CompanyActivity.class);
			intent.putExtra(TCompanyEntity.class.getName(), entity);
		} else if(type.equalsIgnoreCase(HttpManager.TYPE_FINANCING)) {
			;
		} else if(type.equalsIgnoreCase(HttpManager.TYPE_INVESTMENT)) {
			;
		} else if(type.equalsIgnoreCase(HttpManager.TYPE_CASE)) {
			;
		} else {
			T4Log.e(type+"类型不可用");
		}
		startActivity(intent);
	}
	
	//选中某一个tab的时候，将文字置成高亮，并且滑动Focus line到指定项下方
	private void setFocusTab(int pos) {
		if (pos > tabId.length || pos < 0 || pos == mCurrentPos)
			return;
		//执行网络请求
		TaskGetInfomation task = new TaskGetInfomation(this);
		task.execute(tabType[pos]);
		resetContentView();
		
		//记录上一次focus line所在的位置
		int lastX = mTvWidth * mCurrentPos + mOffset;
		
		//切换颜色
		TextView tv = null;
		if (mCurrentPos >= 0) {
			tv = (TextView) findViewById(tabId[mCurrentPos]);
			tv.setTextColor(getResources().getColor(R.color.color_zhonghui));
		}
		mCurrentPos = pos;
		tv = (TextView) findViewById(tabId[mCurrentPos]);
		tv.setTextColor(getResources().getColor(R.color.white));

		//focus line动画效果
		TranslateAnimation animation = new TranslateAnimation(lastX, mTvWidth
				* mCurrentPos + mOffset, 0, 0);
		animation.setFillAfter(true);
		mFocusLine.setAnimation(animation);
	}

	public void onGetCompaniesComplete(String type, IBaseType entity, T4Exception ex) {
		if (ex == null && entity != null) {
			mLlStateView.setVisibility(View.GONE);
			mLlDataView.setVisibility(View.VISIBLE);
			if (type.equalsIgnoreCase(HttpManager.TYPE_COMPANY)) {
				mCompaniesEntity = (TCompaniesEntity)entity;
				mLvData.setAdapter(new InfomationAdapter<TCompanyEntity>(this, mCompaniesEntity.getRecords()));
			}			
		} else {
			String message = "Exception Code: " + ex.getExceptionCode()
					+ "\r\n" + "Message: " + ex.getMessage();
			T4Log.v(message);
			mLlStateView.setVisibility(View.VISIBLE);
			mLlDataView.setVisibility(View.GONE);
			mBtnRetry.setVisibility(View.VISIBLE);
			mTvStateText.setText("获取数据失败，请重试");
			mTvStateText.setVisibility(View.VISIBLE);
			mPbWaiting.setVisibility(View.GONE);
		}
	}
	
	private static class TaskGetInfomation extends
			AsyncTask<String, Void, IBaseType> {

		T4Exception mException = null;
		MainActivity mActivity = null;
		String mType;

		public TaskGetInfomation(MainActivity activity) {
			mActivity = activity;
		}

		@Override
		public IBaseType doInBackground(String... params) {
			mType = params[0];
			
			IBaseType entity = null;

			try {
				entity = HttpManager.instance().getInfomation(mType, RECORD_PERPAGE, PAGE_NUMBER);
			} catch (T4Exception ex) {
				mException = ex;
			}

			return entity;
		}

		@Override
		public void onPostExecute(IBaseType entity) {
			if (mActivity != null) {
				mActivity.onGetCompaniesComplete(mType, entity, mException);
			}
		}
	}
	
	private static class InfomationAdapter<T extends TInfomationEntity> extends BaseAdapter {
		
		private LayoutInflater mInflater;
		T4List<T> mList;
		
		public InfomationAdapter(Context context, T4List<T> list) {
			super();
			mInflater = LayoutInflater.from(context);
			mList = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mList == null)
				return 0;
			return mList.size();
		}

		@Override
		public T getItem(int pos) {
			// TODO Auto-generated method stub
			if (mList == null || pos > mList.size())
				return null;
			return mList.get(pos);
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
			T entity = getItem(pos);
			if (convertView == null) {
				view = newItemView(null);
			} else {
				view = convertView;
			}
			
			bindView(view, entity);
			
			return view;
		}
		
		private void bindView(View view, T entity) {
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
	}
}
