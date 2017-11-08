package com.backofficecloudapps.prop.inventory.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Phoenix
 */
public final class ActionBarBuilder{
  @Nullable
  private android.support.v7.app.ActionBar supportActionBar;
  @Nullable
  private Context context;
  @Nullable
  private View view;

  @Nullable
  private static ActionBarBuilder instance=null;

  /**
   * Get instance action bar builder.
   *
   * @return the action bar builder
   */
  @Nullable
  public static ActionBarBuilder getInstance(){
    if(instance==null)
      instance=new ActionBarBuilder();
      instance.supportActionBar=null;
      instance.view=null;
       instance.context=null;
     return instance;
  }

  /**
   * Set action bar action bar builder.
   *
   * @param context   the context
   * @param actionBar the action bar
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder setActionBar(@Nullable Context context, @Nullable android.support.v7.app.ActionBar actionBar){
    if(actionBar != null) supportActionBar = actionBar;
    if(context != null) this.context = context;
    return this;
  }

  /**
   * Inflate layout action bar builder.
   *
   * @param layout the layout
   * @param root   the root
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder inflateLayout(int layout, ViewGroup root){
    view= LayoutInflater.from(context).inflate(layout, root);
    return this;
  }

  /**
   * Show custom layout action bar builder.
   *
   * @param show the show
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder showCustomLayout(boolean show){
    supportActionBar.setDisplayShowCustomEnabled(show);
    if(view !=null)
      supportActionBar.setCustomView(view);
    return this;
  }

  /**
   * Show default home icon action bar builder.
   *
   * @param show the show
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder showDefaultHomeIcon(boolean show){
    supportActionBar.setDisplayHomeAsUpEnabled(show);
    return this;
  }

  /**
   * Show your home icon action bar builder.
   *
   * @param show the show
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder showYourHomeIcon(boolean show){
    supportActionBar.setDisplayShowHomeEnabled(show);
    return this;
  }

  /**
   * Set home button enabled action bar builder.
   *
   * @param enable the enable
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder setHomeButtonEnabled(boolean enable){
    supportActionBar.setHomeButtonEnabled(enable);
    return this;
  }

  /**
   * Show default home title action bar builder.
   *
   * @param show the show
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder showDefaultHomeTitle(boolean show){
    supportActionBar.setDisplayShowTitleEnabled(show);
    return this;
  }

  /**
   * Set custom title action bar builder.
   *
   * @param text the text
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder setCustomTitle(@Nullable String text){
    if(text!=null)
      supportActionBar.setTitle(text);
    return this;
  }

  /**
   * Set custom icon action bar builder.
   *
   * @param icon the icon
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder setCustomIcon(int icon){
    supportActionBar.setIcon(icon);
    return this;
  }

  /**
   * Set custom icon action bar builder.
   *
   * @param icon the icon
   * @return the action bar builder
   */
  @NonNull
  public ActionBarBuilder setCustomIcon(Drawable icon){
    supportActionBar.setIcon(icon);
    return this;
  }

}
