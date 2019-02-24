package br.com.sae.iot.ui.fragments.IndustryArea;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.sae.iot.R;
import br.com.sae.iot.dao.IndustryAreaDAO;
import br.com.sae.iot.database.SaeDatabase;
import br.com.sae.iot.model.IndustryArea;
import br.com.sae.iot.ui.adapter.IndustryAreaAdapter;

public class ListAreaFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextView listEmpty;
    private ListView mListView;
    private List<IndustryArea> industryAreas;
    private FloatingActionButton floatingActionButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mView = inflater.inflate(R.layout.fragment_area, container, false);
        listEmpty = (TextView) mView.findViewById(R.id.area_empty);
        floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.fab_area);
        floatingActionButton.setOnClickListener(this);
        listEmpty.setEnabled(true);
        new QueryDataTask().execute();
        return mView;
    }

    private void formIndustryArea() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeList() {
        listEmpty.setEnabled(false);
        mListView = mView.findViewById(R.id.list_areas_id);
        IndustryAreaAdapter adapter = new IndustryAreaAdapter(mView.getContext(), industryAreas);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

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
                IndustryAreaDAO dao = database.getIndustryAreaDao();
                industryAreas = dao.all();
                return false;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (industryAreas.size() > 0) {
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
