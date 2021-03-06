package x.y.z.bill.builder.message;

import x.y.z.bill.constant.message.ResultTypeEnum;
import io.alpha.core.dto.ResultDTO;

public final class ResultBuilder {
    private ResultBuilder() {

    }

    /**
     * 生成操作结果
     * 
     * @param resultTypeEnum
     * @return
     */
    public static ResultDTO buildResult(ResultTypeEnum resultTypeEnum) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setStatus(resultTypeEnum.getStatus());
        resultDTO.setResultCode(resultTypeEnum.getCode());
        return resultDTO;
    }

    /**
     * 生成String数据类型操作结果
     * 
     * @param resultTypeEnum
     * @param data
     * @return
     */
    public static ResultDTO<String> buildResult(ResultTypeEnum resultTypeEnum, String data) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setStatus(resultTypeEnum.getStatus());
        resultDTO.setResultCode(resultTypeEnum.getCode());
        resultDTO.setData(data);
        return resultDTO;
    }
}
