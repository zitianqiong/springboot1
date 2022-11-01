package pers.zitianqiong.controller;

/**
 * <p>描述:</p>
 * @author 丛吉钰
 *//*
@CrossOrigin
@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;
    @NacosInjected
    private NamingService namingService;

    *//**
     * get方式传参调用nacos服务
     * @param request .
     * @return String
     **//*
    @RequestMapping("/getClientValueByGet")
    public String getClientValueByGet(HttpServletRequest request) {
        String serviceName = "server";
        String groupName = "web";
        String api = "/getServerValue";
        try {
            String message = request.getParameter("message");
            Instance instance = namingService.selectOneHealthyInstance(serviceName, groupName);
            String url = "http://" + instance.getIp() + ":" + instance.getPort() + api + "?" + "message=" + message;
            String r = restTemplate.getForObject(url, String.class);
            return r;
        } catch (Exception e) {
            return "";
        }
    }

    *//**
     * post方式传参调用nacos服务
     * @param request .
     * @return String
     **//*
    @RequestMapping("/getClientValueByPost")
    public String getClientValueByPost(HttpServletRequest request) {
        String serviceName = "server";
        String groupName = "web";
        String api = "/getServerValue";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            String message = request.getParameter("message");
            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("message", message);

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

            Instance instance = namingService.selectOneHealthyInstance(serviceName, groupName);
            String url = "http://" + instance.getIp() + ":" + instance.getPort() + api;
            String r = restTemplate.postForObject(url, httpEntity, String.class);
            return r;
        } catch (Exception e) {
            return "";
        }
    }
}
*/
