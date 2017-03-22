package com.example.slaby.android_3_remastered;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.BigDecimal;

import static com.example.slaby.android_3_remastered.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    TextView result0;
    TextView result1;
    TextView result2;
    TextView result3;
    Boolean isFinished = false;
    Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // FORCE ORIENTATION
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(activity_main);
        calculator = new Calculator();
        result0 = (TextView) findViewById(R.id.result0);
        result1 = (TextView) findViewById(R.id.result1);
        result2 = (TextView) findViewById(R.id.result2);
        result3 = (TextView) findViewById(R.id.result3);
        clearText();
    }

    public void clearText() {
        setText(result0, "");
        setText(result1, "");
        setText(result2, "");
        setText(result3, "");
    }

    private void setText(TextView view, String value) {
        view.setText(value);
    }

    public void onNumberClick(View view) {
        if (isFinished) {
            showError();
            return;
        }
        Button btn = (Button) view;
        String buttonText = (String) btn.getText();
        calculator.insertNumber(buttonText);
        updateResults();
    }

    public void updateResults() {
        clearText();
        if (calculator.number1 == null && calculator.operation == null && calculator.number0 != null) {
            if (calculator.doesNumber0HasComa) {
                setText(result3, calculator.number0.toString() + ".");
                return;
            }
            setText(result3, calculator.number0.toString());
            return;
        }
        if (calculator.number1 == null && calculator.operation != null && calculator.number0 != null) {
            setText(result2, calculator.number0.toString());
            setText(result3, calculator.operation);
            return;
        }
        if (calculator.number1 != null && calculator.operation != null && calculator.number0 != null) {

            setText(result1, calculator.number0.toString());
            setText(result2, calculator.operation);
            if (calculator.doesNumber1HasComa) {
                setText(result3, calculator.number1.toString() + ".");
            }else{
                setText(result3, calculator.number1.toString());
            }


            return;
        }
        if (calculator.number1 == null && calculator.operation == null && calculator.number0 == null) {
            clearText();
            return;
        }
    }

    public void updateResults(Number result) {
        setText(result0, calculator.number0.toString());
        setText(result1, calculator.operation);
        setText(result2, calculator.number1.toString());
        setText(result3, "= " + result.toString());
    }

    public void showError() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        String errorText = getResources().getString(R.string.invalid_operation);
        Toast toast = Toast.makeText(context, errorText, duration);
        toast.show();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
    }

    public void onOperationClick(View view) {
        if (isFinished) {
            showError();
            return;
        }
        if (calculator.number0 == null || (calculator.number0 != null && calculator.number1 != null)) {
            showError();
            return;
        }
        Button btn = (Button) view;
        String buttonText = (String) btn.getText();
        calculator.setOperation(buttonText);
        updateResults();
    }

    public void onEqual(View view) {
        if (isFinished) {
            showError();
            return;
        }
        if (calculator.number0 == null || calculator.operation == null || calculator.number1 == null) {
            showError();
            return;
        }
        if (calculator.number1.doubleValue() == 0 && calculator.operation.equals("/")) {
            String errorString = getResources().getString(R.string.invalid_operation);
            setText(result0, calculator.number0.toString());
            setText(result1, calculator.operation);
            setText(result2, calculator.number1.toString());
            setText(result3, errorString);
            isFinished = true;
            return;
        }
        Number result;
        try {
            result = calculator.computeOutput();
        } catch (ArithmeticException exception) {
            showError();
            return;
        }

        isFinished = true;
        updateResults(result);
    }

    public void onChangeSign(View view) {
        if (isFinished) {
            showError();
            return;
        }
        if (calculator.number0 != null && calculator.number1 == null && calculator.operation != null) {
            showError();
            return;
        } else if (calculator.number0 == null || calculator.number0 == null && calculator.number1 == null) {
            showError();
            return;
        }
        calculator.changeSign();
        updateResults();
    }

    public void onComa(View view) {

        System.out.println("ABDUL"+ calculator.number0 + "  " + calculator.number1);

        if (calculator.number0 == null) {
            showError();
            return;
        }
        if (calculator.number0 != null && !calculator.can0HasMoreComas && calculator.operation == null) {
            showError();
            return;
        }
        if (calculator.number0 != null && calculator.operation != null && calculator.number1 == null) {
            showError();
            return;
        }
        if (calculator.number0 != null && calculator.operation != null && calculator.number1!= null && !calculator.can1HasMoreComas) {
            showError();
            return;
        }
        if (isFinished) {
            showError();
            return;
        }
        calculator.addComa();
        updateResults();

    }

    public void onReset(View view) {
        clearText();
        calculator = new Calculator();
        isFinished = false;
    }

    public void onBackspace(View view) {
        if (calculator.number0 == null) {
            showError();
            return;
        }

        if (isFinished) {
            showError();
            return;
        }

        isFinished = false;
        calculator.backSpace();
        updateResults();
    }
}
