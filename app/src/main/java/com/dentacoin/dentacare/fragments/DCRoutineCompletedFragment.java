package com.dentacoin.dentacare.fragments;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.dentacoin.dentacare.R;
import com.dentacoin.dentacare.utils.AudibleMessage;
import com.dentacoin.dentacare.utils.DCConstants;
import com.dentacoin.dentacare.utils.Routine;
import com.dentacoin.dentacare.widgets.DCSoundManager;
import com.dentacoin.dentacare.widgets.DCTextView;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

/**
 * Created by Atanas Chervarov on 11/16/17.
 */

public class DCRoutineCompletedFragment extends DCDialogFragment {

    public static final String TAG = DCRoutineCompletedFragment.class.getSimpleName();

    private static final String KEY_ROUTINE_TYPE = "KEY_ROUTINE_TYPE";

    private ShareButton fbShare;
    private DCTextView tvMessage;
    private DCTextView tvCompleted;
    private ImageView ivTooth;

    public static DCRoutineCompletedFragment create(Routine.Type type) {
        final DCRoutineCompletedFragment fragment = new DCRoutineCompletedFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(KEY_ROUTINE_TYPE, type);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine_completed, container);
        fbShare = (ShareButton) view.findViewById(R.id.fb_share);
        tvMessage = (DCTextView) view.findViewById(R.id.tv_message);
        tvCompleted = (DCTextView) view.findViewById(R.id.tv_completed);
        ivTooth = (ImageView) view.findViewById(R.id.iv_tooth);

        if (getArguments() != null) {
            final Routine.Type routineType = (Routine.Type)getArguments().getSerializable(KEY_ROUTINE_TYPE);

            if (routineType != null) {
                AudibleMessage audibleMessage;
                String shareLinkMessage;

                switch (routineType) {
                    case MORNING:
                        shareLinkMessage = getString(R.string.fb_share_morning_routine_completed);
                        audibleMessage = AudibleMessage.MORNING_ROUTINE_END;
                        break;
                    default:
                        shareLinkMessage = getString(R.string.fb_share_evening_routine_completed);
                        audibleMessage = AudibleMessage.EVENING_ROUTINE_END;
                        break;
                }

                tvMessage.setText(audibleMessage.getMessage(getActivity()));

                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(DCConstants.DENTACARE_GOOGLE_PLAY))
                        .setShareHashtag(new ShareHashtag.Builder()
                                .setHashtag("#dentacoin")
                                .build())
                        .setQuote(shareLinkMessage)
                        .build();

                fbShare.setShareContent(shareLinkContent);

                if (audibleMessage.getVoices() != null && audibleMessage.getVoices().length > 0) {
                    DCSoundManager.getInstance().playVoice(getActivity(), audibleMessage.getVoices()[0]);
                }
            }
        }

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(1000);
        ivTooth.startAnimation(alphaAnimation);

        AlphaAnimation dayAlphaAnimation = new AlphaAnimation(0f, 1f);
        dayAlphaAnimation.setDuration(2000);
        tvCompleted.startAnimation(dayAlphaAnimation);

        AlphaAnimation alphaAnimationMessage = new AlphaAnimation(0f, 1f);
        alphaAnimationMessage.setDuration(1000);
        tvMessage.startAnimation(alphaAnimationMessage);

        return view;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        DCSoundManager.getInstance().cancelSounds();
    }
}
