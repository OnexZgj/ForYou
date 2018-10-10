package com.it.onex.foryou.fragment.done;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.it.onex.foryou.R;
import com.it.onex.foryou.activity.addtask.AddTaskActivity;
import com.it.onex.foryou.base.BaseFragment;
import com.it.onex.foryou.bean.TodoSection;
import com.it.onex.foryou.bean.TodoTaskDetail;
import com.it.onex.foryou.constant.Constant;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by OnexZgj on 2018/8/18:11:10.
 * des:待办Fragment
 */

public class DoneFragment extends BaseFragment<DonePresenterImp> implements DoneContract.View, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    private static final int REQUEST_BACK = 1000;
    private static int mType = 0;
    @BindView(R.id.rv_fu_list)
    RecyclerView rvFuList;
    @BindView(R.id.srl_fu_refresh)
    SwipeRefreshLayout srlFuRefresh;

    @Inject
    DoneAdapter mDoneAdapter;
    private static final int REQUEST_EDIT_CODE = 109;
    private int updatePosition = -1;
    private int deletePositon = -1;
    /**
     * 当前点击的position
     */
    private int clickPosition = -1;

    public static DoneFragment getInstance(int type) {
        mType = type;
        return new DoneFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_undone;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }


    @Override
    protected void initView(View view) {
        rvFuList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFuList.setAdapter(mDoneAdapter);

        mDoneAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        /**设置事件监听*/
        mDoneAdapter.setOnItemClickListener(this);

        mDoneAdapter.setOnItemChildClickListener(this);

        srlFuRefresh.setOnRefreshListener(this);

        /**请求数据*/
        mPresenter.getTodoList(0);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (!mDoneAdapter.getData().get(position).isHeader) {
            clickPosition = position;
            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.TASK_KEY, mDoneAdapter.getItem(position));
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_EDIT_CODE);
        }

    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }


    @Override
    public void showLoading() {
        srlFuRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srlFuRefresh.setRefreshing(false);
    }

    @Override
    public void showFaild(String errorMsg) {
        srlFuRefresh.setRefreshing(false);
    }


    @Override
    public void showDoneTask(final TodoTaskDetail datas, int loadType) {

        List<TodoSection> todoSections = new ArrayList<>();
        LinkedHashSet<String> dates = new LinkedHashSet<>();
        for (TodoTaskDetail.DatasBean todoDesBean : datas.getDatas()) {
            dates.add(todoDesBean.getDateStr());
        }
        for (String date : dates) {
            TodoSection todoSectionHead = new TodoSection(true, date);
            todoSections.add(todoSectionHead);
            for (TodoTaskDetail.DatasBean todoDesBean : datas.getDatas()) {
                if (TextUtils.equals(date, todoDesBean.getDateStr())) {
                    TodoSection todoSectionContent = new TodoSection(todoDesBean);
                    todoSections.add(todoSectionContent);
                }
            }
        }
        setLoadDataResult(mDoneAdapter, srlFuRefresh, todoSections, loadType);
    }

    @Override
    public void showDeleteSuccess(String message) {
        if (deletePositon != -1) {
            if (mDoneAdapter.getData().get(deletePositon - 1).isHeader && (mDoneAdapter.getData().size() == deletePositon + 1 || mDoneAdapter.getData().get(deletePositon + 1).isHeader)) {
                mDoneAdapter.getData().remove(deletePositon - 1);
                mDoneAdapter.getData().remove(deletePositon - 1);
                mDoneAdapter.notifyItemRangeRemoved(deletePositon - 1, 2);
            } else {
                mDoneAdapter.getData().remove(deletePositon);
                mDoneAdapter.notifyItemRemoved(deletePositon);
            }
        }
        showSuccess(message);
    }

    @Override
    public void showMarkUnComplete(String message) {

        if (updatePosition != -1) {
            if (mDoneAdapter.getData().get(updatePosition - 1).isHeader && (mDoneAdapter.getData().size() == updatePosition + 1 || mDoneAdapter.getData().get(updatePosition + 1).isHeader)) {
                mDoneAdapter.getData().remove(updatePosition - 1);
                mDoneAdapter.getData().remove(updatePosition - 1);
                mDoneAdapter.notifyItemRangeRemoved(updatePosition - 1, 2);
            } else {
                mDoneAdapter.getData().remove(updatePosition);
                mDoneAdapter.notifyItemRemoved(updatePosition);
            }
        }


//        if (updatePosition != -1) {
//            if (mDoneAdapter.getData().get(updatePosition - 1).isHeader ) {
//                if (mDoneAdapter.getData().size() == updatePosition + 1) {
//                    //第一种情况，数据是最后一条
//                    mDoneAdapter.getData().remove(updatePosition - 1);
//                    mDoneAdapter.getData().remove(updatePosition - 1);
//                    mDoneAdapter.notifyItemRangeRemoved(updatePosition - 1, 2);
//                } else if (mDoneAdapter.getData().get(updatePosition + 1).isHeader) {
//                    //第二种情况 数据非最后一条
//                    mDoneAdapter.getData().remove(updatePosition - 1);
//                    mDoneAdapter.getData().remove(updatePosition - 1);
//                    mDoneAdapter.notifyItemRangeRemoved(updatePosition - 1, 2);
//                }
//            }else{
//                mDoneAdapter.getData().remove(updatePosition);
//                mDoneAdapter.notifyItemRemoved(updatePosition);
//            }
//        }
        showSuccess(message);
    }


    @OnClick(R.id.fab_fu_add_task)
    public void onViewClicked() {
        startActivityForResult(new Intent(getActivity(), AddTaskActivity.class), REQUEST_BACK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_BACK:
                    if (data != null) {
                        TodoTaskDetail.DatasBean addData = (TodoTaskDetail.DatasBean) data.getSerializableExtra(Constant.ADD_DATA);

                        List<TodoSection> todoSections = mDoneAdapter.getData();
                        for (int i = 0; i < todoSections.size(); i++) {
                            TodoSection todoSection = todoSections.get(i);
                            if (todoSection.isHeader && TextUtils.equals(todoSection.header, addData.getDateStr())) {


                                TodoSection section = new TodoSection(addData);
                                mDoneAdapter.getData().add(i + 1, section);
                                mDoneAdapter.notifyItemInserted(i + 1);
                                rvFuList.scrollToPosition(i + 1);
                                return;
                            }
                        }
                        TodoSection sectionHead = new TodoSection(true, addData.getDateStr());
                        mDoneAdapter.getData().add(0, sectionHead);

                        TodoSection section = new TodoSection(addData);
                        mDoneAdapter.getData().add(1, section);
                        mDoneAdapter.notifyItemRangeInserted(0, 2);
                        rvFuList.scrollToPosition(0);
                    }

                    break;
                case REQUEST_EDIT_CODE:
                    if (data != null) {
                        TodoTaskDetail.DatasBean updateData = (TodoTaskDetail.DatasBean) data.getSerializableExtra(Constant.UPDATE_DATA);

                        mDoneAdapter.getData().remove(clickPosition);
                        TodoSection udpateData = new TodoSection(updateData);
                        mDoneAdapter.getData().add(clickPosition, udpateData);
                        mDoneAdapter.notifyItemChanged(clickPosition);
                        rvFuList.scrollToPosition(clickPosition);
                    }
                    break;
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_id_no_complete:
                updatePosition = position;
                mPresenter.updataStatus(mDoneAdapter.getItem(position).t.getId());
                break;
            case R.id.iv_id_delete:
                showDeleteDialog(position);
                break;
        }
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.delete_todo);
        builder.setMessage(R.string.sure_delete_todo);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePositon = position;
                mPresenter.deleteTodo(mDoneAdapter.getItem(position).t.getId());
            }
        });
        builder.show();
    }
}
