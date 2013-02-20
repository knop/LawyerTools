package com.team4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.team4.lawyertools.R;
import com.team4.type.TCompanyEntity;

public class CompanyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		Intent intent = getIntent();
		TCompanyEntity entity = (TCompanyEntity)intent.getSerializableExtra(TCompanyEntity.class.getName());
		showDetail(entity);
	}
	
	private void showDetail(TCompanyEntity entity) {
		if (entity == null)
			return;
		//公司名称
		TextView tvCompanyTitle = (TextView)findViewById(R.id.tv_company_title);
		tvCompanyTitle.setText(entity.getCompanyName());
		
		//信息来源
		TextView tvSource = (TextView)findViewById(R.id.tv_source);
		tvSource.setText(entity.getSource());
		
		//公司类型
		TextView tvCompanyType = (TextView)findViewById(R.id.tv_company_type);
		tvCompanyType.setText(entity.getCompanyType());
		
		//邮箱
		TextView tvMail = (TextView)findViewById(R.id.tv_mail);
		tvMail.setText(entity.getMail());
		
		//联系人姓名
		TextView tvName = (TextView)findViewById(R.id.tv_name);
		tvName.setText(entity.getContactName());
		
		//电话
		TextView tvPhoneNumber = (TextView)findViewById(R.id.tv_phone_number);
		tvPhoneNumber.setText(entity.getPhoneNumber());
		
		//地址
		TextView tvAddress = (TextView)findViewById(R.id.tv_address);
		tvAddress.setText(entity.getAddress());
		
		//资金来源
		TextView tvCapitalSource = (TextView)findViewById(R.id.tv_capital_source);
		tvCapitalSource.setText(entity.getCapitalSource());
		
		//评级
		TextView tvRating = (TextView)findViewById(R.id.tv_rating);
		tvRating.setText(entity.getRating());
		
		//投资行业
		TextView tvIndustry = (TextView)findViewById(R.id.tv_investment_industry);
		tvIndustry.setText(entity.getInvestmentIndustry());
		
		//投资方式
		TextView tvInvestmentWay = (TextView)findViewById(R.id.tv_investment_way);
		tvInvestmentWay.setText(entity.getInvestmentWay());
		
		//流向区域
		TextView tvFlowArea = (TextView)findViewById(R.id.tv_flow_area);
		tvFlowArea.setText(entity.getFlowArea());
		
		//是否有佣金
		TextView tvHaveCommission = (TextView)findViewById(R.id.tv_have_commission);
		String s = entity.getHaveCommission();
		if (s.equalsIgnoreCase("false")) {
			s = "否";
		} else {
			s = "是";
		}
		tvHaveCommission.setText(s);
		
		//佣金比例
		TextView tvRadio = (TextView)findViewById(R.id.tv_radio);
		tvRadio.setText(entity.getCommissionRatio());
		
		//备注
		TextView tvComments = (TextView)findViewById(R.id.tv_comments);
		tvComments.setText(entity.getComments());
	}
}
