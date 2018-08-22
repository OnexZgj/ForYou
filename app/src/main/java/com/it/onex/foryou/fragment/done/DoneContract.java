package com.it.onex.foryou.fragment.done;

import com.it.onex.foryou.base.BaseContract;
import com.it.onex.foryou.bean.TodoTaskDetail;

/**
 * Created by Linsa on 2018/4/12:22:39.
 * des:
 */

public class DoneContract {
    interface View extends BaseContract.BaseView{
        void showDoneTask(TodoTaskDetail data,int loadType);


        /**
         * 删除成功信息展示
         * @param message
         */
        void showDeleteSuccess(String message);

        /**
         * 标识完成的方法
         * @param message
         */
        void showMarkUnComplete(String message);


    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        /**
         * 获取待办任务的数据
         * @param type 0,1,2,3
         */
        void getTodoList(int type);

        /**
         * 刷新方法
         */
        void refresh();


        /**
         * 删除一条todo任务
         * @param id
         */
        void deleteTodo(int id);

        /**
         * 仅仅更新一条状态
         * @param id
         */
        void updataStatus(int id);

        /**
         * 加载更多的方法
         */
        void loadMore();

    }

}
