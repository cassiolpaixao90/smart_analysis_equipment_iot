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
import br.com.sae.iot.utils.FormValidator;

import static android.app.Activity.RESULT_CANCELED;
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

    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_PERMISSION = 200;
    private String imageFilePath = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        mView = inflater.inflate(R.layout.fragment_form_problem, container, false);
        industry = new Industry();
        problem = new Problem();
        spinnerArea = (Spinner) mView.findViewById(R.id.selected_area);
        imageView = (ImageView) mView.findViewById(R.id.image_problem);
        mButtonCam = (Button) mView.findViewById(R.id.button_take_photo);
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


        areas = new ArrayList<String>();
        products = new ArrayList<String>();
        initializeFields();
        new FormProblemFragment.QueryTask().execute();
        return mView;
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
            Uri photoUri = FileProvider.getUriForFile(mView.getContext(), "br.com.sae.iot.provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mView.getContext(), "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(Uri.parse(imageFilePath));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mView.getContext(), "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();

        return image;
    }


}



