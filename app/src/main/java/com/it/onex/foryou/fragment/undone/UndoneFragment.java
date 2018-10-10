package com.it.onex.foryou.fragment.undone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class UndoneFragment extends BaseFragment<UndonePresenterImp> implements UndoneContract.View, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {

    private static int mType = 0;
    @BindView(R.id.rv_fu_list)
    RecyclerView rvFuList;
    @BindView(R.id.srl_fu_refresh)
    SwipeRefreshLayout srlFuRefresh;

    @Inject
    UndoneAdapter mUndoneAdapter;
    @BindView(R.id.fab_fu_add_task)
    FloatingActionButton fabFuAddTask;
    private static final int REQUEST_BACK = 101;
    private static final int REQUEST_EDIT_CODE = 105;
    /**
     * 删除的position
     */
    private int deletePosition = -1;
    /**
     * 更新的position
     */
    private int updatePosition = -1;
    /**
     * 记录点击的position
     */
    private int clickPosition = -1;


    public static UndoneFragment getInstance(int type) {
        mType = type;
        return new UndoneFragment();
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
        srlFuRefresh.setColorSchemeResources(R.color.srl_pink, R.color.srl_pink2, R.color.srl_pink3, R.color.srl_pink4, R.color.srl_pink5);

        rvFuList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFuList.setAdapter(mUndoneAdapter);

        /**设置事件监听*/
        mUndoneAdapter.setOnItemClickListener(this);
        mUndoneAdapter.setOnLoadMoreListener(this);

        mUndoneAdapter.setOnItemChildClickListener(this);

        srlFuRefresh.setOnRefreshListener(this);


        //为了防止第一次应用进行的时候，mPresent还没有进行初始化导致的数据一直在加载
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mPresenter.getUndoneTask(0);
            }
        }, 20);

        /**请求数据*/
//        mPresenter.getUndoneTask(0);

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        clickPosition = position;
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TASK_KEY, mUndoneAdapter.getItem(position));
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_EDIT_CODE);

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
    public void showUndoneTask(final TodoTaskDetail datas, int loadType) {

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
        setLoadDataResult(mUndoneAdapter, srlFuRefresh, todoSections, loadType);
    }


    @Override
    public void showDeleteSuccess(String message) {
        if (deletePosition != -1) {
            if (mUndoneAdapter.getData().get(deletePosition - 1).isHeader && (mUndoneAdapter.getData().size() == deletePosition + 1 || mUndoneAdapter.getData().get(deletePosition + 1).isHeader)) {
                mUndoneAdapter.getData().remove(deletePosition - 1);
                mUndoneAdapter.getData().remove(deletePosition - 1);
                mUndoneAdapter.notifyItemRangeRemoved(deletePosition - 1, 2);
            } else {
                mUndoneAdapter.getData().remove(deletePosition);
                mUndoneAdapter.notifyItemRemoved(deletePosition);
            }
        }
        showSuccess(message);
//        mPresenter.refresh();
    }

    @Override
    public void showMarkComplete(String message) {
        if (updatePosition != -1) {
            if (mUndoneAdapter.getData().get(updatePosition - 1).isHeader && (mUndoneAdapter.getData().size() == updatePosition + 1 || mUndoneAdapter.getData().get(updatePosition + 1).isHeader)) {
                int size = mUndoneAdapter.getData().size();
                mUndoneAdapter.getData().remove(updatePosition - 1);
                mUndoneAdapter.getData().remove(updatePosition - 1);
                if (size == 2){
                    //假如是最后的2条，直接进行notify所有，否则会出现异常
                    mUndoneAdapter.notifyDataSetChanged();
                }else{
                    mUndoneAdapter.notifyItemRangeRemoved(updatePosition - 1, 2);
                }
            } else {
                mUndoneAdapter.getData().remove(updatePosition);
                mUndoneAdapter.notifyItemRemoved(updatePosition);
            }
        }
        showSuccess(message);
    }


    @OnClick(R.id.fab_fu_add_task)
    public void onViewClicked() {
        startActivityForResult(new Intent(getActivity(), AddTaskActivity.class), REQUEST_BACK);
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.delete_todo);
        builder.setMessage(R.string.sure_delete_todo);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePosition = position;
                mPresenter.deleteTodo(mUndoneAdapter.getItem(position).t.getId());
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_BACK:

                    if (data != null) {

                        TodoTaskDetail.DatasBean addData = (TodoTaskDetail.DatasBean) data.getSerializableExtra(Constant.ADD_DATA);
                        List<TodoSection> todoSections = mUndoneAdapter.getData();
                        for (int i = 0; i < todoSections.size(); i++) {
                            TodoSection todoSection = todoSections.get(i);
                            if (todoSection.isHeader && TextUtils.equals(todoSection.header, addData.getDateStr())) {


                                TodoSection section = new TodoSection(addData);
                                mUndoneAdapter.getData().add(i + 1, section);
                                mUndoneAdapter.notifyItemInserted(i + 1);
                                rvFuList.scrollToPosition(i + 1);
                                return;
                            }
                        }
                        TodoSection sectionHead = new TodoSection(true, addData.getDateStr());
                        mUndoneAdapter.getData().add(0, sectionHead);
                        TodoSection section = new TodoSection(addData);
                        mUndoneAdapter.getData().add(1, section);
                        mUndoneAdapter.notifyItemRangeInserted(0, 2);
                        rvFuList.scrollToPosition(0);
                    }

                    break;
                case REQUEST_EDIT_CODE:
                    if (data != null) {
                        TodoTaskDetail.DatasBean updateData = (TodoTaskDetail.DatasBean) data.getSerializableExtra(Constant.UPDATE_DATA);

                        mUndoneAdapter.getData().remove(clickPosition);
                        TodoSection udpateData = new TodoSection(updateData);
                        mUndoneAdapter.getData().add(clickPosition, udpateData);
                        mUndoneAdapter.notifyItemChanged(clickPosition);
                        rvFuList.scrollToPosition(clickPosition);
                    }
//                onRefresh();
                    break;
            }
        }

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_iu_delete:
                //删除的操作
                showDeleteDialog(position);
                break;
            case R.id.iv_iu_complete:
                //表示状态
                updatePosition = position;
                mPresenter.updataStatus(mUndoneAdapter.getItem(position).t.getId());
                break;
        }
    }
}
