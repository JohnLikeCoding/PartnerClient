package com.kuaigui.yueche.driver.mvc;

/**
 * 回调结果接口, MVC中的V, 主要用于更新view
 */
public interface IResultView {
    /**
     * 接口请求前操作
     */
    void showLoadView(String url);

    /**
     * 设置数据,更新视图
     *
     * @param url     请求标识 (根据URL作为表示)(因为一个activity中可能有多个接口请求,所以以这个区分)
     * @param content 接口返回的数据源
     * @param type    用户自定义参数
     */
    void showResultView(String url, String type, String content);

    /**
     * 错误时显示的提示或者操作
     *
     * @param msg 接口返回的错误信息
     * @param url 请求标识 ,(根据URL作为表示)同上,一般不建议使用,因为我们对错误信息可以作统一处理
     * @param e   异常信息
     */
    void showErrorView(String url, Exception e, String msg);

    void showProgressView(String url, int progress);
}
