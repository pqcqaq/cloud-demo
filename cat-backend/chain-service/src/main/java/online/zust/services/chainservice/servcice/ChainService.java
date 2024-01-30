package online.zust.services.chainservice.servcice;

import org.chainmaker.pb.config.ChainConfigOuterClass;

/**
 * @author qcqcqc
 */
public interface ChainService {
    /**
     * 获取链配置
     *
     * @return 链配置
     */
    ChainConfigOuterClass.ChainConfig getChainConfig();
}
