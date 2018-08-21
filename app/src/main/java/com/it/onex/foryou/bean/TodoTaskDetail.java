package com.it.onex.foryou.bean;

import java.util.List;

/**
 * Created by Linsa on 2018/8/21:10:04.
 * des:
 */

public class TodoTaskDetail {


        /**
         * curPage : 1
         * datas : [{"completeDate":null,"completeDateStr":"","content":"是掉发第三方","date":1534867200000,"dateStr":"2018-08-22","id":1347,"status":0,"title":"啦啦啦","type":0,"userId":4421},{"completeDate":null,"completeDateStr":"","content":"fffgg","date":1534780800000,"dateStr":"2018-08-21","id":1452,"status":0,"title":"yyyy","type":0,"userId":4421},{"completeDate":null,"completeDateStr":"","content":"uuuhh","date":1534780800000,"dateStr":"2018-08-21","id":1453,"status":0,"title":"llll","type":0,"userId":4421},{"completeDate":null,"completeDateStr":"","content":"gfgg","date":1534780800000,"dateStr":"2018-08-21","id":1454,"status":0,"title":"gfff","type":0,"userId":4421},{"completeDate":null,"completeDateStr":"","content":"ddddd","date":1534780800000,"dateStr":"2018-08-21","id":1455,"status":0,"title":"uyyyyy","type":0,"userId":4421},{"completeDate":null,"completeDateStr":"","content":"多多照顾就在附近女婿没齿难忘张v吗畜牧场你坐车女cnc","date":1534694400000,"dateStr":"2018-08-20","id":1435,"status":0,"title":"反反复复","type":0,"userId":4421},{"completeDate":null,"completeDateStr":"","content":"xvxvxxvgdgzw","date":1534694400000,"dateStr":"2018-08-20","id":1436,"status":0,"title":"bkx","type":0,"userId":4421},{"completeDate":null,"completeDateStr":"","content":"打算发打发","date":1534521600000,"dateStr":"2018-08-18","id":1346,"status":0,"title":"张更杰","type":0,"userId":4421}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 8
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * completeDate : null
             * completeDateStr :
             * content : 是掉发第三方
             * date : 1534867200000
             * dateStr : 2018-08-22
             * id : 1347
             * status : 0
             * title : 啦啦啦
             * type : 0
             * userId : 4421
             */

            private Object completeDate;
            private String completeDateStr;
            private String content;
            private long date;
            private String dateStr;
            private int id;
            private int status;
            private String title;
            private int type;
            private int userId;

            public Object getCompleteDate() {
                return completeDate;
            }

            public void setCompleteDate(Object completeDate) {
                this.completeDate = completeDate;
            }

            public String getCompleteDateStr() {
                return completeDateStr;
            }

            public void setCompleteDateStr(String completeDateStr) {
                this.completeDateStr = completeDateStr;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public String getDateStr() {
                return dateStr;
            }

            public void setDateStr(String dateStr) {
                this.dateStr = dateStr;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
}
