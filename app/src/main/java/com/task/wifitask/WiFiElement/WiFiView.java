package com.task.wifitask.WiFiElement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.task.wifitask.R;
import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.Factories.DialogFactory;

public class WiFiView extends ConstraintLayout implements WiFiContract.View{

    private ImageView icon_;

    private TextView name_;

    private TextView password_;

    private AppCompatButton info_;

    private AppCompatButton brut_;

    private AppCompatButton connect_;

    private Context context_;

    int countView_ = 5;

    private WiFiInfo wiFiInfo_;

    private WiFiContract.Presenter<WiFiInfo> presenter_;

    private ProgressDialog progressDialog_;

    public WiFiView(Context context) {
        super(context);
        initView(context);
    }

    public WiFiView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public WiFiView(Context context, AttributeSet attributeSet, int int1) {
        super(context, attributeSet, int1);
        initView(context);
    }

    private void ChangeSizeView(View view, int height, int width) {
        ViewGroup.LayoutParams layouts = view.getLayoutParams();
        layouts.width = width;
        layouts.height = height;
        view.setLayoutParams(layouts);
    }

    private void ChangeSizeWithDisplay() {
        int height = (Resources.getSystem().getDisplayMetrics()).widthPixels / countView_;
        int width = height / 20;
        ChangeSizeView(icon_, height, height);
        ChangeSizeView(name_, height / 2, height);
        name_.setTextSize(width);
        ChangeSizeView(password_, height / 2, height);
        password_.setTextSize(width);
        ChangeSizeView(info_, height, height);
        info_.setTextSize(width);
        ChangeSizeView(brut_, height, height);
        brut_.setTextSize(width);
        ChangeSizeView(connect_, height, height);
        connect_.setTextSize(width);
    }


    private void initView(Context context) {
        context_ = context;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(R.layout.wifi_view, this, true);
        Log.e("YES", "YES");

        icon_ = findViewById(R.id.icon);
        name_ = findViewById(R.id.name);
        password_ = findViewById(R.id.password);
        info_ = findViewById(R.id.info);
        brut_ = findViewById(R.id.brut);
        connect_ = findViewById(R.id.connect);

        presenter_ = new WiFiPresenter(new WiFiModel(),this,context_);
    }

    public void ChangeElementWithWifiInfo(WiFiInfo wiFiInfo) {
        ChangeSizeWithDisplay();
        name_.setText(wiFiInfo.getSSID());
        switch (wiFiInfo.getLevel()) {
            default:
                icon_.setImageResource(R.drawable.wifi_nope);
                break;
            case 5:
                icon_.setImageResource(R.drawable.wifi_five);
                break;
            case 4:
                icon_.setImageResource(R.drawable.wifi_four);
                break;
            case 3:
                icon_.setImageResource(R.drawable.wifi_three);
                break;
            case 2:
                icon_.setImageResource(R.drawable.wifi_two);
                break;
            case 1:
                icon_.setImageResource(R.drawable.wifi_one);
                break;
            case 0:
                icon_.setImageResource(R.drawable.wifi_zero);
                break;
        }
        password_.setText(wiFiInfo_.getPassword());

        info_.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = info() ?
                        DialogFactory.createDialog(wiFiInfo_.toString(), context_) :
                        DialogFactory.createDialog(DialogFactory.DialogType.EMPTY, context_);
                dialog.show();
            }
        });

        brut_.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                brut();
            }
        });

        connect_.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = connect() ?
                        DialogFactory.createDialog(DialogFactory.DialogType.CONNECT, context_) :
                        DialogFactory.createDialog(DialogFactory.DialogType.NO_CONNECT, context_);
                dialog.show();
            }
        });
    }

    public AppCompatButton getBrut() {
        return brut_;
    }

    public ImageView getIcon() {
        return icon_;
    }

    public AppCompatButton getInfo() {
        return info_;
    }

    public TextView getName() {
        return name_;
    }

    public TextView getPassword() {
        return password_;
    }

    public WiFiInfo getWiFiInfo() {
        return wiFiInfo_;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setBrut(AppCompatButton AppCompatButton) {
        brut_ = AppCompatButton;
    }

    public void setIcon(ImageView ImageView) {
        icon_ = ImageView;
    }

    public void setInfo(AppCompatButton AppCompatButton) {
        info_ = AppCompatButton;
    }

    public void setName(TextView TextView) {
        name_ = TextView;
    }

    public void setPassword(TextView TextView) {
        password_ = TextView;
    }

    public void setWiFiInfo(WiFiInfo WiFiInfo) {
        wiFiInfo_ = WiFiInfo;
        ChangeElementWithWifiInfo(WiFiInfo);
    }

    public ProgressDialog getProgressDialog(){
        return progressDialog_;
    }

    public void setProgressDialog(ProgressDialog progressDialog){
        progressDialog_ = progressDialog;
    }

    @Override
    public void updatePassword(String password) {
        password_.setText(password);
    }

    @Override
    public void updateProgressDialog(String title) {
        progressDialog_.setProgress(progressDialog_.getProgress()+1);
        progressDialog_.setTitle(title);
    }

    @Override
    public boolean info() {
        return wiFiInfo_!=null && !wiFiInfo_.toString().equals("");
    }

    @Override
    public boolean connect() {
        EditText editText = new EditText(context_);
        editText.setText(password_.getText());

        final boolean[] connect = {false};

        Dialog dialog = new AlertDialog.Builder(context_)
                .setCancelable(false)
                .setPositiveButton("Подключиться",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        connect[0] = presenter_.connect(wiFiInfo_, String.valueOf(editText.getText()));
                        if(connect[0])
                            updatePassword(String.valueOf(editText.getText()));
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setView(editText)
                .setMessage("Введите пароль")
                .create();

        dialog.show();
        return connect[0];
    }

    @Override
    public void brut() {

        AlertDialog alertDialog = new AlertDialog.Builder(context_)
                .setCancelable(false)
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Брут", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressDialog_ = makeProgressDialog(context_);
                        progressDialog_.setMax(14443711);
                        progressDialog_.setProgress(0);
                        progressDialog_.show();
                        presenter_.brut(wiFiInfo_);

                    }
                })
                .setMessage(context_.getResources().getString(R.string.alert_text))
                .create();

    }

    private ProgressDialog makeProgressDialog(Context context){
        ProgressDialog progressDialog = (ProgressDialog) new ProgressDialog.Builder(context)
                .setCancelable(false)
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        presenter_.cancelBrut();
                    }
                })
                .create();

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return progressDialog;
    }

}

