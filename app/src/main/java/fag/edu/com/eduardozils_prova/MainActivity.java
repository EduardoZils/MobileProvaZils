package fag.edu.com.eduardozils_prova;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.orm.SugarContext;

import java.util.Calendar;
import java.util.List;

import fag.edu.com.eduardozils_prova.models.MatrizLeitera;
import fag.edu.com.eduardozils_prova.models.Ordenha;
import fag.edu.com.eduardozils_prova.util.Mensagem;
import fag.edu.com.eduardozils_prova.util.TipoMensagem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {


    private EditText etCodigoLancamento, etQuantidadeLitros, etDataEHora;
    private Spinner spMatriz;
    private Button btSalvarLanc;
    private int day, mounth, year, hour, minute;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;//Dialog pra Date
    private ArrayAdapter<MatrizLeitera> adapterMatriz;
    private int TipoTela = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(this); //responsavel por iniciar o sugar
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            TipoTela = (int) getIntent().getExtras().get("ORDENHA");
        }catch (NullPointerException nll){
            nll.printStackTrace();
        }finally {
            loadXML();
            LoadLists();
            loadEvents();
            limpaCampos();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void limpaCampos() {
        if (TipoTela != 0) {
            for (Ordenha ordenha : Ordenha.listAll(Ordenha.class)) {
                if (ordenha.getCodigo() == TipoTela) {
                    btSalvarLanc.setText("Atualizar");
                    etCodigoLancamento.setText(String.valueOf(ordenha.getCodigo()));
                    etQuantidadeLitros.setText(String.valueOf(ordenha.getQtLitros()));
                    etDataEHora.setText(String.valueOf(ordenha.getDate()));
                }
            }
        } else {
            Ordenha last = Ordenha.last(Ordenha.class);
            if (last != null) {
                etCodigoLancamento.setText(String.valueOf(last.getCodigo() + 1));
            } else {
                etCodigoLancamento.setText("1");
            }
            btSalvarLanc.setText("Salvar");
            etDataEHora.setText("");
            etQuantidadeLitros.setText("");
            LoadLists();
        }
    }

    private void LoadLists() {


        if (MatrizLeitera.listAll(MatrizLeitera.class) != null) {
            System.out.println("MatrizLeitera não está nula");
            List<MatrizLeitera> matrizLeiteraList = MatrizLeitera.listAll(MatrizLeitera.class);
            spMatriz.setAdapter(adapterMatriz = new ArrayAdapter<MatrizLeitera>(MainActivity.this,
                    R.layout.support_simple_spinner_dropdown_item,
                    matrizLeiteraList));
        } else {
            System.out.println("MatrizLeitera está nula");
        }
    }

    private void loadEvents() {

        year = calendar.get(Calendar.YEAR);
        mounth = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(MainActivity.this, this, year, mounth, day);

        btSalvarLanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TipoTela != 0) {
                    for (Ordenha ordenha : Ordenha.listAll(Ordenha.class)) {
                        if (ordenha.getCodigo() == TipoTela) {
                            ordenha.setCodigo(Integer.parseInt(etCodigoLancamento.getText().toString()));
                            ordenha.setQtLitros(Double.parseDouble(etQuantidadeLitros.getText().toString()));
                            ordenha.setMatrizLeitera((MatrizLeitera) spMatriz.getSelectedItem());
                            ordenha.setDate(calendar.getTime());
                            ordenha.update();
                            Mensagem.ExibirMensagem(MainActivity.this, "Ordenha Editada com Sucesso!", TipoMensagem.SUCESSO);
                            finish();
                        }
                    }

                    TipoTela = 0;
                } else {
                    if (spMatriz.getSelectedItem() != null) {
                        if (etCodigoLancamento.getText().length() > 0 && etQuantidadeLitros.getText().length() > 0 && etDataEHora.getText().length() > 0) {
                            Ordenha ordenha = new Ordenha();
                            ordenha.setCodigo(Integer.parseInt(etCodigoLancamento.getText().toString()));
                            ordenha.setQtLitros(Double.parseDouble(etQuantidadeLitros.getText().toString()));
                            ordenha.setMatrizLeitera((MatrizLeitera) spMatriz.getSelectedItem());
                            ordenha.setDate(calendar.getTime());
                            ordenha.save();
                            TipoTela = 0;
                            Mensagem.ExibirMensagem(MainActivity.this, "Ordenha salva com Sucesso!", TipoMensagem.SUCESSO);
                            limpaCampos();
                        } else {
                            Mensagem.ExibirMensagem(MainActivity.this, "Preencha todos os campos!", TipoMensagem.ERRO);
                        }
                    }
                }
            }
        });

        etDataEHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    private void loadXML() {
        etCodigoLancamento = findViewById(R.id.etCodigoLancamento);
        etQuantidadeLitros = findViewById(R.id.etQuantidadeLitros);
        etDataEHora = findViewById(R.id.etDataEHora);
        spMatriz = findViewById(R.id.spMatriz);
        btSalvarLanc = findViewById(R.id.btSalvarLanc);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, MatrizActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, RelatorioActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        etDataEHora.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }
}
