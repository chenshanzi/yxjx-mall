package org.linlinjava.litemall.wx.web;

import com.google.common.collect.Lists;
import org.linlinjava.litemall.core.config.StoreProperties;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.StorageInfo;
import org.linlinjava.litemall.db.domain.StorageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 配置服务
 */
@RestController
@RequestMapping("/wx/config")
@Validated
public class WxConfigController {
    @Autowired
    private StoreProperties storeProperties;

    @GetMapping("/getStoreInfo")
    public Object getStoreInfo() {
        StorageVo storageVo = new StorageVo();
        if (storeProperties.getEnable()) {
            storageVo.setEnable(storeProperties.getEnable());
            storageVo.setScope(storeProperties.getScope());
            List<StorageInfo> storageInfos = Lists.newArrayList();
            storeProperties.getStore().forEach(i -> {
                StorageInfo storageInfo = new StorageInfo();
                storageInfo.setStoreName(i.getStoreName());
                storageInfo.setLatitude(i.getLatitude());
                storageInfo.setLongitude(i.getLongitude());
                storageInfos.add(storageInfo);
            });
            storageVo.setStorages(storageInfos);
        }
        return ResponseUtil.ok(storageVo);
    }
}