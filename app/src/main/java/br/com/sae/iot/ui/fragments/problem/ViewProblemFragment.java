package br.com.sae.iot.ui.fragments.problem;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.sae.iot.R;
import br.com.sae.iot.model.Problem;
import br.com.sae.iot.utils.Constants;

public class ViewProblemFragment extends Fragment {

    private View mView;
    private TextView mTitleProblem, mDescProblem, mAreaProblem, mProductProblem;
    private ImageView mPhotoProblem;
    private FloatingActionButton floatingActionButton;

    private Problem problem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("SAE - Problemas");
        mView = inflater.inflate(R.layout.fragment_view_problem, container, false);
        problem = (Problem) getArguments().getSerializable(Constants.KEY_PROBLEM);
        initializeFields(problem);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initializeFields(Problem problem) {
        this.mTitleProblem = (TextView) mView.findViewById(R.id.title_problem_view);
        this.mDescProblem = (TextView) mView.findViewById(R.id.desc_problem_view);
        this.mAreaProblem = (TextView) mView.findViewById(R.id.area_problem_view);
        this.mProductProblem = (TextView) mView.findViewById(R.id.product_problem_view);
        this.mPhotoProblem = (ImageView) mView.findViewById(R.id.photo_problem_view);
        floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.fab_edit_problem);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditForm();
            }
        });
        setFields(problem);
    }

    private void openEditForm() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_PROBLEM, problem);
        Fragment fragment = new FormProblemFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();
    }

    private void setFields(Problem problem) {
        mTitleProblem.setText(problem.getTitleProblem());
        mDescProblem.setText(problem.getDescProblem());
        mAreaProblem.setText(problem.getAreaProblem());
        mProductProblem.setText(problem.getProductProblem());
        mPhotoProblem.setImageURI(Uri.parse(problem.getPathImage()));
    }

}
