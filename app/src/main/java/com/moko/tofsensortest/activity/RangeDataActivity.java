package com.moko.tofsensortest.activity;

import android.os.Bundle;
import android.view.View;

import com.moko.ble.lib.MokoConstants;
import com.moko.ble.lib.event.ConnectStatusEvent;
import com.moko.ble.lib.event.OrderTaskResponseEvent;
import com.moko.ble.lib.task.OrderTaskResponse;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.tof.MokoSupport;
import com.moko.support.tof.OrderTaskAssembler;
import com.moko.support.tof.entity.OrderCHAR;
import com.moko.support.tof.entity.ParamsKeyEnum;
import com.moko.tofsensortest.R;
import com.moko.tofsensortest.adapter.AdInfoAdapter;
import com.moko.tofsensortest.adapter.AdvInfo;
import com.moko.tofsensortest.databinding.TofActivityRangeDataBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author: jun.liu
 * @date: 2023/11/7 10:45
 * @des:
 */
public class RangeDataActivity extends BaseActivity {
    private TofActivityRangeDataBinding mBind;
    private AdInfoAdapter adapter;

    private final List<AdvInfo> advInfoList = new LinkedList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = TofActivityRangeDataBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        EventBus.getDefault().register(this);
        adapter = new AdInfoAdapter(advInfoList);
        mBind.rvList.setAdapter(adapter);
        MokoSupport.getInstance().sendOrder(OrderTaskAssembler.getTofMode());
        mBind.tvStart.setOnClickListener(v -> {
            advInfoList.clear();
            adapter.replaceData(advInfoList);
            MokoSupport.getInstance().enableToFSensorNotify();
            mBind.tvStart.setEnabled(false);
            mBind.tvStart.setBackgroundResource(R.drawable.shape_radius6_greey);
            mBind.tvStop.setEnabled(true);
            mBind.tvStop.setBackgroundResource(R.drawable.shape_radius_blue_btn_bg);
        });
        mBind.tvStop.setOnClickListener(v -> {
            MokoSupport.getInstance().disableToFSensorNotify();
            mBind.tvStart.setEnabled(true);
            mBind.tvStart.setBackgroundResource(R.drawable.shape_radius_blue_btn_bg);
            mBind.tvStop.setEnabled(false);
            mBind.tvStop.setBackgroundResource(R.drawable.shape_radius6_greey);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 200)
    public void onConnectStatusEvent(ConnectStatusEvent event) {
        String action = event.getAction();
        runOnUiThread(() -> {
            if (MokoConstants.ACTION_DISCONNECTED.equals(action)) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 200)
    public void onOrderTaskResponseEvent(OrderTaskResponseEvent event) {
        final String action = event.getAction();
        runOnUiThread(() -> {
            if (MokoConstants.ACTION_CURRENT_DATA.equals(action)) {
                OrderTaskResponse response = event.getResponse();
                OrderCHAR orderCHAR = (OrderCHAR) response.orderCHAR;
                byte[] value = response.responseValue;
                if (Objects.requireNonNull(orderCHAR) == OrderCHAR.CHAR_TOF_NOTIFY) {
                    int result = MokoUtils.toInt(value);
                    advInfoList.add(0, new AdvInfo(sdf.format(new Date(System.currentTimeMillis())), result+"mm"));
                    adapter.notifyItemInserted(0);
                    mBind.rvList.scrollToPosition(0);
                }
            }
            if (MokoConstants.ACTION_ORDER_FINISH.equals(action)) {
                dismissLoadingProgressDialog();
            }
            if (MokoConstants.ACTION_ORDER_RESULT.equals(action)) {
                OrderTaskResponse response = event.getResponse();
                OrderCHAR orderCHAR = (OrderCHAR) response.orderCHAR;
                byte[] value = response.responseValue;
                if (Objects.requireNonNull(orderCHAR) == OrderCHAR.CHAR_PARAMS) {
                    int head = value[0] & 0xFF;
                    int cmd = value[1] & 0xFF;
                    if (head != 0xEB) return;
                    ParamsKeyEnum paramsKeyEnum = ParamsKeyEnum.fromParamKey(cmd);
                    if (paramsKeyEnum == null) return;
                    if (paramsKeyEnum == ParamsKeyEnum.KEY_READ_TOF_DISTANCE_MODE) {
                        int mode = value[4] & 0xff;
                        //短距模式
                        mBind.tvMode.setText(mode == 1 ? "Short range mode" : "Long range mode");
                    }
                }
            }
        });
    }

    public void onBack(View view) {
        back();
    }

    private void back(){
        MokoSupport.getInstance().disableToFSensorNotify();
        advInfoList.clear();
        finish();
    }
    @Override
    public void onBackPressed() {
        back();
    }
}
