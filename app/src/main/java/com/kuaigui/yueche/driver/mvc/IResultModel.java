package com.kuaigui.yueche.driver.mvc;

import android.os.Environment;

import com.kuaigui.yueche.driver.okhttp.OkRequestParams;
import com.kuaigui.yueche.driver.okhttp.fileutil.FileBody;
import com.kuaigui.yueche.driver.util.AbJsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;


/**
 * Created by chenjun on 2016/8/5.
 */
public class IResultModel {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String POSTSTRING = "POSTSTRING";
    public static final String POSTFILE = "POSTFILE";
    public static final String UPLOADFILE = "UPLOADFILE";
    public static final String DOWNLOADFILE = "DOWNLOADFILE";

    // 旧的HttpClient请求方式
    //  =========================================================================================================================================================================

    /**
     * 请求接口的公共方法
     *
     * @param url        请求地址   （根据url来判断，作为表示符）
     * @param params     params封装的是参数
     * @param resultView resultView,用于回调数据结果
     * @param type       用户自定义参数
     */
   /* public void requestMethod(final String url, final String type, AbRequestParams params, final IResultView resultView) {
       *//* AbRequestParams params = new AbRequestParams();
        if(map!=null&&map.size()!=0){
            try {
                Set<Map.Entry<String, String>> entrySet = map.entrySet();
                Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String>  mapEntry = (Map.Entry<String, String>) iterator.next();
                    params.put(mapEntry.getKey(), mapEntry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*//*
        resultView.showLoadView(url);    // 加载前动作

        TeaApplication.mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {

            // 获取数据成功会调用这里
            @Override
            public void onSuccess(int statusCode, String content) {
                resultView.showResultView(url, type, content);    //  结果的回调
            }

            ;

            // 开始执行前
            @Override
            public void onStart() {
                //显示进度框
            }

            // 失败，调用
            @Override
            public void onFailure(int statusCode, String content, Throwable error) {
                resultView.showErrorView(url, content);
            }

            // 完成后调用，失败，成功
            @Override
            public void onFinish() {
            }
        });
    }*/


    // OKHTTP请求方式（新加）
    //   =====================================================================================================================================================

    /***
     * GET请求
     *
     * @param url        请求地址
     * @param params     参数params封装的是参数
     * @param resultView resultView,用于回调数据结果
     * @param type       用户自定义参数
     */

    public void doGetRequest(final String url, String type, OkRequestParams params, final IResultView resultView) {
        GetBuilder getBuilder = OkHttpUtils.get();//
        ConcurrentHashMap<String, String> urlParams = params.getUrlParams();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            //paramsList.add1(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            //  System.out.println("key===" + entry.getKey());
            //System.out.println("value===" + entry.getValue());
            getBuilder.addParams(entry.getKey(), entry.getValue());
        }
        getBuilder.url(url)//
                .id(101)
                .tag(this)
                .build()//
                .execute(MyStringCallBack(url, type, resultView));
    }

    /**
     * POET请求
     *
     * @param url        请求地址
     * @param params     参数params封装的是参数
     * @param resultView resultView,用于回调数据结果
     * @param type       用户自定义参数
     */
    public void doPostRequest(final String url, String type, OkRequestParams params, final IResultView resultView) {
        PostFormBuilder postBuilder = OkHttpUtils.post();
        postBuilder.url(url);

        ConcurrentHashMap<String, String> urlParams = params.getUrlParams();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            //paramsList.add1(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            //System.out.println("key===" + entry.getKey());
            // System.out.println("value===" + entry.getValue());
            postBuilder.addParams(entry.getKey(), entry.getValue());
        }
//        String tag = urlParams.get("tag");

        postBuilder.id(101)
                .tag(resultView)
                .build()//
                .execute(MyStringCallBack(url, type, resultView));
    }

    /**
     * Post请求（用于Psot的值为JSON数据）
     *
     * @param url        请求地址
     * @param params     参数params封装的是参数
     * @param resultView resultView,用于回调数据结果
     * @param type       用户自定义参数
     */
    public void doPostStringRequest(final String url, String type, OkRequestParams params, final IResultView resultView) {
        OkHttpUtils
                .postString()//
                .url(url)//
                .id(101)
                .content(AbJsonUtil.toJson(params.getUrlParams()))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .tag(resultView)
                .build()//
                .execute(MyStringCallBack(url, type, resultView));

    }

    /**
     * Post文件
     *
     * @param url        请求地址
     * @param params     参数params封装的是参数
     * @param resultView resultView,用于回调数据结果
     *                   <p>
     *                   image 作为key ， 记得跟传进来的时候相对应（目前来说，暂时只是图片）
     * @param type       用户自定义参数
     */
    public void doPostFileRequest(final String url, String type, OkRequestParams params, final IResultView resultView) {
        // 发送文件
        ConcurrentHashMap<String, FileBody> fileParams = params.getFileParams();
        File file = fileParams.get("image").getFile();

        if (!file.exists()) {
            return;
        }
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .tag(this)
                .build()
                .execute(MyStringCallBack(url, type, resultView));
    }

    /**
     * Post上传文件， 可支持多图上传
     *
     * @param url        请求地址
     * @param params     参数params封装的是参数
     *                   imagefilelist 为图片文件list
     * @param resultView resultView,用于回调数据结果
     *                   imageFileList 作为key ，记得跟传进来的时候相对应（目前来说，暂时只是图片的list）
     * @param type       用户自定义参数
     */
    public void doUpLoadFileRequest(final String url, String type, OkRequestParams params, final IResultView resultView) {

        PostFormBuilder postBuilder = OkHttpUtils.post();
        ConcurrentHashMap<String, List<String>> fileListParams = params.getListParams();
        List<String> imagefilelist = fileListParams.get("imageFileList");

        for (int i = 0; i < imagefilelist.size(); i++) {
            //      Uri uri = Uri.fromFile(new File(imagefilelist.get(i)));
            File imageFile = new File(imagefilelist.get(i));
            postBuilder.addFile("imagefile", imageFile.getName(), imageFile);  // 添加文件
        }
        postBuilder
                .url(url)//
                .tag(this)
                .build()//
                .execute(MyStringCallBack(url, type, resultView));


        // 文件直接测试
      /*  PostFormBuilder postBuilder = OkHttpUtils.post();
        File file = new File(Environment.getExternalStorageDirectory(), "0001.png");

        postBuilder.addFile("image01","image01.jpg",file);
        postBuilder.addFile("file02","image02.jpg",file);
        String url02="http://ugiant.f3322.net:51900/photo/uploadMore";
        postBuilder
                .url(url02)//
                .build()//
                .execute(MyStringCallBack(url02,resultView));
*/

    }

    /***
     * Post下载文件，支持进度回调
     *
     * @param url        请求地址
     * @param params     参数params封装的是参数
     * @param resultView resultView,用于回调数据结果
     * @param type 用户自定义参数
     */
    public void doDownloadFileRequest(final String url, final String type, OkRequestParams params, final IResultView resultView) {
        // 下载文件
        OkHttpUtils//
                .get()//
                .url(url)//
                .tag(this)
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "文件名.jar")//
                {
                    @Override
                    public void onBefore(Request request, int id) {
                        resultView.showLoadView(url);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        resultView.showProgressView(url, (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        resultView.showErrorView(url, e, call.toString());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        resultView.showResultView(url, type, "返回文件处理");
                    }
                });


    }

    private StringCallback MyStringCallBack(final String url, final String type, final IResultView resultView) {
        return new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                resultView.showLoadView(url);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                resultView.showProgressView(url, (int) (100 * progress));
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                resultView.showErrorView(url, e, e.toString());
//                Log.e("tag", "url = " + url + "--- onError = " + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
//                Log.e("tag", "url = " + url + "--- content = " + response);
                resultView.showResultView(url, type, response.toString());
            }
        };
    }


}
