package com.kuaigui.yueche.driver.okhttp;

/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.kuaigui.yueche.driver.okhttp.fileutil.FileBody;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


//TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbRequestParams.java
 * 描述：Http请求参数
 *
 * @author chan
 * @date 2016-10-21
 */
public class OkRequestParams {

    /**
     * url参数.
     */
    protected ConcurrentHashMap<String, String> urlParams;

    /**
     * 文件参数.
     */
    protected ConcurrentHashMap<String, FileBody> fileParams;

    /**
     * 获取list.
     */
    protected ConcurrentHashMap<String, List<String>> listParams;

    private final static int boundaryLength = 32;
    private final static String boundaryAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
    private String boundary;

    /**
     * auto generate boundary string
     *
     * @return a boundary string
     */
    private String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < boundaryLength; ++i)
            sb.append(boundaryAlphabet.charAt(random.nextInt(boundaryAlphabet
                    .length())));
        return sb.toString();
    }


    /**
     * default boundary is auto generate {@link #getBoundary()}
     */
    public OkRequestParams() {
        super();
        boundary = getBoundary();
        urlParams = new ConcurrentHashMap<String, String>();
        fileParams = new ConcurrentHashMap<String, FileBody>();
        listParams = new ConcurrentHashMap<String, List<String>>();
    }

    /**
     * 添加一个文件参数
     *
     * @param attr 属性名
     * @param file 文件
     */
    public void put(String attr, File file) {
        if (attr != null && file != null) {
            fileParams.put(attr, new FileBody(file));
        }
    }

    /**
     * @return multipart boundary string
     */
    public String boundaryString() {
        return boundary;
    }

    /**
     * 添加一个int参数
     *
     * @param attr
     * @param value
     */
    public void put(String attr, int value) {
        try {
            urlParams.put(attr, String.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个String参数
     *
     * @param attr
     * @param value
     */
    public void put(String attr, String value) {
        try {
            if (attr != null && value != null) {
                urlParams.put(attr, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个List参数
     *
     * @param attr
     * @param value
     */
    public void put(String attr, List<String> value) {
        try {
            if (attr != null && value != null) {
                listParams.put(attr, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取url参数.
     *
     * @return the url params
     */
    public ConcurrentHashMap<String, String> getUrlParams() {
        return urlParams;
    }

    /**
     * 获取图片文件list
     *
     * @return
     */
    public ConcurrentHashMap<String, List<String>> getListParams() {
        return listParams;
    }

    /**
     * 获取文件参数.
     *
     * @return the file params
     */
    public ConcurrentHashMap<String, FileBody> getFileParams() {
        return fileParams;
    }


}
