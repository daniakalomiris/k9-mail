package com.fsck.k9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fsck.k9.ui.R;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.Account;
import com.fsck.k9.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LabelPage extends K9Activity implements View.OnClickListener {

    protected String messageUid;
    protected String serverId;
    protected String labelString;
    protected EditText editText;
    protected ListView listView;
    protected Button button;
    protected LabelPageLogic lpl = new LabelPageLogic();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_page);
        Account account = null;
        HashMap<String, Integer> labels = null;

        try {
            account = Preferences.getPreferences(this).getDefaultAccount();
            labels = MessagingController.getInstance(this).getLabelList(account);
        } catch (kotlin.UninitializedPropertyAccessException e) {
            System.out.println(e);
        }


        lpl.bindEditTextLogic(this);
        lpl.getExistingLabels(this, labels);
        lpl.onExistingLabelClick(this);

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

    public LabelPageLogic getLabelLogic() {
        return lpl;
    }

 }
    class LabelPageLogic {

        private LabelPage labelPage;


        public void bindEditTextLogic(LabelPage lp) {
            lp.editText = (EditText) lp.findViewById(R.id.label);
            lp.button = (Button) lp.findViewById(R.id.label_button);
            lp.listView = (ListView) lp.findViewById(R.id.existing_labels);
            lp.button.setOnClickListener(lp);
        }

        public void onClickLogic(View view, LabelPage lp) {
            lp.labelString = getLabelString(lp);
            if(lp.labelString.length() > 0) {
                try {
                    setLabel(lp);
                } catch (kotlin.UninitializedPropertyAccessException e) {
                    System.out.println(e);//happens when testing
                }
            }
        }

        public void getExistingLabels(LabelPage lp, HashMap<String, Integer> labels) {
            if(labels != null && labels.size() > 0) {
                final ArrayList<String> labelKeys = new ArrayList<>();
                labelKeys.addAll(labels.keySet());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                        (lp, android.R.layout.simple_list_item_1, labelKeys);
                lp.listView.setAdapter(arrayAdapter);
            }
        }

        public String getLabelString(LabelPage lp) {
            return lp.editText.getText().toString();
        }

        public void setLabel(LabelPage lp) {
            Account account = Preferences.getPreferences(lp).getDefaultAccount();
            MessagingController.getInstance(lp).setLabel(account,lp.serverId,lp.messageUid,lp.labelString);
        }

        public void onExistingLabelClick(final LabelPage lp) {

            lp.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String label = (String) adapterView.getItemAtPosition(i);
                    lp.editText.setText(label);
                    try {
                        setLabel(lp);
                    } catch (kotlin.UninitializedPropertyAccessException e) {
                        System.out.println(e);//happens when testing
                    }
                }
            });
        }
     }



