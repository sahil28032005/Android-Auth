package com.example.chat_application.FragmentsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.chat_application.Fragments.CallsFragment;
import com.example.chat_application.Fragments.ChatsFragment;
import com.example.chat_application.Fragments.StatusFragment;

public class FargmentsGrasper extends FragmentStatePagerAdapter {
    public FargmentsGrasper(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new ChatsFragment();
            case 1:return new StatusFragment();
            case 2:return new CallsFragment();
            default:return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tabTitles="null";
        if(position==0){
            tabTitles="CHATS";
        }
        else if (position==1) {
        tabTitles="STATUS";
        }
        else {
        tabTitles="CALLS";
        }
        return tabTitles;
        }
//        return super.getPageTitle(position);
    }

