package br.com.sae.iot.ui.fragments.problem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.sae.iot.R;
import br.com.sae.iot.dao.IndustryAreaDAO;
import br.com.sae.iot.dao.ProblemDAO;
import br.com.sae.iot.dao.ProductDAO;
import br.com.sae.iot.database.SaeDatabase;
import br.com.sae.iot.model.Industry;
import br.com.sae.iot.model.Problem;
import br.com.sae.iot.utils.Constants;
import br.com.sae.iot.utils.FormValidator;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FormProblemFragment extends Fragment {

    private View mView;
    private EditText mEditTitleProblem, mEditDescProblem;
    private Button mButtonSave;
    private ImageButton mButtonCam;
    private Spinner spinnerArea, spinnerProduct;
    private ImageView imageView;

    private ArrayAdapter<String> areaAdapter;
    private ArrayAdapter<String> producAdapter;

    private List<String> areas;
    private List<String> products;
    private Industry industry;
    private Problem problem;
    private boolean isUpdate = false;
    private String imageFilePath = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("SAE - Problemas");
        mView = inflater.inflate(R.layout.fragment_form_problem, container, false);
        initializeFields();
        industry = new Industry();
        problem = new Problem();
        mButtonCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPermission();
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

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProblem();
            }
        });


        areas = new ArrayList<String>();
        products = new ArrayList<String>();
        new FormProblemFragment.QueryTask().execute();
        return mView;
    }

    private void loadProblems() {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(Constants.KEY_PROBLEM)) {
            Problem pb = (Problem) getArguments().getSerializable(Constants.KEY_PROBLEM);
            loadFields(pb);
            this.isUpdate = true;
        }

    }

    private void loadFields(Problem pb) {
        mEditTitleProblem.setText(pb.getTitleProblem());
        mEditDescProblem.setText(pb.getDescProblem());

        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).equalsIgnoreCase(pb.getAreaProblem())) {
                int spinnerPosition = areaAdapter.getPosition(areas.get(i));
                spinnerArea.setSelection(spinnerPosition);
            }
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equalsIgnoreCase(pb.getProductProblem())) {
                int spinnerPosition = producAdapter.getPosition(products.get(i));
                spinnerProduct.setSelection(spinnerPosition);
            }
        }
        imageView.setImageURI(Uri.parse(pb.getPathImage()));
    }

    private void verifyPermission() {
        ArrayList<String> permissions = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (permissions.isEmpty()) {
            openCameraIntent();
        } else {
            String[] permissoesForRequest = new String[permissions.size()];
            permissions.toArray(permissoesForRequest);
            ActivityCompat.requestPermissions(getActivity(),
                    permissoesForRequest,
                    0);
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(mView.getContext(), Constants.AUTHORITY, photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, Constants.REQUEST_IMAGE);
        }
    }

    public void initializerArea() {
        areaAdapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, areas);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(areaAdapter);
    }

    public void initializerProducts() {
        producAdapter = new ArrayAdapter<String>(mView.getContext(), android.R.layout.simple_spinner_item, products);
        producAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(producAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void saveProblem() {
        if (!formIsValid()) {
            return;
        }
        problem.setIndustryId(1);
        problem.setTitleProblem(mEditTitleProblem.getText().toString());
        problem.setDescProblem(mEditDescProblem.getText().toString());
        new FormProblemFragment.Task().execute();
    }


    private boolean formIsValid() {
        EditText forms[] = {mEditTitleProblem, mEditTitleProblem};
        return FormValidator.isValid(getActivity(), forms);
    }

    private void initializeFields() {
        this.mEditDescProblem = (EditText) mView.findViewById(R.id.desc_problem_id);
        this.mEditTitleProblem = (EditText) mView.findViewById(R.id.name_problem_form_id);
        this.mButtonSave = (Button) mView.findViewById(R.id.btn_save_form_problem);
        this.spinnerArea = (Spinner) mView.findViewById(R.id.selected_area);
        this.imageView = (ImageView) mView.findViewById(R.id.image_problem);
        this.mButtonCam = (ImageButton) mView.findViewById(R.id.button_take_photo);
    }

    /**
     * @description Async Task, para evitar tarefa pesada na thread de UI
     * evitando bloqueio
     */
    private class Task extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                SaeDatabase database = SaeDatabase.getInstance(mView.getContext());
                ProblemDAO dao = database.getProblemDao();
                if (isUpdate) {
                    dao.update(problem);
                } else {
                    dao.save(problem);
                }
                return false;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mEditTitleProblem.setText("");
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

            loadProblems();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mView.getContext(), "Obrigado pelas permissões!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(Uri.parse(imageFilePath));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mView.getContext(), "opção de tirar foto cancelada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        problem.setPathImage(imageFilePath);
        return image;
    }


}



