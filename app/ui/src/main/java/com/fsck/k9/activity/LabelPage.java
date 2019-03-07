package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsck.k9.ui.R;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.Account;
import com.fsck.k9.Preferences;

 public class LabelPage extends K9Activity implements View.OnClickListener {

    protected String messageUid;
    protected String serverId;
    protected String labelString;
    protected EditText editText;
    protected Button button;
    protected LabelPageLogic lpl = new LabelPageLogic();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_page);

        lpl.bindEditTextLogic(this);

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

    public void bindEditText() {
        lpl.bindEditTextLogic(this);
    }

     @Override
    public void onClick(View view) {
        lpl.onClickLogic(view, this);
        finish();
    }

 }
    class LabelPageLogic {

        private LabelPage labelPage;

        public void onCreateLogic(Bundle savedInstanceState, LabelPage lp) {

        }

        public void bindEditTextLogic(LabelPage lp) {
            lp.editText = (EditText) lp.findViewById(R.id.label);
            lp.button = (Button) lp.findViewById(R.id.label_button);
            lp.button.setOnClickListener(lp);
        }

        public void onClickLogic(View view, LabelPage lp) {
            int id = view.getId();
            lp.labelString = getLabelString(lp);

            if(lp.labelString.length() > 0) {
                try {
                    setLabel(lp);
                } catch (kotlin.UninitializedPropertyAccessException e) {
                    System.out.println(e);//happens when testing
                }
            }
        }

        public String getLabelString(LabelPage lp) {
            return lp.editText.getText().toString();
        }

        public void setLabel(LabelPage lp) {
            Account account = Preferences.getPreferences(lp).getDefaultAccount();
            MessagingController.getInstance(lp).setLabel(account,lp.serverId,lp.messageUid,lp.labelString);
        }

     }



