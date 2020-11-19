package com.juntai.disabled.federation.base;

import com.juntai.disabled.basecomponent.base.BaseLazyFragment;
import com.juntai.disabled.federation.R;

/**
 * 扫一扫的空fragment
 * @aouther Ma
 * @date 2019/3/17
 */
public class EmptyScanFragment extends BaseLazyFragment {
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void lazyLoad() {
    }
}
