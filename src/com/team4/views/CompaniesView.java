package com.team4.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team4.activities.CompanyActivity;
import com.team4.activities.ComunicationActivity;
import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TCompaniesEntity;
import com.team4.type.TCompanyEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.type.T4List;
import com.team4.utils.util.T4Log;

public class CompaniesView extends ListView {
	
	private static final String RECORD_PERPAGE = "100";
	private static final String PAGE_NUMBER = "1";
	
	private final Context mContext;
	private TCompaniesEntity mCompaniesEntity;
	
	public CompaniesView(final Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		TaskGetCompanies task = new TaskGetCompanies(CompaniesView.this);
		task.execute();
		mContext = context;
		initHeaderView();
		setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				TCompanyEntity entity = (TCompanyEntity)(getAdapter().getItem(pos));
				showDetail(entity);
			}
		});
	}

	private void initHeaderView() {
		View headerView = View.inflate(mContext,
				R.layout.activity_page_1_header, null);
		Button btn = (Button) headerView.findViewById(R.id.btn_search_done);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				TaskGetCompanies task = new TaskGetCompanies(CompaniesView.this);
//				task.execute();
				Toast.makeText(mContext, "查找功能未完成", Toast.LENGTH_SHORT).show();
			}
		});

		btn = (Button) headerView.findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "添加功能未完成", Toast.LENGTH_SHORT).show();
			}
		});
		addHeaderView(headerView);
	}

	private void initDataAdapter() {
		List<String> data = new ArrayList<String>();
		data.add("111111");
		data.add("222222");
		data.add("333333");
		setAdapter(new ArrayAdapter<String>(mContext,
				android.R.layout.simple_expandable_list_item_1, data));
	}

	private void showDetail(TCompanyEntity entity) {
		if (entity == null) {
			Toast.makeText(mContext, "未获取公司详细信息", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent();
		intent.setClass(mContext, CompanyActivity.class);
		intent.putExtra(TCompanyEntity.class.getName(), entity);
		mContext.startActivity(intent);
	}

	private void showContact() {
		Intent intent = new Intent();
		intent.setClass(mContext, ComunicationActivity.class);
		mContext.startActivity(intent);
	}

	public void onGetCompaniesComplete(TCompaniesEntity entity, T4Exception ex) {
		if (ex == null && entity != null) {
			mCompaniesEntity = entity;
			T4List<TCompanyEntity> records = mCompaniesEntity.getRecords();
			if (records != null) {
				setAdapter(new CompanyAdapter(mContext, records));
			}
		} else {
			String message = "Exception Code: " + ex.getExceptionCode()
					+ "\r\n" + "Message: " + ex.getMessage();
			T4Log.v(message);
		}
	}
	
	private static class TaskGetCompanies extends
			AsyncTask<String, Void, TCompaniesEntity> {

		T4Exception mException = null;
		CompaniesView mActivity = null;

		public TaskGetCompanies(CompaniesView activity) {
			mActivity = activity;
		}

		@Override
		public TCompaniesEntity doInBackground(String... params) {
			TCompaniesEntity entity = null;

			try {
				entity = HttpManager.instance().getInfomation(HttpManager.TYPE_COMPANY, RECORD_PERPAGE, PAGE_NUMBER);
			} catch (T4Exception ex) {
				mException = ex;
			}

			return entity;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		public void onPostExecute(TCompaniesEntity entity) {
			if (mActivity != null) {
				mActivity.onGetCompaniesComplete(entity, mException);
			}
		}
	}

	private static class CompanyAdapter extends BaseAdapter {
		
		private T4List<TCompanyEntity> mCompanies;
		private LayoutInflater mInflater;
		
		public CompanyAdapter(Context context, T4List<TCompanyEntity> companies) {
			super();
			mInflater = LayoutInflater.from(context);
			mCompanies = companies;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mCompanies == null)
				return 0;
			return mCompanies.size();
		}

		@Override
		public TCompanyEntity getItem(int pos) {
			// TODO Auto-generated method stub
			if (mCompanies == null || pos > mCompanies.size())
				return null;
			return mCompanies.get(pos);
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
			TCompanyEntity entity = getItem(pos);
			if (convertView == null) {
				view = newItemView(null);
			} else {
				view = convertView;
			}
			
			bindView(view, entity);
			
			return view;
		}
		
		private void bindView(View view, TCompanyEntity entity) {
			if (view == null || entity == null)
				return;
			
			ViewHolder holder = (ViewHolder)view.getTag();
			holder.tvCompanyName.setText(entity.getCompanyName());
		}
		
		private View newItemView(ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			View view = mInflater.inflate(R.layout.view_company_item, parent);
			holder.tvCompanyName = (TextView)view.findViewById(R.id.tv_company_name);
			view.setTag(holder);
			return view;			
		}
		
		public static class ViewHolder {
			public TextView tvCompanyName;
		}
	}
}
