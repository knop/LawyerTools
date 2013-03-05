package com.team4.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TFinancingEntity;
import com.team4.type.TInfomationEntity;
import com.team4.type.TInvestmentEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.type.IBaseType;
import com.team4.utils.type.T4List;
import com.team4.utils.util.T4Log;

/**
 * @Project LawyerTools
 * @Author Xiaohui Chen
 * @Email knop0211@gmail.com
 * @Creation 2013-3-5 上午9:54:10
 * @Description 匹配信息页面
 * 
 * @History Who When What ------------ -----------
 *          ------------------------------------ Xiaohui Chen 2013-3-5 Create
 * 
 */
public class MatchActivity extends Activity {

	public final static String EXTRA_KEY_ID = "id";
	public final static String EXTRA_KEY_TITLE = "title";
	public final static String EXTRA_KEY_TYPE = "type";

	private ListView mListView;
	private View mLlDataView;
	private View mLlStateView;
	private TextView mTvStateText;
	private ProgressBar mPbWaiting;
	private String mType;
	private MatchAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match);

		mLlDataView = findViewById(R.id.ll_match_data_view);
		mListView = (ListView) findViewById(R.id.lv_match_data);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View v, int pos,
					long id) {
				showDetail(mAdapter.getItem(pos));
			}
		});
		mLlStateView = findViewById(R.id.ll_match_state_view);
		mTvStateText = (TextView) findViewById(R.id.tv_match_state_text);
		mPbWaiting = (ProgressBar) findViewById(R.id.pb_match_waiting);
		mLlDataView.setVisibility(View.GONE);
		mLlStateView.setVisibility(View.VISIBLE);
		mTvStateText.setVisibility(View.VISIBLE);

		Intent intent = getIntent();
		String title = intent.getStringExtra(EXTRA_KEY_TITLE);
		TextView tvTitle = (TextView) findViewById(R.id.tv_match_title);
		tvTitle.setText(title);
		int id = intent.getIntExtra(EXTRA_KEY_ID, -1);
		mType = intent.getStringExtra(EXTRA_KEY_TYPE);
		if (id > -1 && mType != null && mType.length() > 0) {
			mPbWaiting.setVisibility(View.VISIBLE);
			mTvStateText.setText(R.string.state_text_waiting);
			TaskGetMatch task = new TaskGetMatch(this, id);
			task.execute(mType);
		} else {
			mPbWaiting.setVisibility(View.GONE);
			mTvStateText.setText(R.string.state_text_match_none);
		}
	}
	
	private void showDetail(TInfomationEntity entity) {
		if (entity == null) {
			Toast.makeText(this, "没有详细信息", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent();
		//如果是资金，则要匹配项目。
		//如果是项目，就需要匹配资金。
		//所以显示的页面与类型是相反的。
		if (mType.equalsIgnoreCase(HttpManager.TYPE_FINANCING)){
			intent.setClass(this, InvestmentActivity.class);
			intent.putExtra(TInvestmentEntity.class.getName(), entity);
		} else if (mType.equalsIgnoreCase(HttpManager.TYPE_INVESTMENT)){
			intent.setClass(this, FinancingActivity.class);
			intent.putExtra(TFinancingEntity.class.getName(), entity);
		} else {
			T4Log.e(mType+"类型无法获取详细信息");
		}
		startActivity(intent);
	}

	@SuppressWarnings("unchecked")
	public void onGetMatchComplete(IBaseType data, T4Exception ex) {
		if (ex == null && data != null) {
			T4List<TInfomationEntity> list = (T4List<TInfomationEntity>) data;
			if (list.size() <= 0) {
				mLlDataView.setVisibility(View.GONE);
				mLlStateView.setVisibility(View.VISIBLE);
				mPbWaiting.setVisibility(View.GONE);
				mTvStateText.setText(R.string.state_text_match_none);
			} else {
				mLlDataView.setVisibility(View.VISIBLE);
				mLlStateView.setVisibility(View.GONE);
				mAdapter = new MatchAdapter(this, list);
				mListView.setAdapter(mAdapter);
			}
		} else {
			String message = "Exception Code: " + ex.getExceptionCode()
					+ "\r\n" + "Message: " + ex.getMessage();
			T4Log.v(message);
			mLlDataView.setVisibility(View.GONE);
			mLlStateView.setVisibility(View.VISIBLE);
			mPbWaiting.setVisibility(View.GONE);
			mTvStateText.setText(R.string.state_text_match_none);
		}
	}

	private static class MatchAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		T4List<TInfomationEntity> mList;

		public MatchAdapter(Context context, T4List<TInfomationEntity> list) {
			super();
			mInflater = LayoutInflater.from(context);
			mList = list;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public TInfomationEntity getItem(int pos) {
			return mList.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
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

			ViewHolder holder = (ViewHolder) view.getTag();
			holder.tvName.setText(entity.getName());
		}

		private View newItemView(ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			View view = mInflater
					.inflate(R.layout.view_infomation_item, parent);
			holder.tvName = (TextView) view
					.findViewById(R.id.tv_infomation_name);
			view.setTag(holder);
			return view;
		}

		public static class ViewHolder {
			public TextView tvName;
		}
	}

	private static class TaskGetMatch extends
			AsyncTask<String, Void, IBaseType> {

		T4Exception mException = null;
		MatchActivity mActivity = null;
		int mId;

		public TaskGetMatch(MatchActivity activity, int id) {
			mActivity = activity;
			mId = id;
		}

		@Override
		public IBaseType doInBackground(String... params) {
			String type = params[0];
			IBaseType list = null;
			try {
				list = HttpManager.instance().getMatch(mActivity, type, mId);
			} catch (T4Exception ex) {
				mException = ex;
			}
			return list;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		public void onPostExecute(IBaseType list) {
			if (mActivity != null) {
				mActivity.onGetMatchComplete(list, mException);
			}
		}
	}
}
