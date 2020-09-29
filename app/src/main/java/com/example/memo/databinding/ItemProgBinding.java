// Generated by view binder compiler. Do not edit!
package com.example.memo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.senlonase.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemProgBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout rlData;

  @NonNull
  public final TextView tvHeure;

  @NonNull
  public final TextView tvHypo;

  @NonNull
  public final TextView tvMise;

  @NonNull
  public final TextView tvPartant;

  @NonNull
  public final TextView tvReunion;

  @NonNull
  public final TextView tvTitle;

  private ItemProgBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout rlData,
      @NonNull TextView tvHeure, @NonNull TextView tvHypo, @NonNull TextView tvMise,
      @NonNull TextView tvPartant, @NonNull TextView tvReunion, @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.rlData = rlData;
    this.tvHeure = tvHeure;
    this.tvHypo = tvHypo;
    this.tvMise = tvMise;
    this.tvPartant = tvPartant;
    this.tvReunion = tvReunion;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemProgBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemProgBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_prog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemProgBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout rlData = (LinearLayout) rootView;

      id = R.id.tv_heure;
      TextView tvHeure = rootView.findViewById(id);
      if (tvHeure == null) {
        break missingId;
      }

      id = R.id.tv_hypo;
      TextView tvHypo = rootView.findViewById(id);
      if (tvHypo == null) {
        break missingId;
      }

      id = R.id.tv_mise;
      TextView tvMise = rootView.findViewById(id);
      if (tvMise == null) {
        break missingId;
      }

      id = R.id.tv_partant;
      TextView tvPartant = rootView.findViewById(id);
      if (tvPartant == null) {
        break missingId;
      }

      id = R.id.tv_reunion;
      TextView tvReunion = rootView.findViewById(id);
      if (tvReunion == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = rootView.findViewById(id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ItemProgBinding((LinearLayout) rootView, rlData, tvHeure, tvHypo, tvMise,
          tvPartant, tvReunion, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
