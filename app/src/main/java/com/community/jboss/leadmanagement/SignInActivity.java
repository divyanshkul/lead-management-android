package com.community.jboss.leadmanagement.main;

import com.bumptech.glide.Glide;
import com.community.jboss.leadmanagement.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mikhaellopez.circularimageview.CircularImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Divyansh on 06-01-2018.
 */


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog mProgressDialog;

    public void updateUI(FirebaseUser user) {

        hideProgressDialog();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        CircularImageView profileImage = header.findViewById(R.id.nav_profile_image);
        TextView userDisplayName = header.findViewById(R.id.nav_profile_name);
        TextView userEmail = header.findViewById(R.id.nav_profile_email);
        MenuItem signIn = menu.findItem(R.id.nav_sign_in);
        MenuItem signOut = menu.findItem(R.id.nav_sign_out);
        if (user != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getString(R.string.success));
            alertDialog.setMessage(getString(R.string.welcome_string)+" "+user.getDisplayName()+"!");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alertDialog.show();

            userDisplayName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
            Glide.with(this).load(user.getPhotoUrl()).into(profileImage);
            signIn.setChecked(false);
            signIn.setVisible(false);
            signOut.setVisible(true);

        } else {
            Glide.with(this).load(R.drawable.ic_launcher_foreground).into(profileImage);
            userDisplayName.setText(R.string.name_placeholder);
            userEmail.setText(getString(R.string.email_placeholder));
            signOut.setChecked(false);
            signOut.setVisible(false);
            signIn.setVisible(true);

            ;
        }
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loadingText));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    public void onClick(View view) {

    }
}