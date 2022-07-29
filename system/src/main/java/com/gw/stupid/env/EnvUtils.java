package com.gw.stupid.env;

import com.gw.stupid.util.InetUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    private static ConfigurableEnvironment configurableEnvironment = null;

    public static ConfigurableEnvironment getConfigurableEnvironment() {
        return configurableEnvironment;
    }

    public static Boolean getIsStandalone() {
        if (Objects.isNull(isStandalone)) {
            isStandalone = Boolean.getBoolean(EnvConstants.STANDALONE_MODE_KEYS);
        }
        return isStandalone;
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
        return configurableEnvironment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return configurableEnvironment.getProperty(key, defaultValue);
    }

    public static<T> T getProperty(String key, Class<T> clazz) {
        return configurableEnvironment.getProperty(key, clazz);
    }

    public static<T> T getProperty(String key, Class<T> clazz, T defaultValue) {
        return configurableEnvironment.getProperty(key, clazz, defaultValue);
    }

    public static List<String> getPropertyList(String key) {
        List<String> valueList = new LinkedList<>();
        for(int i = 0; i < Integer.MAX_VALUE ;i++) {
            String v = getConfigurableEnvironment().getProperty(key + "[" + i + "]");
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


    public static Resource getDefaultResource() {
        InputStream inputStream = EnvUtils.class.getResourceAsStream("/application.properties");
        assert inputStream != null;
        return new InputStreamResource(inputStream);
    }

}
