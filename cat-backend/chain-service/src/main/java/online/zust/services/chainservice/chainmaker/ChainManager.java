package online.zust.services.chainservice.chainmaker;

import online.zust.services.chainservice.utils.FileUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.chainmaker.sdk.*;
import org.chainmaker.sdk.config.*;
import org.chainmaker.sdk.crypto.ChainMakerCryptoSuiteException;
import org.chainmaker.sdk.sync.TxResultDispatcher;
import org.chainmaker.sdk.utils.CryptoUtils;
import org.chainmaker.sdk.utils.UtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.*;

/**
 * @author qcqcqc
 */
public class ChainManager {

    private static final Logger logger = LoggerFactory.getLogger(ChainManager.class);

    static String OPENSSL_PROVIDER = "openSSL";
    static String TLS_NEGOTIATION = "TLS";

    // chains' map
    private Map<String, ChainClient> chains = new HashMap<>();
    // for singleton mode
    private static ChainManager chainManager = new ChainManager();

    private ChainManager() {
    }

    // singleton getInstance
    public static ChainManager getInstance() {
        return chainManager;
    }

    // get a chain client from chains
    public ChainClient getChainClient(String chainId) {
        return chains.get(chainId);
    }

    public synchronized ChainClient createChainClient(SdkConfig sdkConfig)
            throws ChainClientException, RpcServiceClientException, UtilsException, ChainMakerCryptoSuiteException, IOException {
        checkConfig(sdkConfig.getChainClient());
        String chainId = sdkConfig.getChainClient().getChainId();
        ChainClientConfig chainClientConfig = sdkConfig.getChainClient();
        dealChainClientConfig(chainClientConfig);

        User clientUser;

        if (chainClientConfig.getAuthType().equals(AuthType.PermissionedWithKey.getMsg()) ||
            chainClientConfig.getAuthType().equals(AuthType.Public.getMsg())) {
            clientUser = new User(sdkConfig.getChainClient().getOrgId());
            clientUser.setPukBytes(CryptoUtils.getPemStrFromPublicKey(chainClientConfig.getPublicKey()).getBytes());
            clientUser.setPublicKey(chainClientConfig.getPublicKey());
            clientUser.setPrivateKey(CryptoUtils.getPrivateKeyFromBytes(FileUtils.getFileBytes(chainClientConfig.getUserSignKeyFilePath())));
        } else {
            if (chainClientConfig.getPkcs11() == null) {
                chainClientConfig.setPkcs11(new Pkcs11Config(false));
            }
            boolean pkcs11Enabled = chainClientConfig.getPkcs11().isEnabled();
            clientUser = new User(sdkConfig.getChainClient().getOrgId(),
                    chainClientConfig.getUserSignKeyBytes(),
                    chainClientConfig.getUserSignCrtBytes(),
                    chainClientConfig.getUserKeyBytes(),
                    chainClientConfig.getUserCrtBytes(), pkcs11Enabled);
            if (sdkConfig.getChainClient().getAlias() != null && sdkConfig.getChainClient().getAlias().length() > 0) {
                clientUser.setAlias(sdkConfig.getChainClient().getAlias());
            }
        }

        clientUser.setAuthType(chainClientConfig.getAuthType());
        clientUser.setEnableTxResultDispatcher(chainClientConfig.getEnableTxResultDispatcher());

        List<Node> nodeList = new ArrayList<>();

        for (NodeConfig nodeConfig : sdkConfig.getChainClient().getNodes()) {
            List<byte[]> tlsCaCertList = new ArrayList<>();
            if (nodeConfig.getTrustRootBytes() == null && nodeConfig.getTrustRootPaths() != null) {
                for (String rootPath : nodeConfig.getTrustRootPaths()) {
                    List<File> fileList = FileUtils.getFilesByPath(rootPath);
                    for (File file : fileList) {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        tlsCaCertList.add(FileUtils.readAllBytes(fileInputStream));
                        fileInputStream.close();
                    }
                }

                byte[][] tlsCaCerts = new byte[tlsCaCertList.size()][];
                tlsCaCertList.toArray(tlsCaCerts);
                nodeConfig.setTrustRootBytes(tlsCaCerts);
            }

            String url;

            if (nodeConfig.isEnableTls()) {
                url = "grpcs://" + nodeConfig.getNodeAddr();
            } else {
                url = "grpc://" + nodeConfig.getNodeAddr();
            }

            Node node = new Node();
            node.setTlsCertBytes(nodeConfig.getTrustRootBytes());
            node.setHostname(nodeConfig.getTlsHostName());
            node.setGrpcUrl(url);
            node.setSslProvider(OPENSSL_PROVIDER);
            node.setNegotiationType(TLS_NEGOTIATION);
//            node.setConnectCount(nodeConfig.getConnCnt());
            nodeList.add(node);
        }

        Node[] nodes = new Node[nodeList.size()];
        nodeList.toArray(nodes);
        if (chainClientConfig.getConnPool() == null) {
            chainClientConfig.setConnPool(new ConnPoolConfig());
        }
        if (chainClientConfig.getArchiveCenterQueryFirst() == null) {
            chainClientConfig.setArchiveCenterQueryFirst(false);
        }
        return createChainClient(chainId, clientUser, nodes, chainClientConfig.getRpcClient().getMaxReceiveMessageSize(),
                chainClientConfig.getRetryInterval(), chainClientConfig.getRetryLimit(), chainClientConfig.getArchive(), chainClientConfig.getConnPool(),
                chainClientConfig.getArchiveCenterConfig(), chainClientConfig.getArchiveCenterQueryFirst(), chainClientConfig.getCrypto());
    }

    // create a chain client by chain id, client user and nodes
    private ChainClient createChainClient(String chainId, User clientUser, Node[] nodes, int messageSize, int retryInterval, int retryLimit,
                                          ArchiveConfig archiveConfig, ConnPoolConfig connPoolConfig, ArchiveCenterConfig archiveCenterConfig,
                                          Boolean archiveCenterQueryFirst, CryptoConfig cryptoConfig)
            throws RpcServiceClientException, UtilsException, ChainClientException {
        ChainClient chainClient = chains.get(chainId);
        if (chainClient != null) {
            return chainClient;
        }

        GrpcClientFactory grpcClientFactory = new GrpcClientFactory(nodes, clientUser, messageSize);

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 池中的最大连接数
        poolConfig.setMaxTotal(connPoolConfig.getMaxTotal());
        // 最少的空闲连接数
        poolConfig.setMinIdle(connPoolConfig.getMinIdle());
        // 最多的空闲连接数
        poolConfig.setMaxIdle(connPoolConfig.getMaxIdle());

        //空闲连接异常检测
        poolConfig.setTestWhileIdle(true);
        //当池中的资源耗尽时是否进行阻塞,设置false直接报错,true表示会一直等待，直到有可用资源
        poolConfig.setBlockWhenExhausted(connPoolConfig.isBlockWhenExhausted());
        //当设置阻塞时，等待时间
        poolConfig.setMaxWait(Duration.ofMillis(connPoolConfig.getMaxWaitMillis()));

        // 连接空闲的最小时间,达到此值后空闲连接可能会被移除,默认即为30分钟
        if (connPoolConfig.getMinEvictableIdleTime() != null) {
            poolConfig.setMinEvictableIdleTime(Duration.ofMillis(connPoolConfig.getMinEvictableIdleTime()));
        }
        // 连接空闲的最小时间,达到此值后并且当前空闲对象的数量大于最小空闲数量(minIdle)时，空闲连接可能会被移除,默认即为30分钟
        if (connPoolConfig.getSoftMinEvictableIdleTime() != null) {
            poolConfig.setSoftMinEvictableIdleTime(Duration.ofMillis(connPoolConfig.getSoftMinEvictableIdleTime()));
        }
        //此参数默认是-1，不会检查空闲连接。设置为正数后，才会检测
        if (connPoolConfig.getTimeBetweenEvictionRuns() != null) {
            poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(connPoolConfig.getTimeBetweenEvictionRuns()));
        }
        poolConfig.setLifo(false);


        GenericObjectPool<RpcServiceClient> connPool = new GenericObjectPool<>(grpcClientFactory, poolConfig);
        grpcClientFactory.setPool(connPool);
        chainClient = new ChainClient();
        chainClient.setChainId(chainId);
        chainClient.setRetryInterval(retryInterval);
        chainClient.setRetryLimit(retryLimit);
        chainClient.setClientUser(clientUser);
        chainClient.setConnectionPool(connPool);
        chainClient.setArchiveConfig(archiveConfig);
        chainClient.setArchiveCenterQueryFirst(archiveCenterQueryFirst);
        if (cryptoConfig != null && !Objects.equals(cryptoConfig.getHash(), "")) {
            chainClient.setHash(cryptoConfig.getHash());
        }
        if (clientUser.getEnableTxResultDispatcher() == null) {
            clientUser.setEnableTxResultDispatcher(false);
        }
        if (clientUser.getEnableTxResultDispatcher()) {
            chainClient.setDispatcher(new TxResultDispatcher(chainClient));
            chainClient.getDispatcher().start();
        }
        chainClient.setArchiveCenterConfig(archiveCenterConfig);

        chains.put(chainId, chainClient);

        if (chainClient.getClientUser().getAlias() != null && chainClient.getClientUser().getAlias().length() > 0) {
            try {
                chainClient.enableAlias();
            } catch (ChainMakerCryptoSuiteException | ChainClientException e) {
                throw new ChainClientException("enable Alias failed: " + e.getMessage());
            }
        }
        return chainClient;
    }

    private void dealChainClientConfig(ChainClientConfig chainClientConfig)
            throws UtilsException, ChainMakerCryptoSuiteException {

        String authType = chainClientConfig.getAuthType();
        if (authType.equals(AuthType.PermissionedWithKey.getMsg()) || authType.equals(AuthType.Public.getMsg())) {
            byte[] pemKey = FileUtils.getFileBytes(chainClientConfig.getUserSignKeyFilePath());

//            KeyFactory kf;
//            RSAPrivateKeySpec priv;
            PublicKey publicKey;
//            try {
//                kf = KeyFactory.getInstance("RSA");
//                priv = kf.getKeySpec(CryptoUtils.getPrivateKeyFromBytes(pemKey), RSAPrivateKeySpec.class);
//                RSAPublicKeySpec keySpec = new RSAPublicKeySpec(priv.getModulus(), BigInteger.valueOf(65537));
//                publicKey = kf.generatePublic(keySpec);
//            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//                throw new ChainMakerCryptoSuiteException("new RSAPublicKeySpec err: " + e.getMessage());
//
//            }
            PrivateKey privateKey = CryptoUtils.getPrivateKeyFromBytes(pemKey);
            try {
                publicKey = CryptoUtils.getPublicKeyFromPrivateKey(privateKey);
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
                throw new ChainMakerCryptoSuiteException("Get publicKey from privateKey Error: " + e.getMessage());
            }
            chainClientConfig.setPublicKey(publicKey);
        } else {
            chainClientConfig.setAuthType(AuthType.PermissionedWithCert.getMsg());
            byte[] userKeyBytes = chainClientConfig.getUserKeyBytes();

            if (userKeyBytes == null && chainClientConfig.getUserKeyFilePath() != null) {
                chainClientConfig.setUserKeyBytes(FileUtils.getFileBytes(chainClientConfig.getUserKeyFilePath()));
            }

            byte[] userCrtBytes = chainClientConfig.getUserCrtBytes();
            if (userCrtBytes == null && chainClientConfig.getUserCrtFilePath() != null) {
                chainClientConfig.setUserCrtBytes(FileUtils.getFileBytes(chainClientConfig.getUserCrtFilePath()));
            }

            byte[] userSignKeyBytes = chainClientConfig.getUserSignKeyBytes();
            if (userSignKeyBytes == null && chainClientConfig.getUserSignKeyFilePath() != null) {
                chainClientConfig.setUserSignKeyBytes(FileUtils.getFileBytes(chainClientConfig.getUserSignKeyFilePath()));
            }

            byte[] userSignCrtBytes = chainClientConfig.getUserSignCrtBytes();
            if (userSignCrtBytes == null && chainClientConfig.getUserSignCrtFilePath() != null) {
                chainClientConfig.setUserSignCrtBytes(FileUtils.getFileBytes(chainClientConfig.getUserSignCrtFilePath()));
            }
        }

    }

    private void checkConfig(ChainClientConfig chainClientConfig) throws ChainClientException {
        if (chainClientConfig == null) {
            logger.error("chainClientConfig is null, please check config");
            throw new ChainClientException("chainClientConfig is null");
        }

        checkNodeListConfig(chainClientConfig);
        checkUserConfig(chainClientConfig);
        checkChainConfig(chainClientConfig);
    }

    private void checkNodeListConfig(ChainClientConfig chainClientConfig) throws ChainClientException {

        NodeConfig[] nodeConfigs = chainClientConfig.getNodes();

        for (NodeConfig nodeConfig : nodeConfigs) {
//            if (nodeConfig.getConnCnt() <= 0 || nodeConfig.getConnCnt() > NodeConfig.maxConnCnt) {
//                throw new ChainClientException(String.format("node connection count should >0 && <=%d", NodeConfig.maxConnCnt));
//            }

            if (nodeConfig.isEnableTls()) {
                if (nodeConfig.getTrustRootBytes() == null && nodeConfig.getTrustRootPaths() == null) {
                    throw new ChainClientException("if node useTLS is open, should set caPaths or caCerts");
                }
            }

            if ("".equals(nodeConfig.getTlsHostName())) {
                throw new ChainClientException("if node useTLS is open, should set tls hostname");
            }
        }
    }

    private void checkUserConfig(ChainClientConfig chainClientConfig) throws ChainClientException {
        if ("".equals(chainClientConfig.getUserKeyFilePath()) && chainClientConfig.getUserKeyBytes() == null) {
            throw new ChainClientException("user key cannot be empty");
        }
        if ("".equals(chainClientConfig.getUserCrtFilePath()) && chainClientConfig.getUserCrtBytes() == null) {
            throw new ChainClientException("user cert cannot be empty");
        }
    }

    private void checkChainConfig(ChainClientConfig chainClientConfig) throws ChainClientException {
        if ("".equals(chainClientConfig.getChainId())) {
            throw new ChainClientException("chainId cannot be empty");
        }
    }
}
