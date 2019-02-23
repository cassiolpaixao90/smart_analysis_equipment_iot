//package br.com.sae.iot.ui.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class IndustryArea extends BaseAdapter {
//
//    private final List<IndustryArea> industryAreas = new ArrayList<>();
//    private final Context context;
//
//    public ListIndustryAreaAdapter(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return industryAreas.size();
//    }
//
//    @Override
//    public IndustryArea getItem(int posicao) {
//        return industryAreas.get(posicao);
//    }
//
//    @Override
//    public long getItemId(int posicao) {
//        return industryAreas.get(posicao).getId();
//    }
//
//    @Override
//    public View getView(int posicao, View view, ViewGroup viewGroup) {
//        View viewCriada = criaView(viewGroup);
//        IndustryArea alunoDevolvido = industryAreas.get(posicao);
//        vincula(viewCriada, alunoDevolvido);
//        return viewCriada;
//    }
//
//    private void vincula(View view, Aluno aluno) {
//        TextView nome = view.findViewById(R.id.item_aluno_nome);
//        nome.setText(aluno.getNome());
//        TextView telefone = view.findViewById(R.id.item_aluno_telefone);
//        telefone.setText(aluno.getTelefone());
//    }
//
//    private View criaView(ViewGroup viewGroup) {
//        return LayoutInflater
//                .from(context)
//                .inflate(R.layout.item_aluno, viewGroup, false);
//    }
//
//    public void atualiza(List<Aluno> alunos){
//        this.alunos.clear();
//        this.alunos.addAll(alunos);
//        notifyDataSetChanged();
//    }
//
//    public void remove(Aluno aluno) {
//        alunos.remove(aluno);
//        notifyDataSetChanged();
//    }
//}
//
