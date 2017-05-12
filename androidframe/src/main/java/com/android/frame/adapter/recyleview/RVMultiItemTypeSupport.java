package com.android.frame.adapter.recyleview;

public interface RVMultiItemTypeSupport<T>
{
	int getLayoutId(int itemType);

	int getItemViewType(int position, T t);
}