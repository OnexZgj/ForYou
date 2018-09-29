package com.it.onex.foryou.base;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import java.util.List;

/**
 * 加载显示Fragment的ViewPagerAdapter基类
 * 提供可以刷新的方法
 *
 * Created by OnexZgj on 2018/6/20:10:41.
 * des:
 */
public class BasePagerFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragmentList;
    private FragmentManager mFragmentManager;
    /**下面两个值用来保存Fragment的位置信息，用以判断该位置是否需要更新*/
    private SparseArray<String> mFragmentPositionMap;
    private SparseArray<String> mFragmentPositionMapAfterUpdate;
    public BasePagerFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragmentList = fragments;
        mFragmentManager = fm;
        mFragmentPositionMap = new SparseArray<>();
        mFragmentPositionMapAfterUpdate = new SparseArray<>();
        setFragmentPositionMap();
        setFragmentPositionMapForUpdate();
    }
    /**
     * 保存更新之前的位置信息，用<hashCode, position>的键值对结构来保存
     */
    private void setFragmentPositionMap() {
        mFragmentPositionMap.clear();
        for (int i = 0; i < mFragmentList.size(); i++) {
            mFragmentPositionMap.put(Long.valueOf(getItemId(i)).intValue(), String.valueOf(i));
        }
    }
    /**
     * 保存更新之后的位置信息，用<hashCode, position>的键值对结构来保存
     */
    private void setFragmentPositionMapForUpdate() {
        mFragmentPositionMapAfterUpdate.clear();
        for (int i = 0; i < mFragmentList.size(); i++) {
            mFragmentPositionMapAfterUpdate.put(Long.valueOf(getItemId(i)).intValue(),  String.valueOf(i));
        }
    }
    /**
     * 在此方法中找到需要更新的位置返回POSITION_NONE，否则返回POSITION_UNCHANGED即可
     */
    @Override
    public int getItemPosition(Object object) {
        int hashCode = object.hashCode();
        //查找object在更新后的列表中的位置
        String position = mFragmentPositionMapAfterUpdate.get(hashCode);
        //更新后的列表中不存在该object的位置了
        if (position == null) {
            return POSITION_NONE;
        } else {
            //如果更新后的列表中存在该object的位置, 查找该object之前的位置并判断位置是否发生了变化
            int size = mFragmentPositionMap.size();
            for (int i = 0; i < size ; i++) {
                int key = mFragmentPositionMap.keyAt(i);
                if (key == hashCode) {
                    String index = mFragmentPositionMap.get(key);
                    if (position.equals(index)) {
                        //位置没变依然返回POSITION_UNCHANGED
                        return POSITION_UNCHANGED;
                    } else {
                        //位置变了
                        return POSITION_NONE;
                    }
                }
            }
        }
        return POSITION_UNCHANGED;
    }
    /**
     * 将指定的Fragment替换/更新为新的Fragment
     * @param oldFragment 旧Fragment
     * @param newFragment 新Fragment
     */
    public void replaceFragment(BaseFragment oldFragment, BaseFragment newFragment) {
        int position = mFragmentList.indexOf(oldFragment);
        if (position == -1) {
            return;
        }
        //从Transaction移除旧的Fragment
        removeFragmentInternal(oldFragment);
        //替换List中对应的Fragment
        mFragmentList.set(position, newFragment);
        //刷新Adapter
        notifyItemChanged();
    }
    /**
     * 将指定位置的Fragment替换/更新为新的Fragment，同{@link #replaceFragment(BaseFragment oldFragment, BaseFragment newFragment)}
     * @param position    旧Fragment的位置
     * @param newFragment 新Fragment
     */
    public void replaceFragment(int position, BaseFragment newFragment) {
        BaseFragment oldFragment = mFragmentList.get(position);
        removeFragmentInternal(oldFragment);
        mFragmentList.set(position, newFragment);
        notifyItemChanged();
    }
    /**
     * 移除指定的Fragment
     * @param fragment 目标Fragment
     */
    public void removeFragment(BaseFragment fragment) {
        //先从List中移除
        mFragmentList.remove(fragment);
        //然后从Transaction移除
        removeFragmentInternal(fragment);
        //最后刷新Adapter
        notifyItemChanged();
    }
    /**
     * 移除指定位置的Fragment，同 {@link #removeFragment(BaseFragment fragment)}
     * @param position
     */
    public void removeFragment(int position) {
        BaseFragment fragment = mFragmentList.get(position);
        //然后从List中移除
        mFragmentList.remove(fragment);
        //先从Transaction移除
        removeFragmentInternal(fragment);
        //最后刷新Adapter
        notifyItemChanged();
    }
    /**
     * 添加Fragment
     * @param fragment 目标Fragment
     */
    public void addFragment(BaseFragment fragment) {
        mFragmentList.add(fragment);
        notifyItemChanged();
    }
    /**
     * 在指定位置插入一个Fragment
     * @param position 插入位置
     * @param fragment 目标Fragment
     */
    public void insertFragment(int position, BaseFragment fragment) {
        mFragmentList.add(position, fragment);
        notifyItemChanged();
    }
    private void notifyItemChanged() {
        //刷新之前重新收集位置信息
        setFragmentPositionMapForUpdate();
        notifyDataSetChanged();
        setFragmentPositionMap();
    }
    /**
     * 从Transaction移除Fragment
     * @param fragment 目标Fragment
     */
    private void removeFragmentInternal(BaseFragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitNow();
    }
    /**
     * 此方法不用position做返回值即可破解fragment tag异常的错误
     */
    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode，其实这里不用hashCode用自定义的可以关联当前Item对象的唯一值也可以，只要不是直接返回position
        return mFragmentList.get(position).hashCode();
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public List<BaseFragment> getFragments() {
        return mFragmentList;
    }
}