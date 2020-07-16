package project.mozgovanje.activity.main.bottomnav.adapter;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.google.android.material.bottomnavigation.BottomNavigationView;

@BindingMethods({
        @BindingMethod(
                type = BottomNavigationView.class,
                method = "setOnNavigationItemSelectedListener",
                attribute = "app:onNavigationItemSelected"
        ),
})
public class BottomNaviagionViewBindingAdapter {

}
