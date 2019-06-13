package fag.edu.com.eduardozils_prova.util;

import android.app.AlertDialog;
import android.content.Context;

public class Mensagem {
    public static void ExibirMensagem(Context context, String msg, TipoMensagem tipo) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        if (tipo == TipoMensagem.ALERTA) {
            alert.setTitle("Atenção");
        } else if (tipo == TipoMensagem.ERRO) {
            alert.setTitle("Erro");
        } else if (tipo == TipoMensagem.SUCESSO) {
            alert.setTitle("Sucesso");
        }
        alert.setMessage(msg);
        alert.setNeutralButton("Ok", null);
        alert.show();
    }
}
