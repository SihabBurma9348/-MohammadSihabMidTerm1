// name :- Mohammad Sihab Burma
// N no :- n10659348
package sihab.burma.n01659348;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_TABS = 3; // Number of tabs

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SihFragment();  // First tab fragment
            case 1:
                return new BurFragment();  // Second tab fragment
            case 2:
                return new N01659348Fragment();  // Third tab fragment
            default:
                return new SihFragment(); // Fallback to default fragment
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
