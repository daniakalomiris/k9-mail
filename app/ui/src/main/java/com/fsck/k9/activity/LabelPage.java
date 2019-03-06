	package com.fsck.k9.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fsck.k9.ui.R;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.mailstore.LocalStoreProvider;

 public class LabelPage extends K9Activity implements View.OnClickListener {

    private String messageUid;
    private String serverId;
    private Account account = Preferences.getPreferences(this).getDefaultAccount();
    private MessagingController controller = MessagingController.getInstance(getApplication());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_page);
        setButtonListeners();
	
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        //get the UID
        if(b!=null)
        {
            try{
                this.messageUid = b.getString("messageUid");
                this.serverId = b.getString("serverId"); 
            }
            catch(NumberFormatException e){
            }
        }
    }

     private void setButtonListeners() {
        Button mLabelButtonWork = (Button)findViewById(R.id.label_work_tag);
        mLabelButtonWork.setOnClickListener(this);
        Button mLabelSchoolWork = (Button)findViewById(R.id.label_school_tag);
        mLabelSchoolWork.setOnClickListener(this);
        Button mLabelPersonalWork = (Button)findViewById(R.id.label_personal_tag);
        mLabelPersonalWork.setOnClickListener(this);
    }

     @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.label_work_tag) {
            MessagingController.getInstance(this).setLabel(this.account,this.serverId,this.messageUid,"work");
        } else if(id == R.id.label_school_tag) {
            MessagingController.getInstance(this).setLabel(this.account,this.serverId,this.messageUid,"school");
        } else if(id == R.id.label_personal_tag) {
            MessagingController.getInstance(this).setLabel(this.account,this.serverId,this.messageUid,"rock&roll");
        }
        finish();
    }
}