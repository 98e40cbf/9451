package x.y.z.bill.adapter.channel.dto.response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.request.EBankApplyPayRequestDTO;

public class EBankApplyPayResponseDTO extends ChannelBaseDTO {

    private static final long serialVersionUID = 1L;
    private String channelSendOrder;
    private String channelRecevOrder;
    private String payUrl;
    private Map<String, String> params;
    private String charset;

    public EBankApplyPayResponseDTO() {
        super();
    }

    public EBankApplyPayResponseDTO(EBankApplyPayRequestDTO request) {
        this.channelSendOrder = request.getChannelSendOrder();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public EBankApplyPayResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public String getChannelRecevOrder() {
        return channelRecevOrder;
    }

    public EBankApplyPayResponseDTO setChannelRecevOrder(String channelRecevOrder) {
        this.channelRecevOrder = channelRecevOrder;
        return this;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public EBankApplyPayResponseDTO setPayUrl(String payUrl) {
        this.payUrl = payUrl;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public EBankApplyPayResponseDTO setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public EBankApplyPayResponseDTO setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String createGetUrl() throws UnsupportedEncodingException {
        return this.createGetUrl(StringUtils.isNoneBlank(this.charset) ? this.charset : "GBK");
    }

    public String createGetUrl(String charset) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(payUrl);
        if (params != null && !params.isEmpty()) {
            if (payUrl.contains("?")) {
                if (!payUrl.endsWith("&")) {
                    sb.append("&");
                }
            } else {
                sb.append("?");
            }
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(URLEncoder.encode(entry.getKey(), charset));
                sb.append("=");
                if (StringUtils.isNotBlank(entry.getValue())) {
                    sb.append(URLEncoder.encode(entry.getValue(), charset));
                }
                sb.append("&");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    public String createPostForm() {
        StringBuffer sbHtml = new StringBuffer();

        if (StringUtils.isNotBlank(charset)) {
            if (payUrl.contains("?")) {
                if (!payUrl.endsWith("&")) {
                    payUrl = payUrl + "&";
                }
            } else {
                payUrl = payUrl + "?";
            }
            sbHtml.append("<form id=\"paysubmit\" name=\"paysubmit\" action=\"" + payUrl + "_input_charset=" + charset
                    + "\" method=\"post\">");
        } else {
            sbHtml.append("<form id=\"paysubmit\" name=\"paysubmit\" action=\"" + payUrl + "\" method=\"post\">");
        }

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sbHtml.append(
                        "<input type=\"hidden\" name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\"/>");
            }
        }
        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['paysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EBankApplyPayResponseDTO{");
        sb.append("channelSendOrder='").append(channelSendOrder).append('\'');
        sb.append(", channelRecevOrder='").append(channelRecevOrder).append('\'');
        sb.append(", payUrl='").append(payUrl).append('\'');
        sb.append(", params=").append(params);
        sb.append(", charset='").append(charset).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
