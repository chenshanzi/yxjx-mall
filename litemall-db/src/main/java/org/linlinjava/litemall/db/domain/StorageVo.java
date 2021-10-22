package org.linlinjava.litemall.db.domain;

import java.util.List;

public class StorageVo {
    private Boolean enable = Boolean.FALSE;

    private Integer scope;

    private List<StorageInfo> storages;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public List<StorageInfo> getStorages() {
        return storages;
    }

    public void setStorages(List<StorageInfo> storages) {
        this.storages = storages;
    }

    @Override
    public String toString() {
        return "StorageVo{" +
                "enable=" + enable +
                ", scope=" + scope +
                ", storages=" + storages +
                '}';
    }
}
