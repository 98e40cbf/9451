/*******************************************************************************
 * Created on 2016年5月18日 下午3:17:34
 * Copyright (c) 深圳市小牛在线互联网信息咨询有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛在线互联网信息咨询有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.exception;

/**
 * 
 * @since 1.0.0
 * @version $Id$
 * @author {高磊} 2016年5月18日 下午3:17:34
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1;

    private String errorCode;
    private String message;

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ServiceException(String errorCode, String cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
