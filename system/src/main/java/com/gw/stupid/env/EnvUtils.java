package com.gw.stupid.env;

import com.gw.stupid.common.utils.IOUtils;
import com.gw.stupid.util.InetUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

/**
 * 环境属性工具类
 *
 * @author guanwu
 * @created on 2022-07-29 09:17:43
 **/
public class EnvUtils {

    public static final String STANDALONE_MODE = "standalone";

    public static final String STANDALONE_CLUSTER = "cluster";

    public static final String STUPID_HOME_KEY = "stupid.home";

    private static String localAddress = "";

    private static int port = -1;

    private static Boolean isStandalone = null;

    private static String stupidTmpDir = null;

    private static String stupidHome = null;

    private static final String DEFAULT_CONFIG_NAME = "application.properties";

    private static final String EXT_CONFIG_PATH_KEY = "stupid.spring.config.ext";

    private static ConfigurableEnvironment environment = null;

    public static void setEnvironment(ConfigurableEnvironment env) {
        EnvUtils.environment = env;
    }

    public static ConfigurableEnvironment getEnvironment() {
        return environment;
    }

    public static Boolean isStandalone() {
        if (Objects.isNull(isStandalone)) {
            isStandalone = Boolean.getBoolean(EnvConstants.STANDALONE_MODE_KEYS);
        }
        return isStandalone;
    }

    private static String confPath = "";

    public static String getClusterConfig() {
        return Paths.get(getStupidHome(),
                "conf",
                "cluster.conf")
                .toString();
    }

    public static List<String> readClusterConfig() throws IOException{
        List<String> ips = new ArrayList<>();
        try (Reader r = new InputStreamReader(new FileInputStream(EnvUtils.getClusterConfig()),
                StandardCharsets.UTF_8)) {
            List<String> contents = IOUtils.readLines(r);
            String sharpComment = "#";
            //ip:port,ip2:port,ip3:port
            for (String content : contents) {
                if (content.startsWith(sharpComment)) {
                    continue;
                }

                //todo 处理集群ip节点信息
            }

        } catch (FileNotFoundException ex) {

        }
        return ips;
    }

    public static String getConfPath() {
        if (StringUtils.isNotEmpty(confPath)) {
            return confPath;
        }

        EnvUtils.confPath = Paths.get(getStupidHome(), "conf").toString();

        return EnvUtils.confPath;
    }

    public static String getStupidTmpDir() {
        if (Objects.isNull(stupidTmpDir)) {
            stupidTmpDir = Paths.get(stupidHome, "data", "tmp").toString();
        }
        return stupidTmpDir;
    }

    public static String getStupidHome() {
        if (Objects.isNull(stupidHome)) {
            stupidHome = System.getProperty(STUPID_HOME_KEY);
            if(Objects.isNull(stupidHome)) {
                stupidHome = Paths.get(System.getProperty("user.home"),
                        "stupid").toString();
            }
        }
        return stupidHome;
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public static<T> T getProperty(String key, Class<T> clazz) {
        return environment.getProperty(key, clazz);
    }

    public static<T> T getProperty(String key, Class<T> clazz, T defaultValue) {
        return environment.getProperty(key, clazz, defaultValue);
    }

    public static List<String> getPropertyList(String key) {
        List<String> valueList = new LinkedList<>();
        for(int i = 0; i < Integer.MAX_VALUE ;i++) {
            String v = getEnvironment().getProperty(key + "[" + i + "]");
            if (StringUtils.isEmpty(v)) {
                break;
            }
            valueList.add(v);
        }
        return valueList;
    }

    public static String getLocalAddress() {
        if (StringUtils.isEmpty(localAddress)) {
            localAddress = InetUtils.getCurrentPeerIp() + ":" + getPort();
        }
        return localAddress;
    }

    public static int getPort() {
        if (port == -1) {
            port = getProperty("server.port", Integer.class, 9949);
        }
        return port;
    }

    public static void setPort(int port) {
        EnvUtils.port = port;
    }

    public static Resource getRelativeResource(String parentPath, String path) {
        try {
            InputStream in = new FileInputStream(Paths.get(parentPath, path).toFile());
            return new InputStreamResource(in);
        } catch (FileNotFoundException e) {
        }
        return null;

    }

    private static String FILE_PATH_PREFIX = "file:";

    public static Resource getCustomFileResource() {
        String path  = getProperty(EXT_CONFIG_PATH_KEY);
        if (StringUtils.isNotEmpty(path) && path.startsWith(FILE_PATH_PREFIX)) {
            String []paths = path.split(",",-1);
            String resourcePath = paths[paths.length-1].substring(FILE_PATH_PREFIX.length());
            return getRelativeResource(resourcePath, DEFAULT_CONFIG_NAME);
        }
        return null;
    }

    public static Resource getDefaultResource() {
        InputStream inputStream = EnvUtils.class.getResourceAsStream(String.format("/%s", DEFAULT_CONFIG_NAME));
        assert inputStream != null;
        return new InputStreamResource(inputStream);
    }

    public static Resource getAppConfigFileResource() {
        Resource customResource = getCustomFileResource();
        if (Objects.nonNull(customResource)) {
            return customResource;
        }
        return getDefaultResource();
    }

    public static Map<String, ?> loadProperties(Resource resource) throws IOException {
        return new OriginTrackedPropertiesLoader(resource).load();
    }

}
