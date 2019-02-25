package br.com.sae.iot.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import br.com.sae.iot.R;
import br.com.sae.iot.ui.fragments.problem.ListProblemFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private ImageView bgapp, clover;
    private LinearLayout textsplash, texthome, menus;
    private Animation frombottom;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        mView = inflater.inflate(R.layout.fragment_home, container, false);
        frombottom = AnimationUtils.loadAnimation(mView.getContext(), R.anim.frombottom);

        bgapp = (ImageView) mView.findViewById(R.id.bgapp);
        clover = (ImageView) mView.findViewById(R.id.clover);
        textsplash = (LinearLayout) mView.findViewById(R.id.textsplash);
        texthome = (LinearLayout) mView.findViewById(R.id.texthome);
        menus = (LinearLayout) mView.findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);

        menus.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = new ListProblemFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();
    }
}
