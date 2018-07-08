package com.liye.mycontacts.myContacts;


/**
 * Created by dell-pc on 2018.7.7.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liye.mycontacts.R;

public class ThirdFragment extends android.support.v4.app.Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, null);
        return view;
    }

}