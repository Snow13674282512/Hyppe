package com.quyum.hyppe.ui.home.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.event.ReportFinishEvent;
import com.quyum.hyppe.ui.home.adapter.AddPicAdapter;
import com.quyum.hyppe.utils.GlideEngine;
import com.quyum.hyppe.utils.GridSpacingItemDecoration;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.MyDialog;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportNextActivity extends BaseActivity {
    @BindView(R.id.illegal_tv)
    TextView illegalTv;
    @BindView(R.id.num_tv)
    TextView numTv;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.hint_tv)
    TextView hintTv;
    AddPicAdapter adapter = new AddPicAdapter();
    ArrayList<Photo> selectList = new ArrayList<>();
    MyDialog myDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_next;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Report");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numTv.setText(contentEt.getText().length() + "/200");
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EasyPhotos.createAlbum(mContext, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                        .setCount(4)
                        .setSelectedPhotos(selectList)
                        .setOriginalMenu(true, true, null)
                        .start(101);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.remove(position);
                selectList.clear();
                selectList.addAll(adapter.getData());
                selectList.remove(selectList.size() - 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                selectList.clear();
                selectList.addAll(resultPhotos);
                Photo item = new Photo("", null, "", 0, 0, 0, 0, 0, "");
                resultPhotos.add(item);
                adapter.setNewData(resultPhotos);
                return;
            }
        } else if (RESULT_CANCELED == resultCode) {
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 10, false));
        adapter = new AddPicAdapter();
        recyclerView.setAdapter(adapter);
        Photo item = new Photo("", null, "", 0, 0, 0, 0, 0, "");
        List<Photo> list = new ArrayList<>();
        list.add(item);
        adapter.setNewData(list);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        illegalTv.setText(getIntent().getStringExtra("data"));
    }

    @OnClick(R.id.submit_bt)
    public void onViewClicked() {
        if (TextUtils.isEmpty(contentEt.getText().toString())) {
            ToastUtils.showToast("请输入内容");
            return;
        }
        myDialog = DialogBuilder.selectDialog(mContext, "Back to the home", "To report success", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().post(new ReportFinishEvent());
                finish();
            }
        });
        myDialog.show();
    }
}
