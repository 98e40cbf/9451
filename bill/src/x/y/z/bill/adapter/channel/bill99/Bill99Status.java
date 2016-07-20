/*******************************************************************************
 * Create on 2016年1月14日 下午6:32:38
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.channel.bill99;

/**
 *
 */
public interface Bill99Status {

    String SUCCESS = "00";
    String UNCERTAINTY_68 = "68";
    String UNCERTAINTY_C0 = "C0";

    String TXN_SUCCESS = "S";
    String TXN_FAILED = "F";
    String TXN_UNCERTAINTY = "P";
    String TXN_REVERSED = "R";

}
