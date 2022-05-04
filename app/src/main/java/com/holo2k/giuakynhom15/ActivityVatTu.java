package com.holo2k.giuakynhom15;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.holo2k.giuakynhom15.adapter.KhoAdapter;
import com.holo2k.giuakynhom15.adapter.VatTuAdapter;
import com.holo2k.giuakynhom15.database.DBVatTu;
import com.holo2k.giuakynhom15.error.CheckError;
import com.holo2k.giuakynhom15.model.Kho;
import com.holo2k.giuakynhom15.model.VatTu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityVatTu extends AppCompatActivity {
    ListView lvDSVatTu;
    String uriAnhVT;
    Button btnThemVatTu;
    ImageView imgThoatVatTu, imgShowCamera, imgShowFolder, imgHinhVatTu;
    ArrayList<VatTu> vatTuArrayList = new ArrayList<>();
    VatTuAdapter vatTuAdapter;
    EditText editSearch;
    Button btnThemVatTuDialog;
    ImageView imgThoatThemVatTu;
    EditText editThemMaVatTu;
    EditText editThemTenVatTu;
    EditText editThemXuatXu;
    TextInputLayout txtInputThemMaVT, txtInputThemTenVatTu, txtInputThemXuatXu;
    Dialog dialog;
    public static int CODE_CAMERA = 1234;
    public static int CODE_FOLDER = 1134;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_tu);
        setControls();
        setEvents();
    }

    private void setEvents() {
        showDB();
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                showResultSearch(editSearch.getText().toString());
            }
        });
        imgThoatVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvDSVatTu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityVatTu.this, ActivityChiTietVatTu.class);
                Bundle bundle = new Bundle();
                bundle.putString("chitietvattumavt", vatTuArrayList.get(i).getMaVatTu());
                bundle.putString("chitietvattutenvt", vatTuArrayList.get(i).getTenVatTu());
                bundle.putString("chitietvattuxxvt", vatTuArrayList.get(i).getXuatXu());
                bundle.putString("chitietvattuhinhanhvt", vatTuArrayList.get(i).getUri());
//                intent.putExtra("chitietvattu", vatTuArrayList.get(i));
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });
        btnThemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemVatTu();
            }
        });
    }

    private void dialogThemVatTu() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_vat_tu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //tắt click ngoài là thoát'


        dialog.setCanceledOnTouchOutside(false);
        txtInputThemMaVT = dialog.findViewById(R.id.txtInputThemMaVT);
        txtInputThemTenVatTu = dialog.findViewById(R.id.txtInputThemTenVT);
        txtInputThemXuatXu = dialog.findViewById(R.id.txtInputThemXuatXu);
        btnThemVatTuDialog = dialog.findViewById(R.id.btnThemVatTuChiTiet);
        imgThoatThemVatTu = dialog.findViewById(R.id.imgThoatThemVatTu);
        editThemMaVatTu = dialog.findViewById(R.id.editThemMaVatTu);
        editThemTenVatTu = dialog.findViewById(R.id.editThemTenVatTu);
        editThemXuatXu = dialog.findViewById(R.id.editThemXuatXu);
        imgShowCamera = dialog.findViewById(R.id.img_show_camera);
        imgShowFolder = dialog.findViewById(R.id.img_show_folder);
        imgHinhVatTu = dialog.findViewById(R.id.img_hinh_vat_tu);
        editThemMaVatTu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editThemMaVatTu.getText().toString().length() > 6) {
                    txtInputThemMaVT.setError("Mã vật tư không quá 6 kí tự");
                } else {
                    txtInputThemMaVT.setError(null);
                }
            }
        });
        imgShowCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CODE_CAMERA);
            }
        });
        imgShowFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFileIntent.setType("*/*");
                // Only return URIs that can be opened with ContentResolver
                chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file");
                chooseFileIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                chooseFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(chooseFileIntent, CODE_FOLDER);
            }
        });
        btnThemVatTuDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckError.checkEmptyEditText(editThemMaVatTu, txtInputThemMaVT) &&
                        CheckError.checkEmptyEditText(editThemTenVatTu, txtInputThemTenVatTu) &&
                        CheckError.checkEmptyEditText(editThemXuatXu, txtInputThemXuatXu)
                ) {
                    VatTu vatTu = new VatTu();
                    vatTu.setMaVatTu(editThemMaVatTu.getText().toString().toUpperCase(Locale.ROOT));
                    vatTu.setXuatXu(editThemXuatXu.getText().toString());
                    vatTu.setTenVatTu(editThemTenVatTu.getText().toString());
                    vatTu.setUri(uriAnhVT);
                    MainActivity.dbVatTu.themVatTu(vatTu);
                    dialog.cancel();
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    Toast.makeText(ActivityVatTu.this, "Thêm vật tư thành công", Toast.LENGTH_LONG).show();
                    showDB();
                }
            }
        });

        imgThoatThemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void showResultSearch(String data) {
        vatTuArrayList = MainActivity.dbVatTu.searchVatTu(data);
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void showDB() {
        vatTuArrayList = MainActivity.dbVatTu.getAllVatTu();
        vatTuAdapter = new VatTuAdapter(this, R.layout.item_vattu, vatTuArrayList);
        lvDSVatTu.setAdapter(vatTuAdapter);
    }

    private void setControls() {
        lvDSVatTu = findViewById(R.id.lvDSVatTu);
        btnThemVatTu = findViewById(R.id.btnThemVatTu);
        imgThoatVatTu = findViewById(R.id.imgThoatVatTu);
        editSearch = findViewById(R.id.editSearch);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Boolean checkXoaChiTietVatTu = (Boolean) data.getSerializableExtra("xac_nhan_xoa");
            if (checkXoaChiTietVatTu) {
                Toast.makeText(this, "Xoá vật tư thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        try {
            Boolean checkSuaChiTietVatTu = (Boolean) data.getSerializableExtra("xac_nhan_sua");
            if (checkSuaChiTietVatTu) {
                Toast.makeText(this, "Sửa vật tư thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        if (requestCode == 2) {
            if (resultCode == 0 || resultCode == 1 || resultCode == 2) {
                showDB();
            }
        }

        if (requestCode == CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgHinhVatTu.setImageBitmap(bitmap);
                    dialog.show();
                } else {
                    dialog.show();
                }

            } else {
                dialog.show();
            }
        }
        if (requestCode == CODE_FOLDER) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uriAnhVT = new StringBuilder().append("/storage/emulated/0/").append(data.getData().getPath().substring("/document/primary:".length())).toString();

                    System.out.println("\n\n\n\n" + uriAnhVT + "\n\n\n");
                    File file = new File(uriAnhVT);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imgHinhVatTu.setImageBitmap(bitmap);
                    dialog.show();
                } else {
                    dialog.show();

                }

            } else {
                dialog.show();
            }
        }

    }
}