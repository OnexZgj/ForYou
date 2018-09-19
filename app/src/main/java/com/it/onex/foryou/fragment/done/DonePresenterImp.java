package com.it.onex.foryou.fragment.done;

import com.it.onex.foryou.base.BasePresenter;
import com.it.onex.foryou.bean.DataResponse;
import com.it.onex.foryou.bean.TodoTaskDetail;
import com.it.onex.foryou.constant.Constant;
import com.it.onex.foryou.constant.LoadType;
import com.it.onex.foryou.net.ApiService;
import com.it.onex.foryou.net.RetrofitManager;
import com.it.onex.foryou.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by OnexZgj on 2018/4/12:22:43.
 * des:
 */

public class DonePresenterImp extends BasePresenter<DoneContract.View> implements DoneContract.Presenter {


    private int mType = 0;
    private int mIndexPage = 1;
    private boolean mIsRefreshing = true;

    @Inject
    public DonePresenterImp() {
    }

    @Override
    public void getTodoList(int type) {
        this.mType = type;

        mView.showLoading();

        ApiService apiService = RetrofitManager.create(ApiService.class);
        apiService.getTodoList(type, mIndexPage).compose(mView.<DataResponse<TodoTaskDetail>>bindToLife())
                .compose(RxSchedulers.<DataResponse<TodoTaskDetail>>applySchedulers())
                .subscribe(new Consumer<DataResponse<TodoTaskDetail>>() {
                    @Override
                    public void accept(DataResponse<TodoTaskDetail> data) throws Exception {

                        if (data.getErrorCode() == 0) {

                            int loadType = mIsRefreshing ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;

                            mView.showDoneTask(data.getData(), loadType);
                        } else {
                            mView.showFaild(data.getErrorMsg());
                            if (data.getErrorMsg().equals(Constant.LOGIN_WARN)){
                                mView.jumpToLogin();
                            }
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFaild("请求网络错误!");
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void refresh() {
        mIndexPage = 1;
        mIsRefreshing = true;
        getTodoList(mType);
    }

    @Override
    public void deleteTodo(int id) {
        mView.showLoading();
        RetrofitManager.create(ApiService.class).deleteTodo(id).compose(RxSchedulers.<DataResponse>applySchedulers()).compose(mView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse s) throws Exception {
                        mView.showDeleteSuccess("删除成功!");
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showDeleteSuccess("删除失败,请重试...!");
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void updataStatus(int id) {
        mView.showLoading();
        RetrofitManager.create(ApiService.class).updateStateTodo(id, 0)
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .compose(mView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse s) throws Exception {
                        mView.showMarkUnComplete("标记为未完成!");
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showMarkUnComplete("标记未完成失败，请重试!");
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void loadMore() {
        mIndexPage++;
        mIsRefreshing = false;
        getTodoList(mType);
    }


}
