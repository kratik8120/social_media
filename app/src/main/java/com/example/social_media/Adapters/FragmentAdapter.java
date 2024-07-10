package com.example.social_media.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.social_media.Fragment.CallFragment;
import com.example.social_media.Fragment.ChatsFragment;
import com.example.social_media.Fragment.StatusFragment;

// here we have use the fragmentpageradapter because it ensures that
// fragments are correctly created,
// destroyed, and managed as the user swipes between pages
public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // in this case always start from 0
            case 0:
                return new ChatsFragment();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallFragment();
            default:
                return new ChatsFragment();
        }
    }

    @Override
    // return 3 because there are 3 fragments
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    // this is basically used to give the page title
    public CharSequence getPageTitle(int position) {

        String title=null;
        if(position==0)
        {
            title="Chats";
        }
        if(position==1)
        {
            title="Status";
        }
        if(position==2)
        {
            title="Calls";
        }
        return title;
    }
}
