package br.com.sae.iot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.sae.iot.R;
import br.com.sae.iot.model.Product;

public class ProductAdapter  extends BaseAdapter {

    private List<Product> products;
    private Context context;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    // Retorna a quantidade de elementos na lista
    @Override
    public int getCount() {
        return products.size();
    }

    // Retorna a consumo de uma posição específica
    @Override
    public Product getItem(int posicao) {
        return products.get(posicao);
    }

    // Retorna o ID de uma posição específica
    @Override
    public long getItemId(int posicao) {
        return products.get(posicao).getId();
    }

    // Constrói as views que representam cada uma das linhas da lista
    // Implementa o padrão View Holder para obter melhor performance
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ProductAdapter.ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.adapter_product, viewGroup, false);
            holder = new ProductAdapter.ViewHolder();
            holder.txtNameArea = view.findViewById(R.id.name_productl_id);

            view.setTag(holder);
        } else {
            holder = (ProductAdapter.ViewHolder) view.getTag();
        }

        Product product = products.get(position);
        holder.txtNameArea.setText(product.getName());

        return view;
    }

    public void update(List<Product> productList){
        this.products.clear();
        this.products.addAll(productList);
        notifyDataSetChanged();
    }

    public void remove(Product product) {
        products.remove(product);
        notifyDataSetChanged();
    }

    // Classe para implementação do padrão View Holder
    private static class ViewHolder {
        public TextView txtNameArea;
    }
}


