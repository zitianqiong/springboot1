package pers.zitianqiong.controller;

/**
 * <p>描述:</p>
 * @author 丛吉钰
 *//*
@Controller
@RequestMapping("discovery")
@CrossOrigin
public class DiscoveryController {

    @NacosInjected
    private NamingService namingService;

    *//**
     *
     * @param serviceName .
     * @return List<Instance>
     * @throws NacosException NacosException
     **//*
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    *//**
     *
     * @param request .
     * @return String
     **//*
    @RequestMapping("/getServerValue")
    @ResponseBody
    public String getServerValue(HttpServletRequest request) {
        String message = request.getParameter("message");
        return "the server receives the message : " + message;
    }
}
*/
