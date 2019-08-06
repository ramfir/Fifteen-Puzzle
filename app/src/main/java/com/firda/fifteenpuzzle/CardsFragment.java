package com.firda.fifteenpuzzle;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

public class CardsFragment extends Fragment implements View.OnClickListener {
    public interface GetterImage {
        void getImage(Bitmap bitmap);
    }
    GetterImage mainAct;

    private Cards mCards;
    private TextView TimerTextView;
    private Switch SwitchHints;
    private int count = 0;
    private List<Button> listButtons;
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;
    private static final String TAG = "MainActivity";
    private boolean level;

    private boolean hints;
    private Image selectedImage;
    private int currentColor = Color.parseColor("#ff000000");

    private static final int GALLERY_REQUEST = 1;
    private Bitmap bMap;
    public static final String CARDS_KEY = "mCards";
    public static final String COUNT_KEY = "count";
    public static final String BITMAP_KEY = "drawables";
    public static final String COLOR_KEY = "color";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if (getActivity() instanceof MainActivity )
            mainAct = (GetterImage)context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mainAct != null) mainAct.getImage(selectedImage.getImage());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_cards, container, false);

        setHasOptionsMenu(true);

        TimerTextView = layout.findViewById(R.id.time);
        SwitchHints = layout.findViewById(R.id.switch_view);
        SwitchHints.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onSwitchClicked(isChecked);
            }
        });
        listButtons = new ArrayList<>();
        listButtons.add((Button)layout.findViewById(R.id.one));
        listButtons.add((Button)layout.findViewById(R.id.two));
        listButtons.add((Button)layout.findViewById(R.id.three));
        listButtons.add((Button)layout.findViewById(R.id.four));
        listButtons.add((Button)layout.findViewById(R.id.five));
        listButtons.add((Button)layout.findViewById(R.id.six));
        listButtons.add((Button)layout.findViewById(R.id.seven));
        listButtons.add((Button)layout.findViewById(R.id.eight));
        listButtons.add((Button)layout.findViewById(R.id.nine));
        listButtons.add((Button)layout.findViewById(R.id.ten));
        listButtons.add((Button)layout.findViewById(R.id.eleven));
        listButtons.add((Button)layout.findViewById(R.id.twelve));
        listButtons.add((Button)layout.findViewById(R.id.thirteen));
        listButtons.add((Button)layout.findViewById(R.id.fourteen));
        listButtons.add((Button)layout.findViewById(R.id.fifteen));
        listButtons.add((Button)layout.findViewById(R.id.sixteen));

        for (int i = 0; i < 16; i++)
            listButtons.get(i).setOnClickListener(this);
        startGame(false, savedInstanceState);
        return layout;
    }
    private void startGame(boolean hard, Bundle savedInstanceState) {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 0, 1000);
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setEnabled(true);
        }
        if (savedInstanceState == null) {
            count = 0;
            mCards = new Cards();
            if (selectedImage == null)
                selectedImage = new Image(BitmapFactory.decodeResource(getResources(), R.drawable.cat));
            //---------------------------mainAct.getImage(selectedImage.getImage());
            contribution(hard);
        } else {
            count = savedInstanceState.getInt(COUNT_KEY);
            mCards = (Cards) savedInstanceState.getSerializable(CARDS_KEY);
            selectedImage = new Image((Bitmap)savedInstanceState.getParcelable(BITMAP_KEY));
            mainAct.getImage(selectedImage.getImage());
            currentColor = savedInstanceState.getInt(COLOR_KEY);
            updateButtons();
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(CARDS_KEY, mCards);
        savedInstanceState.putParcelable(BITMAP_KEY, selectedImage.getImage());
        savedInstanceState.putInt(COUNT_KEY, count);
        savedInstanceState.putInt(COLOR_KEY, currentColor);
    }
    private  void finishGame() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setEnabled(false);
        }
    }
    private void contribution(boolean hard) {
        mCards.shuffleCards(hard);
        updateButtons();
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if (getActivity() == null) return;
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    int hours = count / 3600;
                    int minutes = (count% 3600) / 60;
                    int secs = count % 60;
                    String time = String.format(Locale.getDefault(),
                            "%02d:%02d:%02d", hours, minutes, secs);
                    TimerTextView.setText(time);
                    count += 1;
                }
            });
        }
    }
    private void check() {
        for (int i = 0; i < 16; i++) {
            if (Integer.parseInt(listButtons.get(i).getText().toString()) != i + 1)
                break;
            if (i == 15) {
                listButtons.get(i).setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "You have done it in " + TimerTextView.getText() + " seconds", Toast.LENGTH_LONG).show();
                finishGame();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button)v;
        int index = listButtons.indexOf(btn);
        mCards.moveCard(index);
        updateButtons();
        check();
    }

    private void updateButtons() {
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setText(mCards.get(i).toString());
            listButtons.get(i).setBackground(new BitmapDrawable(getResources(), selectedImage.getPieces()[mCards.get(i)-1]));/* drawables.get()*/;
            listButtons.get(i).setVisibility(View.VISIBLE);
            listButtons.get(i).setEnabled(true);
            listButtons.get(i).setTextColor(currentColor);
            if (!hints) listButtons.get(i).setTextSize(0);
            if (mCards.get(i) == 16)  {
                listButtons.get(i).setEnabled(false);
                listButtons.get(i).setTextSize(14);
                listButtons.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.norm_level:
                level = true;
                startGame(false, null);
                return true;
            case R.id.hard_level:
                level = true;
                startGame(true, null);
                return true;
            case R.id.up_image:
                uploadImage();
                return true;
            case R.id.color_picker:
                pickColor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void pickColor() {
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("ColorPicker Dialog");
        builder.setPreferenceName("MyColorPickerDialog");
        builder.setPositiveButton(getString(R.string.confirm), new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                currentColor = colorEnvelope.getColor();
                for (int i = 0; i < 16; i++) {
                    listButtons.get(i).setTextColor(colorEnvelope.getColor());
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bMap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
        if (bMap != null) selectedImage = new Image(bMap);
        if (mainAct != null) mainAct.getImage(selectedImage.getImage());
        Bitmap[] bitmapsArray = selectedImage.getPieces();
        for (int i = 0; i < 16; i++) {
            listButtons.get(i).setBackground(new BitmapDrawable(getResources(), bitmapsArray[i]));
        }
        count = 0;
        updateButtons();
    }

    public void onSwitchClicked(boolean isChcked) {
        boolean on = isChcked;
        if (on) {
            hints = true;
            changeHints(14);
        } else {
            hints = false;
            changeHints(0);
        }
    }
    private void changeHints(int size) {
        for (int i = 0; i < 16; i++) {
            if (!listButtons.get(i).getText().toString().equals("16")) listButtons.get(i).setTextSize(size);
        }
    }
}
