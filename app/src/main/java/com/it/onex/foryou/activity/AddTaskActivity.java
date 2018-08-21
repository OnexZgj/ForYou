package com.it.onex.foryou.activity;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.it.onex.foryou.R;
import com.it.onex.foryou.base.BaseActivity;
import com.it.onex.foryou.utils.TimeUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by OnexZgj on 2018/8/18:17:35.
 * des:新增待办的Activity
 */

public class AddTaskActivity extends BaseActivity<AddTaskActivityImp> implements AddTaskActivityContract.View {
    @BindView(R.id.tiet_adt_title)
    TextInputEditText tietAdtTitle;
    @BindView(R.id.tiet_adt_Content)
    TextInputEditText tietAdtContent;
    @BindView(R.id.tv_adt_date)
    TextView tvAdtDate;
    @BindView(R.id.btn_adt_save)
    Button btnAdtSave;


    int mYear = 2018;
    int mMonth = 8;
    int mDay = 18;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_task;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("新增待办");
        String format = TimeUtil.format(new Date(), TimeUtil.DEFAULT_PATTERN);

        mYear=Integer.parseInt(format.split("-")[0]);
        mMonth=Integer.parseInt(format.split("-")[1]);
        mDay=Integer.parseInt(format.split("-")[2]);


        tvAdtDate.setText(mYear + "-" + mMonth + "-" + mDay);
    }


    @OnClick({R.id.tv_adt_date, R.id.btn_adt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_adt_date:


                new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tvAdtDate.setText(year + "-" + month + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay).show();

                break;
            case R.id.btn_adt_save:
                showLoading();
                mPresenter.addTask(tietAdtTitle.getText().toString(), tietAdtContent.getText().toString(), tvAdtDate.getText().toString(), "0");
                break;
        }
    }

    @Override
    public void showAddTaskSuccess() {
        showSuccess("添加成功!");
    }
}
