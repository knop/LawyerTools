package com.team4.activities;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TComunicationEntity;
import com.team4.type.TComunicationEntity.EnumDirection;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.type.T4List;
import com.team4.utils.util.T4Log;

public class CommunicationActivity extends Activity {
	
	public final static String EXTRA_KEY_ID = "id";
	public final static String EXTRA_KEY_TITLE = "title";
	public final static String EXTRA_KEY_TYPE = "type";
	
	private ListView mListView;
	private View mLlDataView;
	private View mLlStateView;
	private TextView mTvStateText;
	private ProgressBar mPbWaiting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_communication);
		mLlDataView = findViewById(R.id.ll_communication);
		mLlStateView = findViewById(R.id.ll_communication_state);
		mTvStateText = (TextView)findViewById(R.id.tv_state_text);
		mPbWaiting = (ProgressBar)findViewById(R.id.pb_waiting);
		mLlDataView.setVisibility(View.GONE);
		mLlStateView.setVisibility(View.VISIBLE);
		mTvStateText.setVisibility(View.VISIBLE);
		Intent intent = getIntent();
		String title = intent.getStringExtra(EXTRA_KEY_TITLE);
		TextView tvTitle = (TextView)findViewById(R.id.tv_communication_title);
		tvTitle.setText(title);
		int id = intent.getIntExtra(EXTRA_KEY_ID, -1);
		String type = intent.getStringExtra(EXTRA_KEY_TYPE);
		if (id > -1 && type != null && type.length() > 0) {
			mPbWaiting.setVisibility(View.VISIBLE);
			mTvStateText.setText(R.string.state_text_request);
			TaskGetComunication task = new TaskGetComunication(CommunicationActivity.this, id);
			task.execute(type);
		} else {
			mPbWaiting.setVisibility(View.GONE);
			mTvStateText.setText(R.string.state_text_none);
		}
	}

	public void onGetCompaniesComplete(T4List<TComunicationEntity> list, T4Exception ex) {
		if (ex == null && list != null) {
			if (list.size() <= 0) {
				mLlDataView.setVisibility(View.GONE);
				mLlStateView.setVisibility(View.VISIBLE);
				mPbWaiting.setVisibility(View.GONE);
				mTvStateText.setText(R.string.state_text_none);				
			} else {
				mLlDataView.setVisibility(View.VISIBLE);
				mLlStateView.setVisibility(View.GONE);
				mListView = (ListView)findViewById(R.id.lv_communication);
				mListView.setAdapter(new ComunicationAdapter(this, list));
			}
		} else {
			String message = "Exception Code: " + ex.getExceptionCode()
					+ "\r\n" + "Message: " + ex.getMessage();
			T4Log.v(message);
			mLlDataView.setVisibility(View.GONE);
			mLlStateView.setVisibility(View.VISIBLE);
			mPbWaiting.setVisibility(View.GONE);
			mTvStateText.setText(R.string.state_text_none);
		}
	}
	
	private static class ComunicationAdapter extends BaseAdapter {

		private List<TComunicationEntity> mComunications;
		private LayoutInflater mInflater;
		
		public ComunicationAdapter(Context context, List<TComunicationEntity> list) {
			super();
			mInflater = LayoutInflater.from(context);
			mComunications = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mComunications == null)
				return 0;
			return mComunications.size();
		}

		@Override
		public TComunicationEntity getItem(int pos) {
			// TODO Auto-generated method stub
			if (mComunications == null || pos > mComunications.size())
				return null;
			return mComunications.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return pos;
		}

		@Override
		public int getItemViewType(int pos) {
			TComunicationEntity entity = getItem(pos);
			return entity.getDirection();
		}
		
		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			TComunicationEntity entity = getItem(pos);
			if (convertView == null) {
				view = newItemView(null, entity.getDirection());
			} else {
				view = convertView;
			}
			
			bindView(view, entity);
			
			return view;
		}
		
		private void bindView(View view, TComunicationEntity entity) {
			if (view == null || entity == null)
				return;
			
			ViewHolder holder = (ViewHolder)view.getTag();
//			holder.tvContent.setText(entity.getContent());
			String data = entity.getContent();
			data = data.replaceAll("%","%25");//当字符串中包含%时，会出现无法显示的问题，所以要替换成%25
			holder.wvContent.loadData(data, "text/html", null);
		}
		
		private View newItemView(ViewGroup parent, int direction) {
			if (direction == EnumDirection.to) {
				ViewHolder holder = new ViewHolder();
				View view = mInflater.inflate(R.layout.view_communication_item_left, parent);
//				holder.tvContent = (TextView)view.findViewById(R.id.tv_communication_content_left);
				holder.wvContent = (WebView)view.findViewById(R.id.wv_communication_content_left);
				holder.wvContent.getSettings().setDefaultTextEncodingName("utf-8");
				view.setTag(holder);
				return view;
			} else if (direction == EnumDirection.from) {
				ViewHolder holder = new ViewHolder();
				View view = mInflater.inflate(R.layout.view_communication_item_right, parent);
//				holder.tvContent = (TextView)view.findViewById(R.id.tv_communication_content_right);
				holder.wvContent = (WebView)view.findViewById(R.id.wv_communication_content_right);
				holder.wvContent.getSettings().setDefaultTextEncodingName("utf-8");
				view.setTag(holder);
				return view;
			} else {
				T4Log.e("EnumDirection类型错误！");
				return null;
			}		
		}
		
		static class ViewHolder {
//			public TextView tvContent;
			public WebView wvContent;
		}
	}
	
	private static class TaskGetComunication extends
			AsyncTask<String, Void, T4List<TComunicationEntity>> {

		T4Exception mException = null;
		CommunicationActivity mActivity = null;
		int mId;

		public TaskGetComunication(CommunicationActivity activity, int id) {
			mActivity = activity;
			mId = id;
		}

		@Override
		public T4List<TComunicationEntity> doInBackground(String... params) {
			String type = params[0];
			T4List<TComunicationEntity> list = null;
			try {
				list = HttpManager.instance().getComunication(type, mId);
			} catch (T4Exception ex) {
				mException = ex;
			}
			return list;			
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		public void onPostExecute(T4List<TComunicationEntity> list) {
			if (mActivity != null) {
				mActivity.onGetCompaniesComplete(list, mException);
			}
		}
	}
}
