package com.example.kryptonote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KryptoNoteActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kryptonote_layout);
    }

    public void onEncrypt(View v)
    {
        try
        {
            String key = ((EditText) findViewById(R.id.key)).getText().toString();
            String note = ((EditText) findViewById(R.id.data)).getText().toString();

            Pattern notePattern = Pattern.compile("^[A-Z_\\s]+$");
            Matcher noteMatcher = notePattern.matcher(note);
            boolean noteIsMatch = noteMatcher.find();

            boolean keyIsMatch = key != null && !key.isEmpty();

            if (!keyIsMatch)
            {
                throw new RuntimeException("You have not entered a key");
            }
            else if (!noteIsMatch)
            {
                throw new RuntimeException("The text to encrypt/decrypt can only have UPPERCASE letters");
            }
            else
            {
                CipherModel model = new CipherModel(key);
                String encryptedNote = model.encrypt(note);
                ((EditText) findViewById(R.id.data)).setText(encryptedNote);

            }
        }
        catch (Exception e)
        {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            label.show();
        }

    }

    public void onDecrypt(View v)
    {
        try
        {
            String key = ((EditText) findViewById(R.id.key)).getText().toString();
            String note = ((EditText) findViewById(R.id.data)).getText().toString();

            Pattern notePattern = Pattern.compile("^[A-Z_\\s]+$");
            Matcher noteMatcher = notePattern.matcher(note);
            boolean noteIsMatch = noteMatcher.find();

            boolean keyIsMatch = key != null && !key.isEmpty();

            if (!keyIsMatch) {
                throw new RuntimeException("You have not entered a key");
            } else if (!noteIsMatch) {
                throw new RuntimeException("The text to encrypt/decrypt can only have UPPERCASE letters");
            } else {
                CipherModel model = new CipherModel(key);
                String decryptedNote = model.decrypt(note);
                ((EditText) findViewById(R.id.data)).setText(decryptedNote);
            }
        }
        catch (Exception e)
        {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            label.show();
        }
    }

    public void onSave(View v)
    {
        try
        {
            String name = ((EditText) findViewById(R.id.file)).getText().toString();

            Pattern filePattern = Pattern.compile("^[a-zA-Z_0-9]+$");
            Matcher fileMatcher = filePattern.matcher(name);
            boolean fileIsMatch = fileMatcher.find();

            if (!fileIsMatch)
            {
                throw new RuntimeException("File name can only have letters and numbers, and no spaces");
            }
            else
            {
                File dir = this.getFilesDir();
                File file = new File(dir, name);
                FileWriter fw = new FileWriter(file);
                fw.write(((EditText) findViewById(R.id.data)).getText().toString());
                fw.close();
                Toast confirmMsg = Toast.makeText(this, "Note Saved.", Toast.LENGTH_LONG);
                confirmMsg.show();
            }
        }
        catch (Exception e)
        {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            label.show();
        }
    }

    public void onLoad(View v)
    {
        try
        {
            String name = ((EditText) findViewById(R.id.file)).getText().toString();

            Pattern filePattern = Pattern.compile("^[a-zA-Z_0-9]+$");
            Matcher fileMatcher = filePattern.matcher(name);
            boolean fileIsMatch = fileMatcher.find();

            if (!fileIsMatch)
            {
                throw new RuntimeException("File name can only have letters and numbers, and no spaces");
            }
            else
            {
                File dir = this.getFilesDir();
                File file = new File(dir, name);
                FileReader fr = new FileReader(file);
                String show = "";
                for (int c = fr.read(); c != -1; c = fr.read())
                {
                    show += (char) c;
                }
                fr.close();
                ((EditText) findViewById(R.id.data)).setText(show);
            }
        }
        catch (Exception e)
        {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            label.show();
        }
    }
}
