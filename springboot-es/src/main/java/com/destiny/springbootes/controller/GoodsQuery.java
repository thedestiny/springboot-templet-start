package com.destiny.springbootes.controller;

import java.io.Serializable;
import java.util.List;

public class GoodsQuery implements Serializable {


    private List<Long> merchantIds;

    public List<Long> getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(List<Long> merchantIds) {
        this.merchantIds = merchantIds;
    }
}
