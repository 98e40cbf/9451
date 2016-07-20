package x.y.z.bill.adapter.util;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;

/**
 *
 */
public class RequestContext {

    private static final ThreadLocal<ChannelBaseDTO> THREAD_LOCAL = new ThreadLocal<ChannelBaseDTO>() {
        protected ChannelBaseDTO initialValue() {
            return null;
        }
    };

    public static final ChannelBaseDTO get() {
        return THREAD_LOCAL.get();
    }

    public static final ChannelBaseDTO reset(final ChannelBaseDTO data) {
        THREAD_LOCAL.set(data);
        return THREAD_LOCAL.get();
    }

}
