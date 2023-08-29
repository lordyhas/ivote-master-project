package org.unh.i_vote.ui.login;

import static android.content.ContentValues.TAG;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import org.unh.i_vote.MainActivity;
import org.unh.i_vote.R;
import org.unh.i_vote.data.database.FirebaseRef;
import org.unh.i_vote.data.database.model.User;
import org.unh.i_vote.databinding.ActivityLoginBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/*
 *
 * @author lordyhas (Hassan K.)
 *
 */
public class LoginActivity extends AppCompatActivity {

    final static int RC_SIGN_IN_OK = 123;

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private ProgressBar loadingProgressBar;

    //private User user;

    private Boolean haveAccount = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText nameEditText = binding.name;
        final EditText emailEditText = binding.email;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button haveAccountOrNotButton = binding.haveAccountOrNot;
        loginButton.setText("Créer un compte");
        //final Button loginGoogleButton = binding.signWithout;
        // Set the dimensions of the sign-in button.

        //final Button continueButton = binding.signWithout; //findViewById(R.id.sign_in_button);
        //continueButton.setVisibility(View.GONE);
        //continueButton.setSize(SignInButton.SIZE_STANDARD);
        loadingProgressBar = binding.loading;

        //loadingProgressBar.setVisibility(View.INVISIBLE);

        haveAccountOrNotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haveAccount){
                    haveAccountOrNotButton.setText(R.string.have_account);
                    nameEditText.setVisibility(View.VISIBLE);
                    loginButton.setText("Créer un compte");
                }else {
                    haveAccountOrNotButton.setText(R.string.have_no_account);
                    nameEditText.setVisibility(View.GONE);
                    loginButton.setText("Se connecter");
                }
                haveAccount = !haveAccount;
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getNameError() != null) {
                    nameEditText.setError(getString(loginFormState.getNameError()));
                }
                if (loginFormState.getEmailError() != null) {
                    emailEditText.setError(getString(loginFormState.getEmailError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    return;
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());


                    LoggedInUserView loggedUser = loginResult.getSuccess();

                    FirebaseRef.userCollection.document(loggedUser.getDisplayEmail()).get()
                            .addOnSuccessListener(doc -> {
                                if(doc.exists()){
                                    //user = doc.toObject(User.class);
                                    User user = User.Companion
                                            .fromMap(Objects.requireNonNull(doc.getData()));
                                    Log.d(TAG, doc.getId() + " => " + doc.getData());

                                    if(Objects.equals(loggedUser.getPassword(), user.getPassword())){
                                        onConnexion(user);
                                    }else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Mot de passe ou Nom d'utilisateur incorrect ",
                                                Toast.LENGTH_LONG).show();
                                    }


                                } else {

                                    if(loggedUser.isLoggingIn()){
                                        Log.w(TAG, "User "+loggedUser.getDisplayEmail()+"  not found: ");
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "User : "+loggedUser.getDisplayEmail()+" doesn't exist",
                                                Toast.LENGTH_LONG).show();
                                    }else {
                                        User user = new User(
                                                loggedUser.getDisplayName(),
                                                loggedUser.getDisplayEmail(),
                                                loggedUser.getPassword(),
                                                new ArrayList<>(),
                                                new ArrayList<>()
                                        );
                                        FirebaseRef.userCollection
                                                .document(user.getEmail())
                                                .set(user.toMap())
                                                .addOnSuccessListener(unused -> onConnexion(user));
                                    }

                                }

                            }).addOnFailureListener(e ->{
                                Log.e(TAG, "Error getting document: ", e);
                                Toast.makeText(getApplicationContext(), "Erreur de connection, please network connection", Toast.LENGTH_LONG).show();
                            });
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.GONE);
            loadingProgressBar.setVisibility(View.VISIBLE);

            loginViewModel.login(emailEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_OK) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            Log.w(TAG, "signInResult:succeed user= " +account.getEmail()+ " - " + account.getDisplayName());
            loginViewModel.login(account.getEmail(), account.getDisplayName());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //if(account == null) return;
        //loadingProgressBar.setVisibility(View.VISIBLE);
        //loginViewModel.login(account.getEmail(),account.getDisplayName());
        //updateUI(account);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        //Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        //Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void onConnexion(User user){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("email", user.getEmail());
        intent.putExtra("name", user.getName());
        //intent.putExtra("user", user);
        Toast.makeText(
                getApplicationContext(),
                "Welcome : "+user.getName(),
                Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }
}