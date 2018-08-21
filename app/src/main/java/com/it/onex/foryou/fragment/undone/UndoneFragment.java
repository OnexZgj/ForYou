package com.it.onex.foryou.fragment.undone;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.it.onex.foryou.R;
import com.it.onex.foryou.activity.AddTaskActivity;
import com.it.onex.foryou.base.BaseFragment;
import com.it.onex.foryou.bean.TodoTaskDetail;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

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
    private int REQUEST_BACK = 1001;


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

        srlFuRefresh.setColorSchemeResources(R.color.srl_pink,R.color.srl_pink2,R.color.srl_pink3,R.color.srl_pink4,R.color.srl_pink5);

        rvFuList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFuList.setAdapter(mUndoneAdapter);

        /**设置事件监听*/
        mUndoneAdapter.setOnItemClickListener(this);
        mUndoneAdapter.setOnLoadMoreListener(this);

        mUndoneAdapter.setOnItemChildClickListener(this);

        srlFuRefresh.setOnRefreshListener(this);




        /**请求数据*/
        mPresenter.getUndoneTask(0);
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
    public void showUndoneTask(final TodoTaskDetail data, int loadType) {


        setLoadDataResult(mUndoneAdapter, srlFuRefresh, data.getDatas(), loadType);

//        mUndoneAdapter.setNewData(data.getDatas());

    }


    @Override
    public void showDeleteSuccess(String message) {
        showSuccess(message);
        mPresenter.refresh();
    }

    @Override
    public void showMarkComplete(String message) {
        showSuccess(message);
        mPresenter.refresh();
    }


    @OnClick(R.id.fab_fu_add_task)
    public void onViewClicked() {
        startActivityForResult(new Intent(getActivity(),AddTaskActivity.class),REQUEST_BACK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_BACK){
            onRefresh();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.iv_iu_delete:
                //删除的操作
                mPresenter.deleteTodo(mUndoneAdapter.getItem(position).getId());
                break;
            case R.id.iv_iu_complete:
                //表示状态
                mPresenter.updataStatus(mUndoneAdapter.getItem(position).getId());
                break;
        }
    }
}
