package com.it.onex.foryou.activity.addtask;

import com.it.onex.foryou.base.BaseContract;

/**
 * Created by Linsa on 2018/8/20:11:23.
 * des:
 */

public interface AddTaskActivityContract {


    interface View extends BaseContract.BaseView{
        void  showAddTaskSuccess();

        void showUpdateSuccess(String str);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        /**
         * 增加一条待办
         * @param title
         * @param content
         * @param date 日期
         * @param state 0 代表待办 1 代表已完成
         */
        void addTask(String title,String content,String date, String state);

        /**
         * 更新一条待办
         * @param title
         * @param content
         * @param date
         * @param state
         */
        void updateTask(int id,String title,String content,String date, int state,int type);
    }



}
