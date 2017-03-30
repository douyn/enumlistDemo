package com.qiwo.enumlistdemo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatePickerDialogDemoActivity extends AppCompatActivity {

    @Bind(R.id.datepicker)
    Button datepicker;
    @Bind(R.id.timepicker)
    Button timepicker;
    @Bind(R.id.loadingdialog)
    Button loadingdialog;
    @Bind(R.id.singlepicker)
    Button singlepicker;
    @Bind(R.id.multpicker)
    Button multpicker;
    @Bind(R.id.popuppicker)
    Button popuppicker;
    @Bind(R.id.rl_parent)
    RelativeLayout rl_parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_dialog_demo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.datepicker, R.id.timepicker, R.id.loadingdialog, R.id.singlepicker, R.id.multpicker, R.id.popuppicker})
    public void onClick(View view) {
        Calendar ca = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.datepicker:
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Toast.makeText(DatePickerDialogDemoActivity.this, "" + i + i1 + i2, Toast.LENGTH_SHORT).show();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(DatePickerDialogDemoActivity.this,
                        listener, ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.timepicker:
                TimePickerDialog.OnTimeSetListener listener_timepicker = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Toast.makeText(DatePickerDialogDemoActivity.this, "" + i + i1, Toast.LENGTH_SHORT).show();
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(DatePickerDialogDemoActivity.this,
                        listener_timepicker, ca.get(Calendar.HOUR_OF_DAY), ca.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                break;
            case R.id.loadingdialog:
                ProgressDialog loadingDialog = new ProgressDialog(DatePickerDialogDemoActivity.this);
                loadingDialog.setTitle("正在加载");
                loadingDialog.setMessage("加载中...");
                loadingDialog.setCanceledOnTouchOutside(false);
                loadingDialog.show();
                break;
            case R.id.singlepicker:
                final AlertDialog.Builder builder = new AlertDialog.Builder(DatePickerDialogDemoActivity.this);
                builder.setTitle("请选择:");
                final String[] arrays = {"孙悟空", "猪八戒", "沙和尚", "白龙马"};
                builder.setSingleChoiceItems(arrays, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Toast.makeText(DatePickerDialogDemoActivity.this, arrays[i], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                break;
            case R.id.multpicker:
                final AlertDialog.Builder builder2 = new AlertDialog.Builder(DatePickerDialogDemoActivity.this);
                builder2.setTitle("请选择:");
                String[] arrays2 = {"孙悟空", "猪八戒", "沙和尚", "白龙马"};
                builder2.setMultiChoiceItems(arrays2, new boolean[]{false, false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    }
                });
                builder2.setPositiveButton("确定", null);
                builder2.setNegativeButton("取消", null);
                builder2.show();
                break;
            case R.id.popuppicker:
                    popup = new PopupWindow(DatePickerDialogDemoActivity.this);
                    popup.setContentView(getLayoutInflater().inflate(R.layout.view_popup, null));
                    popup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popup.setOutsideTouchable(true);
                    popup.setBackgroundDrawable(new ColorDrawable(0x000000));
                    popup.setFocusable(true);
//                    popup.setAnimationStyle(R.style.);
                    popup.showAtLocation(rl_parent, Gravity.BOTTOM, 0, 0);
                break;

        }
    }

    PopupWindow popup;
}
