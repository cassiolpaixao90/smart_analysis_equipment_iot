package br.com.sae.iot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.sae.iot.R;
import br.com.sae.iot.model.IndustryArea;

public class IndustryAreaAdapter extends BaseAdapter {

    private List<IndustryArea> industryAreas;
    private Context context;

    public IndustryAreaAdapter(Context context, List<IndustryArea> industryAreas) {
        this.context = context;
        this.industryAreas = industryAreas;
    }

    // Retorna a quantidade de elementos na lista
    @Override
    public int getCount() {
        return industryAreas.size();
    }

    // Retorna a consumo de uma posição específica
    @Override
    public IndustryArea getItem(int posicao) {
        return industryAreas.get(posicao);
    }

    // Retorna o ID de uma posição específica
    @Override
    public long getItemId(int posicao) {
        return industryAreas.get(posicao).getId();
    }

    // Constrói as views que representam cada uma das linhas da lista
    // Implementa o padrão View Holder para obter melhor performance
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.adapter_industry_area, viewGroup, false);
            holder = new ViewHolder();
            holder.txtNameArea = view.findViewById(R.id.name_area);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        IndustryArea industryArea = industryAreas.get(position);
        holder.txtNameArea.setText(industryArea.getName());

        return view;
    }

    public void update(List<IndustryArea> industryAreas){
        this.industryAreas.clear();
        this.industryAreas.addAll(industryAreas);
        notifyDataSetChanged();
    }

    public void remove(IndustryArea industryArea) {
        industryAreas.remove(industryArea);
        notifyDataSetChanged();
    }

    // Classe para implementação do padrão View Holder
    private static class ViewHolder {
        public TextView txtNameArea;
    }
}

