package br.com.sae.iot.ui.fragments.problem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.sae.iot.R;
import br.com.sae.iot.dao.IndustryAreaDAO;
import br.com.sae.iot.dao.ProblemDAO;
import br.com.sae.iot.dao.ProductDAO;
import br.com.sae.iot.database.SaeDatabase;
import br.com.sae.iot.model.Industry;
import br.com.sae.iot.model.Problem;
import br.com.sae.iot.utils.FormValidator;

import static android.app.Activity.RESULT_OK;

public class FormProblemFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private EditText mEditTextName;
    private Button mButtonSave, mButtonCam;
    private Spinner spinnerArea, spinnerProduct;
    private List<String> areas;
    private List<String> products;
    private Industry industry;
    private Problem problem;
    private ImageView imageView;
    private static final int REQUEST_IMAGE = 101;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mView = inflater.inflate(R.layout.fragment_form_problem, container, false);
        industry = new Industry();
        problem = new Problem();
        spinnerArea = (Spinner) mView.findViewById(R.id.selected_area);
        imageView = (ImageView) mView.findViewById(R.id.img_view_cam);
        mButtonCam = (Button) mView.findViewById(R.id.take_photo);
        mButtonCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE);
                }
            }
        });

        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                problem.setAreaProblem(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerProduct = (Spinner) mView.findViewById(R.id.selected_product);
        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                problem.setProductProblem(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        areas = new ArrayList<String>();
        products = new ArrayList<String>();
        initializeFields();
        new FormProblemFragment.QueryTask().execute();
        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    public void initializerArea() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, areas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(dataAdapter);
    }

    public void initializerProducts() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, products);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(dataAdapter);
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
        problem.setIndustryId(1);
        problem.setTitleProblem(mEditTextName.getText().toString());
        new FormProblemFragment.Task().execute();
    }


    private boolean formIsValid() {
        EditText forms[] = {mEditTextName};
        return FormValidator.isValid(getActivity(), forms);
    }

    private void initializeFields() {
        this.mEditTextName = mView.findViewById(R.id.name_problem_form_id);
        this.mButtonSave = mView.findViewById(R.id.btn_save_form_problem);
        this.mButtonSave.setOnClickListener(this);
    }

    /**
     * @description Async Task para evitar tarefa pesada na thread de UI
     * evitando bloqueio
     */
    private class Task extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                SaeDatabase database = SaeDatabase.getInstance(mView.getContext());
                ProblemDAO dao = database.getProblemDao();
                dao.save(problem);
                List<Problem> problems = dao.problemByIndustry(1);

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


    private class QueryTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                SaeDatabase database = SaeDatabase.getInstance(mView.getContext());
                IndustryAreaDAO iad = database.getIndustryAreaDao();
                areas = iad.allName();
                ProductDAO pd = database.getProductDao();
                products = pd.allName();
                return false;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (areas.size() > 0) {
                initializerArea();
            }
            if (products.size() > 0) {
                initializerProducts();
            }
        }

    }

}



