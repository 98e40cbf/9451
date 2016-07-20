package x.y.z.bill.adapter.httpclient;

/**
 * User: cuiyonghui Date: 13-6-13 Time: 下午3:35
 */
public class HttpResult {
    private int resultCode;
    private String responseBody;
    private long executeTime;

    public HttpResult(int resultCode, String responseBody) {
        this.resultCode = resultCode;
        this.responseBody = responseBody;
    }

    public HttpResult() {
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HttpResult{");
        sb.append("resultCode=").append(resultCode);
        sb.append(", executeTime=").append(executeTime);
        sb.append(", responseBody='").append(responseBody).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
