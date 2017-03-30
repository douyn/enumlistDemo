package com.qiwo.bean.enumlistdemo;

import java.util.List;

/**
 * Created by l on 2016/7/20.
 */
public class PicList {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"data":[{"id":"128","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqACo_mAAAswjx0CMc881.jpg","desc":"","likes":"3","comments":"1","status":0},{"id":"129","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqAO2yHAAAwVgt7UI4562.jpg","desc":"","likes":"5","comments":"0","status":0},{"id":"130","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqARpFbAAHV9YrK9Zk114.jpg","desc":"","likes":"3","comments":"0","status":0},{"id":"131","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqAYcD-AAA_qnfnz8M139.jpg","desc":"","likes":"4","comments":"0","status":0},{"id":"132","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqAGIkSAACTx8HGv3w247.jpg","desc":"","likes":"3","comments":"1","status":0},{"id":"133","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqAPKxpAAApphYDy3Y384.jpg","desc":"","likes":"6","comments":"0","status":0},{"id":"134","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqAejKGAADIheOahdM451.jpg","desc":"","likes":"3","comments":"5","status":0},{"id":"135","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqAIShXAAAyKaEqj4Y137.jpg","desc":"","likes":"3","comments":"0","status":0},{"id":"136","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzuAEGctAAZRHjEsvIc043.png","desc":"","likes":"2","comments":"0","status":0},{"id":"137","thumb_url":"http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqAHAw5AAA-DPBShk8878.jpg","desc":"","likes":"2","comments":"0","status":0},{"id":"71","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"1","comments":"0","status":0},{"id":"72","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"1","comments":"0","status":0},{"id":"73","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"0","comments":"0","status":0},{"id":"74","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"0","comments":"0","status":0},{"id":"75","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"0","comments":"0","status":0},{"id":"76","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"0","comments":"0","status":0},{"id":"77","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"1","comments":"0","status":0},{"id":"78","thumb_url":"http://image.juooo.net.cn","desc":"","likes":"1","comments":"0","status":0}]}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 128
         * thumb_url : http://image.juooo.net.cn/group1/M00/00/04/wKhupFd6LzqACo_mAAAswjx0CMc881.jpg
         * desc :
         * likes : 3
         * comments : 1
         * status : 0
         */

        private List<PicItem> data;

        public List<PicItem> getData() {
            return data;
        }

        public void setData(List<PicItem> data) {
            this.data = data;
        }

        public static class PicItem {
            private String id;
            private String thumb_url;
            private String desc;
            private String likes;
            private String comments;
            private int status;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getThumb_url() {
                return thumb_url;
            }

            public void setThumb_url(String thumb_url) {
                this.thumb_url = thumb_url;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
