package com.example.astrid.turismo.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.astrid.turismo.PageFragmentInfo;


public class PageAdapter  extends FragmentStatePagerAdapter{

    int mNrOfTabs;

    public PageAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNrOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                PageFragmentInfo tab1 = new PageFragmentInfo();
                return tab1;
            case 1:
                PageFragmentInfo tab2 = new PageFragmentInfo();
                return tab2;
            case 2:
                PageFragmentInfo tab3 = new PageFragmentInfo();
                return tab3;
        }

        return null;
    }

    @Override
    public int getCount() {
        return mNrOfTabs;
    }
}
