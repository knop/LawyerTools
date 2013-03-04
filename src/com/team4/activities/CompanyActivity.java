package com.team4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.team4.common.CommonDef;
import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TCompanyEntity;

public class CompanyActivity extends Activity {

	private TCompanyEntity mCompanyEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		Intent intent = getIntent();
		mCompanyEntity = (TCompanyEntity) intent
				.getSerializableExtra(TCompanyEntity.class.getName());
		showDetail(mCompanyEntity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		MenuInflater inflater = new MenuInflater(this);  
		inflater.inflate(R.menu.communication_menu, menu);
		menu.removeItem(R.id.match);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override  
    public boolean onOptionsItemSelected(MenuItem item) { 
		switch(item.getItemId()){
		case R.id.communication:
			showCommunicationPage();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDetail(final TCompanyEntity entity) {
		if (entity == null)
			return;

		// 公司名称
		TextView tvCompanyTitle = (TextView) findViewById(R.id.tv_company_title);
		tvCompanyTitle.setText(entity.getName());

		// 信息来源
		TextView tvSource = (TextView) findViewById(R.id.tv_source);
		tvSource.setText(entity.getSource());

		// 公司类型
		TextView tvCompanyType = (TextView) findViewById(R.id.tv_company_type);
		tvCompanyType.setText(entity.getCompanyType());

		// 邮箱
		TextView tvMail = (TextView) findViewById(R.id.tv_mail);
		tvMail.setText(entity.getMail());

		// 联系人姓名
		TextView tvName = (TextView) findViewById(R.id.tv_name);
		tvName.setText(entity.getContactName());

		// 电话
		TextView tvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
		tvPhoneNumber.setText(entity.getPhoneNumber());

		// 地址
		TextView tvAddress = (TextView) findViewById(R.id.tv_address);
		tvAddress.setText(entity.getAddress());

		// 资金来源
		TextView tvCapitalSource = (TextView) findViewById(R.id.tv_capital_source);
		tvCapitalSource.setText(entity.getCapitalSource());

		// 评级
		TextView tvRating = (TextView) findViewById(R.id.tv_rating);
		tvRating.setText(entity.getRating());

		// 投资行业
		TextView tvIndustry = (TextView) findViewById(R.id.tv_investment_industry);
		tvIndustry.setText(entity.getInvestmentIndustry());

		// 投资方式
		TextView tvInvestmentWay = (TextView) findViewById(R.id.tv_investment_way);
		tvInvestmentWay.setText(entity.getInvestmentWay());

		// 流向区域
		TextView tvFlowArea = (TextView) findViewById(R.id.tv_flow_area);
		tvFlowArea.setText(entity.getFlowArea());

		// 是否有佣金
		TextView tvHaveCommission = (TextView) findViewById(R.id.tv_have_commission);
		tvHaveCommission.setText(entity.getHaveCommission()?R.string.yes:R.string.no);

		// 佣金比例
		TextView tvRadio = (TextView) findViewById(R.id.tv_radio);
		tvRadio.setText(entity.getCommissionRatio());

		// 备注
		String data = entity.getComments();
		if (data == null || data.length() <= 0)
			data = this.getResources().getString(R.string.text_none);
//		else
//			data = data.replaceAll("%","%25");//当字符串中包含%时，会出现无法显示的问题，所以要替换成%25
		WebView wvComments = (WebView) findViewById(R.id.wv_comments);
		wvComments.getSettings().setDefaultTextEncodingName(CommonDef.ENCODING);
//		wvComments.loadData(data, CommonDef.MINE_TYPE, null);
		wvComments.loadDataWithBaseURL("", data, CommonDef.MINE_TYPE, CommonDef.ENCODING, "");
	}

	private void showCommunicationPage() {
		if (mCompanyEntity == null)
			return;
		Intent intent = new Intent();
		intent.setClass(this, CommunicationActivity.class);
		intent.putExtra(CommunicationActivity.EXTRA_KEY_ID, mCompanyEntity.getId());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TITLE, mCompanyEntity.getName());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TYPE, HttpManager.TYPE_COMPANY);
		startActivity(intent);
	}
}
