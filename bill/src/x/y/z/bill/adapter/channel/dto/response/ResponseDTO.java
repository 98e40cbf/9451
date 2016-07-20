/*******************************************************************************
 * Create on 2016年1月19日 下午3:14:37
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.channel.dto.response;

import io.alpha.core.model.BaseObject;

/**
 *
 */
public class ResponseDTO<T> extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Status status;

    private T data;

    private String responseCode;

    private String responseMessage;

    /**
     * @param status
     * @param data
     * @param responseCode
     * @param responseMessage
     */
    public ResponseDTO(Status status, T data, String responseCode, String responseMessage) {
        super();
        this.status = status;
        this.data = data;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ResponseDTO() {
        super();
    }

    public static final <T> ResponseDTO<T> build(Status status, T data, String responseCode, String responseMessage) {
        ResponseDTO<T> responseDTO = new ResponseDTO<T>(status, data, responseCode, responseMessage);
        return responseDTO;
    }

    public static final <T> ResponseDTO<T> buildTrading(T data, String responseCode, String responseMessage) {
        ResponseDTO<T> responseDTO = new ResponseDTO<T>(Status.UNCERTAINTY, data, responseCode, responseMessage);
        return responseDTO;
    }

    public static final <T> ResponseDTO<T> buildFail(T data, String responseCode, String responseMessage) {
        ResponseDTO<T> responseDTO = new ResponseDTO<T>(Status.FAILED, data, responseCode, responseMessage);
        return responseDTO;
    }

    public static final <T> ResponseDTO<T> buildSuccess(T data, String responseCode, String responseMessage) {
        ResponseDTO<T> responseDTO = new ResponseDTO<T>(Status.SUCCESS, data, responseCode, responseMessage);
        return responseDTO;
    }

    public static final <T> ResponseDTO<T> buildException(T data, String responseCode, String responseMessage) {
        ResponseDTO<T> responseDTO = new ResponseDTO<T>(Status.EXCEPTION, data, responseCode, responseMessage);
        return responseDTO;
    }

    public static final <T> ResponseDTO<T> buildException(String responseCode, String responseMessage) {
        ResponseDTO<T> responseDTO = new ResponseDTO<T>(Status.EXCEPTION, null, responseCode, responseMessage);
        return responseDTO;
    }

    public Status getStatus() {
        return status;
    }

    public ResponseDTO setStatus(Status status) {
        this.status = status;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseDTO<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public ResponseDTO setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public ResponseDTO setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    public enum Status {
        SUCCESS("00", "业务成功"), FAILED("01", "业务失败"), UNCERTAINTY("-1", "业务进行中"), EXCEPTION("-2", "系统异常");
        private String code;
        private String desc;

        Status(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String desc() {
            return this.desc;
        }
    }
}
