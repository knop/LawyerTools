package com.team4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team4.http.HttpManager;
import com.team4.lawyertools.R;
import com.team4.type.TCaseEntity;

public class CaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case);
		Intent intent = getIntent();
		TCaseEntity entity = (TCaseEntity) intent
				.getSerializableExtra(TCaseEntity.class.getName());
		showDetail(entity);
	}

	private void showDetail(final TCaseEntity entity) {
		if (entity == null)
			return;

		Button btnComunication = (Button) findViewById(R.id.btn_case_communication);
		btnComunication.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CaseActivity.this, CommunicationActivity.class);
				intent.putExtra(CommunicationActivity.EXTRA_KEY_ID, entity.getId());
				intent.putExtra(CommunicationActivity.EXTRA_KEY_TITLE, entity.getName());
				intent.putExtra(CommunicationActivity.EXTRA_KEY_TYPE, HttpManager.TYPE_CASE);
				startActivity(intent);
			}
		});
		
		//标题
		TextView tvTitle = (TextView) findViewById(R.id.tv_case_title);
		tvTitle.setText(entity.getName()); 
		
		//案件类型
		TextView tvType = (TextView) findViewById(R.id.tv_case_type);
		tvType.setText(entity.getType());
		
		//收案日期
		TextView tvReceiveAt = (TextView) findViewById(R.id.tv_case_receive_at);
		tvReceiveAt.setText(entity.getReceiveAt());
		
		//审级
		TextView tvLevel = (TextView) findViewById(R.id.tv_case_level);
		tvLevel.setText(entity.getLevel());
		
		//委托人
		TextView tvClient = (TextView) findViewById(R.id.tv_case_client);
		tvClient.setText(entity.getClientName());
		
		//委托人电话
		TextView tvClientPhone = (TextView) findViewById(R.id.tv_case_client_phone);
		tvClientPhone.setText(entity.getClientPhone());
		
		//委托人通讯
		TextView tvClientAddress = (TextView) findViewById(R.id.tv_case_client_address);
		tvClientAddress.setText(entity.getClientAddress());
		
		//对方当事人
		TextView tvOppoClient = (TextView) findViewById(R.id.tv_case_oppo_client);
		tvOppoClient.setText(entity.getOppositeClientName());
		
		//对方当事人电话
		TextView tvOppoClientPhone = (TextView) findViewById(R.id.tv_case_oppo_client_phone);
		tvOppoClientPhone.setText(entity.getOppositeClientPhone());
		
		//对方当事人通讯
		TextView tvOppoClientAddress = (TextView) findViewById(R.id.tv_case_oppo_client_address);
		tvOppoClientAddress.setText(entity.getOppositeClientAddress());
		
		//对方代理人
		TextView tvOppoLawyer = (TextView) findViewById(R.id.tv_case_oppo_lawyer);
		tvOppoLawyer.setText(entity.getOppositeLawyerName());
		
		//对方代理人电话
		TextView tvOppoLawyerPhone = (TextView) findViewById(R.id.tv_case_oppo_lawyer_phone);
		tvOppoLawyerPhone.setText(entity.getOppositeLawyerPhone());
		
		//对方代理人通讯
		TextView tvOppoLawyerAddress = (TextView) findViewById(R.id.tv_case_oppo_lawyer_address);
		tvOppoLawyerAddress.setText(entity.getOppositeLawyerAddress());
		
		//案由
		TextView tvCause = (TextView) findViewById(R.id.tv_case_cause);
		tvCause.setText(entity.getCause());
		
		//法院案号
		TextView tvCourtNo = (TextView) findViewById(R.id.tv_case_court_no);
		tvCourtNo.setText(entity.getCourtCaseNo());
		
		//受理法院
		TextView tvCourtName = (TextView) findViewById(R.id.tv_case_court_name);
		tvCourtName.setText(entity.getCourtName());
		
		//法院通讯地址
		TextView tvCourtAddress = (TextView) findViewById(R.id.tv_case_court_address);
		tvCourtAddress.setText(entity.getCourtAddress());
		
		//受理日期
		TextView tvAcceptAt = (TextView) findViewById(R.id.tv_case_accept_at);
		tvAcceptAt.setText(entity.getAcceptAt());
		
		//收到应诉通知书日期
		TextView tvReceiveNoticeAt = (TextView) findViewById(R.id.tv_case_receive_notice_at);
		tvReceiveNoticeAt.setText(entity.getReceiveNoticeAt());
		
		//主审法官
		TextView tvJugerName = (TextView) findViewById(R.id.tv_case_juger_name);
		tvJugerName.setText(entity.getJugerName());
		
		//主审法官电话
		TextView tvJugerPhone = (TextView) findViewById(R.id.tv_case_juger_phone);
		tvJugerPhone.setText(entity.getJugerPhone());
		
		//书记员
		TextView tvClerkName = (TextView) findViewById(R.id.tv_case_clerk_name);
		tvClerkName.setText(entity.getClerkName());
		
		//书记员电话
		TextView tvClerkPhone = (TextView) findViewById(R.id.tv_case_clerk_phone);
		tvClerkPhone.setText(entity.getClerkPhone());
		
		//举证期限
		TextView tvProofLimit = (TextView) findViewById(R.id.tv_case_proof_limit);
		tvProofLimit.setText(entity.getProofLimit());
		
		//答辩期限
		TextView tvReplyLimit = (TextView) findViewById(R.id.tv_case_reply_limit);
		tvReplyLimit.setText(entity.getReplyLimit());
		
		//开庭日期
		TextView tvOpenAt = (TextView) findViewById(R.id.tv_case_open_at);
		tvOpenAt.setText(entity.getOpenAt());
		
		//判决日期
		TextView tvJudgeAt = (TextView) findViewById(R.id.tv_case_judge_at);
		tvJudgeAt.setText(entity.getJudgeAt());
		
		//判决书签收日期
		TextView tvReceiveJudgeAt = (TextView) findViewById(R.id.tv_case_receive_judge_at);
		tvReceiveJudgeAt.setText(entity.getReceiveJudgeAt());
		
		//上诉期截止日期
		TextView tvAppealLimit = (TextView) findViewById(R.id.tv_case_appeal_limit);
		tvAppealLimit.setText(entity.getAppealLimit());
		
		//创建时间
		TextView tvCreateAt = (TextView) findViewById(R.id.tv_case_create_at);
		tvCreateAt.setText(entity.getCreateAt());
		
		//最后修改时间
		TextView tvModifyAt = (TextView) findViewById(R.id.tv_case_modify_at);
		tvModifyAt.setText(entity.getModifyAt());
		
		//委托人签收
		TextView tvClientReceive = (TextView) findViewById(R.id.tv_case_client_receive);
		tvClientReceive.setText(entity.getClientReceived()?R.string.yes:R.string.no);
	}
}
