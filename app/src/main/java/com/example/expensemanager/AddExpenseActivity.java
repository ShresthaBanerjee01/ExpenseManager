package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;

import com.example.expensemanager.databinding.ActivityAddExpenseBinding;
import com.example.expensemanager.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    @NonNull ActivityAddExpenseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.saveExpense){
            createExpense();
            return true;
        }
        return false;
    }

    private void createExpense() {

        String expenseId = UUID.randomUUID().toString();
        String amount = findViewById(R.id.amount).toString();
        String note = findViewById(R.id.note).toString();
        String category = findViewById(R.id.category).toString();
        String type;

        boolean incomeChecked = binding.incomeRadio.isChecked();

        if(incomeChecked) {
            type="Income";
        }else{
            type="Expense";
        }
        if(amount.trim().length()==0){
            binding.amount.setError("Empty");
            return;
        }
        ExpenseModel expenseModel=new ExpenseModel(expenseId,note,category,type,Long.parseLong(amount), Calendar.getInstance().getTimeInMillis());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(expenseModel);
        finish();

    }
}