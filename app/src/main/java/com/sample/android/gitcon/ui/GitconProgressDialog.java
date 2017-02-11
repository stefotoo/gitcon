package com.sample.android.gitcon.ui;

import android.app.ProgressDialog;
import android.content.Context;

import com.sample.android.gitcon.R;

public class GitconProgressDialog extends ProgressDialog {

    // variables
    private Context mContext;

    public GitconProgressDialog(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GitconProgressDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        init();
    }

    private void init() {
        this.setCancelable(false);
        this.setIndeterminate(true);
        this.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.progress));
        this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
