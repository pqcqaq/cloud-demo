package online.zust.services.chainservice.chainmaker.client;

import online.zust.services.chainservice.chainmaker.ChainManager;
import online.zust.services.chainservice.utils.FileUtils;
import online.zust.services.chainservice.utils.YamlPropertySourceFactory;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.config.SdkConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * @author qcqcqc
 */
@Configuration
@PropertySource(value = "classpath:sdk_config.yml", encoding = "utf-8", factory = YamlPropertySourceFactory.class)
public class InitClient {

    @Value("${chain_client.user_key_file_path}")
    private String clientUserKeyPath;
    @Value("${chain_client.user_crt_file_path}")
    private String clientUserCertPath;
    @Value("${chain_client.user_sign_key_file_path}")
    private String clientUserTlsKeyPath;
    @Value("${chain_client.user_sign_crt_file_path}")
    private String clientUserTlsCertPath;
    @Value("${chain_client.org_id}")
    private String orgId;
    @Value("${chain_client.path:sdk_config.yml}")
    private String sdkConfig;

    @Bean
    public ChainClient getChainClient() throws Exception {
        Yaml yaml = new Yaml();
        InputStream in = InitClient.class.getClassLoader().getResourceAsStream(sdkConfig);

        SdkConfig sdkConfig;
        sdkConfig = yaml.loadAs(in, SdkConfig.class);
        assert in != null;
        in.close();

        ChainManager chainManager = ChainManager.getInstance();
        ChainClient chainClient = chainManager.getChainClient(sdkConfig.getChainClient().getChainId());

        if (chainClient == null) {
            chainClient = chainManager.createChainClient(sdkConfig);
        }
        return chainClient;
    }

    @Bean
    public User getUser() throws Exception {
        return new User(orgId, FileUtils.getResourceFileBytes(clientUserKeyPath),
                FileUtils.getResourceFileBytes(clientUserCertPath),
                FileUtils.getResourceFileBytes(clientUserTlsKeyPath),
                FileUtils.getResourceFileBytes(clientUserTlsCertPath), false);
    }
}
