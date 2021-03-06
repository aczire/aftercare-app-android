package com.dentacoin.dentacare.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.dentacoin.dentacare.R;
import com.dentacoin.dentacare.widgets.DCButton;

/**
 * Created by Atanas Chervarov on 10/10/17.
 */

public class DCAgreementFragment extends DCFragment implements View.OnClickListener {

    public interface IDCAgreementListener {
        void onAgreementAccepted();
    }

    public static final String TAG = DCAgreementFragment.class.getSimpleName();

    private WebView wvAgreement;
    private DCButton btnAgreementAccept;
    private DCButton btnAgreementDecline;
    private IDCAgreementListener listener;

    public void setListener(IDCAgreementListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_agreement, container, false);

        wvAgreement = (WebView) view.findViewById(R.id.wv_agreement);
        wvAgreement.loadUrl("file:///android_asset/web/user-agreement.html");

        btnAgreementAccept = (DCButton) view.findViewById(R.id.btn_agreement_accept);
        btnAgreementAccept.setOnClickListener(this);

        btnAgreementDecline = (DCButton) view.findViewById(R.id.btn_agreement_decline);
        btnAgreementDecline.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_agreement_accept:
                if (listener != null)
                    listener.onAgreementAccepted();
                break;
        }

        if (getActivity() != null && getActivity().getFragmentManager() != null) {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        }
    }
}
