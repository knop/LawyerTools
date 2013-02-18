package com.team4.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.team4.activities.CompanyActivity;
import com.team4.activities.ComunicationActivity;
import com.team4.activities.MainActivity;
import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TCompaniesEntity;
import com.team4.utils.exceptions.T4Exception;
import com.team4.utils.util.T4Log;

public class CompaniesView extends ListView {

	public CompaniesView(final Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		initHeaderView(context);
		initDataAdapter(context);
		setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View v, int pos,
					long id) {
				// TODO Auto-generated method stub
				showDetail(context, pos);
			}
		});
	}

	private void initHeaderView(final Context context) {
		View headerView = View.inflate(context,
				R.layout.activity_page_1_header, null);
		Button btn = (Button) headerView.findViewById(R.id.btn_search_done);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaskGetCompanies task = new TaskGetCompanies(CompaniesView.this);
				task.execute();
				Toast.makeText(context, "查找功能未完成", Toast.LENGTH_SHORT).show();
			}
		});

		btn = (Button) headerView.findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "添加功能未完成", Toast.LENGTH_SHORT).show();
			}
		});
		addHeaderView(headerView);
	}

	private void initDataAdapter(final Context context) {
		List<String> data = new ArrayList<String>();
		data.add("111111");
		data.add("222222");
		data.add("333333");
		setAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_expandable_list_item_1, data));
	}

	private void showDetail(final Context context, int pos) {
		Intent intent = new Intent();
		intent.setClass(context, CompanyActivity.class);
		context.startActivity(intent);
	}

	private void showContact(final Context context) {
		Intent intent = new Intent();
		intent.setClass(context, ComunicationActivity.class);
		context.startActivity(intent);
	}

	public void onGetCompaniesComplete(TCompaniesEntity entity, T4Exception ex) {
		if (ex == null) {
//			mListView.setAdapter(new FileListAdapter(this, fileList));
			;
		} else {
			String message = "Exception Code: " + ex.getExceptionCode()
					+ "\r\n" + "Message: " + ex.getMessage();
			T4Log.v(message);
//			Toast.makeText(this, message, 1000);
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
				entity = HttpManager.instance().getCompanies("10", "1");
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
}
