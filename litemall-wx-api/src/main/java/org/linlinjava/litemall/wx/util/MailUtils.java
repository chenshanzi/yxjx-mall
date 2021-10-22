package org.linlinjava.litemall.wx.util;

import org.linlinjava.litemall.core.util.DateTimeUtil;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;

import java.util.List;

public class MailUtils {

    private static final String MAIL_CONTENT = "下单成功通知。\n订单编号：%s\n订单金额：%s\n下单时间：%s\n收货人姓名：%s\n收货人地址：%s\n收货人电话：%s\n用户留言：%s\n订单商品信息：\n";

    private static final String GOODS_INFO = "商品名称：%s , 购买数量：%s";

    /**
     * 获取订单邮件内容
     *
     * @return
     */
    public static String getMailContent(LitemallOrder order, List<LitemallOrderGoods> orderGoodsList) {
        String mailContent = String.format(MAIL_CONTENT, order.getOrderSn(), order.getOrderPrice().toString(),
                DateTimeUtil.getDateTimeDisplayString(order.getAddTime()),
                order.getConsignee(),
                order.getAddress(),
                order.getMobile(),
                order.getMessage());

        String goodsInfo = "";
        for (LitemallOrderGoods orderGoods : orderGoodsList) {
            goodsInfo += String.format(GOODS_INFO, orderGoods.getGoodsName(), orderGoods.getNumber()).concat("\n");
        }
        return mailContent.concat(goodsInfo);
    }
}
