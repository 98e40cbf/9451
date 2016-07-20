/*******************************************************************************
 * Create on 2015年2月2日 下午2:00:20
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.channel.bill99.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 */
@XStreamAlias("pciInfo")
public class PCIInfo {

    @XStreamAlias("bankId")
    private String bankId;
    @XStreamAlias("storablePan")
    private String storablePan;
    @XStreamAlias("shortPhoneNo")
    private String shortPhoneNo;
    @XStreamAlias("phoneNO")
    private String phoneNO;

    public String getBankId() {
        return bankId;
    }

    public String getStorablePan() {
        return storablePan;
    }

    public String getShortPhoneNo() {
        return shortPhoneNo;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

}
