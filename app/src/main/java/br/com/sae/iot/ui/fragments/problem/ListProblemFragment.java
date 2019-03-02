package br.com.sae.iot.ui.fragments.problem;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sae.iot.R;
import br.com.sae.iot.dao.ProblemDAO;
import br.com.sae.iot.database.SaeDatabase;
import br.com.sae.iot.model.Problem;
import br.com.sae.iot.ui.adapter.ProblemAdapter;

public class ListProblemFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private ListView mListView;
    private List<Problem> problems;
    private FloatingActionButton floatingActionButton;
    private static final String KEY_PROBLEM = "problem";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("SAE - Problemas");

        mView = inflater.inflate(R.layout.fragment_problem, container, false);
        floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.fab_problem);
        floatingActionButton.setOnClickListener(this);
        this.problems = new ArrayList<>();
        new ListProblemFragment.QueryDataTask().execute();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeList() {
        mListView = mView.findViewById(R.id.list_problem_id);
        ProblemAdapter adapter = new ProblemAdapter(mView.getContext(), problems);
        mListView.setAdapter(adapter);
        getActivity().registerForContextMenu(mListView);
        configureListenerClickItem(mListView);
    }

    private void configureListenerClickItem(ListView problems) {
        problems.setOnItemClickListener((adapterView, view, position, id) -> {
            Problem problem = (Problem) adapterView.getItemAtPosition(position);
            openViwerProblem(problem);
        });
    }

    private void openViwerProblem(Problem problem){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PROBLEM, problem);
        Fragment fragment = new ViewProblemFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = new FormProblemFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();
    }

    /**
     * @description Async Task para evitar tarefa pesada na thread de UI
     * evitando bloqueio
     */
    private class QueryDataTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                SaeDatabase database = SaeDatabase.getInstance(mView.getContext());
                ProblemDAO dao = database.getProblemDao();
                problems = dao.problemByIndustry(1);
                return false;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (problems.size() > 0) {
                initializeList();
            }
        }

        @Override
        protected void onCancelled(Object result) {
            super.onCancelled(result);
            Toast.makeText(mView.getContext(), "Error" + result.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
