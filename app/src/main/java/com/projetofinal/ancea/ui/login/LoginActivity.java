package com.projetofinal.ancea.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projetofinal.ancea.MainActivity;
import com.projetofinal.ancea.PacienteActivity;
import com.projetofinal.ancea.R;
import com.projetofinal.ancea.helper.ConfiguracaoFirebase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Status";
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser() != null) {
            startActivity(new Intent(this,MainActivity.class));
            this.finish();
        }

    }


    public void handleLoginRegister(View view){
        // Choose authentication providers
        AuthUI.IdpConfig googleIdp = new AuthUI.IdpConfig.GoogleBuilder()
                .setScopes(Arrays.asList(Scopes.EMAIL,Scopes.PROFILE))
                .build();

        AuthUI.IdpConfig facebookIdp = new AuthUI.IdpConfig.FacebookBuilder()
                .setPermissions(Arrays.asList("email", "public_profile"))
                .build();

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                googleIdp, facebookIdp,
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAlwaysShowSignInMethodScreen(true).build();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = ConfiguracaoFirebase.getFirebaseAutenticacao().getCurrentUser();
                Log.d(TAG, "onActivityResult" + user.getEmail());
                // ...
                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                    Toast.makeText(this,"Bem-vindo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Bem-vindo de volta.", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null){
                    Log.d(TAG, "onActivityResult: usuario cancelou login");
                } else {
                    Log.e(TAG, "onActivityResult:", response.getError());
                }
            }
        }
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }
}


