/*******************************************************************************
 * Created on 2016年5月3日 下午5:18:26
 * Copyright (c) 深圳市小牛在线互联网信息咨询有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛在线互联网信息咨询有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.constant;

/**
 * 
 * @since 1.0.0
 * @version $Id$
 * @author {高磊} 2016年5月3日 下午5:18:26
 */
public class CommonConstant {

    // 生成对账文件存放的根路径，对应的zk路径
    public static final String RECON_PATH = "/config/payment/reconRootDir";

    // 生成对账文件存放的根路径
    public static final String RECON_PATH_VALUE = "/data/recon/";

    // account对应的zk路径
    public final static String ACCOUNT_PATH = "/config/payment/channel/account";

    // 渠道bean命名后缀
    public final static String BEAN_SUFFIX = "adapter";

    // 对账文件名后缀
    public final static String RECON_FILE_SUFFIX = ".csv";

    // 调度任务根目录
    public final static String TASK_PATH = "/config/payment/task/info/";

    // 调度任务触发时间
    public static final String COMMON_CRON = "0 0 5 * * ?";
}
