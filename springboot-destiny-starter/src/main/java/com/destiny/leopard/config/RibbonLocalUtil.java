//package com.destiny.leopard.config;
//
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Enumeration;
//import java.util.List;
//
///**
// * 本地调式feign工具类
// * 在application.yml配置中Apollo的 namespaces 最前面添加 feign-switch 命名空间即可,记得还原不要提交
// * @author yunyang
// */
//@Slf4j
//public class RibbonLocalUtil implements IRule {
//
//    ILoadBalancer balancer = new BaseLoadBalancer();
//
//    @Override
//    public Server choose(Object key) {
//        List<Server> allServers = balancer.getAllServers();
//        String localIp = "";
//        try {
////            localIp = InetAddress.getLocalHost().getHostAddress();
//            localIp = getIpAdd();
//
//        } catch (Exception e) {
//            log.info("未找到本机ip：", e);
//        }
//
//        for (Server server : allServers) {
//            if (server.getHost().equalsIgnoreCase(localIp)) {
//                log.info("本次Feign调用地址：host - [{}] Server - [{}]", server.getHost(), server);
//                return server;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void setLoadBalancer(ILoadBalancer lb) {
//        this.balancer = lb;
//    }
//
//    @Override
//    public ILoadBalancer getLoadBalancer() {
//        return this.balancer;
//    }
//
//    /**
//     * 根据网卡获得IP地址
//     * @return
//     * @throws SocketException
//     * @throws UnknownHostException
//     */
//    public String getIpAdd() throws SocketException {
//        String ip="";
//        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//            NetworkInterface intf = en.nextElement();
//            String name = intf.getName();
//            if (!name.contains("docker") && !name.contains("lo")) {
//                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//                    //获得IP
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()) {
//                        String ipaddress = inetAddress.getHostAddress().toString();
//                        if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
//
//                            System.out.println(ipaddress);
//                            if(!"127.0.0.1".equals(ip)){
//                                ip = ipaddress;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return ip;
//    }
//}
