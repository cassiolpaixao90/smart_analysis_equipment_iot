package br.com.sae.iot.ui.fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.sae.iot.R;
import br.com.sae.iot.dao.IndustryDAO;
import br.com.sae.iot.database.SaeDatabase;
import br.com.sae.iot.model.Industry;
import br.com.sae.iot.utils.FormValidator;

public class IndustryFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private EditText mEditTextName;
    private Button mButtonSave;
    private IndustryDAO dao;
    private SaeDatabase database;
    private Industry industry;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mView = inflater.inflate(R.layout.fragement_industry, container, false);
        initializeFields();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (!formIsValid()) {
            return;
        }
        new IndustryTask().execute();
    }


    private boolean formIsValid() {
        EditText forms[] = {mEditTextName};
        return FormValidator.isValid(getActivity(), forms);
    }

    private void initializeFields() {
        this.mEditTextName = mView.findViewById(R.id.name_company_id);
        this.mButtonSave = mView.findViewById(R.id.btn_save);
        this.mButtonSave.setOnClickListener(this);
    }

    private Industry getByIndustry() {
        return this.industry = new Industry(this.mEditTextName.getText().toString());
    }

    /**
     * @description Async Task para evitar tarefa pesada na thread de UI
     * evitando bloqueio
     */
    private class IndustryTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                SaeDatabase database = SaeDatabase.getInstance(mView.getContext());
                IndustryDAO dao = database.getIndustryDao();
                dao.save(getByIndustry());
                return false;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mEditTextName.setText("");
            Toast.makeText(mView.getContext(), "Salvo com sucesso!", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onCancelled(Object result) {
            super.onCancelled(result);
            Toast.makeText(mView.getContext(), "Error" + result.toString(), Toast.LENGTH_LONG).show();
        }
    }


}

