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
import com.team4.type.TInvestmentEntity;

public class InvestmentActivity extends Activity {

	private TInvestmentEntity mInvestmentEntity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investment);
		Intent intent = getIntent();
		mInvestmentEntity = (TInvestmentEntity) intent
				.getSerializableExtra(TInvestmentEntity.class.getName());
		showDetail(mInvestmentEntity);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		MenuInflater inflater = new MenuInflater(this);  
		inflater.inflate(R.menu.communication_menu, menu);  
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override  
    public boolean onOptionsItemSelected(MenuItem item) { 
		switch(item.getItemId()){
		case R.id.communication:
			showCommunicationPage();
			break;
		case R.id.match:
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDetail(final TInvestmentEntity entity) {
		if (entity == null)
			return;

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
		tvHasCommission.setText(entity.getHasCommission()?R.string.yes:R.string.no);

		// 佣金比例
		TextView tvCommissionProportion = (TextView) findViewById(R.id.tv_investment_commission_proportion);
		tvCommissionProportion.setText(entity.getCommissionProportion());

		// 备注
//		TextView tvComment = (TextView) findViewById(R.id.tv_investment_comment);
//		tvComment.setText(entity.getComment());
		String data = entity.getComment();
		if (data == null || data.length() <= 0)
			data = this.getResources().getString(R.string.text_none);
		else
			data = data.replaceAll("%","%25");//当字符串中包含%时，会出现无法显示的问题，所以要替换成%25
		WebView wvComment = (WebView) findViewById(R.id.wv_investment_comment);
		wvComment.getSettings().setDefaultTextEncodingName(CommonDef.ENCODING);
		wvComment.loadData(data, CommonDef.MINE_TYPE, null);
	}
	
	private void showCommunicationPage() {
		if (mInvestmentEntity == null)
			return;
		Intent intent = new Intent();
		intent.setClass(this, CommunicationActivity.class);
		intent.putExtra(CommunicationActivity.EXTRA_KEY_ID, mInvestmentEntity.getId());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TITLE, mInvestmentEntity.getName());
		intent.putExtra(CommunicationActivity.EXTRA_KEY_TYPE, HttpManager.TYPE_INVESTMENT);
		startActivity(intent);
	}
}
