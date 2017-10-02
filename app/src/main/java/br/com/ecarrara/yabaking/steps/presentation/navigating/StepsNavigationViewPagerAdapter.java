package br.com.ecarrara.yabaking.steps.presentation.navigating;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import br.com.ecarrara.yabaking.steps.domain.entity.Step;

class StepsNavigationViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Step> steps;

    private static final int DEFAULT_STEPS_NAVIGATION_INITIAL_STEP = 0;

    StepsNavigationViewPagerAdapter(FragmentManager fragmentManager, List<Step> steps) {
        this(fragmentManager);
        this.steps = steps;
    }

    StepsNavigationViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return StepDetailFragment.newInstance(steps.get(position));
    }

    @Override
    public int getCount() {
        return steps.size();
    }

}
