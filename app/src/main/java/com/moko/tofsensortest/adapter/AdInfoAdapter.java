package com.moko.tofsensortest.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moko.tofsensortest.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author: jun.liu
 * @date: 2022/10/18 15:50
 * @des:
 */
public class AdInfoAdapter extends BaseQuickAdapter<AdvInfo, BaseViewHolder> {
    public AdInfoAdapter(@Nullable List<AdvInfo> data) {
        super(R.layout.item_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvInfo item) {
        helper.setText(R.id.tvTime, item.scanTime);
        helper.setText(R.id.tvRangingDistance, String.format("距离值：%s", item.distance));
    }
}
