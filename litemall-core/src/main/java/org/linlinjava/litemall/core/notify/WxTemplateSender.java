package org.linlinjava.litemall.core.notify;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.service.LitemallUserFormIdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信模版消息通知
 */
public class WxTemplateSender {
    private final Log logger = LogFactory.getLog(WxTemplateSender.class);

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private LitemallUserFormIdService formIdService;

    private static final JsonParser JSON_PARSER = new JsonParser();

    /**
     * 统一服务消息
     * 小程序模板消息,发送服务通知
     *
     * @param touser      用户openid，可以是小程序的openid，也可以是公众号的openid
     * @param template_id 小程序模板消息模板id
     * @param page        小程序页面路径
     * @param data        小程序模板消息formid
     * @return
     * @author HGL

    public void sendWeappMessage(String touser, String template_id,
    String page, String[] data) {
    logger.info("小程序推送消息开始");

    LitemallUserFormid userFormid = formIdService.queryByOpenId(touser);
    if (userFormid == null) {
    return;
    }
    JSONObject obj = new JSONObject();
    JSONObject weapp_template_msg = new JSONObject();

    try {
    String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + wxMaService.getAccessToken();
    obj.put("touser", touser);
    weapp_template_msg.put("template_id", template_id);
    weapp_template_msg.put("page", page);
    weapp_template_msg.put("form_id", userFormid.getFormid());
    weapp_template_msg.put("data", createMsgData(data));
    weapp_template_msg.put("emphasis_keyword", createMsgData(data).get(0).getValue());
    obj.put("weapp_template_msg", weapp_template_msg);

    HttpClientUtil.Post(url, obj);
    if (formIdService.updateUserFormId(userFormid) == 0) {
    logger.warn("更新数据已失效");
    }
    } catch (Exception e) {
    logger.error("发送消息失败=====", e);
    }
    }*/

    /**
     * 发送微信消息(模板消息),不带跳转
     *
     * @param touser    用户 OpenID
     * @param templatId 模板消息ID
     * @param parms     详细内容
     */
    public void sendWechatMsg(String touser, String notifyType, String templatId, String[] parms) {
        sendSubscribeMsg(touser, notifyType, templatId, parms, "", "", "");
    }

    /**
     * 发送微信消息(模板消息),带跳转
     *
     * @param touser    用户 OpenID
     * @param templatId 模板消息ID
     * @param parms     详细内容
     * @param page      跳转页面
     */
    public void sendWechatMsg(String touser, String notifyType, String templatId, String[] parms, String page) {
        sendSubscribeMsg(touser, notifyType, templatId, parms, page, "", "");
    }

    //发送订阅消息
    private void sendSubscribeMsg(String touser, String notifyType, String templatId, String[] parms, String page, String color,
                                  String emphasisKeyword) {
        WxMaTemplateMessage msg = new WxMaTemplateMessage();
        msg.setTemplateId(templatId);
        msg.setToUser(touser);
        msg.setPage(page);
        msg.setColor(color);
        msg.setEmphasisKeyword(emphasisKeyword);
        msg.setData(createMsgData(notifyType, parms));
        try {
            sendSubscribeMsg(msg);
        } catch (Exception e) {
            logger.error("发送订阅消息异常=====", e);
        }
    }

    private void sendSubscribeMsg(WxMaTemplateMessage msg) throws WxErrorException {
        String responseContent = wxMaService.post("https://api.weixin.qq.com/cgi-bin/message/subscribe/send", msg.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get("errcode").getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent));
        }
    }

    private List<WxMaTemplateData> createMsgData(String notifyType, String[] parms) {
        List<WxMaTemplateData> dataList = new ArrayList<WxMaTemplateData>();
        if (NotifyType.PAY_SUCCEED.getType().equals(notifyType)) {
            dataList.add(new WxMaTemplateData("character_string6", parms[0]));
            dataList.add(new WxMaTemplateData("amount10", parms[1]));
            dataList.add(new WxMaTemplateData("date4", parms[2]));
            dataList.add(new WxMaTemplateData("thing15", parms[3]));
            dataList.add(new WxMaTemplateData("thing17", parms[4]));
        } else if (NotifyType.SHIP.getType().equals(notifyType)) {
            dataList.add(new WxMaTemplateData("time3", parms[0]));
            dataList.add(new WxMaTemplateData("character_string7", parms[1]));
            dataList.add(new WxMaTemplateData("thing21", parms[2]));
            dataList.add(new WxMaTemplateData("thing9", parms[3]));
        }else if(NotifyType.REFUND.getType().equals(notifyType  )){
            dataList.add(new WxMaTemplateData("character_string7", parms[0]));
            dataList.add(new WxMaTemplateData("amount3", parms[1]));
            dataList.add(new WxMaTemplateData("date4", parms[2]));
        }
        return dataList;
    }
}
