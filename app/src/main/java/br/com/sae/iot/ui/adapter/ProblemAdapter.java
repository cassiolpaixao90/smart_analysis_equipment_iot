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
import br.com.sae.iot.model.Problem;


public class ProblemAdapter extends BaseAdapter {

    private List<Problem> problems;
    private Context context;

    public ProblemAdapter(Context context, List<Problem> problems) {
        this.context = context;
        this.problems = problems;
    }

    // Retorna a quantidade de elementos na lista
    @Override
    public int getCount() {
        return problems.size();
    }

    // Retorna a consumo de uma posição específica
    @Override
    public Problem getItem(int posicao) {
        return problems.get(posicao);
    }

    // Retorna o ID de uma posição específica
    @Override
    public long getItemId(int posicao) {
        return problems.get(posicao).getId();
    }

    // Constrói as views que representam cada uma das linhas da lista
    // Implementa o padrão View Holder para obter melhor performance
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.adapter_problem, viewGroup, false);
            holder = new ViewHolder();
            holder.txtNameArea = view.findViewById(R.id.problem_title_id);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Problem problem = problems.get(position);
        holder.txtNameArea.setText(problem.getTitleProblem());

        return view;
    }

    public void update(List<Problem> problems) {
        this.problems.clear();
        this.problems.addAll(problems);
        notifyDataSetChanged();
    }

    public void remove(IndustryArea industryArea) {
        problems.remove(industryArea);
        notifyDataSetChanged();
    }

    // Classe para implementação do padrão View Holder
    private static class ViewHolder {
        public TextView txtNameArea;
    }
}

