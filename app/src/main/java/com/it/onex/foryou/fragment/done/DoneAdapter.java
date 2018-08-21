package com.it.onex.foryou.fragment.done;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.onex.foryou.R;
import com.it.onex.foryou.bean.TodoTaskDetail;
import com.it.onex.foryou.utils.TimeUtil;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by zhanggj on 2018/4/12:23:04.
 * des:
 */

public class DoneAdapter extends BaseQuickAdapter<TodoTaskDetail.DatasBean,BaseViewHolder> {

    @Inject
    public DoneAdapter() {
        super(R.layout.item_done, null);
    }

    @Override
    protected void convert(BaseViewHolder helper,TodoTaskDetail.DatasBean item) {

        helper.setText(R.id.tv_id_title,item.getTitle());
        helper.setText(R.id.tv_id_des,item.getContent());
        helper.setText(R.id.tv_id_date, TimeUtil.format(new Date(item.getDate())));
        helper.addOnClickListener(R.id.iv_id_delete);
        helper.addOnClickListener(R.id.iv_id_no_complete);
    }
}
