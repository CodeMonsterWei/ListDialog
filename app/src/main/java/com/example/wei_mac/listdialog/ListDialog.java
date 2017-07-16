package com.example.wei_mac.listdialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 模擬 Dialog 的方式在內部增加一個 ListView
 * ListView 的 BaseAdapter 的 View 則選用 TextView
 *
 * 主要功能：
 *  多選一型 Dialog
 *  能因應選項數量自動增加 Item
 *  選擇點擊 灰幕 時是否退出畫面
 *
 * 畫面堆疊：
 *  灰幕
 *      DialogView
 *          TextView - Title
 *          TextView - Content
 *          ListView
 *
 * Method 介紹
 *  顯示類型：
 *      顯示Title文字：          setTitleText(String text)
 *                                  當沒有使用時,則不會顯示
 *      顯示Content文字：        setContentText(String text)
 *                                  當沒有使用時,則不會顯示
 *      顯示List文字：           setItemContent(String[] staArr)
 *                              setItemContent(ArrayList<String> arrayList)
 *                                  當沒有使用時,則不會顯示
 *  修改類型：
 *      修改Dialog背景：         setDialogBackground(int backgroundID)
 *      修改Dialog寬高：         setDialogSize(int width, int height)
 *                                  預設為 寬高的 5 分之 3
 *                                  輸入 0 為預設值
 *      修改Title文字大小：       setTitleTextSize(float size)
 *                                  預設為 30
 *      修改Content文字大小：     setContentTextSize(float size)
 *                                  預設為 25
 *      修改Item背景：           setItemBackground(int backgroundID)
 *      修改Item背景顏色：        setItemBackgroundColor(int a, int r ,int g, int b)
 *                              setItemBackgroundColor(int ColorID)
 *                                  當使用 setItemBackground() 則此兩個 method 無效
 *      修改ItemTextSize大小：   setItemTextSize(float size)
 *                                  預設為 20
 *  事件類型：
 *      點擊Item事件：           setOnItemClickListener(OnItemClickListener listener)
 *      取消背景退出事件：        cancelTheBackgroundToExit()
 *      移除Dialog事件：         dismiss()
 */
public class ListDialog{

    private Activity activity;
    private LinearLayout backgroundView,dialogView;
    private TextView titleTextView, contentTextView;
    private ListView listView;
    private String[] itemArray;
    private BaseAdapter baseAdapter;
    private OnItemClickListener onItemClickListener;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private int viewWidth, viewHeight;

    private float ItemTextSize = 20;

    public ListDialog(Activity activity){
        initObject(activity);
        initLayout();
    }

    public ListDialog(Activity activity, @Nullable String title, @Nullable String content, @Nullable String[] itemContent){
        initObject(activity);
        initLayout();
        if (title != null) setTitleText(title);
        if (content != null) setContentText(content);
        if (itemContent != null) setItemContent(itemContent);
    }

    public ListDialog(Activity activity, @Nullable String title, @Nullable String content, @Nullable ArrayList<String> itemContent){
        initObject(activity);
        initLayout();
        if (title != null) setTitleText(title);
        if (content != null) setContentText(content);
        if (itemContent != null) setItemContent(itemContent);
    }

    private void initObject(Activity activity) {
        this.activity = activity;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        viewWidth = (displayMetrics.widthPixels / 5) * 3;
        viewHeight = (displayMetrics.heightPixels / 5) * 3;
        backgroundView = new LinearLayout(activity);
        dialogView = new LinearLayout(activity);
        titleTextView = new TextView(activity);
        contentTextView = new TextView(activity);
        listView = new ListView(activity);
        itemArray = new String[]{};
    }

    private void initLayout() {
        backgroundView.setBackgroundColor(Color.argb(136,0,0,0));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        layoutParams.width = displayMetrics.widthPixels;
        layoutParams.height = displayMetrics.heightPixels;
        backgroundView.setOrientation(LinearLayout.VERTICAL);
        backgroundView.setGravity(Gravity.CENTER);
        backgroundView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundView.setVisibility(View.GONE);
            }
        });
        activity.addContentView(backgroundView,layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            backgroundView.setTranslationZ(100);
        }
        initDialogView(backgroundView);
        backgroundView.setVisibility(View.GONE);
    }

    private void initDialogView(LinearLayout view) {
        dialogView.setBackgroundColor(Color.argb(255,255,255,255));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                viewWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        dialogView.setPadding(32,32,32,32);
        dialogView.setOrientation(LinearLayout.VERTICAL);
        dialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        view.addView(dialogView,layoutParams);

        initTitleTextView(dialogView);
        initContextTextView(dialogView);
        initListView(dialogView);
        changeHeight();
    }

    private void initTitleTextView(LinearLayout view) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setTextSize(30);
        titleTextView.setPadding(0,16,0,8);
        view.addView(titleTextView,layoutParams);
        titleTextView.setVisibility(View.GONE);
    }

    private void initContextTextView(LinearLayout view) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        contentTextView.setGravity(Gravity.CENTER);
        contentTextView.setTextSize(25);
        contentTextView.setPadding(0,8,0,16);
        view.addView(contentTextView,layoutParams);
        contentTextView.setVisibility(View.GONE);
    }

    private void initListView(LinearLayout view) {
        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return itemArray.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(activity);
                textView.setTextSize(ItemTextSize);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                textView.setPadding(16,32,16,32);
                textView.setGravity(Gravity.CENTER);
                textView.setText(itemArray[position]);
                return textView;
            }
        };
        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent,view,position,id);
                    backgroundView.setVisibility(View.GONE);
                }
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        view.addView(listView,layoutParams);
    }

    private void changeHeight(){
        dialogView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    dialogView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    dialogView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (dialogView.getHeight() > viewHeight){
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            viewWidth,
                            viewHeight
                    );
                    dialogView.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public ListDialog setDialogSize(int width, int height){
        if (width > 0) {
            viewWidth = width;
        }else {
            viewWidth = (displayMetrics.widthPixels / 5) * 3;
        }
        if (height > 0) {
            viewHeight = height;
        }else {
            viewHeight = (displayMetrics.heightPixels / 5) * 3;
        }
        changeHeight();
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public ListDialog setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    public ListDialog setTitleText(String text){
        titleTextView.setText(text);
        titleTextView.setVisibility(View.VISIBLE);
        return this;
    }

    public ListDialog setTitleTextSize(float default30){
        titleTextView.setTextSize(default30);
        return this;
    }

    public ListDialog setContentText(String text){
        contentTextView.setText(text);
        contentTextView.setVisibility(View.VISIBLE);
        return this;
    }

    public ListDialog setContentTextSize(float default25){
        contentTextView.setTextSize(default25);
        return this;
    }

    public ListDialog setItemContent(String[] strArr){
        itemArray = strArr;
        baseAdapter.notifyDataSetChanged();
        changeHeight();
        return this;
    }

    public ListDialog setItemContent(ArrayList<String> arrayList){
        itemArray = new String[arrayList.size()];
        for (int i = 0 ; i < arrayList.size() ; i++) itemArray[i] = arrayList.get(i);
        baseAdapter.notifyDataSetChanged();
        changeHeight();
        return this;
    }

    public ListDialog setItemTextSize(float default20){
        ItemTextSize = default20;
        baseAdapter.notifyDataSetChanged();
        changeHeight();
        return this;
    }

    public ListDialog setItemBackground(int backgroundID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setBackground(activity.getResources().getDrawable(backgroundID,null));
        }else {
            listView.setBackground(activity.getResources().getDrawable(backgroundID));
        }
        return this;
    }

    public ListDialog setItemBackgroundColor(int colorID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            listView.setBackgroundColor(activity.getResources().getColor(colorID,null));
        }else {
            listView.setBackgroundColor(activity.getResources().getColor(colorID));
        }
        return this;
    }

    public ListDialog setItemBackgroundColor(int a, int r, int g, int b){
        listView.setBackgroundColor(Color.argb(a,r,g,b));
        return this;
    }

    public ListDialog setDialogBackground(int backgroundID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogView.setBackground(activity.getResources().getDrawable(backgroundID,null));
        }else {
            dialogView.setBackground(activity.getResources().getDrawable(backgroundID));
        }
        return this;
    }

    public ListDialog cancelTheBackgroundToExit(){
        backgroundView.setOnClickListener(null);
        return this;
    }

    public void show(){
        backgroundView.setVisibility(View.VISIBLE);
    }

    public void dismiss(){
        ViewGroup viewGroup = (ViewGroup)(backgroundView.getParent());
        viewGroup.removeView(backgroundView);
    }
}