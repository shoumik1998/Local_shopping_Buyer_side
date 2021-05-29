package com.example.local_shopping;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.HashMap;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details_Activity extends AppCompatActivity {
    String product_number;
    boolean login_status = false;
    Button add_cartBtn;
    Button cancel_btn, save_btn, order_btn;
    ApiInterface apiInterface;
    private ImageView imageView;
    private TextView Country_Text, District_Text, Sub_District_Text, Region_Text, Shop_Name_Text, Shop_Location_Text,
            Product_Name_text, Product_price_text;
    private ElegantNumberButton numberButton;
    private  String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

        Paper.init(Details_Activity.this);
        Paper.book().delete("login_status");


        imageView = findViewById(R.id.datils_image_id);
        Country_Text = findViewById(R.id.details_country_text_DI);
        District_Text = findViewById(R.id.details_district_text_DI);
        Sub_District_Text = findViewById(R.id.details_subdistrict_text_DI);
        Region_Text = findViewById(R.id.details_region_text_DI);
        Shop_Name_Text = findViewById(R.id.details_shopName_text_DI);
        Shop_Location_Text = findViewById(R.id.details_location_text_DI);
        Product_Name_text = findViewById(R.id.details_product_Name_text_DI);
        Product_price_text = findViewById(R.id.details_product_price_text_DI);
        numberButton = findViewById(R.id.elegant_btnID);
        add_cartBtn = findViewById(R.id.ad_cart_BtnID);

        product_id = getIntent().getStringExtra("product_id");
        Glide.with(this).load(getIntent().getStringExtra("img_path")).into(imageView);
        Country_Text.setText(MainActivity.getInstance().read_country());
        District_Text.setText(MainActivity.getInstance().read_district());
        Sub_District_Text.setText(MainActivity.getInstance().read_subdistrict());
        Region_Text.setText(MainActivity.getInstance().read_region());
        Shop_Location_Text.setText(getIntent().getStringExtra("Location"));
        Shop_Name_Text.setText(getIntent().getStringExtra("ShopName"));
        Product_Name_text.setText(getIntent().getStringExtra("Product_Name"));
        Product_price_text.setText(getIntent().getStringExtra("Price") + " " + getIntent().getStringExtra("currency"));





        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Details_Activity.this, "clicked....", Toast.LENGTH_SHORT).show();
            }
        });

        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Toast.makeText(Details_Activity.this, "" + newValue, Toast.LENGTH_SHORT).show();
            }
        });

        add_cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Paper.book().read("login_status") == null) {
                    onLogin_Dialog();
                } else if (Paper.book().read("login_status")) {
                    onOrder_info_Dialog();
                }

            }
        });


    }

    private void onSignUp_Dialog() {
        View view = LayoutInflater.from(Details_Activity.this).inflate(R.layout.signup_dialog, null);
        final EditText E_name = view.findViewById(R.id.name_signup_alertID);
        final EditText E_phn_gmail = view.findViewById(R.id.phn_gmail_signup_alertID);
        final EditText E_password = view.findViewById(R.id.password_signup_alertID);

        final AlertDialog signUp_dialog = new AlertDialog.Builder(Details_Activity.this)
                .setView(view).setPositiveButton("SignUp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final HashMap<String, String> signUp_info = new HashMap<>();

                        String name = E_name.getText().toString();
                        String phn_gmail = E_phn_gmail.getText().toString();
                        String password = E_password.getText().toString();
                        signUp_info.put("name", name);
                        signUp_info.put("phn_gmail", phn_gmail);
                        signUp_info.put("password", password);

                        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                        Call<SignUp_Model> call = apiInterface.signUp(signUp_info);

                        call.enqueue(new Callback<SignUp_Model>() {
                            @Override
                            public void onResponse(Call<SignUp_Model> call, Response<SignUp_Model> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(Details_Activity.this, "Hmm sign up" + response.body().getResponse(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    onSleep();
                                    onLogin_Dialog();
                                } else {
                                    Toast.makeText(Details_Activity.this, "nah hoi ni" + response.body().getResponse(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<SignUp_Model> call, Throwable t) {
                                Toast.makeText(Details_Activity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void onLogin_Dialog() {
        View view = LayoutInflater.from(Details_Activity.this).inflate(R.layout.login_dialog, null);
        final AlertDialog loginDialog = new AlertDialog.Builder(Details_Activity.this)
                .setView(view)
                .setPositiveButton("Log In", null)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

        final EditText phone_email = view.findViewById(R.id.phone_email_alertID);
        final EditText password = view.findViewById(R.id.password_alertID);


        Button signUp_positiveBtn = loginDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        signUp_positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Details_Activity.this, "" + phone_email.getText().toString() + " " + password.getText().toString(), Toast.LENGTH_SHORT).show();
                HashMap<String, String> login_map = new HashMap<>();
                login_map.put("phn_gmail", phone_email.getText().toString());
                login_map.put("password", password.getText().toString());




                apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<SignUp_Model> call = apiInterface.logoIn(login_map);

                call.enqueue(new Callback<SignUp_Model>() {
                    @Override
                    public void onResponse(Call<SignUp_Model> call, Response<SignUp_Model> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Details_Activity.this, "log in success", Toast.LENGTH_SHORT).show();
                            onSleep();
                            onOrder_info_Dialog();
                        } else {
                            Toast.makeText(Details_Activity.this, "Log in failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUp_Model> call, Throwable t) {
                        Toast.makeText(Details_Activity.this, "not responding", Toast.LENGTH_SHORT).show();

                    }
                });

                loginDialog.dismiss();
                login_status = true;
                Paper.book().write("login_status", login_status);
                Paper.book().write("phn_email", phone_email.getText().toString());
            }
        });

        view.findViewById(R.id.textView_login_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUp_Dialog();
                loginDialog.dismiss();
            }
        });

    }

    private void onOrder_info_Dialog() {

        View view = LayoutInflater.from(Details_Activity.this).inflate(R.layout.order_info_layout, null);
        final AlertDialog order_dialog = new AlertDialog.Builder(Details_Activity.this)
                .setView(view)
                .setPositiveButton(null, null)
                .setNegativeButton(null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

        final EditText order_info_name = view.findViewById(R.id.order_info_nameID);
        final EditText order_info_contact = view.findViewById(R.id.order_info_contactID);
        final EditText order_info_address = view.findViewById(R.id.order_info_addressID);

        view.findViewById(R.id.order_info_cancel_BtnID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_dialog.dismiss();
            }
        });

        view.findViewById(R.id.order_info_save_BtnID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Details_Activity.this, "saved information for next use ", Toast.LENGTH_SHORT).show();

            }

        });

        view.findViewById(R.id.order_info_order_BtnID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = order_info_name.getText().toString();
                String contact=order_info_contact.getText().toString();
                String address=order_info_address.getText().toString();

                HashMap<String,String> order_map=new HashMap<>();
                order_map.put("name", name);
                order_map.put("contact", contact);
                order_map.put("address", address);
                order_map.put("phn_email", Paper.book().read("phn_email").toString());
                order_map.put("product_number", numberButton.getNumber());
                order_map.put("product_name",Product_Name_text.getText().toString());
                order_map.put("product_price",Product_price_text.getText().toString());
                order_map.put("product_id",product_id);


                apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<String> call = apiInterface.onOrder(order_map);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Details_Activity.this, "Ordered successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Details_Activity.this, "order failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Details_Activity.this, "failed..."+t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                order_dialog.dismiss();
                Toast.makeText(Details_Activity.this, "" + order_info_name.getText().toString() + "    " + order_info_contact.getText().toString() + "  " + order_info_address.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onSleep() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    Toast.makeText(Details_Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
