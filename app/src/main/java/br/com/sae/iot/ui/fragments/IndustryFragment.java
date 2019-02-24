package br.com.sae.iot.ui.fragments;

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

import br.com.sae.iot.R;
import br.com.sae.iot.utils.FormValidator;

public class IndustryFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private EditText mEditTextName;
    private Button mButtonSave;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mView = inflater.inflate(R.layout.fragement_industry, container, false);

        this.mEditTextName = mView.findViewById(R.id.name_company_id);
        this.mButtonSave = mView.findViewById(R.id.btn_save);
        this.mButtonSave.setOnClickListener(this);
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
    }

    private boolean formIsValid() {
        EditText forms[] = {mEditTextName};
        return FormValidator.isValid(getActivity(), forms);
    }
}
