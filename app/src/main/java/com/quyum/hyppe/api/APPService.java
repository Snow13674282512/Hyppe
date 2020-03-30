package com.quyum.hyppe.api;

import com.quyum.hyppe.base.BaseModel;
import com.quyum.hyppe.base.UserBean;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface APPService {
    //发送短信
    @FormUrlEncoded
    @POST("RsApiPublicController/verification.json")
    Flowable<BaseModel> getCode(@Field("phone") String phone);

    //用户登录
    @POST("RsApiPublicController/login.json")
    Flowable<UserBean> login(@Query("phone") String phone, @Query("password") String password, @Query("code") String code);

    //忘记密码
    @POST("RsApiPublicController/forgetUserPassword.json")
    Flowable<BaseModel> forget(@Query("phone") String phone, @Query("code") String code, @Query("password") String password);

    //投诉
    @FormUrlEncoded
    @POST("RsApiIndexPageController/saveFeedbackReason.json")
    Flowable<BaseModel> saveFeedbackReason(@Field("userId") String userId, @Field("reasonId") String reasonId, @Field("content") String content,
                                           @Field("positionId") String positionId, @Field("USER_TOKEN") String USER_TOKEN);

    //收藏
    @POST("RsApiPersonalCenterController/saveUserCollection.json")
    Flowable<BaseModel> saveUserCollection(@Query("userId") String userId, @Query("USER_TOKEN") String USER_TOKEN,
                                           @Query("type") String type, @Query("positionId") String positionId);

    //申请职位
    @POST("RsApiIndexPageController/saveUserApply.json")
    Flowable<BaseModel> saveUserApply(@Query("userId") String userId, @Query("USER_TOKEN") String USER_TOKEN, @Query("positionId") String positionId);

    //修改手机号，密码，支付密码
    @POST("RsApiPersonalCenterController/updateUserPhoneAndPwd.json")
    Flowable<BaseModel> updateUserPhoneAndPwd(@Query("userId") String userId, @Query("USER_TOKEN") String USER_TOKEN, @Query("phone") String phone,
                                              @Query("oldPwd") String oldPwd, @Query("newPwd") String newPwd, @Query("newPayPwd") String newPayPwd,
                                              @Query("code") String code);

    //验证手机号，密码，支付密码
    @POST("RsApiPersonalCenterController/verifyUserPhoneAndPwd.json")
    Flowable<BaseModel> verifyUserPhoneAndPwd(@Query("userId") String userId, @Query("USER_TOKEN") String USER_TOKEN, @Query("phone") String phone,
                                              @Query("password") String password, @Query("payPwd") String payPwd);

    //添加意见反馈
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/saveUserFeedBack.json")
    Flowable<BaseModel> saveUserFeedBack(@Field("userId") String userId, @Field("USER_TOKEN") String USER_TOKEN, @Field("content") String content);

    //修改用户信息
    @Multipart
    @POST("RsApiPersonalCenterController/updateUserInfo.json")
    Flowable<UserBean> updateUserInfo(@Part("ui_id") RequestBody ui_id, @Part("USER_TOKEN") RequestBody USER_TOKEN,
                                      @Part("ui_nickname") RequestBody ui_nickname, @Part("ui_phone") RequestBody ui_phone,
                                      @Part("ui_sex") RequestBody ui_sex, @Part("ui_birthday") RequestBody ui_birthday,
                                      @Part("ui_address") RequestBody ui_address, @Part("ui_marry") RequestBody ui_marry,
                                      @Part("ui_advantage") RequestBody ui_advantage, @PartMap Map<String, RequestBody> params);

    //实名认证
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/saveUserReal.json")
    Flowable<BaseModel> saveUserReal(@Field("userId") String userId, @Field("USER_TOKEN") String USER_TOKEN, @Field("realName") String realName,
                                     @Field("idCard") String idCard, @Field("bankCard") String bankCard, @Field("bankName") String bankName);

    //用户签到
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/saveUserSign.json")
    Flowable<BaseModel> saveUserSign(@Field("userId") String userId, @Field("USER_TOKEN") String USER_TOKEN, @Field("point") String point);

    //添加用户求职意向
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/saveUserIntention.json")
    Flowable<BaseModel> saveUserIntention(@Field("userId") String userId, @Field("USER_TOKEN") String USER_TOKEN,
                                          @Field("position") String position, @Field("salary") String salary,
                                          @Field("point") String point, @Field("industry") String industry,
                                          @Field("status") String status);

    //修改用户求职意向
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/updateRsUserIntention.json")
    Flowable<BaseModel> updateRsUserIntention(@Field("ueId") String ueId, @Field("USER_TOKEN") String USER_TOKEN,
                                              @Field("position") String position, @Field("salary") String salary,
                                              @Field("point") String point, @Field("industry") String industry,
                                              @Field("status") String status);

    //添加用户工作经历
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/saveUserWorkExperience.json")
    Flowable<BaseModel> saveUserWorkExperience(@Field("userId") String userId, @Field("USER_TOKEN") String USER_TOKEN,
                                               @Field("companyName") String companyName, @Field("startTime") String startTime,
                                               @Field("endTime") String endTime, @Field("position") String position);

    //修改用户工作经历
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/updateUserWorkExperience.json")
    Flowable<BaseModel> updateUserWorkExperience(@Field("ueId") String ueId, @Field("USER_TOKEN") String USER_TOKEN,
                                                 @Field("companyName") String companyName, @Field("startTime") String startTime,
                                                 @Field("endTime") String endTime, @Field("position") String position);

    //添加用户教育经历
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/saveUserEdu.json")
    Flowable<BaseModel> saveUserEdu(@Field("userId") String userId, @Field("USER_TOKEN") String USER_TOKEN,
                                    @Field("education") String education, @Field("university") String university,
                                    @Field("major") String major, @Field("startTime") String startTime,
                                    @Field("endTime") String endTime);

    //修改用户教育经历
    @FormUrlEncoded
    @POST("RsApiPersonalCenterController/updateUserEdu.json")
    Flowable<BaseModel> updateUserEdu(@Field("ueId") String ueId, @Field("USER_TOKEN") String USER_TOKEN,
                                      @Field("education") String education, @Field("university") String university,
                                      @Field("major") String major, @Field("startTime") String startTime,
                                      @Field("endTime") String endTime);
}
