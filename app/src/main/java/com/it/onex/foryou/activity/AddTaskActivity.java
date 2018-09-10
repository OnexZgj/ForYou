package com.it.onex.foryou.activity;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
    @BindView(R.id.et_adt_title)
    EditText etAdtTitle;
    @BindView(R.id.et_adt_content)
    EditText etAdtContent;
    @BindView(R.id.tv_adt_date)
    TextView tvAdtDate;
    @BindView(R.id.btn_adt_save)
    Button btnAdtSave;

//    //选择类型的view
//    @BindView(R.id.ll_choose_type)
//    LinearLayout llChooseType;

//    @BindView(R.id.tv_type_name)
//    TextView tvTypeName;

    int mYear = 2018;
    int mMonth = 8;
    int mDay = 18;
    private String mCustomType = "0";


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
                mPresenter.addTask(etAdtTitle.getText().toString(), etAdtContent.getText().toString(), tvAdtDate.getText().toString(), mCustomType);
                break;
//            case R.id.ll_choose_type:
//                showBottomSheet();
//                break;
        }
    }

    @Override
    public void showAddTaskSuccess() {
        showSuccess("添加成功!");
    }



    /**
     * 展示选择的分类
     */
//    private void showBottomSheet() {
//        new BottomSheet.Builder(this, R.style.BottomSheet_StyleDialog)
//                .title("选择分类:")
//                .sheet(R.menu.add_todo_bottom_sheet)
//                .listener(new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case R.id.item_add_default:
//                                mCustomType = "0";
//                                tvTypeName.setText("默认");
//                                break;
//                            case R.id.item_add_work:
//                                mCustomType = "1";
//                                tvTypeName.setText("工作");
//                                break;
//                            case R.id.item_add_life:
//                                mCustomType = "2";
//                                tvTypeName.setText("生活");
//                                break;
//                            case R.id.item_add_study:
//                                mCustomType = "3";
//                                tvTypeName.setText("学习");
//                                break;
//                        }
//                    }
//                }).show();
//    }
}
