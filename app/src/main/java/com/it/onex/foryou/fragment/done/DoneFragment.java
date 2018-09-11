package com.it.onex.foryou.fragment.done;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.it.onex.foryou.R;
import com.it.onex.foryou.activity.addtask.AddTaskActivity;
import com.it.onex.foryou.base.BaseFragment;
import com.it.onex.foryou.bean.TodoTaskDetail;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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

        /**设置事件监听*/
        mDoneAdapter.setOnItemClickListener(this);

        mDoneAdapter.setOnItemChildClickListener(this);

        srlFuRefresh.setOnRefreshListener(this);

        /**请求数据*/
        mPresenter.getTodoList(0);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        ARouter.getInstance().build("/article/ArticleTypeActivity")
//                .withString(Constant.CONTENT_TITLE_KEY, mUndoneAdapter.getItem(position).getDoneList().get(0).getTodoList().get(0).getTitle())
//                .withObject(Constant.CONTENT_CHILDREN_DATA_KEY, mUndoneAdapter.getItem(position).getChildren())
//                .navigation();
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
    public void showDoneTask(final TodoTaskDetail data, int loadType) {

        setLoadDataResult(mDoneAdapter, srlFuRefresh, data.getDatas(), loadType);

    }

    @Override
    public void showDeleteSuccess(String message) {
        showSuccess(message);
        mPresenter.refresh();
    }

    @Override
    public void showMarkUnComplete(String message) {
        showSuccess(message);
        mPresenter.refresh();
    }


    @OnClick(R.id.fab_fu_add_task)
    public void onViewClicked() {
        startActivityForResult(new Intent(getActivity(), AddTaskActivity.class), REQUEST_BACK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_BACK) {
            onRefresh();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_id_no_complete:
                mPresenter.updataStatus(mDoneAdapter.getItem(position).getId());
                break;
            case R.id.iv_id_delete:
                mPresenter.deleteTodo(mDoneAdapter.getItem(position).getId());
                break;
        }
    }
}
