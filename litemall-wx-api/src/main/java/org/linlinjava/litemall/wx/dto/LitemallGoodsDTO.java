package org.linlinjava.litemall.wx.dto;

import org.linlinjava.litemall.db.domain.LitemallGoods;

public class LitemallGoodsDTO extends LitemallGoods {
    private Object specificationList;

    private Object productList;

    public Object getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(Object specificationList) {
        this.specificationList = specificationList;
    }

    public Object getProductList() {
        return productList;
    }

    public void setProductList(Object productList) {
        this.productList = productList;
    }
}
