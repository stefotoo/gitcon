package com.sample.android.gitcon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.rey.material.widget.Button;
import com.sample.android.gitcon.R;
import com.sample.android.gitcon.constants.Constants;
import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.preferences.AppPreferences;
import com.sample.android.gitcon.tasks.GetUserDetailsApiTask;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;
import com.sample.android.gitcon.ui.GitconProgressDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity
        extends AppCompatActivity
        implements View.OnClickListener, TextView.OnEditorActionListener {

    // constants
    public static final String BASIC_TAG = LoginActivity.class.getName();

    // Ui
    @Bind(R.id.cl_activity_login)
    ConstraintLayout cl;
    @Bind(R.id.iv_activity_login_logo)
    ImageView ivLogo;
    @Bind(R.id.et_activity_login_username)
    MaterialEditText etUsername;
    @Bind(R.id.btn_activity_login)
    Button btnLogin;

    private GitconProgressDialog pd;

    // get intent methods
    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    // methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        initVariables();
        initListeners();
        setupUi();
        addEditTextValidators();
        animateViews();
    }

    private void initVariables() {
        pd = new GitconProgressDialog(this);
    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
        etUsername.setOnEditorActionListener(this);
    }

    private void setupUi() {
        pd.setMessage(getString(R.string.dialog_sign_in_message));
    }

    private void addEditTextValidators() {
        etUsername.addValidator(new RegexpValidator(
                getString(R.string.error_empty_username),
                Constants.REGEX_NON_EMPTY_STRING));
    }

    private void animateViews() {
        Animation ivAnim = AnimationUtils.loadAnimation(this, R.anim.slide_logo);
        Animation editTextAnim = AnimationUtils.loadAnimation(this, R.anim.slide_edittext);
        Animation btnAnim = AnimationUtils.loadAnimation(this, R.anim.slide_button);

        ivLogo.startAnimation(ivAnim);
        etUsername.startAnimation(editTextAnim);
        btnLogin.startAnimation(btnAnim);
    }

    private void requestLogin() {
        if (!etUsername.validate()) {
            return;
        }

        executeLoginRequest();
    }

    private void executeLoginRequest() {
        new GetUserDetailsApiTask(
                this,
                etUsername.getText().toString(),
                new SimpleTask.SimpleCallback<AUser>() {
                    @Override
                    public void onStart() {
                        pd.show();
                    }

                    @Override
                    public void onComplete(AUser res, String errorMessage) {
                        pd.dismiss();

                        if (res != null) {
                            AppPreferences.setUser(LoginActivity.this, res);
                            startActivity(UserDetailsActivity.getIntent(LoginActivity.this, null));
                            finish();
                        } else {
                            Snackbar.make(cl, errorMessage, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_login: {
                requestLogin();
                break;
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if ((keyEvent != null &&
                (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                (i == EditorInfo.IME_ACTION_DONE)) {
            requestLogin();
        }

        return false;
    }
}
