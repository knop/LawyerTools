package com.team4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TInvestmentEntity;

public class InvestmentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investment);
		Intent intent = getIntent();
		TInvestmentEntity entity = (TInvestmentEntity) intent
				.getSerializableExtra(TInvestmentEntity.class.getName());
		showDetail(entity);
	}

	private void showDetail(final TInvestmentEntity entity) {
		if (entity == null)
			return;

		Button btnComunication = (Button) findViewById(R.id.btn_investment_comunication);
		btnComunication.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(InvestmentActivity.this, ComunicationActivity.class);
				intent.putExtra(ComunicationActivity.EXTRA_KEY_ID, entity.getId());
				intent.putExtra(ComunicationActivity.EXTRA_KEY_TITLE, entity.getName());
				intent.putExtra(ComunicationActivity.EXTRA_KEY_TYPE, HttpManager.TYPE_INVESTMENT);
				startActivity(intent);
			}
		});

		// 公司名称
		TextView tvTitle = (TextView) findViewById(R.id.tv_investment_title);
		tvTitle.setText(entity.getName());

		// 信息来源
		TextView tvInfoSource = (TextView) findViewById(R.id.tv_investment_info_source);
		tvInfoSource.setText(entity.getInfoSource());

		// 公司类型
		TextView tvCompanyType = (TextView) findViewById(R.id.tv_investment_company_type);
		tvCompanyType.setText(entity.getCompanyType());

		// 邮箱
		TextView tvEmail = (TextView) findViewById(R.id.tv_investment_email);
		tvEmail.setText(entity.getEmail());

		// 联系人姓名
		TextView tvContactPerson = (TextView) findViewById(R.id.tv_investment_contact_person);
		tvContactPerson.setText(entity.getContactPerson());

		// 电话
		TextView tvTelephone = (TextView) findViewById(R.id.tv_investment_telephone);
		tvTelephone.setText(entity.getTelePhone());

		// 审批周期
		TextView tvExamineCycle = (TextView) findViewById(R.id.tv_investment_examine);
		String examineCycle = entity.getExamineCycleFloor() + "-" + entity.getExamineCycleUpper() + "个月";
		tvExamineCycle.setText(examineCycle);
		
		// 地址
//		TextView tvAddress = (TextView) findViewById(R.id.tv_investment_address);
//		tvAddress.setText(entity.getAddress());

		// 资金来源
		TextView tvCapitalSource = (TextView) findViewById(R.id.tv_investment_capital_source);
		tvCapitalSource.setText(entity.getCapitalSource());

		// 评级
		TextView tvRate = (TextView) findViewById(R.id.tv_investment_rate);
		tvRate.setText(entity.getRate());

		// 投资行业
		TextView tvIndustry = (TextView) findViewById(R.id.tv_investment_industry);
		tvIndustry.setText(entity.getInvestmentIndustry());

		// 投资方式
		TextView tvInvestmentStyle = (TextView) findViewById(R.id.tv_investment_style);
		tvInvestmentStyle.setText(entity.getStyle());

		// 投资额度
		TextView tvQuota = (TextView) findViewById(R.id.tv_investment_quota);
		tvQuota.setText(entity.getQuotaFloor() + "-" + entity.getQuotaUpper() + "千万");
		
		// 投资收益
		TextView tvProfit = (TextView) findViewById(R.id.tv_investment_profit);
		tvProfit.setText(entity.getProfitFloor() + "-" + entity.getProfitUpper() + "%");
		
		// 投资期限
		TextView tvHorizon = (TextView) findViewById(R.id.tv_investment_horizon);
		tvHorizon.setText(entity.getHorizonFloor() + "-" + entity.getHorizonFloor() + "个月");
		
		// 前期费用
		TextView tvPriorCost = (TextView) findViewById(R.id.tv_investment_prior_cost);
		tvPriorCost.setText(entity.getHasPriorCost()?"":entity.getPriorCost());
		
		// 担保方式
		TextView tvGuaranteeType = (TextView) findViewById(R.id.tv_investment_guarantee_type);
		tvGuaranteeType.setText(entity.getGuaranteeType());
		
		// 还款方式
		TextView tvPayment = (TextView) findViewById(R.id.tv_investment_payment);
		tvPayment.setText(entity.getPayment());
		
		// 投资用途
		TextView tvPurpose = (TextView) findViewById(R.id.tv_investment_purpose);
		tvPurpose.setText(entity.getPurpose());
		
		// 流向区域
		TextView tvAreas = (TextView) findViewById(R.id.tv_investment_areas);
		tvAreas.setText(entity.getAreas());

		// 是否有佣金
		TextView tvHasCommission = (TextView) findViewById(R.id.tv_investment_has_commission);
		if (entity.getHasCommission()) {
			tvHasCommission.setText("否");
		} else {
			tvHasCommission.setText("是");
		}

		// 佣金比例
		TextView tvCommissionProportion = (TextView) findViewById(R.id.tv_investment_commission_proportion);
		tvCommissionProportion.setText(entity.getCommissionProportion());

		// 备注
		TextView tvComment = (TextView) findViewById(R.id.tv_investment_comment);
		tvComment.setText(entity.getComment());
	}
}
