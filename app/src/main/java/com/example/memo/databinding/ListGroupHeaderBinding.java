// Generated by view binder compiler. Do not edit!
package com.example.memo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.senlonase.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListGroupHeaderBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView fleche;

  @NonNull
  public final ImageView ivImg;

  @NonNull
  public final TextView lblListHeader;

  private ListGroupHeaderBinding(@NonNull RelativeLayout rootView, @NonNull ImageView fleche,
      @NonNull ImageView ivImg, @NonNull TextView lblListHeader) {
    this.rootView = rootView;
    this.fleche = fleche;
    this.ivImg = ivImg;
    this.lblListHeader = lblListHeader;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListGroupHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListGroupHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_group_header, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListGroupHeaderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fleche;
      ImageView fleche = rootView.findViewById(id);
      if (fleche == null) {
        break missingId;
      }

      id = R.id.iv_img;
      ImageView ivImg = rootView.findViewById(id);
      if (ivImg == null) {
        break missingId;
      }

      id = R.id.lblListHeader;
      TextView lblListHeader = rootView.findViewById(id);
      if (lblListHeader == null) {
        break missingId;
      }

      return new ListGroupHeaderBinding((RelativeLayout) rootView, fleche, ivImg, lblListHeader);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
