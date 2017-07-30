package com.developer.maheshwari.itunes.beanClass;

import com.developer.maheshwari.itunes.adapter.ListViewAdapter;
import com.developer.maheshwari.itunes.response.ResponseClass;

import java.util.ArrayList;

public interface onSingleListItemClicked
{
    public void OnSingleListItemClicked(ListViewAdapter adapter, ArrayList<ResponseClass.results> list, int position);
}
