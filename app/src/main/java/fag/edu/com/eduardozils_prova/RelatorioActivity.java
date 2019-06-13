package fag.edu.com.eduardozils_prova;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fag.edu.com.eduardozils_prova.adapter.RelatorioAdapter;
import fag.edu.com.eduardozils_prova.models.Ordenha;
import fag.edu.com.eduardozils_prova.util.Mensagem;
import fag.edu.com.eduardozils_prova.util.TipoMensagem;

public class RelatorioActivity extends AppCompatActivity {

    private ListView lvOrdenha;
    private RelatorioAdapter relatorioAdapter;
    private List<Ordenha> ordenhaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvOrdenha = findViewById(R.id.lvOrdenha);

        carregaLista();


        lvOrdenha.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Ordenha ordenha = ordenhaList.get(position);
                final AlertDialog.Builder alertConfirmacao = new AlertDialog.Builder(RelatorioActivity.this);
                alertConfirmacao.setTitle("O que deseja fazer?");
                alertConfirmacao.setMessage("Deseja excluir ou editar o registro?");
                alertConfirmacao.setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ordenha.delete();
                        Mensagem.ExibirMensagem(RelatorioActivity.this, "Ordenha removida com Sucesso!", TipoMensagem.SUCESSO);
                        carregaLista();
                    }
                });
                alertConfirmacao.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(RelatorioActivity.this, MainActivity.class);
                        intent.putExtra("ORDENHA", ordenha.getCodigo());
                        startActivity(intent);
                    }
                });
                alertConfirmacao.show();
                carregaLista();



               /* Ordenha ordenha = ordenhaList.get(position);
                ordenha.delete();
                Mensagem.ExibirMensagem(RelatorioActivity.this, "Ordenha removida com Sucesso!", TipoMensagem.SUCESSO);
                */
                return true;
            }


        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void carregaLista() {
        ordenhaList = Ordenha.listAll(Ordenha.class, "codigo desc");
        relatorioAdapter = new RelatorioAdapter(RelatorioActivity.this, ordenhaList);
        lvOrdenha.setAdapter(relatorioAdapter);
    }
}
