package com.example.oop2022finalp2.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.oop2022finalp2.MainActivity;
import com.example.oop2022finalp2.RelaxActivity;
import com.example.oop2022finalp2.WordTestActivity;
import com.example.oop2022finalp2.databinding.FragmentNotificationsBinding;
import com.example.oop2022finalp2.ui.dashboard.DashboardFragment;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("今天应该背单词了")
                .setPositiveButton("现在开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), WordTestActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("休息一下",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), RelaxActivity.class);
                        startActivity(intent);
                    }
        })
        .show();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}