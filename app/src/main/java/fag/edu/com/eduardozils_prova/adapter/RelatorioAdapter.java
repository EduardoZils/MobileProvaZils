package fag.edu.com.eduardozils_prova.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fag.edu.com.eduardozils_prova.R;
import fag.edu.com.eduardozils_prova.models.Ordenha;

public class RelatorioAdapter extends BaseAdapter {
    LayoutInflater myInflater;
    List<Ordenha> ordenhaList;

    public RelatorioAdapter(Context context, List<Ordenha> imovelList) {
        this.ordenhaList = imovelList;
        myInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ordenhaList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordenhaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Ordenha ordenha = ordenhaList.get(position); //Recupero o Imovel da posição passada
        view = myInflater.inflate(R.layout.item_ordenha, null);//Seto o layout escolhido na View

        ((TextView) view.findViewById(R.id.tvID)).setText(String.valueOf(ordenha.getMatrizLeitera().getCodigo()));
        ((TextView) view.findViewById(R.id.tvDesc)).setText(String.valueOf(ordenha.getMatrizLeitera().getDescricao()));
        ((TextView) view.findViewById(R.id.tvDataEHora)).setText(String.valueOf(ordenha.getDate()));
        ((TextView) view.findViewById(R.id.tvLitros)).setText(String.valueOf(ordenha.getQtLitros()));
        return view;
    }
}
