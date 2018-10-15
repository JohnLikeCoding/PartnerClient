package com.kuaigui.yueche.driver.mvc;


import com.kuaigui.yueche.driver.okhttp.OkRequestParams;

/**
 * @author: zengxc
 * @description:
 * @date: 2017/12/1 10:38
 */
public class BaseController {

    private IResultView resultView;
    private IResultModel resultModel;

    public BaseController(IResultView resultView) {
        this.resultView = resultView;
        resultModel = new IResultModel();
    }

    /***OKHttp的get请求***/
    public void doGetRequest(String url, String type, OkRequestParams params) {
        resultModel.doGetRequest(url, type, params, resultView);
    }

    /***OKHttp的post请求***/
    public void doPostRequest(String url, String type, OkRequestParams params) {
        resultModel.doPostRequest(url, type, params, resultView);
    }

    /***OKHttp的post请求（JSON格式发送）***/
    public void doPostStringRequest(String url, String type, OkRequestParams params) {
        resultModel.doPostStringRequest(url, type, params, resultView);
    }

    /***OKHttp的post请求（文件发送）***/
    public void doPostFileRequest(String url, String type, OkRequestParams params) {
        resultModel.doPostFileRequest(url, type, params, resultView);
    }

    /***OKHttp的post请求（上传文件， 支持单个，多个文件）***/
    public void doUpLoadFileRequest(String url, String type, OkRequestParams params) {
        resultModel.doUpLoadFileRequest(url, type, params, resultView);
    }

    /***OKHttp的post请求（下载文件文件）***/
    public void doDownloadFileRequest(String url, String type, OkRequestParams params) {
        resultModel.doDownloadFileRequest(url, type, params, resultView);
    }


}
