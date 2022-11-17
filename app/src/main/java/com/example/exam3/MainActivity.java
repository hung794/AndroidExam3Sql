package com.example.exam3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exam3.database.DbHelper;
import com.example.exam3.entity.ListProductAdapter;
import com.example.exam3.entity.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final DbHelper dbHelper = new DbHelper(this);
    private EditText name;
    private EditText price;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Product> list = dbHelper.getAllProduct();
        ListProductAdapter listProductAdapter = new ListProductAdapter(this, list);
        ListView listView = findViewById(R.id.listProduct);
        listView.setAdapter(listProductAdapter);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        add = findViewById(R.id.add);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                String regex = "-?\\d+(.\\d+)?";
                if(name.getText().toString().isEmpty() || price.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "Bạn phải nhập đầy đủ tên và giá sản phẩm", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0,0);
                    toast.show();
                }
                else if(!price.getText().toString().matches(regex)){
                    Toast toast = Toast.makeText(MainActivity.this, "Giá sản phẩm phải là số", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0,0);
                    toast.show();
                }
                else {
                    Product product = new Product(name.getText().toString(), Double.parseDouble(price.getText().toString()));
                    dbHelper.addProduct(product);
                    List<Product> list = dbHelper.getAllProduct();
                    ListProductAdapter listProductAdapter = new ListProductAdapter(this, list);
                    ListView listView = findViewById(R.id.listProduct);
                    listView.setAdapter(listProductAdapter);
                    Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_LONG).show();
                    name.setText(null);
                    price.setText(null);
                }
                break;
            default:
                break;
        }
    }
}