package br.edu.ufam.icomp.taprental.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.db.CustomerDAO;
import br.edu.ufam.icomp.taprental.db.EmployeeDAO;
import br.edu.ufam.icomp.taprental.model.Customer;
import br.edu.ufam.icomp.taprental.model.Employee;

public class EmployeeRegisterActivity extends AppCompatActivity {
    private Employee employee;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        Intent intent = getIntent();

        edtName = (EditText) findViewById(R.id.edtEmployeeName);
        Button btnConfirm = (Button) findViewById(R.id.btnEmployeeRegisterConfirm);

        if(intent.hasExtra("employeeObj")) {
            this.employee = (Employee) intent.getSerializableExtra("employeeObj");
            edtName.setText(this.employee.getName());
        }
        else this.employee = new Employee();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeRegisterActivity activity = EmployeeRegisterActivity.this;
                EmployeeDAO employeeDAO = new EmployeeDAO(activity);

                activity.employee.setName(activity.edtName.getText().toString());

                if(activity.employee.getId() != -1) {
                    employeeDAO.updateEmployee(activity.employee);
                }
                else employeeDAO.addEmployee(activity.employee);

                activity.setResult(RESULT_OK);
                activity.finish();
            }
        });
    }
}
