package com.rhernandez.social_ruth.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rhernandez.social_ruth.R;
import com.rhernandez.social_ruth.models.RequestAuthenticate;
import com.rhernandez.social_ruth.models.ResponseAuth;
import com.rhernandez.social_ruth.utilities.NetworkAPI;
import com.rhernandez.social_ruth.utilities.NetworkGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    private EditText user, pass;
    private NetworkAPI networkAPI = NetworkGenerator.createService(NetworkAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
    }

    public boolean validateFields() {
        if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Estimada usuaria, favor ingresar los datos indicados.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void login(View view) {
        if (validateFields()) {
            authenticate();
        }
    }

    public void authenticate() {
        final MaterialDialog progress = showProgress();
        RequestAuthenticate authenticate = new RequestAuthenticate();
        authenticate.setUser(user.getText().toString());
        authenticate.setPassword(pass.getText().toString());
        authenticate.setIp("192.12.99.3");
        authenticate.setDeviceId("123456432465576586546");
        authenticate.setOperativeSystem("ANDROID");
        authenticate.setVersion("1.1");
        Call<ResponseAuth> authenticateCall = networkAPI.authenticate(authenticate);
        authenticateCall.enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                ResponseAuth responseAuth = response.body();
                if (responseAuth.isSuccessful()) {
                    progress.dismiss();
                    Intent startApp = new Intent(AuthenticationActivity.this, MainActivity.class);
                    startActivity(startApp);
                    finish();
                } else {
                    progress.dismiss();
                    Toast.makeText(AuthenticationActivity.this, responseAuth.getDescription(), Toast.LENGTH_SHORT).show();
                    pass.setText("");
                }
            }

            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(AuthenticationActivity.this, getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                pass.setText("");
            }
        });
    }

    public MaterialDialog showProgress() {
        return new MaterialDialog.Builder(this)
                .titleColorRes(R.color.colorPrimaryDark)
                .title("Autenticación")
                .contentColorRes(R.color.colorPrimaryDark)
                .backgroundColorRes(R.color.white)
                .content("Cargando...")
                .cancelable(false)
                .progress(true, 0)
                .show();
    }
}
