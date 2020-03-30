package com.quyum.hyppe.base;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : Xue
 *     time   : 2019/03/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class UserBean extends BaseModel implements Serializable {


    /**
     * data : {"base_order_string":"","dynamic_update_fileld":"","ui_id":"21528d414e744694a99a14325f825b7c","ui_nickname":"测试号","ui_account":"15242262155","ui_password":"e10adc3949ba59abbe56e057f20f883e","ui_payPwd":"","ui_phone":"15242262155","ui_createtime":"2019-10-25 17:50:50","ui_sex":"男","ui_headurl":"http://47.95.220.237/strawberry/upload/tq/1571995712542_21380134678b4b6394aa31e5d6ffe392.jpg","ui_token":"b326d4575ed844deb8b02828c65c473c","ui_birthday":"1994-06-28","ui_address":"辽宁省沈阳市浑南新区","ui_marry":"已婚","ui_invitation_code":"232366","ui_advantage":"优势优势优势优势优势优势优势","ui_work_status":"0","ui_sign_day":"0","ui_realName":"","ui_idCard":"","ui_cardNo":"","ui_isReal":"0","ui_parId":"tool","ui_type":"4","ui_workTime":"2.5","ui_verify":"","eduExperienceList":[{"base_order_string":"","dynamic_update_fileld":"","ue_id":"bcd4db041fd247378427c482d3cf2105","ue_user_id":"21528d414e744694a99a14325f825b7c","ue_name":"辽宁师范大学","ue_startTime":"2013-09","ue_endTime":"2017-06","ue_content":"电子信息工程","ue_edu":"本科","ue_edu_order":"6","ue_createTime":"2019-10-25 17:38:30","ue_type":"0"}],"workExperienceList":[{"base_order_string":"","dynamic_update_fileld":"","ue_id":"1","ue_user_id":"21528d414e744694a99a14325f825b7c","ue_name":"千翼科技","ue_startTime":"2018-05","ue_endTime":"2019-09","ue_content":"java开发工程师","ue_edu":"","ue_edu_order":"","ue_createTime":"2019-10-25 17:38:30","ue_type":"1"}],"jobExperienceList":[{"base_order_string":"","dynamic_update_fileld":"","ue_id":"2","ue_user_id":"21528d414e744694a99a14325f825b7c","ue_name":"java","ue_startTime":"5000-7000","ue_endTime":"随时到岗","ue_content":"沈阳市","ue_edu":"计算机","ue_edu_order":"","ue_createTime":"2019-10-25 17:38:30","ue_type":"2"}],"isSign":"0"}
     */

    public DataBean data;

    @Override
    public boolean isNull() {
        return data == null;
    }

    public static class DataBean implements Serializable {
        public static final String TAG = "UserBean";
        private static final long serialVersionUID = 1L;
        /**
         * base_order_string :
         * dynamic_update_fileld :
         * ui_id : 21528d414e744694a99a14325f825b7c
         * ui_nickname : 测试号
         * ui_account : 15242262155
         * ui_password : e10adc3949ba59abbe56e057f20f883e
         * ui_payPwd :
         * ui_phone : 15242262155
         * ui_createtime : 2019-10-25 17:50:50
         * ui_sex : 男
         * ui_headurl : http://47.95.220.237/strawberry/upload/tq/1571995712542_21380134678b4b6394aa31e5d6ffe392.jpg
         * ui_token : b326d4575ed844deb8b02828c65c473c
         * ui_birthday : 1994-06-28
         * ui_address : 辽宁省沈阳市浑南新区
         * ui_marry : 已婚
         * ui_invitation_code : 232366
         * ui_advantage : 优势优势优势优势优势优势优势
         * ui_work_status : 0
         * ui_sign_day : 0
         * ui_realName :
         * ui_idCard :
         * ui_cardNo :
         * ui_isReal : 0
         * ui_parId : tool
         * ui_type : 4
         * ui_workTime : 2.5
         * ui_verify :
         * eduExperienceList : [{"base_order_string":"","dynamic_update_fileld":"","ue_id":"bcd4db041fd247378427c482d3cf2105","ue_user_id":"21528d414e744694a99a14325f825b7c","ue_name":"辽宁师范大学","ue_startTime":"2013-09","ue_endTime":"2017-06","ue_content":"电子信息工程","ue_edu":"本科","ue_edu_order":"6","ue_createTime":"2019-10-25 17:38:30","ue_type":"0"}]
         * workExperienceList : [{"base_order_string":"","dynamic_update_fileld":"","ue_id":"1","ue_user_id":"21528d414e744694a99a14325f825b7c","ue_name":"千翼科技","ue_startTime":"2018-05","ue_endTime":"2019-09","ue_content":"java开发工程师","ue_edu":"","ue_edu_order":"","ue_createTime":"2019-10-25 17:38:30","ue_type":"1"}]
         * jobExperienceList : [{"base_order_string":"","dynamic_update_fileld":"","ue_id":"2","ue_user_id":"21528d414e744694a99a14325f825b7c","ue_name":"java","ue_startTime":"5000-7000","ue_endTime":"随时到岗","ue_content":"沈阳市","ue_edu":"计算机","ue_edu_order":"","ue_createTime":"2019-10-25 17:38:30","ue_type":"2"}]
         * isSign : 0
         */

        public String base_order_string;
        public String dynamic_update_fileld;
        public String ui_reason;
        public String ui_id;
        public String ui_nickname;
        public String ui_account;
        public String type;
        public String ui_password;
        public String ui_payPwd;
        public String ui_phone;
        public String ui_createtime;
        public String ui_sex;
        public String ui_headurl;
        public String ui_token;
        public String ui_birthday;
        public String ui_address;
        public String ui_marry;
        public String ui_invitation_code;
        public String ui_advantage;
        public String ui_work_status;
        public String ui_sign_day;
        public String yiji;
        public String erji;
        public String ui_realName;
        public String ui_idCard;
        public String ui_cardNo;
        public String ui_isReal;
        public String ui_parId;
        public String ui_type;
        public String ui_workTime;
        public String ui_verify;
        public String isSign;
        public List<EduExperienceListBean> eduExperienceList;
        public List<WorkExperienceListBean> workExperienceList;
        public List<JobExperienceListBean> jobExperienceList;

        public static class EduExperienceListBean implements Serializable {
            /**
             * base_order_string :
             * dynamic_update_fileld :
             * ue_id : bcd4db041fd247378427c482d3cf2105
             * ue_user_id : 21528d414e744694a99a14325f825b7c
             * ue_name : 辽宁师范大学
             * ue_startTime : 2013-09
             * ue_endTime : 2017-06
             * ue_content : 电子信息工程
             * ue_edu : 本科
             * ue_edu_order : 6
             * ue_createTime : 2019-10-25 17:38:30
             * ue_type : 0
             */

            public String base_order_string;
            public String dynamic_update_fileld;
            public String ue_id;
            public String ue_user_id;
            public String ue_name;
            public String ue_startTime;
            public String ue_endTime;
            public String ue_content;
            public String ue_edu;
            public String ue_edu_order;
            public String ue_createTime;
            public String ue_type;
        }

        public static class WorkExperienceListBean implements Serializable {
            /**
             * base_order_string :
             * dynamic_update_fileld :
             * ue_id : 1
             * ue_user_id : 21528d414e744694a99a14325f825b7c
             * ue_name : 千翼科技
             * ue_startTime : 2018-05
             * ue_endTime : 2019-09
             * ue_content : java开发工程师
             * ue_edu :
             * ue_edu_order :
             * ue_createTime : 2019-10-25 17:38:30
             * ue_type : 1
             */

            public String base_order_string;
            public String dynamic_update_fileld;
            public String ue_id;
            public String ue_user_id;
            public String ue_name;
            public String ue_startTime;
            public String ue_endTime;
            public String ue_content;
            public String ue_edu;
            public String ue_edu_order;
            public String ue_createTime;
            public String ue_type;
        }

        public static class JobExperienceListBean implements Serializable {
            /**
             * base_order_string :
             * dynamic_update_fileld :
             * ue_id : 2
             * ue_user_id : 21528d414e744694a99a14325f825b7c
             * ue_name : java
             * ue_startTime : 5000-7000
             * ue_endTime : 随时到岗
             * ue_content : 沈阳市
             * ue_edu : 计算机
             * ue_edu_order :
             * ue_createTime : 2019-10-25 17:38:30
             * ue_type : 2
             */

            public String base_order_string;
            public String dynamic_update_fileld;
            public String ue_id;
            public String ue_user_id;
            public String ue_name;
            public String ue_startTime;
            public String ue_endTime;
            public String ue_content;
            public String ue_edu;
            public String ue_edu_order;
            public String ue_createTime;
            public String ue_type;
        }
    }
}
