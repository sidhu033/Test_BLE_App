package gamsystech.user.newbleupdated.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import gamsystech.user.newbleupdated.fragments.dashboard_fragment.DashBoardFragment;
import gamsystech.user.newbleupdated.fragments.summary_fragment.SummaryFragment1;
import gamsystech.user.newbleupdated.model.TreatmentRequestModel;
import static gamsystech.user.newbleupdated.adapters.DashboardTabs.DASHBOARD;
import static gamsystech.user.newbleupdated.adapters.DashboardTabs.SUMMARY;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter
{
    Context context;
    public Fragment dashBoardFragment;
    TreatmentRequestModel treatmentRequestModel ;

    public HomeFragmentPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        dashBoardFragment = DashBoardFragment.newInstance(DASHBOARD);
      //  summaryfragment = SummaryFragment.newInstance(SUMMARY);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case DASHBOARD:
                return dashBoardFragment;

            case  SUMMARY:

                SummaryFragment1 summaryFragment1  = new SummaryFragment1();
                summaryFragment1.newInstance();
                return summaryFragment1;


            default:
                return DashBoardFragment.newInstance(position);
        }

    }

    @Override
    public int getItemPosition(@NonNull Object object)
    {
        return POSITION_NONE;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container)
    {
        try {
            super.finishUpdate(container);
        } catch (Exception nullPointerException)
        {
            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }
}
