package com.team4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TFinancingEntity;

public class FinancingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_financing);
		Intent intent = getIntent();
		TFinancingEntity entity = (TFinancingEntity) intent
				.getSerializableExtra(TFinancingEntity.class.getName());
		showDetail(entity);
	}

	private void showDetail(final TFinancingEntity entity) {
		if (entity == null)
			return;

		Button btnComunication = (Button) findViewById(R.id.btn_financing_communication);
		btnComunication.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(FinancingActivity.this, CommunicationActivity.class);
				intent.putExtra(CommunicationActivity.EXTRA_KEY_ID, entity.getId());
				intent.putExtra(CommunicationActivity.EXTRA_KEY_TITLE, entity.getName());
				intent.putExtra(CommunicationActivity.EXTRA_KEY_TYPE, HttpManager.TYPE_FINANCING);
				startActivity(intent);
			}
		});

		// 公司名称
		TextView tvTitle = (TextView) findViewById(R.id.tv_financing_title);
		tvTitle.setText(entity.getName());

		// 信息来源
		TextView tvInfoSource = (TextView) findViewById(R.id.tv_financing_info_source);
		tvInfoSource.setText(entity.getInfoSource());

		// 公司类型
		TextView tvCompanyType = (TextView) findViewById(R.id.tv_financing_company_type);
		tvCompanyType.setText(entity.getCompanyType());

		// 邮箱
		TextView tvEmail = (TextView) findViewById(R.id.tv_financing_email);
		tvEmail.setText(entity.getEmail());

		// 联系人姓名
		TextView tvContactPerson = (TextView) findViewById(R.id.tv_financing_contact_person);
		tvContactPerson.setText(entity.getContactPerson());

		// 电话
		TextView tvTelephone = (TextView) findViewById(R.id.tv_financing_telephone);
		tvTelephone.setText(entity.getTelePhone());

		// 评级
		TextView tvRate = (TextView) findViewById(R.id.tv_financing_rate);
		tvRate.setText(entity.getRate());

		// 融资行业
		TextView tvIndustry = (TextView) findViewById(R.id.tv_financing_industry);
		tvIndustry.setText(entity.getIndustry());

		// 融资方式
		TextView tvStyle = (TextView) findViewById(R.id.tv_financing_style);
		tvStyle.setText(entity.getStyle());

		// 融资额度
		TextView tvQuota = (TextView) findViewById(R.id.tv_financing_quota);
		tvQuota.setText(entity.getQuotaFloor() + "-" + entity.getQuotaUpper() + "千万");
		
		// 融资期限
		TextView tvHorizon = (TextView) findViewById(R.id.tv_financing_horizon);
		tvHorizon.setText(entity.getHorizonFloor() + "-" + entity.getHorizonFloor() + "个月");
		
		// 融资成本
		TextView tvCost = (TextView) findViewById(R.id.tv_financing_cost);
		tvCost.setText("");
		
		// 担保方式
		TextView tvGuaranteeType = (TextView) findViewById(R.id.tv_financing_guarantee_type);
		tvGuaranteeType.setText(entity.getGuaranteeType());
		
		// 还款方式
		TextView tvPayment = (TextView) findViewById(R.id.tv_financing_payment);
		tvPayment.setText(entity.getPayment());
		
		// 项目所在地
		TextView tvArea = (TextView) findViewById(R.id.tv_financing_area);
		tvArea.setText(entity.getArea());
		
		// 是否有佣金
		TextView tvHasCommission = (TextView) findViewById(R.id.tv_financing_has_commission);
		if (entity.getHasCommission()) {
			tvHasCommission.setText("否");
		} else {
			tvHasCommission.setText("是");
		}

		// 佣金比例
		TextView tvCommissionProportion = (TextView) findViewById(R.id.tv_financing_commission_proportion);
		tvCommissionProportion.setText(entity.getCommissionProportion());

		// 备注
		TextView tvComment = (TextView) findViewById(R.id.tv_financing_comment);
		tvComment.setText(entity.getComment());
	}
}
