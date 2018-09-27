package com.it.onex.foryou.net;

import com.it.onex.foryou.bean.AddToDoDetail;
import com.it.onex.foryou.bean.DataResponse;
import com.it.onex.foryou.bean.TodoTaskDetail;
import com.it.onex.foryou.bean.UndoneTaskBean;
import com.it.onex.foryou.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by OneXzgj on 2018/4/3:09:56.
 * des:网络请求接口
 */

public interface ApiService {


    /**
     * 获取未完成的任务的列表
     * http://wanandroid.com/lg/todo/list/0/json
     *
     * @param type 0 代表 一个默认、1 代表工作 、2 代表 学习 、3 代表生活
     * @return
     */
    @GET("lg/todo/list/{type}/json")
    Observable<DataResponse<UndoneTaskBean>> getUndoneTaskData(@Path("type") int type);



    /**
     * 进行添加一条任务
     * http://www.wanandroid.com/lg/todo/add/json
     * 方法：POST
     * title: 新增标题
     * content: 新增详情
     * date: 2018-08-01
     * type: 0
     */
    @POST("lg/todo/add/json")
    @FormUrlEncoded
    Observable<DataResponse<AddToDoDetail>> addTask(@Field("title") String title, @Field("content") String contennt, @Field("date") String date, @Field("type") String type);


    /**
     * 删除一条todo的api
     * http://www.wanandroid.com/lg/todo/delete/83/json
     */

    @GET("lg/todo/delete/{id}/json")
    Observable<DataResponse> deleteTodo(@Path("id") int id);


    /**
     * 更新一条todo
     * http://www.wanandroid.com/lg/todo/update/83/json
     * id: 拼接在链接上，为唯一标识
     * title: 更新标题
     * content: 新增详情
     * date: 2018-08-01
     * status: 0 // 0为未完成，1为完成
     * type: 0
     */

    @POST("lg/todo/update/{id}/json")
    @FormUrlEncoded
    Observable<DataResponse> updateTodo(@Path("id") int id,@Field("title") String title, @Field("content") String content, @Field("date") String date,@Field("status") int status, @Field("type") int type);

    /**
     * 仅仅更新状态
     * http://www.wanandroid.com/lg/todo/done/80/json
     *  方法：POST
     * id: 拼接在链接上，为唯一标识
     * status: 0或1，传1代表未完成到已完成，反之则反之。
     */

    @POST("lg/todo/done/{id}/json")
    @FormUrlEncoded
    Observable<DataResponse> updateStateTodo(@Path("id") int id,@Field("status") int status);


    /**
     * 未完成的列表
     * http://www.wanandroid.com/lg/todo/listnotdo/0/json/1
     * http://www.wanandroid.com/lg/todo/listnotdo/类型/json/页码
     * 方法：POST
     * 类型：类型拼接在链接上，目前支持0,1,2,3
     * 页码: 拼接在链接上，从1开始；
     */

    @GET("lg/todo/listnotdo/{type}/json/{page}")
    Observable<DataResponse<TodoTaskDetail>> getNotodoList(@Path("type") int type, @Path("page") int page);



    /**
     * 完成的todo
     * http://www.wanandroid.com/lg/todo/listdone/0/json/1
     * http://www.wanandroid.com/lg/todo/listnotdo/类型/json/页码
     * 方法：POST
     * 类型：类型拼接在链接上，目前支持0,1,2,3
     * 页码: 拼接在链接上，从1开始；
     */

    @GET("lg/todo/listdone/{type}/json/{page}")
    Observable<DataResponse<TodoTaskDetail>> getTodoList(@Path("type") int id,@Path("page") int page);










//    /**
//     * 获取文章列表的操作
//     * http://www.wanandroid.com/article/list/0/json
//     *
//     * @param page 页码，拼接在连接中，从0开始。
//     * @return
//     */
//    @GET("/article/list/{page}/json")
//    Observable<DataResponse<Article>> getHomeArticles(@Path("page") int page);
//
//
//    /**
//     * 获取首页的Banner的操作
//     * http://www.wanandroid.com/banner/json
//     *
//     * @return
//     */
//    @GET("/banner/json")
//    Observable<DataResponse<List<BannerData>>> getHomeBanners();
//
//
//    /**
//     * 收藏站内文章
//     * http://www.wanandroid.com/lg/collect/1165/json
//     *
//     * @param id 文章的id
//     * @return
//     */
//    @POST("/lg/collect/{id}/json")
//    Observable<DataResponse> collectArticle(@Path("id") int id);
//
//
//    /**
//     * 删除收藏文章
//     *
//     * @param id       文章id
//     * @param originId 列表页下发，无则为-1
//     * @return Deferred<DataResponse>
//     */
//    @POST("/lg/uncollect/{id}/json")
//    @FormUrlEncoded
//    Observable<DataResponse> removeCollectArticle(@Path("id") int id, @Field("originId") int originId);
//
//
    /**
     * 登录接口
     * http://www.wanandroid.com/user/login
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @POST("/user/login")
    @FormUrlEncoded
    Observable<DataResponse<User>> login(@Field("username") String username, @Field("password") String password);


    /**
     * 注册用户的方法
     * http://www.wanandroid.com/user/register
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Observable<DataResponse> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

//    /**
//     * 体系结构
//     * http://www.wanandroid.com/tree/json
//     */
//
//    @GET("/tree/json")
//    Observable<DataResponse<List<KnowledgeSystem>>> getKnowledgeSystemTree();
//
//    /**
//     * 体系结构下面的文章
//     * http://www.wanandroid.com/article/list/0/json?cid=60
//     */
//
//    @GET("/article/list/{page}/json")
//    Observable<DataResponse<ArticleTypeContent>> getArticleTypeContent(@Path("page") int page, @Query("cid") int cid);
//
//
//    /**
//     * 导航数据页面
//     * http://www.wanandroid.com/navi/json
//     */
//    @GET("/navi/json")
//    Observable<DataResponse<List<Navigation>>> getNavigationContent();
//
//
//    /**
//     * 项目分类
//     * http://www.wanandroid.com/project/tree/json
//     */
//    @GET("/project/tree/json")
//    Observable<DataResponse<List<Project>>> getProjectData();
//
//
//    /**
//     * http://www.wanandroid.com/project/list/1/json?cid=294
//     * 项目分类中的详细数据列表
//     */
//    @GET("/project/list/{page}/json")
//    Observable<DataResponse<ProjectDetail>> getProjectDetailInfo(@Path("page") int page, @Query("cid") int cid);
//
//
//    /**
//     * 热搜api
//     * http://www.wanandroid.com//hotkey/json
//     */
//    @GET("/hotkey/json")
//    Observable<DataResponse<List<HotKey>>> getHotKey();
//
//    /**
//     * 常用网站
//     * http://www.wanandroid.com/friend/json
//     */
//    @GET("/friend/json")
//    Observable<DataResponse<List<Friend>>> getFriendLink();
//
//
//    /**
//     * 搜索页面
//     * http://www.wanandroid.com/article/query/0/json
//     * 页码：拼接在链接上，从0开始。
//     k ： 搜索关键词
//     */
//    @POST("article/query/{page}/json")
//    @FormUrlEncoded
//    Observable<DataResponse<Article>> getSearchArticles(@Path("page") int page, @Field("k") String key);


}
