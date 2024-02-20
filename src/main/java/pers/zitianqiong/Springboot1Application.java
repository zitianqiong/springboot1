package pers.zitianqiong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import pers.zitianqiong.utils.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author zitianqiong
 */
@SpringBootApplication
@EnableCaching
@Slf4j
@ServletComponentScan
public class Springboot1Application {

    /**
     * @param args .
     * @throws UnknownHostException UnknownHostException
     **/
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Springboot1Application.class, args);
        Environment env = application.getEnvironment();
        InetAddress address = getLocalHostExactAddress();
        if (address != null){
            String ip = address.getHostAddress();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            if (path != null){
                path = "/"+path;
            }else{
                path = StringUtils.EMPTY;
            }
            boolean swagger = Boolean.parseBoolean(env.getProperty("springdoc.swagger-ui.enabled"));

            log.info("\n----------------------------------------------------------\n\t"
                            + "系统应用正在运行! 请访问URLs:\n\t"
                            + "本地: \t\thttp://localhost:{}{}/\n\t"
                            + "External: \thttp://{}:{}{}/\n"
                            + (swagger? "\tdoc: \t\thttp://localhost:{}{}/doc.html\n" : "")
                            + (swagger? "\tswagger: \thttp://localhost:{}{}/swagger-ui/index.html\n" : "")
                            + "----------------------------------------------------------"
                    , port, path, ip, port, path, port, path, port, path);
        }

    }

    public static InetAddress getLocalHostExactAddress() {
        try {
            InetAddress candidateAddress = null;

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface iface = networkInterfaces.nextElement();
                // 该网卡接口下的ip会有多个，也需要一个个的遍历，找到自己所需要的
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddrs.nextElement();
                    // 排除loopback回环类型地址（不管是IPv4还是IPv6 只要是回环地址都会返回true）
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了 就是我们要找的
                            // ~~~~~~~~~~~~~绝大部分情况下都会在此处返回你的ip地址值~~~~~~~~~~~~~
                            return inetAddr;
                        }

                        // 若不是site-local地址 那就记录下该地址当作候选
                        if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }

                    }
                }
            }

            // 如果出去loopback回环地之外无其它地址了，那就回退到原始方案吧
            return candidateAddress == null ? InetAddress.getLocalHost() : candidateAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
