package br.com.sae.iot.ui.fragments.product;

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
import br.com.sae.iot.dao.ProductDAO;
import br.com.sae.iot.database.SaeDatabase;
import br.com.sae.iot.model.Product;
import br.com.sae.iot.ui.adapter.ProductAdapter;

public class ListProductFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private ListView mListView;
    private List<Product> products;
    private FloatingActionButton floatingActionButton;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("SAE - Produtos");
        mView = inflater.inflate(R.layout.fragment_product, container, false);
        floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.fab_product);
        floatingActionButton.setOnClickListener(this);
        this.products = new ArrayList<>();
        new ListProductFragment.QueryDataTask().execute();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeList() {
        mListView = mView.findViewById(R.id.list_product_id);
        ProductAdapter adapter = new ProductAdapter(mView.getContext(), products);
        mListView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        Fragment fragment = new FormProductFragment();
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
                ProductDAO dao = database.getProductDao();
                products = dao.all();
                return false;
            } catch (Exception e) {
                return true;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (products.size() > 0) {
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
