package com.team4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TCompanyEntity;

public class CompanyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		Intent intent = getIntent();
		TCompanyEntity entity = (TCompanyEntity) intent
				.getSerializableExtra(TCompanyEntity.class.getName());
		showDetail(entity);
	}

	private void showDetail(final TCompanyEntity entity) {
		if (entity == null)
			return;

		Button btnComunication = (Button) findViewById(R.id.btn_company_communication);
		btnComunication.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CompanyActivity.this, CommunicationActivity.class);
				intent.putExtra(CommunicationActivity.EXTRA_KEY_ID, entity.getId());
				intent.putExtra(CommunicationActivity.EXTRA_KEY_TITLE, entity.getName());
				intent.putExtra(CommunicationActivity.EXTRA_KEY_TYPE, HttpManager.TYPE_COMPANY);
				startActivity(intent);
			}
		});

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
		else
			data = data.replaceAll("%","%25");//当字符串中包含%时，会出现无法显示的问题，所以要替换成%25
		WebView wvComments = (WebView) findViewById(R.id.wv_comments);
		wvComments.getSettings().setDefaultTextEncodingName("utf-8");
		wvComments.loadData(data, "text/html", null);
	}
}
