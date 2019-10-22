package com.it.onex.foryou.bean;

import com.chad.library.adapter.base.entity.SectionEntity;


public class TodoSection extends SectionEntity<TodoTaskDetail.DatasBean> {

    public TodoSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public TodoSection(TodoTaskDetail.DatasBean todoBean) {
        super(todoBean);
    }
}
