package fag.edu.com.eduardozils_prova;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import fag.edu.com.eduardozils_prova.models.MatrizLeitera;
import fag.edu.com.eduardozils_prova.models.Ordenha;
import fag.edu.com.eduardozils_prova.util.Mensagem;
import fag.edu.com.eduardozils_prova.util.TipoMensagem;


public class MatrizActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private EditText etCodigoMatriz, etDescricao, etIdade, etDataEHora;
    private Button btSalvarMatriz;
    private int day, mounth, year, hour, minute;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;//Dialog pra Date
    private TimePickerDialog timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadXML();

        loadEvents();
        limpaCampos();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void limpaCampos(){
        MatrizLeitera last = MatrizLeitera.last(MatrizLeitera.class);
        if (last != null) {
            etCodigoMatriz.setText(String.valueOf(last.getCodigo() + 1));
        } else {
            etCodigoMatriz.setText("1");
        }
        etDataEHora.setText("");
        etIdade.setText("");
        etDescricao.setText("");
    }
    private void loadEvents() {

        year = calendar.get(Calendar.YEAR);
        mounth = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        timePicker = new TimePickerDialog(MatrizActivity.this, this, 15,50,true);
        datePickerDialog = new DatePickerDialog(MatrizActivity.this, this, year, mounth, day);


        btSalvarMatriz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCodigoMatriz.getText().length() > 0 && etDataEHora.getText().length() > 0 && etDescricao.getText().length() > 0 && etIdade.getText().length() > 0){
                    MatrizLeitera matriz = new MatrizLeitera();
                    matriz.setCodigo(Integer.parseInt(etCodigoMatriz.getText().toString()));
                    matriz.setIdade(Integer.parseInt(etIdade.getText().toString()));
                    matriz.setDataUltParto(calendar.getTime());
                    matriz.setDescricao(etDescricao.getText().toString());
                    matriz.save();
                    Mensagem.ExibirMensagem(MatrizActivity.this, "Matriz salva com Sucesso!", TipoMensagem.SUCESSO);
                    limpaCampos();
                }else{
                    Mensagem.ExibirMensagem(MatrizActivity.this, "Preencha todos os campos!", TipoMensagem.ERRO);
                }
            }
        });

        etDataEHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show();
                datePickerDialog.show();
            }
        });
    }

    private void loadXML() {
        etCodigoMatriz = findViewById(R.id.etCodigoMatriz);
        etDataEHora = findViewById(R.id.etDataEHora);
        etDescricao = findViewById(R.id.etDescricao);
        etIdade = findViewById(R.id.etIdade);
        btSalvarMatriz = findViewById(R.id.btSalvarMatriz);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        etDataEHora.setText( dayOfMonth + "/"+ (month + 1)+ "/"+ year);
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String aux = etDataEHora.getText().toString();
        etDataEHora.setText( aux+ " " + hourOfDay + ":" + minute);
    }
}
