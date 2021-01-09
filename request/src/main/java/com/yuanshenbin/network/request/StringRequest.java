package com.yuanshenbin.network.request;


import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.tools.HeaderUtils;
import com.yanzhenjie.nohttp.tools.IOUtils;
import com.yuanshenbin.network.manager.NetworkManager;
import com.yuanshenbin.network.model.RecordModel;


/**
 * author : yijiupi
 * time   : 2018/4/17
 * desc   :
 */

public class StringRequest extends Request<String> {

    private String url, param;
    private long start;

    public StringRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        this.url = url;
        this.start = System.currentTimeMillis();
        DeviceBandwidthSampler.getInstance().startSampling();
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public long getStart() {
        return start;
    }

    @Override
    public String parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {


        if (responseBody == null || responseBody.length == 0) {
            if (NetworkManager.getInstance().getInitializeConfig().getIPrintLog() != null) {
                NetworkManager.getInstance().getInitializeConfig().getIPrintLog().onPrintResult("");
            }
            return "";
        }
        String charset = HeaderUtils.parseHeadValue(responseHeaders.getContentType(), "charset", "");
        if (NetworkManager.getInstance().getInitializeConfig().getIPrintLog() != null) {
            NetworkManager.getInstance().getInitializeConfig().getIPrintLog().onPrintResult(charset);
        }
        String result = IOUtils.toString(responseBody, charset);


        RecordModel model = null;
        if (NetworkManager.getInstance().getInitializeConfig().getIDevelopMode() != null) {
            String connectionQuality = ConnectionClassManager.getInstance().getCurrentBandwidthQualityStr();
            double downloadKBitsPerSecond = ConnectionClassManager.getInstance().getDownloadKBitsPerSecond();
            model = new RecordModel(url, param, result, System.currentTimeMillis() - start, connectionQuality, downloadKBitsPerSecond, this.getHeaders().toRequestHeaders());
            NetworkManager.getInstance().getInitializeConfig().getIDevelopMode().onRecord(model);
        }

        return result;

    }

}
