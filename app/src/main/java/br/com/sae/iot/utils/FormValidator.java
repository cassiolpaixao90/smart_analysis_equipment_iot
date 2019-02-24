package br.com.sae.iot.utils;

import android.content.Context;
import android.widget.EditText;

import br.com.sae.iot.R;

public class FormValidator {

    public static boolean isValid(Context context, EditText forms[]) {
        boolean valid = true;
        for (EditText et : forms) {
            if (et.getText().toString().isEmpty()) {
                et.setError(context.getString(R.string.msg_erro_obrigatorio));
                valid = false;
            }
        }
        return valid;
    }
}
