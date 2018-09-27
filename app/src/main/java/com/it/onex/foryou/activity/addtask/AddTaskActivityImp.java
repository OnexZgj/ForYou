package com.it.onex.foryou.activity.addtask;

import com.it.onex.foryou.base.BasePresenter;
import com.it.onex.foryou.bean.AddToDoDetail;
import com.it.onex.foryou.bean.DataResponse;
import com.it.onex.foryou.net.ApiService;
import com.it.onex.foryou.net.RetrofitManager;
import com.it.onex.foryou.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by OnexZgj on 2018/8/20:11:34.
 * des:
 */

public class AddTaskActivityImp extends BasePresenter<AddTaskActivityContract.View> implements AddTaskActivityContract.Presenter {

    @Inject
    public AddTaskActivityImp() {

    }


    @Override
    public void addTask(String title, String content, String date, String state) {
        RetrofitManager.create(ApiService.class).addTask(title,content,date,state)
                .compose(RxSchedulers.<DataResponse<AddToDoDetail>>applySchedulers())
                .compose(mView.<DataResponse<AddToDoDetail>>bindToLife())
                .subscribe(new Consumer<DataResponse<AddToDoDetail>>() {
                    @Override
                    public void accept(DataResponse<AddToDoDetail> dataResponse) throws Exception {

                        if (dataResponse.getErrorCode() == 0) {
                            mView.showAddTaskSuccess();
                        }else{
                            mView.showFaild(dataResponse.getErrorMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFaild("添加待办失败,请重试...");
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void updateTask(int id,String title, String content, String date, int state,int type) {
        RetrofitManager.create(ApiService.class).updateTodo(id,title,content,date,state,type)
                .compose(mView.<DataResponse>bindToLife())
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse dataResponse) throws Exception {
                        if (dataResponse.getErrorCode()==0){
                            mView.showUpdateSuccess("更新成功");
                        }else {
                            mView.showFaild(dataResponse.getErrorMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFaild("更新失败,请重试...");
                        mView.hideLoading();
                    }
                });
    }
}
